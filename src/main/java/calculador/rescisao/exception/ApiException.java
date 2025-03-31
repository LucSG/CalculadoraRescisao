
package calculador.rescisao.exception;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {

    private int statusCode;
    private String message;
    private String details;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiException(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }
}