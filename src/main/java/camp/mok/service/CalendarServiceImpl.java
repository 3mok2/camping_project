package camp.mok.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import camp.mok.domain.CalendarVO;
import camp.mok.repository.CalendarRepository;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	private CalendarRepository calendarRepository;
	
	@Override
	public void insert(CalendarVO vo) {
			calendarRepository.insert(vo);
	}

	@Override
	public List<CalendarVO> getReserve() {
		return calendarRepository.getAllCalendars();
	}

	@Override
	public List<CalendarVO> read(Long dno) {
		
		return calendarRepository.get(dno);
	}

}
