package gameObjects;

import java.util.ArrayList;
import java.util.Comparator;

import items.GameItem;
import items.ItemDrop;
import main.GameObject;

public class Puzzle extends GameObject {

	private boolean isNew;
	
	private String type;
	private GameItem reward;
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
			try {
				reward = (GameItem)(Class.forName (getVariantAttribute ("reward")).newInstance ());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int boundXOffset = Integer.parseInt (getVariantAttribute ("boundXOffset"));
			int boundYOffset = Integer.parseInt (getVariantAttribute ("boundYOffset"));
			int boundWidth = Integer.parseInt (getVariantAttribute ("boundWidth"));
			int boundHeight = Integer.parseInt (getVariantAttribute ("boundHeight"));
			createHitbox (boundXOffset, boundYOffset, boundWidth, boundHeight);
			//Link puzzle components
			switch (type) {
				case "block":
					ArrayList<GameObject> blocks = getCollidingObjects ("gameObjects.RainbowBlock");
					ArrayList<GameObject> targets = getCollidingObjects ("gameObjects.RainbowTarget");
					for (int i = 0; i < blocks.size (); i ++) {
						((RainbowBlock)(blocks.get (i))).addToPuzzle (this);
					}
					for (int i = 0; i < targets.size (); i ++) {
						((RainbowTarget)(targets.get (i))).addToPuzzle (this);
					}
					renderComparator = new BlockPuzzleComparator ();
					break;
			}
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
	
	@Override
	public void forget () {
		for (int i = 0; i < components.size (); i ++) {
			components.get (i).forget ();
		}
		super.forget ();
	}
	
	public void add (GameObject component) {
		components.add (component);
	}
	
	public void doWin () {
		new ItemDrop (reward).declare (getX (), getY ());
		forget ();
	}
	
	public void remove (GameObject component) {
		components.remove (component);
	}
}