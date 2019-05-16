package projectiles;

import java.awt.Color;

import gameObjects.Particle;

public class PlayerMagic extends PlayerProjectile {
	
	private static final double speed = 10;
	
	private static final int numParticles = 100;
	
	private static final int fieldSize = 16;
	
	public PlayerMagic (double direction) {
		super ();
		setVelocityX (speed * Math.cos (direction));
		setVelocityY (speed * Math.sin (direction));
		createHitbox (1, 1, 14, 14);
	}
	
	@Override
	public void draw () {
		int baseRed = 0x66;
		int baseGreen = 0x10;
		int baseBlue = 0x1D;
		for (int i = 0; i < numParticles; i ++) {
			double scale = Math.random ();
			Color used = new Color ((int)(baseRed * scale), (int)(baseGreen * scale), (int)(baseBlue * scale));
			double centerX = getX () + fieldSize / 2;
			double centerY = getY () + fieldSize / 2;
			double particleX = centerX + (Math.random () - .5) * fieldSize;
			double particleY = centerY + (Math.random () - .5) * fieldSize;
			double particleDist = Math.sqrt ((centerX - particleX) * (centerX - particleX) + (centerY - particleY) * (centerY - particleY));
			Color particleColor = used;
			int particleSize = 1;
			double particleSpeed = 1 * Math.random ();
			double particleDirection = Math.PI * 2 * Math.random ();
			double particleDurability = 10;
			double particleDecayProbability = .5;
			if (particleDist <= fieldSize / 2) {
				new Particle (particleX, particleY, used, particleSize, particleDurability, particleDirection, particleSpeed, particleDecayProbability);
			}
		}
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
