package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import ai.Pathfinder;
import gui.ColorMap;
import gui.HealthBar;
import gui.InteractBubble;
import gui.Interactable;
import gui.ManaBar;
import gui.StatusBar;
import items.GameItem;
import main.GameObject;
import main.MainLoop;
import projectiles.MagicRing;
import projectiles.PlayerMagic;
import projectiles.Projectile;
import resources.Sprite;
import resources.Spritesheet;
import spriteParsers.JigsawFilter;
import spriteParsers.PixelParser;
import util.Vector2D;
import visualEffects.Outline;

public class Player extends GameObject implements Damageable {
	double health = 0;
	double maxHealth = 0;
	double mana = 0;
	double maxMana = 0;
	int direction = 0;
	int attackDamage = 0;
	double speed = 3;
	double attackSpeed = 2;
	int animation;
	int animationFrame;
	int invulTime = 0;
	int invulLength = 10;
	int healthbarTimer = 0;
	boolean swinging = false;
	boolean magicSelected = false;
	boolean noclip = false;
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_RIGHT = 3;
	public static final double[] RADIAN_DIRECTION_MAP = new double[] {Projectile.DIRECTION_UP, Projectile.DIRECTION_LEFT, Projectile.DIRECTION_DOWN, Projectile.DIRECTION_RIGHT};
	public static final Vector2D[] VECTOR_DIRECTION_MAP = new Vector2D[] {
			Projectile.VEC_DIRECTION_UP,
			Projectile.VEC_DIRECTION_LEFT,
			Projectile.VEC_DIRECTION_DOWN,
			Projectile.VEC_DIRECTION_RIGHT
	};
	private static final int[] WEAPON_OFFSETS = new int[] {0, -4, -4, 0, 0, 0, 0, 0};
	private static ArrayList<Point> weaponOffsets;
	private static StatusBar healthBar;
	private static StatusBar manaBar;
	private static LinkedList<StatusBar> statusBars;
	Color particleColor;
	Sprite armSprite;
	Sword swordObject;
	Sprite[] swordSprites;
	SmallCollider sc;
	InteractBubble bubble;
	HealthBar healthBar2;
	ManaBar manaBar2;
	public Player () {
		this.setSprite (getSprites ().playerIdle);
		this.armSprite = getSprites ().playerArmsIdle;
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.createHitbox (4, 1, 8, 14);
		this.animation = 0;
		this.animationFrame = 0;
		this.particleColor = new Color (0xFFFFFF);
		this.swordObject = new Sword ();
		this.healthBar = new StatusBar (new Sprite (new Spritesheet ("resources/sprites/itemhealth.png"), 16, 1));
		this.manaBar = new StatusBar (new Sprite (new Spritesheet ("resources/sprites/manabar.png"), 16, 1));
		this.healthBar2 = new HealthBar ();
		this.manaBar2 = new ManaBar ();
		this.setVariantAttribute ("layer", "0");
		healthBar2.declare (0, 0);
		manaBar2.declare (0, 16);
		statusBars = new LinkedList<StatusBar> ();
		bubble = new InteractBubble (32);
		bubble.declare (0, 0);
		setMaxHealth (50);
		setHealth (50);
		setMaxMana (50);
		setMana (50);
		setPersistent (true);
	}
	@Override
	public void frameEvent () {
		healthBar2.setMaxFill (maxHealth);
		healthBar2.setFillAmt (health);
		manaBar2.setMaxFill (maxMana);
		manaBar2.setFillAmt (mana);
		bubble.setCenter (getCenterX (), getCenterY ());
		if (invulTime != 0) {
			invulTime --;
		}
		if (keyPressed ('V')) {
			for (int wx = 0; wx < 32; wx++) {
				for (int wy = 0; wy < 32; wy++) {
					new Isopod ().declare (getX () + wx * 32, getY () + wy * 32);
				}
			}
		}
		if (keyPressed (KeyEvent.VK_SHIFT)) {
			if (magicSelected) {
				if (getGui ().getItemMenu ().getEquippedWeapon () != null) {
					magicSelected = false;
				}
			} else {
				if (getGui ().getItemMenu ().getEquippedSpell () != null) {
					magicSelected = true;
				}
			}
		}
		if (/*!keyCheck (KeyEvent.VK_SHIFT)*/true) {
			if (!swinging) {
				if (keyCheck ('W')) {
					direction = DIRECTION_UP;
					setY (getY () - speed);
					if (!noclip && collidingWithBarrier ()) {
						this.backstepY ();
					}
				}
				if (keyCheck ('A')) {
					direction = DIRECTION_LEFT;
					setX (getX () - speed);
					if (!noclip && collidingWithBarrier ()) {
						this.backstepX ();
					}
				}
				if (keyCheck ('S')) {
					direction = DIRECTION_DOWN;
					setY (getY () + speed);
					if (!noclip && collidingWithBarrier ()) {
						this.backstepY ();
					}
				}
				if (keyCheck ('D')) {
					direction = DIRECTION_RIGHT;
					setX (getX () + speed);
					if (!noclip && collidingWithBarrier ()) {
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
			if (keyPressed (0x20)) {
				int checkX = (int)getX () + 7;
				int checkY = (int)getY () + 7;
				switch (direction) {
					case DIRECTION_UP:
						checkY -= 16;
						break;
					case DIRECTION_LEFT:
						checkX -= 16;
						break;
					case DIRECTION_DOWN:
						checkY += 16;
						break;
					case DIRECTION_RIGHT:
						checkX += 16;
						break;
				}
				sc = new SmallCollider (Interactable.class, checkX, checkY);
				Interactable interactedObject = sc.collide ();
				if (interactedObject == null) {
					if (magicSelected) {
						GameItem equippedSpell = getGui ().getItemMenu ().getEquippedSpell ();
						if (equippedSpell != null) {
							equippedSpell.use ();
						}
					} else {
						if (!swinging) {
							GameItem equippedWeapon = getGui ().getItemMenu ().getEquippedWeapon ();
							if (equippedWeapon != null) {
								equippedWeapon.use ();
							}
						}
					}
				} else {
					interactedObject.click ();
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
	public void onDeclare () {
		//Use the startpos if present
		ArrayList<GameObject> startpos = MainLoop.getObjectMatrix ().getObjects ("gameObjects.Startpos");
		if (startpos != null && startpos.size () != 0) {
			this.setX (startpos.get (0).getX ());
			this.setY (startpos.get (0).getY ());
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
				this.armSprite.setFrame ((int)(animationFrame / attackSpeed));
				swordObject.setSprite (swordSprites [direction]);
				swordObject.getAnimationHandler ().setFrame ((int)(animationFrame / attackSpeed));
				if (direction != DIRECTION_UP) {
					this.getAnimationHandler ().animate ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY (), false, false);
					this.armSprite.draw ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY ());
					//swordSprites [direction].draw ((int)this.getX () + WEAPON_OFFSETS [direction * 2], (int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
					swordObject.setX ((int)this.getX () + weaponOffsets.get (direction * 3 + swordObject.getAnimationHandler ().getFrame ()).x);
					swordObject.setY ((int)this.getY () + weaponOffsets.get (direction * 3 + swordObject.getAnimationHandler ().getFrame ()).y);
					swordObject.getAnimationHandler ().animate ((int)swordObject.getX () - getRoom ().getViewX (), (int)swordObject.getY () - getRoom ().getViewY (), false, false);
				} else {
					//swordSprites [direction].draw ((int)this.getX () + WEAPON_OFFSETS [direction * 2], (int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
					swordObject.setX ((int)this.getX () + weaponOffsets.get (direction * 3 + swordObject.getAnimationHandler ().getFrame ()).x);
					swordObject.setY ((int)this.getY () + weaponOffsets.get (direction * 3 + swordObject.getAnimationHandler ().getFrame ()).y);
					swordObject.getAnimationHandler ().animate ((int)swordObject.getX () - getRoom ().getViewX (), (int)swordObject.getY () - getRoom ().getViewY (), false, false);
					this.armSprite.draw ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY ());
					this.getAnimationHandler ().animate ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY (), false, false);
				}
				animationFrame ++;
				if (animationFrame >= (int)(attackSpeed * 3)) {
					animationFrame = 0;
					swinging = false;
					swordObject.setPosition (-32, -32);
				}
			}
		}
		Iterator<StatusBar> iter = statusBars.iterator ();
		int offset = -2;
		while (iter.hasNext ()) {
			StatusBar working = iter.next ();
			if (working.getFadeTimer () <= 0) {
				statusBars.remove (working);
			} else {
				working.render ((int)(this.getX () - getRoom ().getViewX ()), (int)(this.getY () - getRoom ().getViewY () + offset));
			}
			offset -= 4;
		}
		if (keyCheck (KeyEvent.VK_SHIFT)) {
			if (magicSelected) {
				GameItem working = getGui ().getItemMenu ().getEquippedSpell ();
				if (working != null) {
					working.draw (0, 0);
				}
			} else {
				GameItem working = getGui ().getItemMenu ().getEquippedWeapon ();
				if (working != null) {
					working.draw (0, 0);
				}
			}
		}
	}
	public void useSword (GameItem weapon) {
		Spritesheet sheet = new Spritesheet ("resources/sprites/" + weapon.getName () + "Sheet.png");
		swordSprites = new Sprite[] {
				new Sprite (sheet, new int[] {0, 16, 32}, new int[] {0, 0, 0}, 16, 16),
				new Sprite (sheet, new int[] {0, 16, 32}, new int[] {16, 16, 16}, 16, 16),
				new Sprite (sheet, new int[] {0, 16, 32}, new int[] {32, 32, 32}, 16, 16),
				new Sprite (sheet, new int[] {0, 16, 32}, new int[] {48, 48, 48}, 16, 16)
		};
		sheet = new Spritesheet ("resources/sprites/gold_sword_sheet_meta.png");
		Sprite swordMeta = new Sprite (sheet, 16, 16);
		sheet = new Spritesheet ("resources/sprites/sword_arm_meta.png");
		Sprite armMeta = new Sprite (sheet, 16, 16);
		weaponOffsets = JigsawFilter.filter (PixelParser.parse (swordMeta), PixelParser.parse (armMeta), 0x0000FF);
		swordObject.setSwordUsed (weapon);
		this.setSprite (getSprites ().playerIdle);
		this.armSprite = getSprites ().swordArmSprites [direction];
		this.getAnimationHandler ().setFrame (direction);
		this.getAnimationHandler ().setAnimationSpeed (0);
		animationFrame = 0;
		swinging = true;
	}
	public boolean collidingWithBarrier () {
		/*if (isColliding ("gameObjects.RainbowBlock")) {
			((RainbowBlock)(getCollidingObjects ("gameObjects.RainbowBlock").get (0))).setVelocities (getDirection ());
			return true;
		}*/
		if (getRoom ().isColliding (getHitbox ()) || isColliding ("gameObjects.Tree") || isColliding ("gameObjects.BerryBush") || isColliding ("gameObjects.InfusionAltar")) {
			return true;
		}
		if (isColliding ("gameObjects.Windmill")) {
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
	
	public double getMaxMana () {
		return maxMana;
	}
	
	public void setMaxHealth (double maxHealth) {
		this.maxHealth = maxHealth;
		healthBar.setMaxFill (maxHealth);
	}
	
	public void setMaxMana (double maxMana) {
		this.maxMana = maxMana;
		manaBar.setMaxFill (maxMana);
	}
	
	public void depleteMana (double amount) {
		setMana (mana - amount);
		manaBar.setFadeTimer (80);
		statusBars.remove (manaBar);
		statusBars.add (manaBar);
	}
	
	@Override
	public void damage (double amount) {
		setHealth (health - amount);
		healthBar.setFadeTimer (80);
		statusBars.remove (healthBar);
		statusBars.add (healthBar);
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
	
	public double getMana () {
		return mana;
	}
	
	@Override
	public void setHealth (double health) {
		if (health > maxHealth) {
			health = maxHealth;
		} else {
			this.health = health;
		}
		healthBar.setCurrentFill (health);
	}
	
	public void setMana (double mana) {
		if (mana > maxMana) {
			mana = maxMana;
		} else {
			this.mana = mana;
		}
		manaBar.setCurrentFill (mana);
	}
	
	public int getDirection () {
		return direction;
	}
	
	public Vector2D getFacingDirection () {
		switch (direction) {
			case DIRECTION_UP:
				return new Vector2D (0, -1);
			case DIRECTION_LEFT:
				return new Vector2D (-1, 0);
			case DIRECTION_DOWN:
				return new Vector2D (0, 1);
			case DIRECTION_RIGHT:
				return new Vector2D (1, 0);
			default:
				return new Vector2D (0, 0);
		}
	}
}