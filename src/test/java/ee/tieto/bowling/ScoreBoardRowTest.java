package ee.tieto.bowling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Date: 19.12.13
 * Time: 10:53
 *
 * @author Vahur Kaar
 */
public class ScoreBoardRowTest {

	private ScoreBoardRow scoreBoardRow;

	@Before
	public void setUp() throws Exception {
		scoreBoardRow = new ScoreBoardRow();
	}

	@Test
	public void threeStrikes() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);

		Assert.assertEquals(new Integer(10), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(60), scoreBoardRow.getTotalScore());
	}

	@Test
	public void twoStrikesAndASpare() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);

		Assert.assertEquals(new Integer(30), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(50), scoreBoardRow.getTotalScore());
	}

	@Test
	public void twoStrikesAndASplit() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(2);

		Assert.assertEquals(new Integer(25), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(39), scoreBoardRow.getTotalScore());
	}

	@Test
	public void strikeAndTwoSplits() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(6);
		scoreBoardRow.insertScore(4);

		Assert.assertEquals(new Integer(36), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(46), scoreBoardRow.getTotalScore());
	}

	@Test
	public void strikeAndASplitAndASpare() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(6);
		scoreBoardRow.insertScore(2);

		Assert.assertEquals(new Integer(36), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(44), scoreBoardRow.getTotalScore());
	}

	@Test
	public void strikeAndTwoSpares() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(2);
		scoreBoardRow.insertScore(6);
		scoreBoardRow.insertScore(4);

		Assert.assertEquals(new Integer(27), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(27), scoreBoardRow.getTotalScore());
	}

	@Test
	public void threeSpares() throws Exception {
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(8);
		scoreBoardRow.insertScore(2);
		scoreBoardRow.insertScore(4);
		scoreBoardRow.insertScore(6);

		Assert.assertEquals(new Integer(32), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(42), scoreBoardRow.getTotalScore());
	}

	@Test
	public void twoSparesAndOneSplit() throws Exception {
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(2);
		scoreBoardRow.insertScore(8);
		scoreBoardRow.insertScore(4);
		scoreBoardRow.insertScore(2);

		Assert.assertEquals(new Integer(28), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(28), scoreBoardRow.getTotalScore());
	}

	@Test
	public void spareAndTwoSplits() throws Exception {
		scoreBoardRow.insertScore(7);
		scoreBoardRow.insertScore(3);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(2);
		scoreBoardRow.insertScore(9);
		scoreBoardRow.insertScore(0);

		Assert.assertEquals(new Integer(31), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(31), scoreBoardRow.getTotalScore());
	}

	@Test
	public void rowIsFull() throws Exception {
		for (int i = 0; i < ScoreBoardRow.ROW_LIMIT; i++) {
			scoreBoardRow.getColumns().add(new ScoreBoardRowColumn());
		}

		Assert.assertTrue(scoreBoardRow.isFull());
	}

	@Test
	public void rowIsNotFull() throws Exception {
		for (int i = 0; i < 2; i++) {
			scoreBoardRow.getColumns().add(new ScoreBoardRowColumn());
		}

		Assert.assertFalse(scoreBoardRow.isFull());
	}
}
