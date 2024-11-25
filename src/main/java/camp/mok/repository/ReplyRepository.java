package camp.mok.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import camp.mok.domain.Criteria;
import camp.mok.domain.ReplyVO;

public interface ReplyRepository {

	int insert(ReplyVO vo);
	
	ReplyVO read(Long rno);
	
	int delete(Long rno);
	
	int update(ReplyVO vo);
	
	List<ReplyVO> getList (
		@Param("bno") Long bno,
		@Param("criteria") Criteria criteria);
	
	int getReplyCount(Long bno);
	
	void deleteAll(Long bno);
}
