package puzzle;

import java.awt.Point;
import java.util.ArrayList;

import ai.TileCollider;
import main.GameObject;
import projectiles.PlayerLightBolt;
import projectiles.Projectile;
import resources.Sprite;
import resources.Spritesheet;

public class LightFocuser extends PuzzleComponent {

	public static Sprite lightAbsorb = new Sprite (new Spritesheet ("resources/sprites/light_absorb.png"), 16, 16);
	public static Sprite focuserSprite = new Sprite ("resources/sprites/glass.png");
	
	private LightFocusAnimation focusParticle;
	
	private double beamDirection = Projectile.DIRECTION_INVALID;
	
	public LightFocuser () {
		setSprite (focuserSprite);
		focusParticle = null;
		createHitbox (0, 0, 16, 16);
	}
	
	@Override
	public void frameEvent () {
		if (isColliding ("projectiles.PlayerLightBolt")) {
			ArrayList<GameObject> bolts = getCollidingObjects ("projectiles.PlayerLightBolt");
			beamDirection = ((PlayerLightBolt)bolts.get (0)).getDirection ();
			System.out.println (beamDirection);
			for (int i = 0; i < bolts.size (); i ++) {
				bolts.get (i).forget ();
			}
			if (focusParticle == null) {
				focusParticle = new LightFocusAnimation ();
				focusParticle.declare (getX (), getY ());
			}
		}
	}
	
	public void onLightFocus () {
		//Light beam starts in the light focuser
		ArrayList<Point> pts = new ArrayList<Point> ();
		Point currentPoint = new Point ((int)getX () + 8, (int)getY () + 8);
		pts.add (currentPoint);
		//Initialize important components
		double currentDirection = beamDirection;
		TileCollider beamCollider = new TileCollider ();
		beamCollider.declare (getX (), getY ());
		//find needed offsets
		double xOffset = (int)Math.cos (beamDirection);
		double yOffset = -(int)Math.sin (beamDirection);
		//Get coordinates in tiles
		int curX = (int)getX () / 16;
		int curY = (int)getY () / 16;
		//until barrier hit
		while (!beamCollider.checkCollision (curX, curY)) {
			beamCollider.setPosition (curX * 16 + 1, curY * 16 + 1);
			if (beamCollider.isColliding ("puzzle.LightBouncer")) {
				LightBouncer bouncer = (LightBouncer)beamCollider.getCollidingObjects ("puzzle.LightBouncer").get (0);
				Double newDir = bouncer.redirect (currentDirection);
				if (newDir != null) {
					pts.add (new Point (curX * 16 + 8, curY * 16 + 8));
					currentDirection = newDir;
					xOffset = (int)Math.cos (currentDirection);
					yOffset = -(int)Math.sin (currentDirection);
				} else {
					break;
				}
			}
			curX += xOffset;
			curY += yOffset;
		}
		//End beam at the tile, not right before it
		curX -= xOffset;
		curY -= yOffset;
		if (currentDirection == Projectile.DIRECTION_UP) {
			pts.add (new Point (curX * 16 + 8, curY * 16));
		} else if (currentDirection == Projectile.DIRECTION_DOWN) {
			pts.add (new Point (curX * 16 + 8, curY * 16 + 16));
		} else if (currentDirection == Projectile.DIRECTION_LEFT) {
			pts.add (new Point (curX * 16 + 16, curY * 16 + 8));
		} else if (currentDirection == Projectile.DIRECTION_RIGHT) {
			pts.add (new Point (curX * 16, curY * 16 + 8));
		}
		new LightPath (pts.toArray (new Point[2])).declare (0, 0);
	}
	
	private class LightFocusAnimation extends GameObject {
		
		public LightFocusAnimation () {
			setSprite (lightAbsorb);
			getAnimationHandler ().setRepeat (false);
		}
		
		@Override
		public void frameEvent () {
			if (getAnimationHandler ().isDone ()) {
				focusParticle = null;
				onLightFocus ();
				forget ();
			}
		}
	}

	@Override
	public void onSolve() {
		// TODO Auto-generated method stub
		
	}
}
