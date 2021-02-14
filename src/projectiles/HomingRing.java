package projectiles;

import java.awt.Point;
import java.util.ArrayList;

import gameObjects.Enemy;
import main.GameObject;
import main.MainLoop;
import resources.Sprite;
import resources.Spritesheet;
import util.Vector2D;

public class HomingRing extends MagicRing {
	
	public static final double MAX_FOLLOW_DIST = 1000;
	
	public static Spritesheet ringSheet = new Spritesheet ("resources/sprites/homing_ring.png");
	public static Sprite ringSprite = new Sprite (ringSheet, 16, 16);
	
	private GameObject target;
	private GameObject prev;
	
	public HomingRing (Vector2D startVelocity) {
		super (startVelocity);
		this.setSprite (ringSprite);
		this.setDurability (MAX_DRB + 4);
	}
	
	@Override
	public double getBaseDamage () {
		return 10;
	}
	
	@Override
	public Point getTarget () {
		
		//Do stuff
		if (target != null && isColliding (target)) {
			prev = target;
			target = null;
		}
		
		//Calculate closest enemy if needed
		if (target == null) {
			//Setup vars
			ArrayList<GameObject> enemies = MainLoop.getObjectMatrix ().getAll (Enemy.class);
			double closest = Double.POSITIVE_INFINITY;
			GameObject closestEnemy = null;
			//Loop through the enemies
			for (int i = 0; i < enemies.size (); i++) {
				GameObject curr = enemies.get (i);
				double dist = curr.getDistance (this);
				Vector2D offsV = Vector2D.getOffset (this, curr);
				Vector2D dirV = new Vector2D (vx, vy);
				double dot = Vector2D.dot (offsV, dirV);
				if (dot < 0) {
					dist *= 100; //Discourages the ring from turning around
				} else {
					dist *= (1 / Vector2D.dot (offsV, dirV));
				}
				if (dist < closest && curr != prev && curr.isOnScreen ()) {
					closest = dist;
					closestEnemy = curr;
				}
			}
			target = closestEnemy;
		}
		if (target != null) {
			return new Point ((int)target.getX (), (int)target.getY ());
		} else {
			return null;
		}
	}

}
