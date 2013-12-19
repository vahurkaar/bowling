package ee.tieto.bowling;

import ee.tieto.bowling.exception.GameHasAlreadyStartedException;
import ee.tieto.bowling.exception.GameIsOverException;
import ee.tieto.bowling.exception.GameNotStartedException;
import ee.tieto.bowling.exception.NoPlayersException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Date: 19.12.13
 * Time: 8:11
 *
 * @author Vahur Kaar
 */
public class BowlingGameTest {

	@InjectMocks
	private BowlingGame bowlingGame = new BowlingGame();

	@Mock
	public ScoreBoard scoreBoard;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void resetGameTest() throws Exception {
		bowlingGame.addPlayer(new Player("Dummy"));
		bowlingGame.startPlaying();
		bowlingGame.resetGame();

		Assert.assertTrue(bowlingGame.getPlayers().isEmpty());
		Assert.assertNull(bowlingGame.getCurrentRound());
		Assert.assertNull(bowlingGame.getCurrentPlayer());
		Assert.assertFalse(bowlingGame.isGameOver());
	}

	@Test
	public void addingSinglePlayer() throws Exception {
		Player player = new Player("Dummy player");

		bowlingGame.addPlayer(player);

		List<Player> bowlingGamePlayers = bowlingGame.getPlayers();
		Assert.assertEquals(player, bowlingGamePlayers.get(0));
		verify(scoreBoard).addPlayer(player);
	}

	@Test
	public void addingMultiplePlayers() throws Exception {
		Player firstPlayer = new Player("Dummy player1");
		Player secondPlayer = new Player("Dummy player2");

		bowlingGame.addPlayer(firstPlayer);
		bowlingGame.addPlayer(secondPlayer);

		List<Player> bowlingGamePlayer = bowlingGame.getPlayers();
		Assert.assertEquals(firstPlayer, bowlingGamePlayer.get(0));
		Assert.assertEquals(secondPlayer, bowlingGamePlayer.get(1));
		verify(scoreBoard).addPlayer(firstPlayer);
		verify(scoreBoard).addPlayer(secondPlayer);
	}

	@Test(expected = GameHasAlreadyStartedException.class)
	public void addingPlayerIsOnlyAllowedBeforeTheGameStarts() throws Exception {
		Player firstPlayer = new Player("Dummy player1");
		Player secondPlayer = new Player("Dummy player2");

		bowlingGame.addPlayer(firstPlayer);
		bowlingGame.startPlaying();
		bowlingGame.addPlayer(secondPlayer);
		verify(scoreBoard, never()).addPlayer(firstPlayer);
		verify(scoreBoard, never()).addPlayer(secondPlayer);
	}

	@Test(expected = NoPlayersException.class)
	public void gameCanBeStartedOnlyWhenThereArePlayers() throws Exception {
		bowlingGame.startPlaying();
	}

	@Test(expected = GameHasAlreadyStartedException.class)
	public void gameCanBeStartedOnlyOnce() throws Exception {
		Player player = new Player("Dummy");
		bowlingGame.addPlayer(player);

		bowlingGame.startPlaying();
		bowlingGame.startPlaying();
	}

	@Test(expected = GameNotStartedException.class)
	public void playerScoreCanNotBeAddedWhenGameHasNotStartedYet() throws Exception {
		Player player = new Player("Dummy");
		bowlingGame.addPlayer(player);
		bowlingGame.addScoreToCurrentPlayer(10);
	}

	@Test
	public void addPlayerScoreTurnGoesToNextPlayer() throws Exception {
		Player firstPlayer = new Player("Dummy player1");
		Player secondPlayer = new Player("Dummy player2");
		bowlingGame.addPlayer(firstPlayer);
		bowlingGame.addPlayer(secondPlayer);
		bowlingGame.startPlaying();
		when(scoreBoard.insertPlayerScore(firstPlayer, 10)).thenReturn(true);

		bowlingGame.addScoreToCurrentPlayer(10);

		verify(scoreBoard).insertPlayerScore(firstPlayer, 10);
		Assert.assertEquals(bowlingGame.getCurrentPlayer(), secondPlayer);
	}

