package camp.mok.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Criteria {

	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10); // 1페이지, 한 페이지당 게시물 10개
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public int getMaxRow() {
		return pageNum*amount;
	}
	
	public int getMinRow() {
		return (pageNum-1)*amount;
	}
	
	public String[] getTypes() {
		return type == null ? new String[] {} : type.split(""); // 타입이 null 일때 가져온다, 널이 아닐때 스플릿한다.
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.amount)
				.queryParam("type", this.type)
				.queryParam("keyword", this.keyword);
		return builder.toUriString();
	}
	
	private Category category;
}
