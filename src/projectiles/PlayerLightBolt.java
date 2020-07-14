package projectiles;

import java.awt.Color;

import gameObjects.Particle;
import resources.Sprite;
import resources.Spritesheet;

public class PlayerLightBolt extends PlayerProjectile {
	
	private static final double speed = 10;
	
	public static final Spritesheet lightSheet = new Spritesheet ("resources/sprites/light_bolt_projectile.png");
	public static final Sprite lightBoltUp = new Sprite (lightSheet, 16, 0, 16, 16);
	public static final Sprite lightBoltRight = new Sprite (lightSheet, 0, 0, 16, 16);
	
	public PlayerLightBolt (double direction) {
		super ();
		//setVelocityX (speed * Math.cos (direction));
		//setVelocityY (speed * -Math.sin (direction));
		setDirection (direction);
		setSpeed (speed);
		createHitbox (1, 1, 14, 14);
		if (direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
			setSprite (lightBoltUp);
		} else {
			setSprite (lightBoltRight);
		}
		if (direction == DIRECTION_LEFT) {
			setFlipHorizontal (true);
		}
		if (direction == DIRECTION_DOWN) {
			setFlipVertical (true);
		}
		getAnimationHandler ().setAnimationSpeed (.5);
	}

	@Override
	public double getBaseDamage () {
		return Double.POSITIVE_INFINITY;
	}
	
	@Override
	public void projectileFrame () {
		if (getRoom ().isColliding (getHitbox ())) {
			forget ();
		}
	}
}
