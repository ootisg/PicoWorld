package projectiles;

import gameObjects.DamageSource;
import gameObjects.Player;
import main.GameObject;
import resources.Sprite;

public class EnemyFireball extends EnemyProjectile implements DamageSource {
	
	private static final double speed = 10;
	
	private int spriteOffset;
	
	private Sprite rotatedFireball;
	
	public EnemyFireball (double direction) {
		rotatedFireball = getSprites ().fireballSprites [0].getRotatedSprite (direction);
		spriteOffset = rotatedFireball.getWidth () / 2 - getSprites ().fireballSprites [0].getWidth () / 2;
		setSprite (rotatedFireball);
		setVelocityX (speed * Math.cos (direction));
		setVelocityY (speed * Math.sin (direction));
		createHitbox (0, 0, 16, 16);
	}
	
	@Override
	public void hitEvent (GameObject target) {
		if (target instanceof Player) {
			((Player)target).damageEvent (this);
		}
		forget ();
	}
	
	@Override
	public void drawSprite () {
		//Draws the Sprite associated with this GameObject
		if (getSprite () != null) {	
			getAnimationHandler ().animate ((int) getX () - getRoom ().getViewX () - spriteOffset, (int) getY () - getRoom ().getViewY () - spriteOffset, getFlipHorizontal (), getFlipVertical ());
		}
	}

	@Override
	public double getBaseDamage () {
		return 10;
	}
}
