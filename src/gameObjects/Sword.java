package gameObjects;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import main.MainLoop;
import main.GameObject;
import main.Hitbox;
import main.ObjectMatrix;

public class Sword extends GameObject {
	Raster alphaRaster;
	BufferedImage usedImage;
	public Sword () {
		this.createHitbox (0, 0, 20, 20);
		this.declare (-32, -32);
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.setSprite (sprites.swordSprites [0]);
	}
	public boolean isCollidingRaster (Hitbox hitbox) {
		usedImage = this.getSprite ().getImageArray () [this.getAnimationHandler ().getFrame ()];
		alphaRaster = usedImage.getAlphaRaster ();
		if (this.getHitbox ().checkOverlap (hitbox)) {
			for (int i = 0; i < usedImage.getWidth (); i ++) {
				for (int j = 0; j < usedImage.getHeight (); j ++) {
					if (i + this.getX () > hitbox.x && i + this.getX () < hitbox.x + hitbox.width && j+ this.getY () > hitbox.y && j + this.getY () < hitbox.y + hitbox.height) {
						if (alphaRaster.getSample (i, j, 0) != 0) {
							return true;
						}
					}
				}
			}
			return false;
		} else {
			return false;
		}
	}
	@Override
	public void draw () {
		//Do nothing
	}
}