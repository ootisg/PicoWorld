package items;

import main.GameObject;

public class ItemDrop extends GameObject {
	private GameItem item;
	public ItemDrop (GameItem item) {
		this.item = item;
		this.setSprite (item.getIcon ());
		this.createHitbox (0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (isColliding ("gameObjects.Player")) {
			if (getGui ().getItemMenu ().addItem (item)) {
				this.forget ();
			}
		}
	}
	@Override
	public void draw () {
		item.draw ((int)(getX () - getRoom ().getViewX ()), (int)(getY () - getRoom ().getViewY ()));
	}
}