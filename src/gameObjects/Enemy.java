package gameObjects;

import main.GameObject;
import main.MainLoop;

public abstract class Enemy extends GameObject {
	int invulTime = 20;
	int invul = 0;
	int health = 100;
	int baseDamage = 1;
	float knockbackX = 0;
	float knockbackY = 0;
	int knockbackTime = 0;
	Player player = (Player) MainLoop.getObjectMatrix ().get ("gameObjects.Player", 0);
	@Override
	public void frameEvent () {
		if (invul == 0) {
			if (((Sword)MainLoop.getObjectMatrix().get ("gameObjects.Sword", 0)).isCollidingRaster (this.getHitbox ())) {
				this.damageEvent ();
			}
		}
		if (health <= 0) {
			this.deathEvent ();
		}
		enemyFrame ();
		if (isColliding (player)) {
			attackEvent ();
		}
		if (invul > 0) {
			invul --;
		}
		if (this.knockbackTime != 0) {
			this.setX (this.getX () + knockbackX);
			this.setY (this.getY () + knockbackY);
			this.knockbackTime --;
		} else {
			knockbackX = 0;
			knockbackY = 0;
		}
		//System.out.println(this.knockbackTime);
	}
	public void attackEvent () {
		player.damage (this.baseDamage);
	}
	public void damageEvent () {
		invul = invulTime;
		this.knockbackTime = 3;
		float knockbackMagnitude = 4.5f;
		switch (player.direction) {
			case Player.DIRECTION_UP:
				this.knockbackY = -knockbackMagnitude;
				break;
			case Player.DIRECTION_LEFT:
				this.knockbackX = -knockbackMagnitude;
				break;
			case Player.DIRECTION_DOWN:
				this.knockbackY = knockbackMagnitude;
				break;
			case Player.DIRECTION_RIGHT:
				this.knockbackX = knockbackMagnitude;
				break;
		}
		System.out.println("733T");
	}
	public void deathEvent () {
		this.forget ();
	}
	public void damage (int amount) {
		this.health -= amount;
	}
	public void setHealth (int health) {
		this.health = health;
	}
	public int getHealth () {
		return this.health;
	}
	public void enemyFrame () {
		
	}
}