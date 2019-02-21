package projectiles;

import gameObjects.DamageSource;
import main.GameObject;

public abstract class Projectile extends GameObject implements DamageSource {
	private double velocityX;
	private double velocityY;
	
	@Override
	public void frameEvent () {
		this.setX (this.getX () + velocityX);
		this.setY (this.getY () + velocityY);
		projectileFrame ();
	}
	public void projectileFrame () {
		
	}
	public double getVelocityX () {
		return this.velocityX;
	}
	public double getVelocityY () {
		return this.velocityY;
	}
	public double getSpeed () {
		return Math.sqrt (this.velocityX * this.velocityX + this.velocityY * this.velocityY);
	}
	public double getDirection () {
		if (velocityY < 0) {
			return Math.atan (-velocityY / velocityX);
		} else {
			return Math.atan (-velocityY / velocityX) + Math.PI / 2;
		}
	}
	public void setVelocityX (double velocityX) {
		this.velocityX = velocityX;
	}
	public void setVelocityY (double velocityY) {
		this.velocityY = velocityY;
	}
	public void setSpeed (double speed) {
		double scalar = speed / getSpeed ();
		this.velocityX *= scalar;
		this.velocityY *= scalar;
	}
	public void setDirection (double direction) {
		double scalar = getSpeed ();
		this.velocityX = Math.cos (direction) * scalar;
		this.velocityY = Math.sin (direction) * scalar;
	}
}