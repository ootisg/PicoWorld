package gameObjects;

import java.util.Comparator;

import main.GameObject;

public class BlockPuzzleComparator implements Comparator<GameObject> {

	public BlockPuzzleComparator () {
		
	}
	
	@Override
	public int compare (GameObject component1, GameObject component2) {
		if (!((component1 instanceof BlockPuzzleComponent) && (component2 instanceof BlockPuzzleComponent))) {
			return component1.compareTo (component2);
		} else {
			BlockPuzzleComponent cast1 = (BlockPuzzleComponent)component1;
			BlockPuzzleComponent cast2 = (BlockPuzzleComponent)component2;
			if (cast1.isMoving () && !cast2.isMoving ()) {
				return -1;
			} else if (!cast1.isMoving () && !cast2.isMoving ()) {
				return 1;
			} else {
				return component1.compareTo (component2);
			}
		}
	}
}