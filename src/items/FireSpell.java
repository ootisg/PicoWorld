package items;

import gameObjects.Player;
import main.GameAPI;
import projectiles.PlayerFireball;
import projectiles.PlayerMagic;
import resources.Sprite;
import resources.Spritesheet;

public class FireSpell extends SpellItem {
	public FireSpell () {
		super ();
		setProperty ("displayName", "FIREBALL");
	}
	@Override
	public void draw (int x, int y) {
		super.draw (x, y);
		System.out.println (animationHandler.getFrame ());
	}
	
	@Override
	public boolean use () {
		Player player = GameAPI.getPlayer ();
		if (player.getMana () >= 10) {
			player.depleteMana (10);
			new PlayerFireball (Player.RADIAN_DIRECTION_MAP [player.getDirection ()]).declare (player.getX () + Math.cos (Player.RADIAN_DIRECTION_MAP [player.getDirection ()]) * 16, player.getY () + Math.sin (Player.RADIAN_DIRECTION_MAP [player.getDirection ()] * 16));
			return true;
		}
		return false;
	}
}