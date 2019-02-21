package gameObjects;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import items.GameItem;
import main.GameObject;
import main.MainLoop;
import resources.Sprite;
import resources.Spritesheet;

public class Player extends GameObject implements Damageable {
	double health = 50;
	double maxHealth = 50;
	int direction = 0;
	int attackDamage = 0;
	double speed = 3;
	int animation;
	int animationFrame;
	int invulTime = 0;
	int invulLength = 10;
	int healthbarTimer = 0;
	boolean swinging = false;
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_RIGHT = 3;
	private static final int[] WEAPON_OFFSETS = new int[] {0, -4, -4, 0, 0, 0, 0, 0};
	private static Sprite healthbarSprite;
	Color particleColor;
	Sprite armSprite;
	Sword swordObject;
	Sprite[] swordSprites;
	public Player () {
		this.setSprite (getSprites ().playerIdle);
		this.armSprite = getSprites ().playerArmsIdle;
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.createHitbox (0, 0, 16, 16);
		this.animation = 0;
		this.animationFrame = 0;
		this.particleColor = new Color (0xFFFFFF);
		this.swordObject = new Sword ();
		this.healthbarSprite = new Sprite (new Spritesheet ("resources/sprites/itemhealth.png"), 16, 1);
		setPersistent (true);
	}
	@Override
	public void frameEvent () {
		if (invulTime != 0) {
			invulTime --;
		}
		if (!keyCheck (KeyEvent.VK_SHIFT)) {
			if (!swinging) {
				if (keyCheck ('W')) {
					direction = DIRECTION_UP;
					setY (getY () - speed);
					if (collidingWithBarrier ()) {
						this.backstepY ();
					}
				}
				if (keyCheck ('A')) {
					direction = DIRECTION_LEFT;
					setX (getX () - speed);
					if (collidingWithBarrier ()) {
						this.backstepX ();
					}
				}
				if (keyCheck ('S')) {
					direction = DIRECTION_DOWN;
					setY (getY () + speed);
					if (collidingWithBarrier ()) {
						this.backstepY ();
					}
				}
				if (keyCheck ('D')) {
					direction = DIRECTION_RIGHT;
					setX (getX () + speed);
					if (collidingWithBarrier ()) {
						this.backstepX ();
					}
				}
			}
			if (keyCheck ('W') || keyCheck ('A') || keyCheck ('S') || keyCheck ('D')) {
				if (this.getSprite () != getSprites ().playerWalkSprites [direction] && !swinging) {
					this.setSprite (getSprites ().playerWalkSprites [direction]);
					this.armSprite = getSprites ().playerArmSprites [direction];
					this.getAnimationHandler ().setAnimationSpeed (.25);
				}
			} else if (!swinging) {
				this.setSprite (getSprites ().playerIdle);
				this.armSprite = getSprites ().playerArmsIdle;
				this.getAnimationHandler ().setFrame (direction);
				this.getAnimationHandler ().setAnimationSpeed (0);
			}
			if (keyPressed (0x20) && !swinging) {
				GameItem equippedWeapon = getGui ().getItemMenu ().getEquippedWeapon ();
				if (equippedWeapon != null) {
					equippedWeapon.use ();
				}
			}
			double x = this.getX ();
			double y = this.getY ();
			int viewX = getRoom ().getViewX ();
			int viewY = getRoom ().getViewY ();
			int viewportWidth = MainLoop.getWindow ().getResolution ()[0];
			int viewportHeight = MainLoop.getWindow ().getResolution ()[1];
			double scrollSection = .25;
			int leftBound = (int)(viewportWidth * scrollSection);
			int rightBound = viewportWidth - leftBound;
			int topBound = (int)(viewportHeight * scrollSection);
			int bottomBound = viewportHeight - topBound;
			if (y - viewY >= bottomBound && y - bottomBound < getRoom ().getHeight () * 16 - viewportHeight) {
				viewY = (int) y - bottomBound;
				getRoom ().setView (getRoom ().getViewX (), viewY);
			}
			if (y - viewY <= topBound && y - topBound > 0) {
				viewY = (int) y - topBound;
				getRoom ().setView (getRoom ().getViewX (), viewY);
			}
			if (x - viewX >= rightBound && x - rightBound < getRoom ().getWidth () * 16 - viewportWidth) {
				viewX = (int) x - rightBound;
				getRoom ().setView (viewX, getRoom ().getViewY ());
			}
			if (x - viewX <= leftBound && x - leftBound > 0) {
				viewX = (int) x - leftBound;
				getRoom ().setView (viewX, getRoom ().getViewY ());
			}
		}
		if (health <= 0) {
			health = maxHealth;
			MainLoop.getConsole ().enable ("You died oof");
		}
	}
	@Override
	public void draw () {
		if (invulTime == 0 || (invulTime / 4) % 2 != 0) {
			if (!swinging) {
				this.armSprite.setFrame (this.getSprite ().getFrame ());
				this.getAnimationHandler ().animate ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY (), false, false);
				this.armSprite.draw ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY ());
			} else {
				this.armSprite.setFrame (animationFrame / 3);
				swordObject.setSprite (swordSprites [direction]);
				swordObject.getAnimationHandler ().setFrame (animationFrame / 3);
				if (direction != DIRECTION_UP) {
					this.getAnimationHandler ().animate ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY (), false, false);
					this.armSprite.draw ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY ());
					//swordSprites [direction].draw ((int)this.getX () + WEAPON_OFFSETS [direction * 2], (int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
					swordObject.setX ((int)this.getX () + WEAPON_OFFSETS [direction * 2]);
					swordObject.setY ((int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
					swordObject.getAnimationHandler ().animate ((int)swordObject.getX () - getRoom ().getViewX (), (int)swordObject.getY () - getRoom ().getViewY (), false, false);
				} else {
					//swordSprites [direction].draw ((int)this.getX () + WEAPON_OFFSETS [direction * 2], (int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
					swordObject.setX ((int)this.getX () + WEAPON_OFFSETS [direction * 2]);
					swordObject.setY ((int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
					swordObject.getAnimationHandler ().animate ((int)swordObject.getX () - getRoom ().getViewX (), (int)swordObject.getY () - getRoom ().getViewY (), false, false);
					this.armSprite.draw ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY ());
					this.getAnimationHandler ().animate ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY (), false, false);
				}
				animationFrame ++;
				if (animationFrame == 9) {
					animationFrame = 0;
					swinging = false;
					swordObject.setPosition (-32, -32);
				}
			}
		}
		if (healthbarTimer != 0) {
			if (healthbarTimer <= 20) {
				drawHealthbar (((double)healthbarTimer / 20) * 255.0);
			} else {
				drawHealthbar (255);
			}
		}
		if (healthbarTimer > 0) {
			healthbarTimer --;
		}
	}
	public void drawHealthbar (double opacity) {
		BufferedImage usedImage = healthbarSprite.getImageArray ()[(int)(Math.ceil ((health / maxHealth) * 14)) - 1];
		WritableRaster r = usedImage.getAlphaRaster ();
		for (int i = 1; i < 15; i ++) {
			r.setPixel (i, 0, new double[] {opacity});
		}
		Sprite.draw (usedImage, (int)getX () - getRoom ().getViewX (), (int)getY () - 2 - getRoom ().getViewY ());
	}
	public void useSword (GameItem weapon) {
		Spritesheet sheet = new Spritesheet ("resources/sprites/" + weapon.getName () + "Sheet.png");
		swordSprites = new Sprite[] {
				new Sprite (sheet, new int[] {0, 20, 40}, new int[] {0, 0, 0}, 20, 20),
				new Sprite (sheet, new int[] {0, 20, 40}, new int[] {20, 20, 20}, 20, 20),
				new Sprite (sheet, new int[] {0, 20, 40}, new int[] {40, 40, 40}, 20, 20),
				new Sprite (sheet, new int[] {0, 20, 40}, new int[] {60, 60, 60}, 20, 20)
		};
		swordObject.setSwordUsed (weapon);
		this.setSprite (getSprites ().playerIdle);
		this.armSprite = getSprites ().swordArmSprites [direction];
		this.getAnimationHandler ().setFrame (direction);
		this.getAnimationHandler ().setAnimationSpeed (0);
		animationFrame = 0;
		swinging = true;
	}
	public boolean collidingWithBarrier () {
		if (getRoom ().isColliding (getHitbox ()) || isColliding ("gameObjects.Tree")) {
			return true;
		}
		return false;
	}
	public void focusView () {
		int windowWidth = MainLoop.getWindow ().getResolution () [0];
		int windowHeight = MainLoop.getWindow ().getResolution () [1];
		int newViewX = (int)(getX () - windowWidth / 2);
		int newViewY = (int)(getY () - windowHeight / 2);
		if (newViewX + windowWidth > getRoom ().getWidth () * 16) {
			newViewX = getRoom ().getWidth () * 16 - windowWidth;
		}
		if (newViewY + windowHeight > getRoom ().getHeight () * 16) {
			newViewY = getRoom ().getHeight () * 16 - windowHeight;
		}
		if (newViewX < 0) {
			newViewX = 0;
		}
		if (newViewY < 0) {
			newViewY = 0;
		}
		getRoom ().setView (newViewX, newViewY);
	}
	public double getMaxHealth () {
		return maxHealth;
	}
	public void setMaxHealth (double maxHealth) {
		this.maxHealth = maxHealth;
	}
	@Override
	public void damage (double amount) {
		health -= amount;
	}
	
	@Override
	public void damageEvent (DamageSource source) {
		if (invulTime == 0) {
			damage (source.getBaseDamage ());
			healthbarTimer = 100;
			invulTime = 10;
		}
	}
	@Override
	public double getHealth () {
		return health;
	}
	@Override
	public void setHealth (double health) {
		this.health = health;
	}
}