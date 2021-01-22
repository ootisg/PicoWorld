package gameObjects;

import main.GameObject;
import resources.Sprite;
import resources.Spritesheet;

public class Teleporter extends GameObject {

	private static Spritesheet tpsheet = new Spritesheet ("resources/sprites/teleport_point.png");
	private static Sprite tpsprite = new Sprite (tpsheet, 16, 16);
	
	public Teleporter () {
		setSprite (tpsprite);
	}
	
}
