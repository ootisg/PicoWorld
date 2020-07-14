package puzzle;

import java.util.ArrayList;

import main.GameObject;
import resources.Sprite;
import resources.Spritesheet;

public class RainbowTarget extends BlockPuzzleComponent {
	
	public RainbowTarget () {
		setSprite (new Sprite (new Spritesheet ("resources/sprites/targets.png"), 16, 16));
		getAnimationHandler ().setAnimationSpeed (0);
		createHitbox (0, 0, 16, 16);
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
	public void frameEvent () {
		ArrayList<GameObject> objs = getCollidingObjects ("puzzle.RainbowBlock");
		for (int i = 0; i < objs.size (); i ++) {
			if (objs.get (i).getX () == getX () && objs.get (i).getY () == getY () && getVariantAttribute ("color").equals (objs.get (i).getVariantAttribute ("color"))) {
				puzzle.doWin ();
			}
		}
	}
	
	@Override
	public void draw () {
		getAnimationHandler ().setFrame (getColorId ());
		super.draw ();
	}
}
