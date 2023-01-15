package project.payhereprocess.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.payhereprocess.presentation.accountbook.dto.AccountUpdateRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private LocalDateTime useDate = LocalDateTime.now();

    @Column(name = "updateDate")
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name = "isDeleted")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public AccountBook(Long amount, String memo, User user) {
        this.amount = amount;
        this.memo = memo;
        this.user = user;
    }

    public AccountBook update(AccountUpdateRequestDto dto) {
        this.amount = dto.getAmount();
        this.memo = dto.getMemo();
        return this;
    }

    public AccountBook delete() {
        this.isDeleted = true;
        this.updateDate = LocalDateTime.now();
        return this;
    }

    public AccountBook restore() {
        this.isDeleted = false;
        this.updateDate = LocalDateTime.now();
        return this;
    }
}
