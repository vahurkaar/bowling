package ee.tieto.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.12.13
 * Time: 10:52
 *
 * @author Vahur Kaar
 */
public class ScoreBoardRowColumn {

    public static final Integer DEFAULT_MAX_NUMBER_OF_SCORES = 2;
    public static final Integer MAX_NUMBER_OF_PINS = 10;

    private Integer totalScore;
    private boolean finalized = false;
    private Integer maxNumberOfScores = DEFAULT_MAX_NUMBER_OF_SCORES;
    private List<Integer> scores = new ArrayList<Integer>();

    public ScoreBoardRowColumn() {
    }

    public ScoreBoardRowColumn(Integer limit) {
        maxNumberOfScores = limit;
    }

    public boolean insertScore(Integer score) {
        if (canInsertScores()) {
            scores.add(score);
            if (isColumnStateFinal()) {
                finalizeColumn();
                return true;
            }
        }

		return false;
    }

	public void calculateTotalScore(int index, List<ScoreBoardRowColumn> columns, boolean alwaysAssignScore) {
		if (hasStrike()) {
			calculateStrike(index, columns, alwaysAssignScore);
		} else if (hasSpare()) {
			calculateSpare(index, columns, alwaysAssignScore);
		} else if (isFull()) {
			calculateDefaultScore();
		}

		if (alwaysAssignScore && getTotalScore() == null) {
			setTotalScore(getScores().get(0));
		}
	}

	private void calculateDefaultScore() {
		int score = scores.get(0) + scores.get(1);
		setTotalScore(score);
	}

	private void calculateSpare(int index, List<ScoreBoardRowColumn> columns, boolean alwaysAssignScore) {
		if (++index < columns.size()) {
			int score = 10;
			score += columns.get(index).getScores().get(0);
			setTotalScore(score);
		}

		if (alwaysAssignScore && getTotalScore() == null) {
			setTotalScore(ScoreBoardRowColumn.MAX_NUMBER_OF_PINS);
		}
	}

	private void calculateStrike(int index, List<ScoreBoardRowColumn> columns, boolean alwaysAssignScore) {
		Integer score = 0;
		int summedScoresCount = 0;

		int numberOfScoresToSummarize = 3;
		while (summedScoresCount < numberOfScoresToSummarize && index < columns.size()) {
			ScoreBoardRowColumn col = columns.get(index);
			for (int i = 0; i < col.getScores().size() && summedScoresCount < numberOfScoresToSummarize; i++, summedScoresCount++) {
				score += col.getScores().get(i);
			}
			index++;
		}

		if (summedScoresCount == numberOfScoresToSummarize || alwaysAssignScore) {
			setTotalScore(score);
		}
	}

	protected boolean isColumnStateFinal() {
		return hasStrike() || hasSpare() || isFull();
	}

	protected boolean isFull() {
        return scores.size() == maxNumberOfScores;
    }

    protected boolean hasSpare() {
        return scores.size() >= ScoreBoardRowColumn.DEFAULT_MAX_NUMBER_OF_SCORES &&
                scores.get(0) + scores.get(1) == MAX_NUMBER_OF_PINS;
    }

    protected boolean hasStrike() {
		return scores.size() == 1 && scores.get(0) == 10;
    }

    private void finalizeColumn() {
        finalized = true;
    }

    public boolean canInsertScores() {
        return !finalized && scores.size() < maxNumberOfScores;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public List<Integer> getScores() {
        return scores;
    }

}
