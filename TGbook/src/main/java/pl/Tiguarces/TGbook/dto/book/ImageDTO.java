package pl.Tiguarces.TGbook.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageDTO {
    private String url;
    private boolean isMain;
}
