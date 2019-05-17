package gameObjects;

import items.GameItem;
import main.GameObject;

public class ItemDrop extends GameObject {
	
	private GameItem item;
	
	private boolean variantFill;
	
	public ItemDrop () {
		variantFill = true;
	}
	
	public ItemDrop (GameItem item) {
		this.item = item;
		this.setSprite (item.getIcon ());
		this.createHitbox (0, 0, 16, 16);
		variantFill = false;
	}
	@Override
	public void frameEvent () {
		if (variantFill) {
			try {
				this.item = (GameItem)Class.forName ("items." + getVariantAttribute ("item")).newInstance ();
				this.setSprite (item.getIcon ());
				this.createHitbox (0, 0, 16, 16);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			variantFill = false;
		}
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