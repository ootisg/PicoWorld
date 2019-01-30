package gameObjects;

import java.awt.Color;
import java.awt.image.Raster;

import main.GameObject;
import main.MainLoop;
import main.ObjectMatrix;

public class FrostElemental extends Enemy {
	Color particleColor;
	double densityCoefficient;
	double particleSpeedCoefficient;
	public FrostElemental () {
		this.setSprite (getSprites ().frostElemental);
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.createHitbox (0, 0, 16, 16);
		this.particleColor = new Color (0xFF, 0xFF, 0xFF, 0x80);
		this.densityCoefficient = .07; //Should be .2 while moving, .07 while idle
		this.particleSpeedCoefficient = .2;
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
	}
	@Override
	public void draw () {
		//this.setX(this.getX () + 2);
		getSprites ().frostEyes.draw ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY ());
	}
}