package items;

import gameObjects.Player;
import main.GameAPI;
import projectiles.PlayerMagic;
import resources.Sprite;
import resources.Spritesheet;

public class DeathSpell extends SpellItem {
	public DeathSpell () {
		super ();
		setProperty ("displayName", "h");
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
			new PlayerMagic (Player.RADIAN_DIRECTION_MAP [player.getDirection ()]).declare (player.getX (), player.getY ());
			return true;
		}
		return false;
	}
}