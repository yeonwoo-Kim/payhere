package project.payhereprocess.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_book")
@Getter
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @Enumerated(EnumType.STRING)
    private isDeleted isDeleted;

    @ManyToOne
    @JoinColumn(name = "email")
    private User user;
}
