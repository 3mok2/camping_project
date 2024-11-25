package camp.mok.service;

import java.util.List;

import camp.mok.domain.CalendarVO;

public interface CalendarService {
	
	void insert(CalendarVO vo);
	
	List<CalendarVO> getReserve();
	
	List<CalendarVO> read(Long dno);
}
