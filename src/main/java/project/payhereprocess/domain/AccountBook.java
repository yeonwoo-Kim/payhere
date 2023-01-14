package project.payhereprocess.domain;

import lombok.Builder;
import lombok.Getter;
import project.payhereprocess.presentation.accountbook.dto.AccountRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_book")
@Getter
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "memo")
    private String memo;

    @Column(name = "useDate")
    private LocalDateTime useDate;

    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "isDeleted")
    private String isDeleted;

    @ManyToOne
    @JoinColumn(name = "email")
    private User user;

    @Builder
    public AccountBook(Long amount, String memo, LocalDateTime useDate, LocalDateTime updateDate, String isDeleted, User user) {
        this.amount = amount;
        this.memo = memo;
        this.useDate = useDate;
        this.updateDate = updateDate;
        this.isDeleted = isDeleted;
    }

}
