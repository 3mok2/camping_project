package camp.mok.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pagination {

	private Criteria criteria;
	private int startPage; // 시작 페이지
	private int endPage; // 끝 페이지
	private int tempEndPage; // 실제 끝 페이지
	private int totalCount; // 총 게시물
	private int displayPageNum=10;
	private boolean prev; // 이전페이지 여부
	private boolean next; // 다음페이지 여부
	
	public Pagination(Criteria criteria, int totalCount) {
		this.criteria = criteria;
		this.totalCount = totalCount;
		// 페이지 : 1페이지, 보이는개수:10 , 1/10=0.1 올림-> 1*보이는개수 = 10페이지 까지
		// 페이지 : 2페이지, 보이는개수:10 , 2/10=0.2 올림-> 1*보이는개수 = 10
		// 페이지 : 15페이지, 보이는개수:10 , 15/10=1.5 올림-> 2*보이는개수 = 20(endPage)
		endPage = (int)Math.ceil(criteria.getPageNum()/(double)displayPageNum)*displayPageNum;
		startPage = endPage-displayPageNum+1; // 20-10+1 = 11
		// 408개/10 = 40.8 올림 -> 41개 페이지
		tempEndPage = (int)Math.ceil(totalCount/(double)criteria.getAmount()); // 실제 끝 페이지
		
		prev = startPage != 1; // 1이 아닐때 이전 활성화
		next = endPage < tempEndPage; // 마지막 페이지를 제외하면 항상 tempEndPage가 더 크다.
		if(endPage>tempEndPage) {endPage=tempEndPage;} // endPage가 클 경우 tempEndPage까지 표시
	}
}
