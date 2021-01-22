package visualEffects;

import java.awt.Color;
import java.awt.Graphics;

import main.MainLoop;

public class ColorOverlay extends ScreenOverlay {

	private Color overlayColor;
	
	public ColorOverlay (Color c) {
		overlayColor = c;
	}
	
	public Color getColor () {
		return overlayColor;
	}
	
	public void setColor (Color c) {
		overlayColor = c;
	}
	
	@Override
	public void draw () {
		int[] dimensions = MainLoop.getWindow ().getResolution ();
		Graphics g = MainLoop.getWindow ().getBufferGraphics ();
		g.setColor (overlayColor);
		g.fillRect (0, 0, dimensions[0], dimensions[1]);
	}
}
