package gameObjects;

import java.util.ArrayList;

import gui.StatusBar;
import main.GameObject;
import main.MainLoop;
import projectiles.PlayerMagic;
import projectiles.PlayerProjectile;
import projectiles.Projectile;
import resources.Sprite;
import resources.Spritesheet;

public abstract class Enemy extends GameObject implements Damageable, DamageSource {
	int invulTime = 20;
	int invul = 0;
	double health = 100;
	double maxHealth = 100;
	double baseDamage = 10;
	float knockbackX = 0;
	float knockbackY = 0;
	float knockbackMagnitude = 4.5f;
	int knockbackTime = 0;
	
	StatusBar healthBar = null;
	
	@Override
	public void frameEvent () {
		if (invul == 0) {
			if (getPlayer ().swordObject.isCollidingRaster (this.getHitbox ())) {
				this.damageEvent (getPlayer ().swordObject);
				this.showHealthBar ();
			} else {
				ArrayList<GameObject> damageSources = getCollidingChildren (DamageSource.class);
				if (damageSources.size () != 0) {
					DamageSource dmgSrc = null;
					int idx = 0;
					while (idx < damageSources.size () && !damageSources.getClass ().equals (this.getClass ())) {
						dmgSrc = (DamageSource)damageSources.get (idx);
						idx++;
					}
					if (dmgSrc != null && !dmgSrc.getClass ().equals (this.getClass ())) {
						this.damageEvent (dmgSrc);
						if (dmgSrc instanceof Projectile) {
							((Projectile)dmgSrc).hitEvent (this);
						}
					}
				}
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
		} else if (knockbackX != 0 || knockbackY != 0) {
			knockbackX = 0;
			knockbackY = 0;
			onKnockbackEnd ();
		}
		//System.out.println(this.knockbackTime);
	}
	
	@Override
	public void draw () {
		super.draw ();
		if (healthBar != null && healthBar.getFadeTimer () > 0) {
			healthBar.render ((int)(this.getX () - getRoom ().getViewX ()), (int)(this.getY () - getRoom ().getViewY () - 2));
		}
	}
	
	public void attackEvent () {
		getPlayer ().damageEvent (this);
	}
	public void onKnockbackEnd () {
		
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
		if (source instanceof PlayerProjectile) {
			damage (source.getBaseDamage ());
		}
		showHealthBar ();
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
	public void setMaxHealth (double health) {
		this.maxHealth = health;
	}
	
	@Override
	public double getHealth () {
		return this.health;
	}
	
	@Override
	public double getMaxHealth () {
		return maxHealth;
	}
	
	@Override
	public double getBaseDamage () {
		return baseDamage;
	}
	
	public void showHealthBar () {
		if (healthBar == null) {
			this.healthBar = new StatusBar (new Sprite (new Spritesheet ("resources/sprites/itemhealth.png"), 16, 1));
			this.healthBar.setMaxFill (getMaxHealth ());
		}
		healthBar.setCurrentFill (health);
		healthBar.setFadeTimer (80);
	}
	
	public void enemyFrame () {
		
	}

}