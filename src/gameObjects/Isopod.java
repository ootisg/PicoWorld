package gameObjects;

import resources.Sprite;
import resources.Spritesheet;

public class Isopod extends Enemy {
	
	public Isopod () {
		Spritesheet sheet = new Spritesheet ("resources/sprites/isopod2.png");
		Sprite sprite = new Sprite (sheet, 16, 16);
		setSprite (sprite);
	}

}
