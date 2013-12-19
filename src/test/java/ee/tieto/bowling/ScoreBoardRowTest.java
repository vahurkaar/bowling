package ee.tieto.bowling;

import ee.tieto.bowling.exception.ScoreBoardIsFullException;
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
    public void elevenStrikes() throws Exception {
        for (int i = 0; i < 11; i++) {
            scoreBoardRow.insertScore(10);
        }

        Assert.assertEquals(new Integer(300), scoreBoardRow.getTotalScore());
        scoreBoardRow.finalizeScore();
        Assert.assertEquals(new Integer(300), scoreBoardRow.getTotalScore());
    }

    @Test
	public void threeStrikes() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);

		Assert.assertEquals(new Integer(30), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(60), scoreBoardRow.getTotalScore());
	}

	@Test
	public void twoStrikesAndASpare() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);

		Assert.assertEquals(new Integer(45), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(55), scoreBoardRow.getTotalScore());
	}

	@Test
	public void twoStrikesAndAnOpen() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(2);

		Assert.assertEquals(new Integer(49), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(49), scoreBoardRow.getTotalScore());
	}

	@Test
	public void strikeAndTwoSpares() throws Exception {
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
	public void strikeAndASpareAndAnOpen() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(6);
		scoreBoardRow.insertScore(2);

		Assert.assertEquals(new Integer(44), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(44), scoreBoardRow.getTotalScore());
	}

	@Test
	public void strikeAndTwoOpens() throws Exception {
		scoreBoardRow.insertScore(10);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(2);
		scoreBoardRow.insertScore(6);
		scoreBoardRow.insertScore(4);

		Assert.assertEquals(new Integer(24), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(34), scoreBoardRow.getTotalScore());
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
	public void twoSparesAndOneOpen() throws Exception {
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(5);
		scoreBoardRow.insertScore(2);
		scoreBoardRow.insertScore(8);
		scoreBoardRow.insertScore(4);
		scoreBoardRow.insertScore(2);

		Assert.assertEquals(new Integer(32), scoreBoardRow.getTotalScore());
		scoreBoardRow.finalizeScore();
		Assert.assertEquals(new Integer(32), scoreBoardRow.getTotalScore());
	}

	@Test
	public void spareAndTwoOpens() throws Exception {
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
		for (int i = 0; i < ScoreBoardRow.COLUMN_LIMIT; i++) {
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

    @Test(expected = ScoreBoardIsFullException.class)
    public void cannotInsertMoreThanElevenCells() throws Exception {
        for (int i = 0; i < 13; i++) {
            scoreBoardRow.insertScore(10);
        }
    }
}
