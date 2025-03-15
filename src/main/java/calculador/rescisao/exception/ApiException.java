
package calculador.rescisao.exception;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {

    private int statusCode;
    private String message;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }
}