package ee.tieto.bowling.exception;

/**
 * @author <a href="mailto:vahurkaar@gmail.com">Vahur Kaar</a>
 */
public class SquareIsFinalizedException extends RuntimeException {

    public SquareIsFinalizedException(String message) {
        super(message);
    }
}
