package pl.Tiguarces.TGbook.model.purchase.entity;

import lombok.*;
import pl.Tiguarces.TGbook.model.book.entity.Book;
import pl.Tiguarces.TGbook.model.user.entity.Account;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotNull(message = "Purchase date cannot be null")
    @NotBlank(message = "Purchase date cannot be blank")
    @Pattern.List({
            @Pattern(regexp = "0[1-9]-0[1-9]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "0[1-9]-1[1-2]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "[1-2]\\d-0[1-9]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "[1-2]\\d-1[1-2]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),

            @Pattern(regexp = "3[0-1]-0[1-9]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy"),
            @Pattern(regexp = "3[0-1]-1[1-2]-[1-2]\\d{3}", message = "Required pattern: dd-mm-yyyy")
    }) private String purchaseDate;

    @Enumerated(STRING)
    private PurchaseStatus status;

    @Enumerated(STRING)
    private DeliveryType delivery;

    @Enumerated(STRING)
    private PaymentType payment;

    @Min(value = 1, message = "Price should be greater than 1")
    private float price;

    @ManyToMany
    @JoinTable(
            name = "books_purchases",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    ) private List<Book> books;

    @OneToOne(cascade = { REFRESH, REMOVE }, orphanRemoval = true)
    private Account purchaser;
}
