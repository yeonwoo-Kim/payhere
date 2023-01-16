package project.payhereprocess.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .status(e.getStatus())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }
}
