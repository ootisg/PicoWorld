package gameObjects;

import items.MagicGem;
import resources.Sprite;
import resources.Spritesheet;

public class MagicSlime extends Enemy {

	public MagicSlime () {
		createHitbox (1, 6, 14, 10);
		Spritesheet working = new Spritesheet ("resources/sprites/magic_slime.png");
		setSprite (new Sprite (working, 16, 16));
	}
	
	@Override
	public void enemyFrame () {
		
	}
	
	@Override
	public void deathEvent () {
		new MagicDrop (new MagicGem ()).declare (getX (), getY ());
		super.deathEvent ();
	}
	
}
