package SKKU.Dteam3.backend.error;

import lombok.Data;

@Data
public class ErrorCode {
    private final int code;
    private final String message;
}
