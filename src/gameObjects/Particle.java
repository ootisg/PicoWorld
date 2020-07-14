package gameObjects;

import java.awt.Color;

import main.GameObject;
import main.MainLoop;

public class Particle extends GameObject {
	public Color color;
	public int size;
	public double durability;
	public double direction;
	public double speed;
	public Particle (double x, double y, Color color, int size, double durability) {
		this.declare (x, y);
		this.color = color;
		this.size = size;
		this.durability = durability;
		this.direction = 0;
		this.speed = 0;
	}
	public Particle (double x, double y, Color color, int size, double durability, double direction, double speed) {
		this.declare (x, y);
		this.color = color;
		this.size = size;
		this.durability = durability;
		this.direction = direction;
		this.speed = speed;
	}
	public Particle (double x, double y, Color color, int size, double durability, double direction, double speed, double randomDecayProbability) {
		this.declare (x, y);
		this.color = color;
		this.size = size;
		this.durability = durability - Math.random () * durability * randomDecayProbability;
		this.direction = direction;
		this.speed = speed;
	}
	@Override
	public void frameEvent () {
		if (durability <= 0) {
			this.forget ();
		}
		durability -= 1;
		if (speed != 0) {
			this.setX (this.getX () + speed * Math.cos (direction));
			this.setY (this.getY () - speed * Math.sin (direction));
		}
	}
	@Override
	public void draw () {
		MainLoop.getWindow ().getBufferGraphics ().setColor (this.color);
		MainLoop.getWindow ().getBufferGraphics ().fillRect ((int)this.getX () - getRoom ().getViewX (), (int)this.getY () - getRoom ().getViewY (), size, size);
	}
}
