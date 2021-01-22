package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import resources.Sprite;

public class Lock extends GameObject {

	private MapExit pairedExit;
	private String lockType;
	
	public Lock () {
		createHitbox (0, 0, 16, 16);
	}
	
	@Override
	public void onDeclare () {
		
		//Get the lock type
		lockType = getVariantAttribute ("type");
		if (lockType == null) {
			lockType = "vines"; //Defaults to vines
		}
		
		//Set the sprite accordingly
		setSprite (new Sprite ("resources/sprites/" + lockType + ".png"));
		
	}
	
	@Override
	public void frameEvent () {
		
		//Find the exit to be locked
		if (pairedExit == null) {
			ArrayList<GameObject> doors = getCollidingObjects ("gameObjects.MapExit");
			if (!doors.isEmpty ()) {
				pairedExit = (MapExit)doors.get (0);
			}
		}
		
		//Lock the exit
		if (pairedExit != null) {
			pairedExit.setLocked (true);
		}
		
		//Check to unlock
		switch (lockType) {
			case "vines":
				if (getPlayer ().swordObject.isCollidingRaster (this.getHitbox ())) {
					unlock ();
				}
				break;
			default:
				break;
		}
		
	}
	
	public void unlock () {
		if (pairedExit != null) {
			pairedExit.setLocked (false);
		}
		forget ();
	}
	
}
