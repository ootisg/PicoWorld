package gameObjects;

import java.awt.Point;
import java.util.LinkedList;

import ai.PathDisplayer;
import ai.Pathfinder;
import resources.Sprite;
import resources.Spritesheet;

public class TestEnemy extends FourDirectionalEnemy {
	
	private double targetX = -1;
	private double targetY = -1;
	
	private double movementSpeed = 1;
	
	private int sightRadius = 20;
	
	private PathDisplayer currentPath;
	
	public TestEnemy () {
		super ();
		setDirection (Direction.RIGHT);
		setSpeed (2);
		createHitbox (0, 0, 16, 16);
	}

	@Override
	protected Sprite[] getUsedSprites () {
		Spritesheet potatSheet = new Spritesheet ("resources/sprites/potat.png");
		Sprite upSprite = new Sprite (potatSheet, new int[] {0, 16, 32, 48, 64, 80}, new int[] {48, 48, 48, 48, 48, 48}, 16, 16);
		Sprite leftSprite = new Sprite (potatSheet, new int[] {0, 16, 32, 48, 64, 80}, new int[] {16, 16, 16, 16, 16, 16}, 16, 16);
		Sprite downSprite = new Sprite (potatSheet, new int[] {0, 16, 32, 48, 64, 80}, new int[] {32, 32, 32, 32, 32, 32}, 16, 16);
		Sprite rightSprite = new Sprite (potatSheet, new int[] {0, 16, 32, 48, 64, 80}, new int[] {0, 0, 0, 0, 0, 0}, 16, 16);
		Sprite upIdle = new Sprite (potatSheet, 0, 48, 16, 16);
		Sprite leftIdle = new Sprite (potatSheet, 0, 16, 16, 16);
		Sprite downIdle = new Sprite (potatSheet, 0, 32, 16, 16);
		Sprite rightIdle = new Sprite (potatSheet, 0, 0, 16, 16);
		return new Sprite[] {upSprite, upIdle, leftSprite, leftIdle, downSprite, downIdle, rightSprite, rightIdle};
	}

	@Override
	public void move () {
		if ((targetX == -1 && targetY == -1) || (Math.abs (getX () - targetX) < getSpeed () && Math.abs (getY () - targetY) < getSpeed ())) {
			recalculatePath ();
		}
		if (targetX != -1 && targetY != -1 && !(Math.abs (getX () - targetX) < getSpeed () && Math.abs (getY () - targetY) < getSpeed ())) {
			switch (getDirection ()) {
				case UP:
					setY (getY () - getSpeed ());
					break;
				case LEFT:
					setX (getX () - getSpeed ());
					break;
				case DOWN:
					setY (getY () + getSpeed ());
					break;
				case RIGHT:
					setX (getX () + getSpeed ());
					break;
			}
			double xDiff = getX () - targetX;
			double yDiff = getY () - targetY;
			if (xDiff * xDiff + yDiff * yDiff <= getSpeed () * getSpeed ()) {
				targetX = -1;
				targetY = -1;
			}
		} else {
		}
	}
	
	public void recalculatePath () {
		int pathX = (int)getX ();
		int pathY = (int)getY ();
		int topLeft = pathX / 16 - sightRadius;
		int topRight = pathY / 16 - sightRadius;
		int rangeWidth = sightRadius * 2;
		int startX = pathX / 16;
		int startY = pathY / 16;
		int endX = ((int)getPlayer ().getX () + 8) / 16;
		int endY = ((int)getPlayer ().getY () + 8) / 16;
		LinkedList<Point> pts = Pathfinder.findPath (topLeft, topRight, rangeWidth, rangeWidth, startX, startY, endX, endY);
		if (pts != null && pts.size () > 1) {
			currentPath = new PathDisplayer ("", pts, 16);
			currentPath.setAnchor (new Point ((int)getX () / 16, (int)getY () / 16));
			Point usedAnchor = new Point (pathX / 16 - pts.get (0).x, pathY / 16 - pts.get (0).y);
			Point newTarget = new Point (pts.get (1).x + usedAnchor.x, pts.get (1).y + usedAnchor.y);
			setNewTarget (newTarget);
		}
	}
	
	public void setNewTarget (Point target) {
		if (target.x == (int)getX () / 16) {
			targetY = target.y * 16;
			targetX = getX ();
			if (targetY < getY ()) {
				setDirection (Direction.UP);
			} else {
				setDirection (Direction.DOWN);
			}
		} else if (target.y == (int)getY () / 16) {
			targetX = target.x * 16;
			targetY = getY ();
			if (targetX < getX ()) {
				setDirection (Direction.LEFT);
			} else {
				setDirection (Direction.RIGHT);
			}
		}
	}
	
	@Override
	public void frameEvent () {
		move ();
		super.frameEvent ();
	}
	
	@Override
	public void draw () {
		super.draw ();
		if (currentPath != null) {
			currentPath.draw ();
		}
	}
	
	@Override
	public void onKnockbackEnd () {
		recalculatePath ();
	}
}
