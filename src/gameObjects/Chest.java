package gameObjects;

import java.io.IOException;

import gui.Interactable;
import items.GameItem;
import main.GameObject;
import resources.Sprite;

public class Chest extends Saveable implements Interactable {
	public Chest () {
		this.setSprite (new Sprite ("resources/sprites/chest.png"));
		createHitbox (0, 0, 32, 32);
	}
	@Override
	public void click () {
		try {
			GameItem droppedItem = (GameItem)(Class.forName ("items." + getVariantAttribute ("contents")).newInstance ());
			new ItemDrop (droppedItem).declare (getX (), getY ());
			save ("opened");
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
	@Override
	public void onDeclare () {
		load ();
	}
	@Override
	public void frameEvent () {
		//System.out.println (getVariantData ());
	}
	@Override
	public void load () {
		if (getSaveData () != null) {
			forget ();
		}
	}
	@Override
	public void hover () {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unhover () {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean useDefaultHover () {
		return true;
	}
}
