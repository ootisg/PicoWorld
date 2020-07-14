package puzzle;

import java.util.ArrayList;
import java.util.Iterator;

import gameObjects.Saveable;
import main.GameObject;
import main.MainLoop;

public class EnemyPuzzle extends Saveable {
	
	private boolean isNew = true;
	private boolean doSave;
	
	public EnemyPuzzle () {
		
	}
	
	@Override
	public void frameEvent () {
		if (isNew) {
			String stringSave = this.getVariantAttribute ("save");
			if (stringSave != null && stringSave.equals ("true")) {
				doSave = true;
			} else {
				doSave = false;
			}
			doSave = true;
			load ();
			isNew = false;
		}
		try {
			ArrayList<GameObject> enemies = MainLoop.getObjectMatrix ().getAll (Class.forName ("gameObjects.Enemy"));
			if (enemies.size () == 0) {
				solve ();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void solve () {
		try {
			ArrayList<GameObject> components = MainLoop.getObjectMatrix ().getAll (Class.forName ("gameObjects.EnemyPuzzleComponent"));
			Iterator<GameObject> iter = components.iterator ();
			while (iter.hasNext ()) {
				((EnemyPuzzleComponent)(iter.next ())).onSolve ();
			}
			if (doSave) {
				save ("solved");
			}
			forget ();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void load () {
		if (doSave) {
			String data = getSaveData ();
			if (data != null) {
				solve ();
			}
		}
	}
}
