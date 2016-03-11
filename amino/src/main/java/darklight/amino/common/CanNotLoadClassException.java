package darklight.amino.common;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class CanNotLoadClassException extends AminoException {

    public CanNotLoadClassException(String message) {
        super(message);
    }

    public CanNotLoadClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
