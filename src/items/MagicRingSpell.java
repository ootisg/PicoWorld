package items;

import gameObjects.Player;
import main.GameAPI;
import projectiles.MagicRing;
import projectiles.PlayerMagic;
import util.Vector2D;

public class MagicRingSpell extends SpellItem {

	public MagicRingSpell () {
		super ();
		setProperty ("displayName", "Magic Ring");
	}
	
	@Override
	public boolean use () {
		Player player = GameAPI.getPlayer ();
		if (player.getMana () >= 10) {
			player.depleteMana (10);
			
			//Get the direction and define initial displacement and velocity
			Vector2D dir = player.VECTOR_DIRECTION_MAP [player.getDirection ()];
			Vector2D displacement = new Vector2D (dir);
			Vector2D velocity = new Vector2D (dir);
			displacement.scale (16); //Displace by 16 pixels
			velocity.scale (20); //Set initial velocity to 10 units
			
			//Make the spell projectile and displace it accordingly
			MagicRing projectile = new MagicRing (velocity);
			projectile.declare (player.getX () + displacement.x, player.getY () + displacement.y);
			
			return true;
		}
		return false;
	}
	
}
