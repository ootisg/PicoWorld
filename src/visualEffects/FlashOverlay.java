package visualEffects;

import java.awt.Color;
import util.UnitTransform;

public class FlashOverlay extends ColorOverlay {
	
	private Color startColor;
	private Color endColor;
	private FlashGenerator fg;
	
	private long creationTime;
	
	public FlashOverlay (Color startColor, Color endColor, int startLength, int peakLength, int endLength) {
		super ();
		this.startColor = startColor;
		this.endColor = endColor;
		this.fg = new FlashGenerator (startLength, peakLength, endLength);
		this.creationTime = System.currentTimeMillis ();
	}
	
	@Override
	public void draw () {
		double t = System.currentTimeMillis () - creationTime;
		float fv = (float)fg.transform (t);
		System.out.println(fv);
		float r = ((float)(1 - (startColor.getRed () / 255)) + fv);
		float g = ((float)(1 - (startColor.getGreen () / 255)) + fv);
		float b = ((float)(1 - (startColor.getBlue () / 255)) + fv);
		float a = ((float)(1 - (startColor.getAlpha () / 255)) + fv);
		this.setColor (new Color (r, g, b, .5f));
		super.draw ();
	}
	
	public static class FlashGenerator implements UnitTransform {
		
		private int startLength;
		private int peakLength;
		private int endLength;
		
		public FlashGenerator (int startLength, int peakLength, int endLength) {
			this.startLength = startLength;
			this.peakLength = peakLength;
			this.endLength = endLength;
		}

		@Override
		public double transform (double t) {
			double nt = 0;
			if (t < startLength) {
				nt = t / startLength;
			} else if (t < startLength + peakLength) {
				nt = 1;
			} else if (t < startLength + peakLength + endLength) {
				nt = 1 - ((t - startLength - peakLength) / endLength);
			}
			//Bound and return the new t value
			if (nt < 0) {
				return 0;
			}
			if (nt > 1) {
				return 1;
			}
			return nt;
		}
		
	}
}
