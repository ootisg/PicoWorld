package util;

public class Vector2D {

	public double x;
	public double y;
	
	public Vector2D (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D (Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public void add (Vector2D toAdd) {
		x += toAdd.x;
		y += toAdd.y;
	}
	
	public void diff (Vector2D toSub) {
		x -= toSub.x;
		y -= toSub.y;
	}
	
	public void scale (double amt) {
		x *= amt;
		y *= amt;
	}
}
