package ee.tieto.bowling;

import ee.tieto.bowling.exception.PlayerDoesNotExistException;
import ee.tieto.bowling.exception.ScoreBoardIsFullException;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 19.12.13
 * Time: 8:44
 *
 * @author Vahur Kaar
 */
public class ScoreBoard {

	private Map<Player, ScoreBoardRow> scoreBoardTableRows = new HashMap<Player, ScoreBoardRow>();

	public void addPlayer(Player player) {
		scoreBoardTableRows.put(player, new ScoreBoardRow());
	}

	public boolean insertPlayerScore(Player player, Integer score) {
		if (scoreBoardTableRows.containsKey(player)) {
			if (isTableFull()) {
				throw new ScoreBoardIsFullException("Scoreboard is totally full");
			}

			return scoreBoardTableRows.get(player).insertScore(score);
		} else {
			throw new PlayerDoesNotExistException("The scoreboard does not contain the given player");
		}
	}

	public ScoreBoardRow getPlayerScoreTableRow(Player player) {
		if (scoreBoardTableRows.containsKey(player)) {
			return scoreBoardTableRows.get(player);
		}
		return null;
	}

	public boolean isTableFull() {
		boolean isTableFull = true;

		for (ScoreBoardRow row : scoreBoardTableRows.values()) {
			if (!row.isFull()) {
				isTableFull = false;
				break;
			}
		}

		return isTableFull;
	}

	public void finalizeBoard() {
		for (ScoreBoardRow row : scoreBoardTableRows.values()) {
			row.finalizeScore();
		}
	}

	public Player getWinner() {
		Player winner = null;
		Integer maxScore = 0;

		for (Map.Entry<Player, ScoreBoardRow> rowEntry : scoreBoardTableRows.entrySet()) {
			ScoreBoardRow row = scoreBoardTableRows.get(rowEntry.getKey());
			if (row.getTotalScore() > maxScore) {
				maxScore = row.getTotalScore();
				winner = rowEntry.getKey();
			}
		}

		return winner;
	}

	public Map<Player, ScoreBoardRow> getScoreBoardTableRows() {
		return scoreBoardTableRows;
	}
}
