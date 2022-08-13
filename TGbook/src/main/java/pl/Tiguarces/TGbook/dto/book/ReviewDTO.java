package pl.Tiguarces.TGbook.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDTO {
    private String content;
    private String createdDate;
    private String authorNickname;
}
