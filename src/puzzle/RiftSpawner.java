package puzzle;

import java.lang.reflect.InvocationTargetException;

import gameObjects.ItemDrop;
import gameObjects.Rift;
import items.GameItem;
import main.GameObject;

public class RiftSpawner extends PuzzleComponent {
	
	public RiftSpawner () {
		System.out.println("RIFTSPAWNER");
	}
	
	@Override
	public void onSolve() {
		System.out.println ("SPAWNING RIFT");
		Rift r = new Rift ();
		r.declare (getX (), getY ());
		r.setVariantAttribute ("destination", getVariantAttribute ("riftDestination"));
	}

}
