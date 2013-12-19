package ee.tieto.bowling;

import ee.tieto.bowling.exception.PlayerDoesNotExistException;
import ee.tieto.bowling.exception.ScoreBoardIsFullException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Date: 19.12.13
 * Time: 9:57
 *
 * @author Vahur Kaar
 */
public class ScoreBoardTest {
	
	private ScoreBoard scoreBoard;

	@Before
	public void setUp() throws Exception {
		scoreBoard = new ScoreBoard();
	}

	@Test
	public void addingPlayerCreatesEntryInScoreTable() throws Exception {
		Player player = new Player("Dummy");

		scoreBoard.addPlayer(player);

		Assert.assertTrue(scoreBoard.getScoreBoardTableRows().containsKey(player));
		Assert.assertNotNull(scoreBoard.getPlayerScoreTableRow(player));
	}

	@Test(expected = PlayerDoesNotExistException.class)
	public void insertingPlayerScoreThrowsExceptionWhenPlayerDoesNotExist() throws Exception {
		Player player = new Player("Dummy");
		scoreBoard.insertPlayerScore(player, 1);
	}

	@Test
	public void insertPlayerScoreDelegatesToSubRow() throws Exception {
		Player player = new Player("Dummy");
		scoreBoard.addPlayer(player);
		scoreBoard.insertPlayerScore(player, 10);
	}

	@Test(expected = ScoreBoardIsFullException.class)
	public void insertingPlayerScoreThrowsExceptionWhenTableIsFull() throws Exception {
		ScoreBoardRow row = mock(ScoreBoardRow.class);
		when(row.isFull()).thenReturn(true);
		Player player = new Player("Dummy");
		scoreBoard.getScoreBoardTableRows().put(player, row);

		scoreBoard.insertPlayerScore(player, 10);
	}

	@Test
	public void getPlayerScoreTableReturnsNullWhenPlayerDoesNotExist() throws Exception {
		Player player = new Player("Dummy");
		ScoreBoardRow row = scoreBoard.getPlayerScoreTableRow(player);
		Assert.assertNull(row);
	}

	@Test
	public void scoreTableIsFull() throws Exception {
		ScoreBoardRow row = mock(ScoreBoardRow.class);
		when(row.isFull()).thenReturn(true);
		scoreBoard.getScoreBoardTableRows().put(new Player("Dummy"), row);

		Assert.assertTrue(scoreBoard.isTableFull());
	}

	@Test
	public void scoreTableIsNotFull() throws Exception {
		ScoreBoardRow row = mock(ScoreBoardRow.class);
		when(row.isFull()).thenReturn(true);
		scoreBoard.getScoreBoardTableRows().put(new Player("Dummy1"), row);
		row = mock(ScoreBoardRow.class);
		when(row.isFull()).thenReturn(false);
		scoreBoard.getScoreBoardTableRows().put(new Player("Dummy2"), row);

		Assert.assertFalse(scoreBoard.isTableFull());
	}

	@Test
	public void findWinner() throws Exception {
		Player firstPlayer = new Player("Dummy 1");
		Player secondPlayer = new Player("Dummy 2");

		ScoreBoardRow firstRow = mock(ScoreBoardRow.class);
		when(firstRow.getTotalScore()).thenReturn(100);

		ScoreBoardRow secondRow = mock(ScoreBoardRow.class);
		when(secondRow.getTotalScore()).thenReturn(120);

		scoreBoard.getScoreBoardTableRows().put(firstPlayer, firstRow);
		scoreBoard.getScoreBoardTableRows().put(secondPlayer, secondRow);

		Assert.assertEquals(secondPlayer, scoreBoard.getWinner());
	}

	@Test
	public void finalizeBoardTest() throws Exception {
		Player firstPlayer = new Player("Dummy 1");
		Player secondPlayer = new Player("Dummy 2");

		ScoreBoardRow firstRow = mock(ScoreBoardRow.class);
		ScoreBoardRow secondRow = mock(ScoreBoardRow.class);

		scoreBoard.getScoreBoardTableRows().put(firstPlayer, firstRow);
		scoreBoard.getScoreBoardTableRows().put(secondPlayer, secondRow);

		scoreBoard.finalizeBoard();

		verify(firstRow).finalizeScore();
		verify(secondRow).finalizeScore();
	}
}
