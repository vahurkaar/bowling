package ee.tieto.bowling.exception;

/**
 * Date: 19.12.13
 * Time: 8:34
 *
 * @author Vahur Kaar
 */
public class GameHasAlreadyStartedException extends RuntimeException {

	public GameHasAlreadyStartedException(String message) {
		super(message);
	}
}
