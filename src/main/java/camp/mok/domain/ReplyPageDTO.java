package camp.mok.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class ReplyPageDTO {
	private int replyCount;
	private List<ReplyVO> list;
}
