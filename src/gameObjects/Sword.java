package gameObjects;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import items.GameItem;
import main.MainLoop;
import main.GameObject;
import main.Hitbox;
import main.ObjectMatrix;

public class Sword extends GameObject implements DamageSource {
	private Raster alphaRaster;
	private BufferedImage usedImage;
	private GameItem swordUsed;
	public Sword () {
		this.createHitbox (0, 0, 20, 20);
		this.declare (-32, -32);
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.setPersistent (true);
	}
	public boolean isCollidingRaster (Hitbox hitbox) {
		if (this.getSprite () != null) {
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
			}
		}
		return false;
	}
	public GameItem getSwordUsed () {
		return swordUsed;
	}
	public void setSwordUsed (GameItem swordUsed) {
		this.swordUsed = swordUsed;
	}
	@Override
	public void draw () {
		//Do nothing
	}
	@Override
	public double getBaseDamage () {
		return 10;
	}
}