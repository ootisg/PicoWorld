package gameObjects;

public class BlockPuzzleComponent extends PuzzleComponent {
	
	protected int velocityX;
	protected int velocityY;
	public static int speed = 4;
	
	public BlockPuzzleComponent () {
		
	}
	
	public boolean isMoving () {
		if (velocityX == 0 && velocityY == 0) {
			return true;
		} else {
			return false;
		}
	}
}
