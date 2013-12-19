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
        boolean giveTurnToOpponent;

        if (isFull()) {
            throw new ScoreBoardIsFullException("You cannot add any more scores to the table");
        }

        giveTurnToOpponent = addScoreToRow(score);
        calculateScore();

        return giveTurnToOpponent;
    }

	private boolean addScoreToRow(Integer score) {
		boolean isNextPlayersTurn;
		if (getLastColumn() != null && getLastColumn().canInsertScores()) {
			isNextPlayersTurn = getLastColumn().insertScore(score);
		} else {
			ScoreBoardRowColumn column = createColumn();
			columns.add(column);
			isNextPlayersTurn = column.insertScore(score);
		}
		return isNextPlayersTurn;
	}

    private void calculateScore() {
        if (isFull()) {
            finalizeScore();
        } else {
            recalculateColumnScores(false);
        }
    }

    private ScoreBoardRowColumn createColumn() {
        if (existsOneMoreSpareColumn()) {
            return new ScoreBoardRowFinalColumn();
        } else {
            return new ScoreBoardRowColumn();
        }
    }

    private boolean existsOneMoreSpareColumn() {
        return COLUMN_LIMIT - columns.size() == 1;
    }

    public ScoreBoardRowColumn getLastColumn() {
        int numberOfColumns = columns.size();

        if (numberOfColumns > 0) {
            return columns.get(numberOfColumns - 1);
        }

        return null;
    }

    public boolean isFull() {
        return columns.size() >= COLUMN_LIMIT && !getLastColumn().canInsertScores();
    }

    public Integer getTotalScore() {
        Integer sum = 0;

        for (ScoreBoardRowColumn column : columns) {
            Integer score = column.getTotalScore();
            if (score != null) {
                sum += score;
            }
        }

        return sum;
    }

    private void recalculateColumnScores(boolean alwaysAssignScore) {
        for (int i = 0; i < columns.size(); i++) {
            ScoreBoardRowColumn column = columns.get(i);
			column.calculateTotalScore(i, columns, alwaysAssignScore);
        }
    }

    public void finalizeScore() {
        recalculateColumnScores(true);
    }

    public List<ScoreBoardRowColumn> getColumns() {
        return columns;
    }
}
