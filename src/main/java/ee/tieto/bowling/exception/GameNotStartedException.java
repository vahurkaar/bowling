package ee.tieto.bowling.exception;

/**
 * Date: 19.12.13
 * Time: 8:50
 *
 * @author Vahur Kaar
 */
public class GameNotStartedException extends RuntimeException {

	public GameNotStartedException(String message) {
		super(message);
	}

}
