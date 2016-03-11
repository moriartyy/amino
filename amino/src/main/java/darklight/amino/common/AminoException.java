package darklight.amino.common;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class AminoException extends RuntimeException {

    public AminoException(String message) {
        super(message);
    }

    public AminoException(String message, Throwable cause) {
        super(message, cause);
    }
}
