package gameObjects;

import gui.Interactable;
import items.Wheat;
import main.GameObject;
import resources.Sprite;
import visualEffects.Outline;

public class Windmill extends GameObject implements Interactable {
	
	public static Sprite millSprite = new Sprite ("resources/sprites/windmill.png");
	
	private int wheatNumber = 0;
	
	public Windmill () {
		setSprite (millSprite);
		this.setPriority (-2);
		this.createHitbox (16, 128, 64, 32);
	}

	@Override
	public void hover () {
		//Do nothing
	}
	
	@Override
	public void unhover () {
		//Do nothing
	}

	@Override
	public void click () {
		System.out.println ("MILL!");
		Wheat wheat = new Wheat ();
		while (getGui ().getItemMenu ().hasSimilar (wheat)) {
			getGui ().getItemMenu ().removeSimilar (wheat);
			wheatNumber++;
		}
	}

	@Override
	public boolean useDefaultHover () {
		return true;
	}

}
