package chineseCheckers.gameStaff;

public abstract class AbstractMoveArbiter {
	protected Board board;
	protected Piece[][] pieces;
	protected Piece[] selected;

	public AbstractMoveArbiter(Board board, Piece[][] pieces, Piece[] selected) {
		this.board = board;
		this.pieces = pieces;
		this.selected = selected;
	}

	private Field getField(byte part, byte field) {
		if (part > 12 | part < 0)
			return null;

		if (field < 0 | field > 9)
			return null;

		if (part == 12)
			return board.middle;
		else
			return board.triangles[part].fields[field];// A co z prawem
														// Persefony, Moze
														// dodać getery?
	}

	private boolean correctPiece(byte player, Field f1) {
		for (int i = 0; i < 10; i++)
			if (pieces[player][i].getField() == f1)
				return true;

		return false;
	}

	public boolean[] isCorrect(byte player, byte part1, byte field1, byte part2, byte field2) {
		Field f1 = getField(part1, field1);
		Field f2 = getField(part2, field2);

		boolean ans[] = new boolean[2];

		if (f1 == null | f2 == null) {
			ans[0] = false;
			ans[1] = true;
			return ans;
		}

		if (correctPiece(player, f1)) {

			ans[0] = correctFields(f1, f2, player);
			ans[1] = !f1.isNeighbour(f2);
			return ans;
		}

		ans[0] = false;
		ans[1] = true;
		return ans;
	}
	// private boolean correctFields(Field f1, Field f2, byte player){return
	// false;}

	private boolean correctFields(Field f1, Field f2, byte player) {
		if (f1.getHome() == player && f2.getHome() != player)
			return false;

		if (f2.isFree())
			if (f1.isNeighbour(f2))
				return true;
			else
				return lineAble(f1, f2);
		else
			return false;
	}

	private boolean lineAble(Field f1, Field f2) {
		if (f2 == null)
			return false;

		if (f2.isFree())
			for (byte i = 0; i < 6; i++) {
				if (f1.directNeighbour(i) != null)
					if (!f1.directNeighbour(i).isFree())
						if (f1.directNeighbour(i).directNeighbour(i) == f2)
							return true;
			}

		return false;
	}
}
