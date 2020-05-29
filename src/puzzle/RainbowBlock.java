package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import resources.Sprite;
import resources.Spritesheet;

public class RainbowBlock extends BlockPuzzleComponent {
	
	private RainbowBlock link;
	
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
		if (getPlayer ().swordObject.isColliding (this)) {
			RainbowBlock newBlock;
			switch (getVariantAttribute ("color")) {
				case "orange":
					setVariantAttribute ("color", "red");
					newBlock = new RainbowBlock ();
					newBlock.addToPuzzle (puzzle);
					link (newBlock);
					newBlock.setVariantAttribute ("color", "yellow");
					newBlock.declare (getX (), getY ());
					newBlock.setVelocities (getPlayer ().getDirection ());
					break;
				case "green":
					setVariantAttribute ("color", "yellow");
					newBlock = new RainbowBlock ();
					newBlock.addToPuzzle (puzzle);
					link (newBlock);
					newBlock.setVariantAttribute ("color", "blue");
					newBlock.declare (getX (), getY ());
					newBlock.setVelocities (getPlayer ().getDirection ());
					break;
				case "purple":
					setVariantAttribute ("color", "red");
					newBlock = new RainbowBlock ();
					newBlock.addToPuzzle (puzzle);
					link (newBlock);
					newBlock.setVariantAttribute ("color", "blue");
					newBlock.declare (getX (), getY ());
					newBlock.setVelocities (getPlayer ().getDirection ());
					break;
				default:
					break;
			}
		}
		setX (getX () + velocityX);
		setY (getY () + velocityY);
		if (getRoom ().isColliding (getHitbox ())) {
			backstep ();
			velocityX = 0;
			velocityY = 0;
		}
		if (isColliding ("gameObjects.RainbowBlock")) {
			ArrayList<GameObject> objs = getCollidingObjects ("gameObjects.RainbowBlock");
			if (!(objs.size () == 1 && isLinkedTo ((RainbowBlock)objs.get (0)))) {
				if (Math.abs (getColorId () - ((RainbowBlock)(objs.get (0))).getColorId ()) == 1) {
					if (objs.get (0).getX () == getX () && objs.get (0).getY () == getY ()) {
						String newColor = null;
						switch (Math.max (getColorId (), ((RainbowBlock)objs.get (0)).getColorId ())) {
							case 1:
								newColor = "yellow";
								break;
							case 2:
								newColor = "green";
								break;
							case 3:
								newColor = "blue";
								break;
							case 4:
								newColor = "purple";
								break;
							default:
								break;
						}
						if (newColor != null) {
							puzzle.remove (objs.get (0));
							objs.get (0).forget ();
							setVariantAttribute ("color", newColor);
							velocityX = 0;
							velocityY = 0;
						}
					}
				} else {
					backstep ();
					velocityX = 0;
					velocityY = 0;
				}
			}
		}
		if (link != null && !isColliding (link)) {
			link = null;
		}
	}

	@Override
	public void draw () {
		getAnimationHandler ().setFrame (getColorId ());
		super.draw ();
	}
	
	public void link (RainbowBlock block) {
		link = block;
		block.link = this;
	}
	
	public boolean isLinkedTo (RainbowBlock block) {
		return block == link;
	}
}