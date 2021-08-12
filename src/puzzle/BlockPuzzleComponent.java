package puzzle;

public abstract class BlockPuzzleComponent extends PuzzleComponent {
	
	protected int velocityX;
	protected int velocityY;
	protected Puzzle puzzle;
	public static int speed = 4;
	
	public BlockPuzzleComponent () {
		
	}
	
	public boolean isMoving () {
		if (velocityX == 0 && velocityY == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public void addToPuzzle (Puzzle puzzle) {
		puzzle.add (this);
		this.puzzle = puzzle;
	}
}
