
package camp.mok.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private String nickName;
	private String galleryImg;
	
	private String cateId;
	private String cateName;
	
	private int replyCnt;
	private int likeHit;
	
	private List<BoardAttachVO> attachList;

	@DateTimeFormat(pattern = "yyyy년MM월dd일 HH시mm분")
	private LocalDateTime regDate;
	@DateTimeFormat(pattern = "yyyy년MM월dd일 HH시mm분")
	private LocalDateTime updateDate;
}
