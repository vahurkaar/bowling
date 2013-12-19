package ee.tieto.bowling;

import java.util.List;

/**
 * Date: 19.12.13
 * Time: 20:16
 *
 * @author Vahur Kaar
 */
public class ScoreBoardRowFinalColumn extends ScoreBoardRowColumn {

	private static final Integer EXTRA_NUMBER_OF_ROWS = 3;

	public ScoreBoardRowFinalColumn() {
		super(EXTRA_NUMBER_OF_ROWS);
	}

	@Override
	protected boolean isColumnStateFinal() {
		return hasTwoStrikes() || hasSpare() && isFull() || hasOpen();
	}

	@Override
	public void calculateTotalScore(int index, List<ScoreBoardRowColumn> columns, boolean alwaysAssignScore) {
		if (hasTwoStrikes() || hasStrikeAndSpare()) {
			int totalScore = MAX_NUMBER_OF_PINS + MAX_NUMBER_OF_PINS + MAX_NUMBER_OF_PINS;
			setTotalScore(totalScore);
		} else if (hasSpare()) {
			int totalScore = MAX_NUMBER_OF_PINS + getScores().get(2) + getScores().get(2);
			setTotalScore(totalScore);
		} else if (hasOpen()) {
			int totalScore = getScores().get(0) + getScores().get(1);
			setTotalScore(totalScore);
		}

		if (alwaysAssignScore && getTotalScore() == null) {
			setTotalScore(getScores().get(0));
		}
	}

	@Override
	protected boolean hasSpare() {
		return isFull() && getScores().get(2) > 0 &&
				getScores().get(0) + getScores().get(1) == MAX_NUMBER_OF_PINS;
	}

	private boolean hasStrikeAndSpare() {
		return isFull() && getScores().get(0) == MAX_NUMBER_OF_PINS &&
				getScores().get(1) + getScores().get(2) == MAX_NUMBER_OF_PINS;
	}

	private boolean hasOpen() {
		if (getScores().size() < DEFAULT_MAX_NUMBER_OF_SCORES) {
			return false;
		}

		Integer firstScore = getScores().get(0);
		Integer secondScore = getScores().get(1);

		return firstScore < MAX_NUMBER_OF_PINS && secondScore < MAX_NUMBER_OF_PINS
				&& firstScore + secondScore < MAX_NUMBER_OF_PINS;
	}

	private boolean hasTwoStrikes() {
		Integer firstScore = null;
		Integer secondScore = null;

		if (getScores().size() > 0) {
			firstScore = getScores().get(0);
		}

		if (getScores().size() > 1) {
			secondScore = getScores().get(1);
		}

		return firstScore != null && secondScore != null &&
				firstScore == MAX_NUMBER_OF_PINS && secondScore == MAX_NUMBER_OF_PINS;
	}
}
