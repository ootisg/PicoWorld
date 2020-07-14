package projectiles;

import java.awt.Color;

import gameObjects.Particle;

public class PlayerFireball extends PlayerProjectile {
	
	private static final double speed = 10;
	
	private static final int numParticles = 100;
	
	private static final int fieldSize = 16;
	
	public PlayerFireball (double direction) {
		super ();
		setVelocityX (speed * Math.cos (direction));
		setVelocityY (speed * Math.sin (direction));
		createHitbox (1, 1, 14, 14);
		int vert = 0;
		if (direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
			vert = 1;
		}
		setSprite (getSprites ().fireballSprites [vert]);
		if (direction == DIRECTION_LEFT) {
			setFlipHorizontal (true);
		}
		if (direction == DIRECTION_UP) {
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
