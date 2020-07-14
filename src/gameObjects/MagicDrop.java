package gameObjects;

import items.GameItem;
import main.GameObject;

public class MagicDrop extends GameObject {
	
	private GameItem item;
	
	public MagicDrop () {
		
	}
	
	public MagicDrop (GameItem item) {
		this.item = item;
		this.setSprite (item.getIcon ());
		this.createHitbox (0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (isColliding ("gameObjects.Player")) {
			collect ();
		}
	}
	@Override
	public void draw () {
		item.draw ((int)(getX () - getRoom ().getViewX ()), (int)(getY () - getRoom ().getViewY ()));
	}
	public void collect () {
		getGui ().getMagicContainer ().add (.5);
		forget ();
	}
}