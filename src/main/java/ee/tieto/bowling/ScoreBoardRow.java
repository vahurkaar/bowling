package ee.tieto.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.12.13
 * Time: 9:27
 *
 * @author Vahur Kaar
 */
public class ScoreBoardRow {

	public static final Integer ROW_LIMIT = 10;
	private List<ScoreBoardRowColumn> columns = new ArrayList<ScoreBoardRowColumn>();
	private Integer totalScore = 0;

	public boolean insertScore(Integer score) {
		return false;
	}

	public boolean isFull() {
		return columns.size() >= ROW_LIMIT;
	}

	public List<ScoreBoardRowColumn> getColumns() {
		return columns;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void finalizeScore() {
	}
}
