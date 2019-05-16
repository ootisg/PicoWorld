package gui;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import resources.Sprite;

public class StatusBar {
	
	private Sprite images;
	
	private int time;
	
	private double current;
	
	private double max;
	
	public StatusBar (Sprite images) {
		this.images = images;
	}
	
	public void render (int x, int y) {
		double opacity = 255;
		if (time <= 20) {
			opacity = (((double)time / 20) * 255.0);
		}
		if (time != 0) {
			int index = (int)(Math.ceil ((current / max) * 14)) - 1;
			if (index < 0) {
				index = 0;
			}
			if (index > images.getImageArray ().length) {
				index = images.getImageArray ().length - 1;
			}
			BufferedImage usedImage = images.getImageArray ()[index];
			WritableRaster r = usedImage.getAlphaRaster ();
			for (int i = 1; i < 15; i ++) {
				r.setPixel (i, 0, new double[] {opacity});
			}
			Sprite.draw (usedImage, x, y);
		}
		if (time > 0) {
			time --;
		}
	}
	
	public void setFadeTimer (int time) {
		this.time = time;
	}
	
	public void setCurrentFill (double current) {
		this.current = current;
	}
	
	public void setMaxFill (double max) {
		this.max = max;
	}
	
	public int getFadeTimer () {
		return time;
	}
}
