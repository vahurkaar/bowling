package ee.tieto.bowling;

import ee.tieto.bowling.exception.GameHasAlreadyStartedException;
import ee.tieto.bowling.exception.GameIsOverException;
import ee.tieto.bowling.exception.GameNotStartedException;
import ee.tieto.bowling.exception.NoPlayersException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Date: 19.12.13
 * Time: 8:11
 *
 * @author Vahur Kaar
 */
public class BowlingGame {

	private ScoreBoard scoreBoard;
	private List<Player> players;
	private Integer currentRound;
	private Player currentPlayer;
	private ListIterator<Player> currentPlayerIterator;
	private Player winner;

	public BowlingGame() {
		resetGame();
	}

	public void resetGame() {
		scoreBoard = new ScoreBoard();
		players = new ArrayList<Player>();
		currentPlayer = null;
		currentRound = null;
		currentPlayerIterator = null;
		winner = null;
	}

	public void addPlayer(Player player) {
		if (gameHasStarted()) {
			throw new GameHasAlreadyStartedException("You cannot add new players, when the game has already started");
		}

		players.add(player);
		scoreBoard.addPlayer(player);
	}

	public void startPlaying() {
		if (gameHasStarted()) {
			throw new GameHasAlreadyStartedException("You cannot start the game, once it has been already started");
		}

		startGame();
	}

	public void endPlaying() {
		if (isGameOver()) {
			return;
		}
		scoreBoard.finalizeBoard();
		winner = scoreBoard.getWinner();
	}

	public void addScoreToCurrentPlayer(Integer score) {
		if (gameHasStarted()) {
			if (isGameOver()) {
				throw new GameIsOverException("There are no more rounds to go");
			}

			boolean isNextPlayersTurn = scoreBoard.insertPlayerScore(currentPlayer, score);
			if (isNextPlayersTurn) {
				nextPlayer();
			}

		} else {
			throw new GameNotStartedException("Game has not been started yet!");
		}
	}

	public Integer getPlayerTotalScore(Player player) {
		ScoreBoardRow scoreBoardRow = scoreBoard.getPlayerScoreTableRow(player);

		if (scoreBoardRow != null) {
			return scoreBoardRow.getTotalScore();
		}

		return null;
	}

	public ScoreBoardRow getPlayerScoreTable(Player player) {
		return scoreBoard.getPlayerScoreTableRow(player);
	}

	public boolean isGameOver() {
		return winner != null;
	}

	private void startGame() {
		if (noPlayersHaveBeenAdded()) {
			throw new NoPlayersException("There are no players in the game!");
		}
		currentRound = 1;
		initializeCurrentPlayer();
	}

	private boolean noPlayersHaveBeenAdded() {
		return players.size() == 0;
	}

	private void nextPlayer() {
		if (currentPlayerIterator.hasNext()) {
			currentPlayer = currentPlayerIterator.next();
		} else {
			nextRound();
		}
	}

	private void nextRound() {
		if (scoreBoard.isTableFull()) {
			endPlaying();
			return;
		}
		currentRound++;
		initializeCurrentPlayer();
	}

	private void initializeCurrentPlayer() {
		currentPlayerIterator = players.listIterator();
		currentPlayer = currentPlayerIterator.next();
	}

	private boolean gameHasStarted() {
		return currentRound != null;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Integer getCurrentRound() {
		return currentRound;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getWinner() {
		return winner;
	}
}
