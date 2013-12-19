package ee.tieto.bowling.exception;

/**
 * Date: 19.12.13
 * Time: 9:08
 *
 * @author Vahur Kaar
 */
public class NoPlayersException extends RuntimeException {

	public NoPlayersException(String message) {
		super(message);
	}
}
