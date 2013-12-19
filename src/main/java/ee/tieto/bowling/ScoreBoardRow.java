package ee.tieto.bowling;

import ee.tieto.bowling.exception.ScoreBoardIsFullException;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.12.13
 * Time: 9:27
 *
 * @author Vahur Kaar
 */
public class ScoreBoardRow {

    public static final Integer COLUMN_LIMIT = 10;

    private List<ScoreBoardRowColumn> columns = new ArrayList<ScoreBoardRowColumn>();

    public boolean insertScore(Integer score) {
        boolean isNextPlayersTurn;

        if (isFull()) {
            throw new ScoreBoardIsFullException("You cannot add any more scores to the table");
        }

        isNextPlayersTurn = addScoreToRow(score);
        calculateScore();

        return isNextPlayersTurn;
    }

    private void calculateScore() {
        if (isFull()) {
            finalizeScore();
        } else {
            recalculateColumnScores(false);
        }
    }

    private boolean addScoreToRow(Integer score) {
        boolean isNextPlayersTurn;
        if (getLastColumn() != null && getLastColumn().canInsertScores()) {
            isNextPlayersTurn = getLastColumn().insertScore(score);
        } else {
            ScoreBoardRowColumn column = new ScoreBoardRowColumn();
            columns.add(column);
            isNextPlayersTurn = column.insertScore(score);
        }
        return isNextPlayersTurn;
    }

    public ScoreBoardRowColumn getLastColumn() {
        int numberOfColumns = columns.size();

        if (!columns.isEmpty()) {
            return columns.get(numberOfColumns - 1);
        }

        return null;
    }

    public boolean isFull() {
        return columns.size() >= COLUMN_LIMIT;
    }

    public Integer getTotalScore() {
        Integer sum = 0;

        for (ScoreBoardRowColumn column : columns) {
            Integer score = column.getScore();
            if (score != null) {
                sum += score;
            }
        }

        return sum;
    }

    public void recalculateColumnScores(boolean alwaysAssignScore) {
        for (int i = 0; i < columns.size(); i++) {
            ScoreBoardRowColumn column = columns.get(i);

            if (column.columnHasStrike()) {
                calculateStrike(i, columns, alwaysAssignScore);
            } else if (column.columnHasSpare()) {
                calculateSpare(i, columns, alwaysAssignScore);
            } else if (column.columnIsFull()) {
                calculateDefaultScore(column);
            }
        }
    }

    private void calculateDefaultScore(ScoreBoardRowColumn column) {
        int score = column.getScores().get(0) + column.getScores().get(1);
        column.setScore(score);
    }

    private void calculateSpare(int index, List<ScoreBoardRowColumn> columns, boolean alwaysAssignScore) {
        ScoreBoardRowColumn column = columns.get(index);
        if (++index < columns.size()) {
            int score = 10;
            score += columns.get(index).getScores().get(0);
            column.setScore(score);
        }

        if (alwaysAssignScore && column.getScore() == null) {
            column.setScore(10);
        }
    }

    private void calculateStrike(int index, List<ScoreBoardRowColumn> columns, boolean alwaysAssignScore) {
        ScoreBoardRowColumn column = columns.get(index);
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
            column.setScore(score);
        }
    }

    public void finalizeScore() {
        recalculateColumnScores(true);
    }

    public List<ScoreBoardRowColumn> getColumns() {
        return columns;
    }
}
