package projectiles;

import resources.Sprite;
import resources.Spritesheet;
import util.Vector2D;

public class DarkFlame extends PlayerProjectile {

	public static Spritesheet fireSheet = new Spritesheet ("resources/sprites/dark_flame.png");
	public static Sprite fireSprite = new Sprite (fireSheet, 16, 16);
	
	private Vector2D velocity;
	private int durability;
	private int frameCount;
	
	public DarkFlame (Vector2D velocity) {
		this (velocity, 30);
	}
	
	public DarkFlame (Vector2D velocity, int durability) {
		this.velocity = velocity;
		this.durability = durability;
		setSprite (fireSprite);
		getAnimationHandler ().setAnimationSpeed (.5);
		getAnimationHandler ().setRepeat (false);
		createHitbox (0, 0, 16, 16);
	}
	
	@Override
	public void projectileFrame () {
		frameCount++;
		if (getRoom ().isColliding (getHitbox())) {
			forget ();
		} else {
			if (frameCount == 1 && durability > 0) {
				DarkFlame nextFlame = new DarkFlame (velocity, durability - 1);
				nextFlame.declare (getX () + velocity.x, getY () + velocity.y);
			}
			if (getAnimationHandler ().isDone()) {
				forget ();
			}
		}
	}
	
	@Override
	public double getBaseDamage() {
		return 10;
	}

}
