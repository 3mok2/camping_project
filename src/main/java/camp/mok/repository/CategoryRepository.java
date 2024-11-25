package camp.mok.repository;

import java.util.List;

import camp.mok.domain.Category;

public interface CategoryRepository {

	Category readByCateId(String cateID);
	
	List<Category> selectAll();
}
