package resources;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.MainLoop;

public class Sprite {
	private BufferedImage[] imageArray;
	private boolean isAnimated;
	private int frame;
	private int width;
	private int height;
	public Sprite (BufferedImage image) {
		imageArray = new BufferedImage[] {image};
		width = image.getWidth ();
		height = image.getHeight ();
		isAnimated = false;
	}
	public Sprite (BufferedImage[] images) {
		imageArray = images;
		width = images [0].getWidth ();
		height = images [0].getHeight ();
		isAnimated = true;
	}
	public Sprite (String filepath) {
		imageArray = new BufferedImage[1];
		try {
			imageArray [0] = ImageIO.read (new File (filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = imageArray [0].getWidth ();
		height = imageArray [0].getHeight ();
		isAnimated = false;
	}
	public Sprite (String[] filepaths) {
		int files = filepaths.length;
		imageArray = new BufferedImage[files];
		for (int i = 0; i < files; i ++) {
			try {
				imageArray [i] = ImageIO.read (new File (filepaths [i]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		width = imageArray [0].getWidth ();
		height = imageArray [0].getHeight ();
		isAnimated = true;
	}
	public Sprite (Spritesheet spritesheet, int x, int y, int width, int height) {
		imageArray = new BufferedImage[1];
		imageArray[0] = spritesheet.getImage ().getSubimage (x, y, width, height);
		this.width = width;
		this.height = height;
		isAnimated = true;
	}
	public Sprite (Spritesheet spritesheet, int[] x, int[] y, int width, int height) {
		imageArray = new BufferedImage[x.length];
		if (x.length == y.length) {
			for (int i = 0; i < x.length; i ++) {
				imageArray [i] = spritesheet.getImage ().getSubimage (x[i], y[i], width, height);
			}
		}
		this.width = width;
		this.height = height;
		isAnimated = true;
	}
	public Sprite (Spritesheet spritesheet, int width, int height) {
		int sheetWidth = spritesheet.getWidth ();
		int sheetHeight = spritesheet.getHeight ();
		imageArray = new BufferedImage[(int) (Math.floor (sheetHeight / height) * Math.floor (sheetWidth / width))];
		for (int i = 0; i < Math.floor (sheetHeight / height); i ++) {
			for (int c = 0; c < Math.floor (sheetWidth / width); c ++) {
				imageArray [i * (sheetWidth / width) + c] = spritesheet.getImage ().getSubimage (c * width, i * height, width, height);
			}
		}
		this.width = width;
		this.height = height;
		isAnimated = true;
	}
	public Sprite getRotatedSprite (double angle) {
		BufferedImage[] newImages = new BufferedImage[imageArray.length];
		int newWidth;
		if (width > height) {
			newWidth = width;
		} else {
			newWidth = height;
		}
		newWidth = (int)Math.ceil (width * Math.sqrt (2));
		int centerX = newWidth / 2;
		int centerY = newWidth / 2;
		for (int i = 0; i < newImages.length; i ++) {
			BufferedImage workingImage = imageArray [i];
			BufferedImage newImage = new BufferedImage (newWidth, newWidth, workingImage.getType ());
			AffineTransform transform = new AffineTransform ();
			transform.translate (centerX - workingImage.getWidth () / 2, centerY - workingImage.getHeight () / 2);
			transform.rotate (angle, width / 2, height / 2);
			AffineTransformOp operation = new AffineTransformOp (transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			operation.filter (workingImage, newImage);
			newImages [i] = newImage;
		}
		return new Sprite (newImages);
	}
	public static void draw (BufferedImage image, int x, int y) {
		Graphics bufferImage = MainLoop.getWindow ().getBuffer ();
		if (bufferImage != null) {
			bufferImage.drawImage (image, x, y, null);
		}
	}
	public void draw (int x, int y) {
		this.draw (x, y, frame);
	}
	public void draw (int x, int y, int frame) {
		Sprite.draw (this.imageArray [frame], x, y);
	}
	public void draw (int x, int y, boolean flipHorizontal, boolean flipVertical) {
		this.draw (x, y, frame, flipHorizontal, flipVertical);
	}
	public void draw (int x, int y, int frame, boolean flipHorizontal, boolean flipVertical) {
		int x1, x2, y1, y2;
		if (flipHorizontal) {
			x1 = width;
			x2 = 0;
		} else {
			x1 = 0;
			x2 = width;
		}
		if (flipVertical) {
			y1 = height;
			y2 = 0;
		} else {
			y1 = 0;
			y2 = height;
		}
		Graphics bufferImage = MainLoop.getWindow ().getBuffer ();
		if (bufferImage != null) {
			bufferImage.drawImage (this.imageArray [frame], x, y, x + width, y + height, x1, y1, x2, y2, null);
		}
	}
	public void setFrame (int frame) {
		this.frame = frame;
	}
	public int getFrame () {
		return frame;
	}
	public void setIsAnimated (boolean isAnimated) {
		this.isAnimated = isAnimated;
	}
	public boolean getIsAnimated () {
		return isAnimated;
	}
	public BufferedImage[] getImageArray () {
		return imageArray;
	}
	public int getFrameCount () {
		return imageArray.length;
	}
	public int getWidth () {
		return width;
	}
	public int getHeight () {
		return height;
	}
}