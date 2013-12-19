package ee.tieto.bowling.exception;

/**
 * Date: 19.12.13
 * Time: 10:26
 *
 * @author Vahur Kaar
 */
public class GameIsOverException extends RuntimeException {

	public GameIsOverException(String message) {
		super(message);
	}
}
