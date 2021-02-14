package projectiles;

import java.awt.Color;
import java.awt.Point;

import main.GameObject;
import main.MainLoop;
import resources.Sprite;
import resources.Spritesheet;
import util.Vector2D;
import visualEffects.ParticleMaker;

public class MagicRing extends PlayerProjectile {

	public static final int MAX_DRB = 3;
	
	public static Spritesheet ringSheet = new Spritesheet ("resources/sprites/magic_ring.png");
	public static Sprite ringSprite = new Sprite (ringSheet, 16, 16);
	
	double vx = 0;
	double vy = 0;
	double ax = 0;
	double ay = 0;
	
	int time = 0;
	
	int drb;
	
	private Color particleColor;
	
	public MagicRing () {
		super ();
		setSprite (ringSprite);
		getAnimationHandler ().setAnimationSpeed (.5);
		createHitbox (0, 0, 16, 16);
		
		drb = MAX_DRB;
		setParticleColor (new Color (0x000000));
	}
	
	public MagicRing (Vector2D initialVelocity) {
		this ();
		vx = initialVelocity.x;
		vy = initialVelocity.y;
	}
	
	@Override
	public double getBaseDamage () {
		return 50;
	}
	
	public void projectileFrame () {
		
		//Get the displacement vector
		Point target = getTarget ();
		if (target == null) {
			forget ();
			return;
		}
		int centerX = (int)this.getCenterX ();
		int centerY = (int)this.getCenterY ();
		int diffX = target.x - centerX;
		int diffY = target.y - centerY;
		
		//Normalize the vector
		double dist = Math.sqrt (diffX * diffX + diffY * diffY);
		
		//Calculate the scale
		double scale = 1;
		if (scale < .5) {
			scale = .5;
		} else if (scale > 1) {
			scale = 1;
		}
		scale = 2;
		
		//Apply the acceleration and move the spell
		ax = scale * (double)diffX / dist;
		ay = scale * (double)diffY / dist;
		vx += ax;
		vy += ay;
		this.setX (getX () + vx);
		this.setY (getY () + vy);
		
		//Cap the velocity
		double maxspd;
		if (time > 10) {
			maxspd = 10;
		} else {
			maxspd = 20 - time;
		}
		double vmag = Math.sqrt (vx * vx + vy * vy);
		if (vmag > maxspd) {
			vx = (vx / vmag) * maxspd;
			vy = (vy / vmag) * maxspd;
		}
		
		//Keep the spell from going offscreen
		if (getX () < getRoom ().getViewX ()) {
			vx = 0;
			setX (getRoom ().getViewX ());
		}
		if (getY () < getRoom ().getViewY ()) {
			vy = 0;
			setY (getRoom ().getViewY ());
		}
		if (getX () + 16 > getRoom ().getViewX () + MainLoop.getWindow ().getResolution ()[0]) {
			vx = 0;
			setX (getRoom ().getViewX () + MainLoop.getWindow ().getResolution ()[0] - 17);
		}
		if (getY () + 16 > getRoom ().getViewY () + MainLoop.getWindow ().getResolution ()[1]) {
			vy = 0;
			setY (getRoom ().getViewY () + MainLoop.getWindow ().getResolution ()[1] - 17);
		}
		
		time++;
		
	}
	
	public Point getTarget () {
		return new Point (this.getMouseX () + getRoom ().getViewX (), this.getMouseY () + getRoom ().getViewY ());
	}
	
	public void setDurability (int durability) {
		drb = durability;
	}
	
	public void setParticleColor (Color particleColor) {
		this.particleColor = particleColor;
	}
	
	@Override
	public void hitEvent (GameObject hitObject) {
		drb--;
		if (drb == 0) {
			forget ();
		}
	}
	
	@Override
	public void forget () {
		//Forget this object
		super.forget ();
	}

}
