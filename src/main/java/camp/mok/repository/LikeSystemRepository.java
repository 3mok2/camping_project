package camp.mok.repository;

import camp.mok.domain.LikeDTO;

public interface LikeSystemRepository {

	void insert(LikeDTO likeDTO);
	
	void delete(LikeDTO likeDTO);
	
	LikeDTO get(LikeDTO likeDTO);
}
