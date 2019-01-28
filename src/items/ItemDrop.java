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
			if (gui.getItemMenu ().addItem (item)) {
				this.forget ();
			}
		}
	}
}