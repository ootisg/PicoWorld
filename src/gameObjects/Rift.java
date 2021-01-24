package gameObjects;

import main.GameObject;
import resources.Sprite;

public class Rift extends GameObject {

	public static Sprite riftSprite = new Sprite ("resources/sprites/rift.png");
	
	public Rift () {
		setSprite (riftSprite);
		createHitbox (4, 16, 8, 16);
	}
	
	@Override
	public void declare (double x, double y) {
		super.declare (x, y - 16); //Move the rift up to account for the tall sprite
	}
	
}
