package items;

import gameObjects.Player;
import main.GameAPI;
import projectiles.PlayerFireball;
import projectiles.PlayerLightBolt;
import projectiles.PlayerMagic;
import resources.Sprite;
import resources.Spritesheet;

public class LightBoltSpell extends SpellItem {
	public LightBoltSpell () {
		super ();
		setProperty ("displayName", "LIGHT BOLT");
	}
	@Override
	public void draw (int x, int y) {
		super.draw (x, y);
	}
	
	@Override
	public boolean use () {
		Player player = GameAPI.getPlayer ();
		if (player.getMana () >= 5) {
			player.depleteMana (5);
			new PlayerLightBolt (Player.RADIAN_DIRECTION_MAP [player.getDirection ()]).declare (player.getX () + Math.cos (Player.RADIAN_DIRECTION_MAP [player.getDirection ()]) * 16, player.getY () + Math.sin (Player.RADIAN_DIRECTION_MAP [player.getDirection ()] * 16));
			return true;
		}
		return false;
	}
}