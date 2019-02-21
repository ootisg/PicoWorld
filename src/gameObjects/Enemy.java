package gameObjects;

import main.GameObject;
import main.MainLoop;

public abstract class Enemy extends GameObject implements Damageable {
	int invulTime = 20;
	int invul = 0;
	double health = 100;
	int baseDamage = 1;
	float knockbackX = 0;
	float knockbackY = 0;
	float knockbackMagnitude = 4.5f;
	int knockbackTime = 0;
	@Override
	public void frameEvent () {
		if (invul == 0) {
			if (getPlayer ().swordObject.isCollidingRaster (this.getHitbox ())) {
				this.damageEvent (getPlayer ().swordObject);
			}
		}
		if (health <= 0) {
			this.deathEvent ();
		}
		enemyFrame ();
		if (isColliding (getPlayer ())) {
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
		getPlayer ().damage (this.baseDamage);
	}
	
	@Override
	public void damageEvent (DamageSource source) {
		invul = invulTime;
		this.knockbackTime = 3;
		switch (getPlayer ().direction) {
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
		if (source instanceof Sword) {
			damage (Integer.parseInt (((Sword) source).getSwordUsed ().getProperty ("attack")));
		}
	}
	public void deathEvent () {
		this.forget ();
	}
	
	@Override
	public void damage (double amount) {
		this.health -= amount;
	}
	
	@Override
	public void setHealth (double health) {
		this.health = health;
	}
	
	@Override
	public double getHealth () {
		return this.health;
	}
	
	public void enemyFrame () {
		
	}
}