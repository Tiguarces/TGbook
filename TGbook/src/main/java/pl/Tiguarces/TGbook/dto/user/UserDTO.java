package pl.Tiguarces.TGbook.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private String username;
    private boolean enabled;
    private String nickname;
    private String createdDate;
    private String email;
    private Review[] reviews;
    private Purchase[] purchases;

    @Getter
    @Setter
    @Builder
    public static class Review {
        private String content;
        private String createdDate;
        private String bookName;
    }

    @Getter
    @Setter
    @Builder
    public static class Purchase {
        private String purchaseDate;
        private String status;
        private String deliveryType;
        private String paymentType;
        private float price;
        private String[] bookNames;
    }
}
