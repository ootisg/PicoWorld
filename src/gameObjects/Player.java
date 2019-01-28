package gameObjects;

import java.awt.Color;
import java.awt.event.KeyEvent;

import main.GameObject;
import main.MainLoop;
import resources.Sprite;

public class Player extends GameObject {
	double health = 0;
	double maxHealth = 0;
	int direction = 0;
	double speed = 3;
	int animation;
	int animationFrame;
	boolean swinging = false;
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_RIGHT = 3;
	private static final int[] WEAPON_OFFSETS = new int[] {0, -4, -4, 0, 0, 0, 0, 0};
	Color particleColor;
	Sprite armSprite;
	Sword swordObject;
	public Player (int x, int y) {
		this.declare (x, y);
		this.setSprite (sprites.playerIdle);
		this.armSprite = sprites.playerArmsIdle;
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.createHitbox (0, 0, 16, 16);
		this.animation = 0;
		this.animationFrame = 0;
		this.particleColor = new Color (0xFFFFFF);
		this.swordObject = new Sword ();
	}
	@Override
	public void frameEvent () {
		if (!keyCheck (KeyEvent.VK_SHIFT)) {
			if (!swinging) {
				if (keyCheck ('W')) {
					direction = DIRECTION_UP;
					setY (getY () - speed);
					if (room.isColliding (this.getHitbox ())) {
						//this.backstepY ();
					}
				}
				if (keyCheck ('A')) {
					direction = DIRECTION_LEFT;
					setX (getX () - speed);
					if (room.isColliding (this.getHitbox ())) {
						//this.backstepX ();
					}
				}
				if (keyCheck ('S')) {
					direction = DIRECTION_DOWN;
					setY (getY () + speed);
					if (room.isColliding (this.getHitbox ())) {
						//this.backstepY ();
					}
				}
				if (keyCheck ('D')) {
					direction = DIRECTION_RIGHT;
					setX (getX () + speed);
					if (room.isColliding (this.getHitbox ())) {
						//this.backstepX ();
					}
				}
			}
			if (keyCheck ('W') || keyCheck ('A') || keyCheck ('S') || keyCheck ('D')) {
				if (this.getSprite () != sprites.playerWalkSprites [direction] && !swinging) {
					this.setSprite (sprites.playerWalkSprites [direction]);
					this.armSprite = sprites.playerArmSprites [direction];
					this.getAnimationHandler ().setAnimationSpeed (.25);
				}
			} else if (!swinging) {
				this.setSprite (sprites.playerIdle);
				this.armSprite = sprites.playerArmsIdle;
				this.getAnimationHandler ().setFrame (direction);
				this.getAnimationHandler ().setAnimationSpeed (0);
			}
			if (keyPressed (0x20) && !swinging) {
				this.setSprite (sprites.playerIdle);
				this.armSprite = sprites.swordArmSprites [direction];
				this.getAnimationHandler ().setFrame (direction);
				this.getAnimationHandler ().setAnimationSpeed (0);
				animationFrame = 0;
				swinging = true;
			}
			double x = this.getX ();
			double y = this.getY ();
			int viewX = room.getViewX ();
			int viewY = room.getViewY ();
			int viewportWidth = MainLoop.getWindow ().getResolution ()[0];
			int viewportHeight = MainLoop.getWindow ().getResolution ()[1];
			double scrollSection = .25;
			int leftBound = (int)(viewportWidth * scrollSection);
			int rightBound = viewportWidth - leftBound;
			int topBound = (int)(viewportHeight * scrollSection);
			int bottomBound = viewportHeight - topBound;
			if (y - viewY >= bottomBound && y - bottomBound < room.getHeight () * 16 - viewportHeight) {
				viewY = (int) y - bottomBound;
				room.setView (room.getViewX (), viewY);
			}
			if (y - viewY <= topBound && y - topBound > 0) {
				viewY = (int) y - topBound;
				room.setView (room.getViewX (), viewY);
			}
			if (x - viewX >= rightBound && x - rightBound < room.getWidth () * 16 - viewportWidth) {
				viewX = (int) x - rightBound;
				room.setView (viewX, room.getViewY ());
			}
			if (x - viewX <= leftBound && x - leftBound > 0) {
				viewX = (int) x - leftBound;
				room.setView (viewX, room.getViewY ());
			}
		}
	}
	@Override
	public void draw () {
		if (!swinging) {
			this.armSprite.setFrame (this.getSprite ().getFrame ());
			this.getAnimationHandler ().animate ((int)this.getX () - room.getViewX (), (int)this.getY () - room.getViewY (), false, false);
			this.armSprite.draw ((int)this.getX () - room.getViewX (), (int)this.getY () - room.getViewY ());
		} else {
			this.armSprite.setFrame (animationFrame / 3);
			swordObject.setSprite (sprites.swordSprites [direction]);
			swordObject.getAnimationHandler ().setFrame (animationFrame / 3);
			if (direction != DIRECTION_UP) {
				this.getAnimationHandler ().animate ((int)this.getX () - room.getViewX (), (int)this.getY () - room.getViewY (), false, false);
				this.armSprite.draw ((int)this.getX () - room.getViewX (), (int)this.getY () - room.getViewY ());
				//sprites.swordSprites [direction].draw ((int)this.getX () + WEAPON_OFFSETS [direction * 2], (int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
				swordObject.setX ((int)this.getX () + WEAPON_OFFSETS [direction * 2]);
				swordObject.setY ((int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
				swordObject.getAnimationHandler ().animate ((int)swordObject.getX () - room.getViewX (), (int)swordObject.getY () - room.getViewY (), false, false);
			} else {
				//sprites.swordSprites [direction].draw ((int)this.getX () + WEAPON_OFFSETS [direction * 2], (int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
				swordObject.setX ((int)this.getX () + WEAPON_OFFSETS [direction * 2]);
				swordObject.setY ((int)this.getY () + WEAPON_OFFSETS [direction * 2 + 1]);
				swordObject.getAnimationHandler ().animate ((int)swordObject.getX () - room.getViewX (), (int)swordObject.getY () - room.getViewY (), false, false);
				this.armSprite.draw ((int)this.getX () - room.getViewX (), (int)this.getY () - room.getViewY ());
				this.getAnimationHandler ().animate ((int)this.getX () - room.getViewX (), (int)this.getY () - room.getViewY (), false, false);
			}
			animationFrame ++;
			if (animationFrame == 9) {
				animationFrame = 0;
				swinging = false;
				swordObject.setPosition (-32, -32);
			}
		}
	}
	public void damage(int baseDamage) {
		// TODO Auto-generated method stub
		
	}
}