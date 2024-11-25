package camp.mok.repository;

import java.util.List;

import camp.mok.domain.CalendarVO;

public interface CalendarRepository {

	List<CalendarVO> getAllCalendars();
	
	void insert(CalendarVO vo);
	
	List<CalendarVO> get(Long dno);
}
