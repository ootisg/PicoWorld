package items;

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
	public void onDeclare () {
		if (variantFill) {
			try {
				this.item = (GameItem)Class.forName ("items." + getVariantAttribute ("item")).newInstance ();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setSprite (item.getIcon ());
		this.createHitbox (0, 0, 16, 16);
	}
	@Override
	public void frameEvent () {
		if (isColliding ("gameObjects.Player")) {
			if (getGui ().getItemMenu ().addItem (item)) {
				collect ();
			}
		}
	}
	@Override
	public void draw () {
		item.draw ((int)(getX () - getRoom ().getViewX ()), (int)(getY () - getRoom ().getViewY ()));
	}
	public boolean collect () {
		forget ();
		return true;
	}
}