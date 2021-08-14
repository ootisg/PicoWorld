package puzzle;

import java.lang.reflect.InvocationTargetException;

import gameObjects.ItemDrop;
import gameObjects.Rift;
import items.GameItem;
import main.GameObject;

public class RiftSpawner extends PuzzleComponent {
	
	public RiftSpawner () {
		
	}
	
	@Override
	public void onSolve() {
		Rift r = new Rift ();
		r.declare (getX (), getY ());
		r.setVariantAttribute ("destination", getVariantAttribute ("riftDestination"));
	}

}
