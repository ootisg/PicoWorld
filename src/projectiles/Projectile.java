package projectiles;

import gameObjects.DamageSource;
import main.GameObject;
import util.Vector2D;

public abstract class Projectile extends GameObject implements DamageSource {
	private double velocityX = 1;
	private double velocityY = 0;
	
	private double direction;
	
	public static final double DIRECTION_INVALID = Double.NaN;
	public static final double DIRECTION_RIGHT = 0;
	public static final double DIRECTION_UP = Math.PI / 2;
	public static final double DIRECTION_LEFT = Math.PI;
	public static final double DIRECTION_DOWN = (Math.PI * 3) / 2;
	
	public static final Vector2D VEC_DIRECTION_NONE = new Vector2D (0, 0);
	public static final Vector2D VEC_DIRECTION_RIGHT = new Vector2D (1, 0);
	public static final Vector2D VEC_DIRECTION_UP = new Vector2D (0, -1);
	public static final Vector2D VEC_DIRECTION_LEFT = new Vector2D (-1, 0);
	public static final Vector2D VEC_DIRECTION_DOWN = new Vector2D (0, 1);
	
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
		return direction;
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
		this.direction = direction;
		this.velocityX = Math.cos (direction) * scalar;
		this.velocityY = -Math.sin (direction) * scalar;
	}
	//Unsure if I'll actually use these
	public static boolean isHorizontalDirection (double direction) {
		if (direction == DIRECTION_RIGHT || direction == DIRECTION_LEFT) {
			return true;
		}
		return false;
	}
	public static boolean isVerticalDirection (double direction) {
		if (direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
			return true;
		} else {
			return false;
		}
	}
}