package camp.mok.repository;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import camp.mok.AppTest;
import camp.mok.domain.CalendarVO;
import lombok.extern.log4j.Log4j;

@Log4j
public class CalendarRepositoryTest extends AppTest{

	@Autowired
	CalendarRepository calendarRepository;
	
	@Test
	public void test() {
		
	}

}
