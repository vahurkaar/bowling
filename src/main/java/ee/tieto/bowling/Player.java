package ee.tieto.bowling;

/**
 * Date: 19.12.13
 * Time: 8:29
 *
 * @author Vahur Kaar
 */
public class Player {

	private final String name;

	public Player(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Player player = (Player) o;

		if (name != null ? !name.equals(player.name) : player.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}
}
