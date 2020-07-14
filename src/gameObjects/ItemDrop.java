package gameObjects;

import items.GameItem;
import main.GameObject;

public class ItemDrop extends Saveable {
	
	private GameItem item;
	
	private boolean saveIfCollected;
	
	private boolean variantFill;
	
	public ItemDrop () {
		variantFill = true;
		saveIfCollected = true;
	}
	
	public ItemDrop (GameItem item) {
		this.item = item;
		this.setSprite (item.getIcon ());
		this.createHitbox (0, 0, 16, 16);
		variantFill = false;
	}
	@Override
	public void onDeclare () {
		if (saveIfCollected) {
			load ();
			try {
				this.item = (GameItem)Class.forName ("items." + getVariantAttribute ("item")).newInstance ();
				this.setSprite (item.getIcon ());
				this.createHitbox (0, 0, 16, 16);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void frameEvent () {
		if (isColliding ("gameObjects.Player")) {
			if (getGui ().getItemMenu ().addItem (item)) {
				collect ();
			}
		}
	}
	public void setSaveIfCollected (boolean saveIfCollected) {
		this.saveIfCollected = saveIfCollected;
	}
	@Override
	public void draw () {
		item.draw ((int)(getX () - getRoom ().getViewX ()), (int)(getY () - getRoom ().getViewY ()));
	}
	@Override
	public void load () { 
		if (("collected").equals (getSave ().getSaveData (getRoom ().getRoomName (), getSaveId ()))) {
			forget ();
		}
	}
	public void collect () {
		if (saveIfCollected) {
			save ("collected");
		}
		forget ();
	}
}