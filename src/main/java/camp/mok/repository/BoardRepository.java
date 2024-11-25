package camp.mok.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import camp.mok.domain.ArticleVO;
import camp.mok.domain.BoardVO;
import camp.mok.domain.Criteria;

public interface BoardRepository {

	List<ArticleVO> getList(Criteria criteria);
	
	void insert(ArticleVO vo);
	
	Integer insertSelectKey(ArticleVO vo);
	
	ArticleVO read(Long bno);
	
	int delete(Long bno);
	
	int update(ArticleVO board);
	
	int getTotalCount(Criteria criteria);
	
	void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	
	void updateLikeCnt(@Param("bno")Long bno, @Param("amount")int amount);
	
	void galleryImgUpdate(ArticleVO vo);
}
