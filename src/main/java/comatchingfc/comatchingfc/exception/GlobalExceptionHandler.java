package comatchingfc.comatchingfc.exception;

import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Response<ResponseCode> handleCustomException(BusinessException ex) {
        return new Response<>(ex.getResponseCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<ResponseCode> handleValidException() {
        return new Response<>(ResponseCode.BAD_REQUEST);
    }
}
