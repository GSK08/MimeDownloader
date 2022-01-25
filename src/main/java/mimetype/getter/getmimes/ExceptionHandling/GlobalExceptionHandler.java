package mimetype.getter.getmimes.ExceptionHandling;

import org.apache.tika.mime.MimeTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Object getEcxeption(Exception e){
        return new ApiEcxeption(e.getMessage(), e.getCause(), e.getClass());
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<Object> handleIOException(IOException e){
        return new ResponseEntity<>(getEcxeption(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MimeTypeException.class})
    public ResponseEntity<Object> handleMimeTypeException(MimeTypeException e){
        return new ResponseEntity<>(getEcxeption(e), HttpStatus.BAD_REQUEST);
    }

}
