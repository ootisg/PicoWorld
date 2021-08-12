package puzzle;

import java.lang.reflect.InvocationTargetException;

import gameObjects.ItemDrop;
import items.GameItem;
import main.GameObject;
import main.ObjectMatrix;

public class PuzzleRewardSpawner extends PuzzleComponent {

	public PuzzleRewardSpawner () {
		
	}

	@Override
	public void onSolve() {
		try {
			Class<?> rewardClass = Class.forName (getVariantAttribute ("rewardType"));
			GameObject rewardObj;
			if (GameItem.class.isAssignableFrom (rewardClass)) {
				rewardObj = new ItemDrop ((GameItem)rewardClass.getConstructor ().newInstance ());
			} else {
				rewardObj = (GameObject)rewardClass.getConstructor ().newInstance ();
			}
			rewardObj.declare (getX (), getY ());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
