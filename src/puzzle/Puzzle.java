package puzzle;

import java.util.ArrayList;
import java.util.Comparator;

import gameObjects.Saveable;
import items.GameItem;
import items.ItemDrop;
import main.GameObject;
import main.MainLoop;

public class Puzzle extends Saveable {

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
			String puzzleId = this.getVariantAttribute ("id");
			ArrayList<GameObject> loadedComponents = MainLoop.getObjectMatrix ().getAll (PuzzleComponent.class);
			//Link puzzle components
			switch (type) {
				
				case "block":
					renderComparator = new BlockPuzzleComparator ();
				default:
					for (int i = 0; i < loadedComponents.size (); i++) {
						PuzzleComponent workingComp = (PuzzleComponent)loadedComponents.get (i);
						String compId = workingComp.getVariantAttribute ("puzzleId");
						if (compId != null && puzzleId.equals (compId)) {
							workingComp.addToPuzzle (this);
						}
					}
					break;
			}
			//Ensure the above code doesn't run twice
			load ();
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
		save ("solved");
		forget ();
	}
	
	public void remove (GameObject component) {
		components.remove (component);
	}
	
	@Override
	public void load () {
		if (("solved").equals (getSave ().getSaveData (getRoom ().getRoomName (), getSaveId ()))) {
			forget ();
		}
	}
}