package pl.Tiguarces.TGbook.model.book.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookType {
    SOFT_COVER("Softcover"), HARD_COVER("Hardcover");
    private final String type;
}
