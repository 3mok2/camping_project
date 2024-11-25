package camp.mok.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CalendarVO {
	
	private int dno; // deck number

	private String reserveComment; // 적을 글자
	
	private String ymd;
	
	private int year; // 예약 년도
	
	private int month;
	
	private int day;
}
