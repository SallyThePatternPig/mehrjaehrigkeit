package selina.praxisarbeit.mehrjaehrigkeit.validation;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private String message;

    public ValidationException(String message){
        this.message = message;
    }
}
