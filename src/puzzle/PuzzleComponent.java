package puzzle;

import main.GameObject;

public abstract class PuzzleComponent extends GameObject {

	private Puzzle puzzle;
	
	public void addToPuzzle (Puzzle p) {
		this.puzzle = p;
		p.add (this);
	}
	
	public Puzzle getPuzzle () {
		return puzzle;
	}
	
	public abstract void onSolve ();
	
}
