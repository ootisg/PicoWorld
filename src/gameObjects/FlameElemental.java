package gameObjects;

import java.awt.Color;
import java.awt.image.Raster;

import main.GameObject;
import main.MainLoop;
import main.ObjectMatrix;
import projectiles.Direction;
import projectiles.EnemyFireball;
import resources.Sprite;

public class FlameElemental extends Enemy {
	Sprite eyes;
	Color particleColor;
	double densityCoefficient;
	double particleSpeedCoefficient;
	double targetX;
	double targetY;
	double speed;
	double sightRange;
	int timer1;
	int timer2;
	public FlameElemental () {
		this.eyes = new Sprite ("resources/sprites/flameEyes.png");
		this.setSprite (getSprites ().frostElemental);
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.createHitbox (0, 0, 16, 16);
		this.particleColor = new Color (0xFF, 0xA5, 0x00, 0x80);
		this.densityCoefficient = .2; //Should be .2 while moving, .07 while idle
		this.particleSpeedCoefficient = .5;
		this.speed = 3;
		this.sightRange = 128;
		this.health = 15;
		this.knockbackMagnitude = 10f;
		targetX = -1;
		targetY = -1;
	}
	@Override
	public void enemyFrame () {
		Raster raster = this.getSprite ().getImageArray () [this.getAnimationHandler ().getFrame ()].getAlphaRaster ();
		if (raster != null) {
			for (int i = 0; i < 16; i ++) {
				for (int j = 0; j < 16; j ++) {
					if (Math.random () * 255 < (raster.getSample (i, j, 0) * densityCoefficient)) {
						new Particle (i + this.getX (), j + this.getY (), particleColor, 1, 30, Math.random () * Math.PI * 2, Math.random () * particleSpeedCoefficient, .25);
					}
				}
			}
		}
		aiFrame ();
	}
	@Override
	public void draw () {
		//this.setX(this.getX () + 2);
		eyes.draw ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY ());
	}
	public void aiFrame () {
		timer1 ++;
		double originX = this.getX () + 8;
		double originY = this.getY () + 8;
		double xOffset = 0;
		double yOffset = 0;
		double tempTargetX = getPlayer ().getX () + 8;
		double tempTargetY = getPlayer ().getY () + 8;
		double tempDistX = tempTargetX - originX;
		double tempDistY = tempTargetY - originY;
		if (!getRoom ().isColliding (originX, originY, tempTargetX, tempTargetY) && tempDistX * tempDistX + tempDistY * tempDistY <= sightRange * sightRange) {
			targetX = getPlayer ().getX () + 8;
			targetY = getPlayer ().getY () + 8;
		}
		if (targetX != -1 && targetY != -1) {
			if (timer1 >= 120) {
				timer1 = 0;
				timer2 = 15;
			}
			if (originX == targetX) {
				if (originY < targetY) {
					originY -= speed;
				} else {
					originY += speed;
				}
			}
			double xDist = targetX - originX;
			double yDist = targetY - originY;
			double unitDistance = Math.sqrt (xDist * xDist + yDist * yDist);
			double ang;
			if (originY < targetY) {
				ang = Math.acos (xDist / unitDistance);
			} else {
				ang = -Math.acos (xDist / unitDistance);
			}
			if (timer2 != 0 && timer2 % 5 == 0) {
				EnemyFireball working = new EnemyFireball (ang);
				working.declare (getX (), getY ());
			}
			xOffset = (xDist / unitDistance) * speed;
			yOffset = (yDist / unitDistance) * speed;
			if (xDist * xDist + yDist * yDist > speed * speed) {
				setX (getX () + xOffset);
				setY (getY () + yOffset);
			}
			xDist = targetX - (getX () + 8);
			yDist = targetY - (getY () + 8);
			if (xDist * xDist + yDist * yDist <= speed * speed) {
				targetX = -1;
				targetY = -1;
			}
		}
		if (timer2 > 0) {
			timer2 --;
		}
	}
}