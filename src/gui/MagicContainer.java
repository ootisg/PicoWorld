package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.MainLoop;
import resources.Sprite;

public class MagicContainer extends GuiComponent {
	
	private static Sprite overlay = new Sprite ("resources/sprites/magic_container.png");
	private double fill;
	private double maxFill = 1;
	
	private Color fillColor;
	
	public MagicContainer () {
		super ();
		fill = .5;
		fillColor = new Color (0x00FFFF);
	}
	
	public void setMaxFill (double fill) {
		maxFill = fill;
	}
	
	public void setFill (double fill) {
		this.fill = fill;
	}
	
	public boolean add (double amount) {
		if (fill == maxFill) {
			return false;
		} else if (fill + amount >= maxFill) {
			fill = maxFill;
		} else {
			fill += amount;
		}
		return true;
	}
	
	public double getMaxFill () {
		return maxFill;
	}
	
	public double getFill () {
		return fill;
	}
	
	public double getPercentFull () {
		return (fill / maxFill) * 100;
	}
	
	public boolean full () {
		return fill == maxFill;
	}
	
	public void setFillColor (Color color) {
		fillColor = color;
	}
	
	public Color getFillColor () {
		return fillColor;
	}
	
	@Override
	public void draw () {
		int fillAmt = (int)((fill / maxFill) * 22);
		Graphics g = MainLoop.getWindow ().getBufferGraphics ();
		g.setColor (fillColor);
		g.fillRect ((int)getX (), (int)(getY () + 23 - fillAmt), 16, (int)fillAmt);
		overlay.draw ((int)getX (), (int)getY ());
	}
}
