package ee.tieto.bowling;

import org.junit.Assert;
import org.junit.Test;

/**
 * Date: 19.12.13
 * Time: 10:14
 *
 * @author Vahur Kaar
 */
public class PlayerTest {

	@Test
	public void playersAreEqual() throws Exception {
		Player player1 = new Player("Dummy");
		Player player2 = new Player("Dummy");

		Assert.assertEquals(player1, player2);
	}
}
