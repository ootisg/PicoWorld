package gameObjects;

import main.GameObject;
import resources.Sprite;
import resources.Spritesheet;

public class RainbowTarget extends BlockPuzzleComponent {
	
	public RainbowTarget () {
		setSprite (new Sprite (new Spritesheet ("resources/sprites/targets.png"), 16, 16));
		getAnimationHandler ().setAnimationSpeed (0);
		setHidden (true);
	}
	
	public int getColorId () {
		switch (getVariantAttribute ("color")) {
			case "red":
				return 0;
			case "orange":
				return 1;
			case "yellow":
				return 2;
			case "green":
				return 3;
			case "blue":
				return 4;
			case "purple":
				return 5;
			default:
				return 0;
		}
	}
	
	@Override
	public void draw () {
		getAnimationHandler ().setFrame (getColorId ());
		super.draw ();
	}
}
