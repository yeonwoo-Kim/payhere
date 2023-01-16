package project.payhereprocess.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Account
    NOT_AUTHORITY(400, "A001", "리소스에 대한 접근권한이 없습니다."), IS_DELETED(400, "A002", "삭제된 내역입니다.")
    // User
    , DUPLICATION(400, "U001", "이미 사용중인 이메일입니다."), USER_NOT_FOUND(400, "U002", "존재하지 않는 사용자입니다.");

    private int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}