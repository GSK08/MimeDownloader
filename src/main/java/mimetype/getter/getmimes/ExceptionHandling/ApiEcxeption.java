package mimetype.getter.getmimes.ExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiEcxeption {
    String message;
    Throwable cause;
    Class exClass;
}
