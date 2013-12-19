package ee.tieto.bowling;

import ee.tieto.bowling.exception.SquareIsFinalizedException;import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.12.13
 * Time: 10:52
 *
 * @author Vahur Kaar
 */
public class ScoreBoardRowColumn {

    public static final Integer DEFAULT_MAX_NUMBER_OF_SCORES = 2;
    private static final Integer MAX_NUMBER_OF_PINS = 10;

    private Integer score;
    private boolean finalized = false;
    private Integer maxNumberOfScores = DEFAULT_MAX_NUMBER_OF_SCORES;
    private List<Integer> scores = new ArrayList<Integer>();

    public ScoreBoardRowColumn() {}

    public boolean insertScore(Integer score) {
        if (canInsertScores()) {
            scores.add(score);
            if (columnHasStrike() || columnHasSpare() || columnIsFull()) {
                finalizeColumn();
                return true;
            }

            return false;
        }

        throw new SquareIsFinalizedException("No more scores can be added to the scoreboard column");
    }

    public boolean columnIsFull() {
        return scores.size() == maxNumberOfScores;
    }

    public boolean columnHasSpare() {
        return scores.size() == ScoreBoardRowColumn.DEFAULT_MAX_NUMBER_OF_SCORES &&
                scores.get(0) + scores.get(1) == MAX_NUMBER_OF_PINS;
    }

    public boolean columnHasStrike() {
        return scores.size() == 1 && scores.get(0).equals(MAX_NUMBER_OF_PINS);
    }

    public void finalizeColumn() {
        finalized = true;
    }

    public boolean canInsertScores() {
        return !finalized && scores.size() < maxNumberOfScores;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public Integer getMaxNumberOfScores() {
        return maxNumberOfScores;
    }
}
