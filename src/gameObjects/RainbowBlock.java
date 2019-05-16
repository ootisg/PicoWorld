package gameObjects;

import main.GameObject;
import resources.Sprite;
import resources.Spritesheet;

public class RainbowBlock extends BlockPuzzleComponent {
	
	public RainbowBlock () {
		setSprite (new Sprite (new Spritesheet ("resources/sprites/blocks.png"), 16, 16));
		getAnimationHandler ().setAnimationSpeed (0);
		createHitbox (1, 1, 14, 14);
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
	
	public void setVelocities (int direction) {
		switch (direction) {
			case Player.DIRECTION_UP:
				velocityX = 0;
				velocityY = -speed;
				break;
			case Player.DIRECTION_LEFT:
				velocityX = -speed;
				velocityY = 0;
				break;
			case Player.DIRECTION_RIGHT:
				velocityX = speed;
				velocityY = 0;
				break;
			case Player.DIRECTION_DOWN:
				velocityX = 0;
				velocityY = speed;
				break;
			default:
				velocityX = 0;
				velocityY = 0;
				break;
		}
	}
	
	@Override
	public void frameEvent () {
		setHidden (true);
		if (isColliding (getPlayer ())) {
			setVelocities (getPlayer ().getDirection ());
		}
		setX (getX () + velocityX);
		setY (getY () + velocityY);
		if (getRoom ().isColliding (getHitbox ())) {
			backstep ();
		}
		if (isColliding ("gameObjects.RainbowBlock")) {
			
		}
	}

	@Override
	public void draw () {
		getAnimationHandler ().setFrame (getColorId ());
		super.draw ();
	}
}