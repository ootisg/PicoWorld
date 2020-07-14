package gameObjects;

import puzzle.EnemyPuzzleComponent;
import resources.Sprite;

public class FalseWall extends EnemyPuzzleComponent {
	
	public FalseWall () {
		
	}
	
	@Override
	public void onSolve () {
		getRoom ().setTile ((int)(getX ()) / 16, (int)(getY ()) / 16, "transparent.png:0");
	}
}
