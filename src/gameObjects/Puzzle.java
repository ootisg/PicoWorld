package gameObjects;

import java.util.ArrayList;
import java.util.Comparator;

import main.GameObject;

public class Puzzle extends GameObject {

	private boolean isNew;
	
	private String type;
	private String reward;
	private ArrayList<GameObject> components;
	private Comparator<? super GameObject> renderComparator;
	
	public Puzzle () {
		isNew = true;
		components = new ArrayList<GameObject> ();
		renderComparator = null;
	}
	
	@Override
	public void frameEvent () {
		if (isNew) {
			//Set variables relating to attributes given in the map editor
			type = getVariantAttribute ("type");
			reward = getVariantAttribute ("reward");
			int boundXOffset = Integer.parseInt (getVariantAttribute ("boundXOffset"));
			int boundYOffset = Integer.parseInt (getVariantAttribute ("boundYOffset"));
			int boundWidth = Integer.parseInt (getVariantAttribute ("boundWidth"));
			int boundHeight = Integer.parseInt (getVariantAttribute ("boundHeight"));
			createHitbox (boundXOffset, boundYOffset, boundWidth, boundHeight);
			//Link puzzle components
			switch (type) {
				case "block":
					components = getCollidingObjects ("gameObjects.RainbowBlock");
					ArrayList<GameObject> targets = getCollidingObjects ("gameObjects.RainbowTarget");
					components.addAll (targets);
					renderComparator = new BlockPuzzleComparator ();
					break;
			}
			System.out.println (components.size ());
			//Ensure the above code doesn't run twice
			isNew = false;
		}
	}
	
	@Override
	public void draw () {
		components.sort (renderComparator);
		for (int i = 0; i < components.size (); i ++) {
			components.get (i).draw ();
		}
	}
}