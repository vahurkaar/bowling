package ee.tieto.bowling.exception;

/**
 * Date: 19.12.13
 * Time: 10:44
 *
 * @author Vahur Kaar
 */
public class ScoreBoardIsFullException extends RuntimeException {

	public ScoreBoardIsFullException(String message) {
		super(message);
	}
}
