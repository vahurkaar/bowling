package ee.tieto.bowling.exception;

/**
 * Date: 19.12.13
 * Time: 9:19
 *
 * @author Vahur Kaar
 */
public class PlayerDoesNotExistException extends RuntimeException {

	public PlayerDoesNotExistException(String message) {
		super(message);
	}
}
