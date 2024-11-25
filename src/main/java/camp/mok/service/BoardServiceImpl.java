package camp.mok.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.BoardAttachVO;
import camp.mok.domain.BoardVO;
import camp.mok.domain.Criteria;
import camp.mok.domain.LikeDTO;
import camp.mok.repository.BoardAttachRepository;
import camp.mok.repository.BoardRepository;
import camp.mok.repository.LikeSystemRepository;
import camp.mok.repository.ReplyRepository;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private BoardAttachRepository boardAttachRepository;
	
	@Autowired
	private LikeSystemRepository likeSystemRepository;
	
	private static final List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");
	
	@Transactional
	@Override
	public void register(ArticleVO board) {
		Integer key = boardRepository.insertSelectKey(board);
		log.info(key);
		// 첨부파일이 있을 대
		if(board.getAttachList()!=null && !board.getAttachList().isEmpty()) { // null이 아니거나 비어있지 않을 때
			board.getAttachList().forEach(attachFile->{
				attachFile.setBno(board.getBno());
				boardAttachRepository.insert(attachFile);
			});
		}
	}

	@Override
	public ArticleVO get(Long bno) {
		return boardRepository.read(bno);
	}

	@Transactional
	@Override
	public boolean modify(ArticleVO board) {
		List<BoardAttachVO> attachList = board.getAttachList();
		
		if(attachList!=null) {
			// 삭제 
			List<BoardAttachVO> delList = attachList.stream()
					.filter(attach -> attach.getBno()!=null).collect(Collectors.toList());
			deleteFiles(delList);
			delList.forEach(vo->{
				boardAttachRepository.delete(vo.getUuid());
			});
			
			// 새로 추가 
			attachList.stream()
			.filter(attach -> attach.getBno()==null).forEach(vo->{
				vo.setBno(board.getBno());
				boardAttachRepository.insert(vo);
			});
		}
		
		boardRepository.update(board);
		
		List<BoardAttachVO> selectByBno = boardAttachRepository.selectByBno(board.getBno());
	    String galleryImg = "";
	    if (selectByBno != null && !selectByBno.isEmpty()) {
	        for (BoardAttachVO attach : selectByBno) {
	            if (isImageFile(attach.getFileName())) {
	                String fileName = attach.getFileName();
	                String uploadPath = attach.getUploadPath();
	                String uuid = attach.getUuid();
	                galleryImg = uploadPath + "/" + uuid + "_" + fileName;
	                break;
	            }
	        }
	    }
	    // 게시물 객체에 업데이트된 이미지 경로 설정
	    board.setGalleryImg(galleryImg);
	    // 게시물 업데이트 이후에 실행되는 작업
	    boardRepository.galleryImgUpdate(board);
		log.info(selectByBno);
		
		return boardRepository.update(board) == 1;
	}


	private boolean isImageFile(String fileName) {
		String extension = FilenameUtils.getExtension(fileName);
        return imageExtensions.contains(extension.toLowerCase());	}

	@Transactional
	@Override
	public boolean remove(Long bno) {
		List<BoardAttachVO> attachList = getAttachList(bno);
		int replyCount = replyRepository.getReplyCount(bno);
		if(replyCount!=0) {
			replyRepository.deleteAll(bno);
		}
		if(attachList!=null) {
			deleteFiles(attachList);
			boardAttachRepository.deleteAll(bno);
		}
		return boardRepository.delete(bno) == 1;
	}

	

	@Override
	public List<ArticleVO> getList(Criteria criteria) {
		return boardRepository.getList(criteria);
	}

	@Override
	public int totalCount(Criteria criteria) {
		return boardRepository.getTotalCount(criteria);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return boardAttachRepository.selectByBno(bno);
	}

	@Override
	public BoardAttachVO getAttach(String uuid) {
		return boardAttachRepository.selectByUuid(uuid);
	}
	
	private void deleteFiles(List<BoardAttachVO> delList) {
		delList.forEach(vo->{
			File file = new File("C:/storage/" + vo.getUploadPath(),vo.getUuid() + "_" + vo.getFileName());
			file.delete();
			if(vo.isFileType()) {
				file = new File("C:/storage/" + vo.getUploadPath(),"s_" + vo.getUuid() + "_" + vo.getFileName());
				file.delete();
			}
		});
	}

	@Transactional
	@Override
	public boolean hitLike(LikeDTO likeDTO) {
		LikeDTO result = likeSystemRepository.get(likeDTO);
		if(result==null) { 			// 추천
			likeSystemRepository.insert(likeDTO);
			boardRepository.updateLikeCnt(likeDTO.getBno(), 1);
			return true;
		} else { // 추천 취소
			likeSystemRepository.delete(likeDTO);
			boardRepository.updateLikeCnt(likeDTO.getBno(), -1);
			return false;
		}
	}

	@Override
	public boolean isLike(LikeDTO likeDTO) {
		return likeSystemRepository.get(likeDTO) != null;
	}

	@Override
	public void galleryImgUpdate(ArticleVO board) {
		boardRepository.galleryImgUpdate(board);
	}

}
