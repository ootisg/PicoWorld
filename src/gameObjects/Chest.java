package gameObjects;

import java.io.IOException;

import items.GameItem;
import items.ItemDrop;
import main.GameObject;
import resources.Sprite;

public class Chest extends Saveable {
	public Chest () {
		this.setSprite (new Sprite ("resources/sprites/chest.png"));
		createHitbox (0, 0, 32, 32);
	}
	@Override
	public void onDeclare () {
		load ();
	}
	@Override
	public void frameEvent () {
		if (getPlayer ().swordObject.isCollidingRaster (this.getHitbox ())) {
			try {
				GameItem droppedItem = (GameItem)(Class.forName ("items." + getVariantAttribute ("contents")).newInstance ());
				new ItemDrop (droppedItem).declare (getX (), getY ());
				save ("opened");
				try {
					Saveable.writeData ("saves/save.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.forget ();
		}
		//System.out.println (getVariantData ());
	}
	@Override
	public void load () {
		if (getSaveData () != null) {
			//forget ();
		}
	}
}
