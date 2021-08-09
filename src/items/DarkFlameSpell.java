package items;

import gameObjects.Player;
import main.GameAPI;
import projectiles.DarkFlame;
import projectiles.PlayerLightBolt;
import util.Vector2D;

public class DarkFlameSpell extends SpellItem {
	
	public DarkFlameSpell () {
		super ();
		setProperty ("displayName", "DARK FLAME");
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
			Vector2D facing = player.getFacingDirection ();
			facing.scale (16);
			DarkFlame newFlame = new DarkFlame (facing);
			newFlame.declare (player.getX () + facing.x, player.getY () + facing.y);
			return true;
		}
		return false;
	}

}
