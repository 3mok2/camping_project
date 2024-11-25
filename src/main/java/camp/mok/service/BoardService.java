package camp.mok.service;

import java.util.List;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.BoardAttachVO;
import camp.mok.domain.BoardVO;
import camp.mok.domain.Criteria;
import camp.mok.domain.LikeDTO;

public interface BoardService {

	BoardAttachVO getAttach(String uuid);
	
	int totalCount(Criteria criteria);
	
	void register(ArticleVO board);
	
	ArticleVO get(Long bno);
	
	boolean remove(Long bno);
	
	List<ArticleVO> getList(Criteria criteria);
	
	List<BoardAttachVO> getAttachList(Long bno);
	
	boolean hitLike(LikeDTO likeDTO);
	
	boolean isLike(LikeDTO likeDTO);

	boolean modify(ArticleVO board);
	
	void galleryImgUpdate(ArticleVO board);
}