	@Test
	public void addPlayerScoreTurnStaysWithTheSamePlayer() throws Exception {
		Player firstPlayer = new Player("Dummy player1");
		Player secondPlayer = new Player("Dummy player2");
		bowlingGame.addPlayer(firstPlayer);
		bowlingGame.addPlayer(secondPlayer);
		bowlingGame.startPlaying();
		when(scoreBoard.insertPlayerScore(firstPlayer, 5)).thenReturn(false);

		bowlingGame.addScoreToCurrentPlayer(5);

		verify(scoreBoard).insertPlayerScore(firstPlayer, 5);
		Assert.assertEquals(bowlingGame.getCurrentPlayer(), firstPlayer);
	}

	@Test
	public void addPlayerScoreGoesToNextRoundWhenAllPlayersHaveMadeTheirTurns() throws Exception {
		Player player = new Player("Dummy");
		bowlingGame.addPlayer(player);
		bowlingGame.startPlaying();
		when(scoreBoard.insertPlayerScore(player, 5)).thenReturn(true);

		bowlingGame.addScoreToCurrentPlayer(5);

		Assert.assertEquals(bowlingGame.getCurrentPlayer(), player);
		Assert.assertEquals(new Integer(2), bowlingGame.getCurrentRound());
	}

	@Test
	public void askingPlayersCurrentScoreDelegatesToScoreBoardRowTotalScore() throws Exception {
		Player firstPlayer = new Player("Dummy player1");
		Player secondPlayer = new Player("Dummy player2");
		bowlingGame.addPlayer(firstPlayer);
		bowlingGame.addPlayer(secondPlayer);
		bowlingGame.startPlaying();
		ScoreBoardRow scoreBoardRowMock = mock(ScoreBoardRow.class);
		when(scoreBoard.getPlayerScoreTableRow(firstPlayer)).thenReturn(scoreBoardRowMock);

		bowlingGame.getPlayerTotalScore(firstPlayer);
		verify(scoreBoard).getPlayerScoreTableRow(firstPlayer);
		verify(scoreBoardRowMock).getTotalScore();
	}

	@Test
	public void askingPlayersCurrentScoreReturnsNullWhenPlayerIsNotOnScoreboard() throws Exception {
		Player firstPlayer = new Player("Dummy");
		Player secondPlayer = new Player("Dummy2");
		bowlingGame.addPlayer(firstPlayer);
		bowlingGame.startPlaying();

		Integer score = bowlingGame.getPlayerTotalScore(secondPlayer);
		Assert.assertNull(score);
	}

	@Test
	public void askingPlayersScoreTable() throws Exception {
		Player firstPlayer = new Player("Dummy player1");
		Player secondPlayer = new Player("Dummy player2");
		bowlingGame.addPlayer(firstPlayer);
		bowlingGame.addPlayer(secondPlayer);
		bowlingGame.startPlaying();

		bowlingGame.getPlayerScoreTable(firstPlayer);
		verify(scoreBoard).getPlayerScoreTableRow(firstPlayer);
	}

	@Test
	public void gameOverTest() throws Exception {
		Player player = new Player("Dummy");
		bowlingGame.addPlayer(player);
		bowlingGame.startPlaying();
		when(scoreBoard.insertPlayerScore(player, 10)).thenReturn(true);
		when(scoreBoard.isTableFull()).thenReturn(true);
		when(scoreBoard.getWinner()).thenReturn(player);

		bowlingGame.addScoreToCurrentPlayer(10);

		Assert.assertTrue(bowlingGame.isGameOver());
		Assert.assertNotNull(bowlingGame.getWinner());
	}

	@Test(expected = GameIsOverException.class)
	public void scoreCannotBeAddedWhenGameIsOver() throws Exception {
		Player player = new Player("Dummy");
		bowlingGame.addPlayer(player);
		bowlingGame.startPlaying();
		when(scoreBoard.insertPlayerScore(player, 10)).thenReturn(true);
		when(scoreBoard.isTableFull()).thenReturn(true);
		when(scoreBoard.getWinner()).thenReturn(player);

		bowlingGame.addScoreToCurrentPlayer(10);
		bowlingGame.addScoreToCurrentPlayer(10);
	}
}
