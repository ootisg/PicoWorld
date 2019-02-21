package gameObjects;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import main.GameObject;
import resources.Sprite;

public class Tree extends GameObject {
	public Tree () {
		setSprite (new Sprite ("resources/sprites/tree.png"));
		createHitbox (0, 0, 32, 32);
	}
	@Override
	public boolean isColliding (GameObject object) {
		if (object.getHitbox () == null || this.getHitbox () == null) {
			return false;
		}
		if (this.getHitbox ().checkOverlap (object.getHitbox ())) {
			BufferedImage usedImage = this.getSprite ().getImageArray () [this.getAnimationHandler ().getFrame ()];
			Raster alphaRaster = usedImage.getAlphaRaster ();
			for (int i = 0; i < usedImage.getWidth (); i ++) {
				for (int j = 0; j < usedImage.getHeight (); j ++) {
					if (i + this.getX () > object.getHitbox ().x && i + this.getX () < object.getHitbox ().x + object.getHitbox ().width && j+ this.getY () > object.getHitbox ().y && j + this.getY () < object.getHitbox ().y + object.getHitbox ().height) {
						if (alphaRaster.getSample (i, j, 0) != 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
