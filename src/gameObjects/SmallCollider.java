package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import main.MainLoop;
import resources.Sprite;

public class SmallCollider extends GameObject {

	Class<?> collideType;
	
	public SmallCollider (Class<?> collideType, int x, int y) {
		createHitbox (0, 0, 2, 2);
		this.collideType = collideType;
		setX (x);
		setY (y);
	}
	
	public Interactable collide () {
		ArrayList<GameObject> allCollidingObjs = MainLoop.getObjectMatrix ().getAll (collideType);
		for (int i = 0; i < allCollidingObjs.size (); i ++) {
			if (isColliding (allCollidingObjs.get (i))) {
				return (Interactable)allCollidingObjs.get (i);
			}
		}
		return null;
	}
	
}
