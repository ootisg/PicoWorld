package puzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import main.GameObject;
import main.MainLoop;

public class LightPath extends GameObject {
	
	public static int BEAM_LENGTH = 40;
	public static int BEAM_SPEED = 16; 
	
	private Point[] path;
	private Point currentPoint;
	private int currentPathPoint;
	//private int currentDistance;
	
	private boolean finished;
	
	public LightPath (Point[] path) {
		this.path = path;
		currentPathPoint = 0;
		currentPoint = path [0];
		//currentDistance = 0;
	}
	
	@Override
	public void frameEvent () {
		if (!finished) {
			ArrayList<GameObject> switches = new ArrayList<GameObject> ();
			int nextIndex = currentPathPoint + 1;
			Point curr = currentPoint;
			Point next = path [nextIndex];
			int totalDist = BEAM_SPEED;
			int prevDist = BEAM_SPEED;
			while (totalDist > 0) {
				totalDist -= getDistance (curr, next);
				if (totalDist >= 0) {
					if (nextIndex >= path.length - 1) {
						//Done rendering(?)
					} else {
						//Update currentPathPoint and curr
						currentPathPoint ++;
						curr = path [nextIndex++];
						
						//Check for switch collision here
						switches.addAll (getCollidingSwitches (currentPoint.x, currentPoint.y, curr.x, curr.y));
						
						//Update next and distance stuffs
						next = path [nextIndex];
						prevDist = totalDist;
						
						//Update currentPoint
						currentPoint = path [currentPathPoint];
					}
				}
			}
			
			//Traverse in a straight line
			currentPoint = traverse (curr, next, prevDist);
			
			//Activate switches
			switches.addAll (getCollidingSwitches (curr.x, curr.y, currentPoint.x, currentPoint.y));
			for (int i = 0; i < switches.size (); i++) {
				LightSwitch currentSwitch = (LightSwitch)switches.get (i);
				System.out.println (currentSwitch);
				currentSwitch.activate (this);
			}
		}
	}
	
	@Override
	public void draw () {
		if (!finished) {
			Graphics g = MainLoop.getWindow ().getBufferGraphics ();
			g.setColor (new Color (0xFFFF00));
			int nextIndex = currentPathPoint + 1;
			Point curr = currentPoint;
			Point next = path [nextIndex];
			int totalDist = BEAM_LENGTH;
			int prevDist = BEAM_LENGTH;
			while (totalDist > 0) {
				totalDist -= getDistance (curr, next);
				if (totalDist >= 0) {
					if (nextIndex >= path.length - 1) {
						finished = true;
					} else {
						g.drawLine (curr.x - getRoom ().getViewX (), curr.y - getRoom ().getViewY (), next.x - getRoom ().getViewX (), next.y - getRoom ().getViewY ());
						curr = path [nextIndex];
						next = path [nextIndex + 1];
						nextIndex ++;
						prevDist = totalDist;
					}
				}
			}
			next = traverse (curr, next, prevDist);
			g.drawLine (curr.x - getRoom ().getViewX (), curr.y - getRoom ().getViewY (), next.x - getRoom ().getViewX (), next.y - getRoom ().getViewY ());
		}
	}
	
	public double getDistance (Point a, Point b) {
		return Math.sqrt ((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
	
	public Point traverse (Point start, Point to, int distance) {
		if (start.x == to.x) {
			if (start.y < to.y) {
				return new Point (start.x, start.y + distance);
			} else {
				return new Point (start.x, start.y - distance);
			}
		} else {
			if (start.x < to.x) {
				return new Point (start.x + distance, start.y);
			} else {
				return new Point (start.x - distance, start.y);
			}
		}
	}
	
	/**
	 * Gets all of the light-sensitive switches on the given vertical or horizontal line
	 * @param startX the x-coordinate of the line's starting point
	 * @param startY the y-coordinate of the line's starting point
	 * @param endX the x-coordinate of the line's ending point
	 * @param endY the y-coordinate of the line's ending point
	 * @return an ArrayList of the switches that were activated
	 */
	public ArrayList<GameObject> getCollidingSwitches (int startX, int startY, int endX, int endY) {
		//Make hitbox for line
		if (startX == endX) {
			//Line is vertical
			if (startY > endY) {
				//Swap startY and endY
				int temp = endY;
				endY = startY;
				startY = temp;
			}
			createHitbox (startX, startY, 1, endY - startY); //Create hitbox for line
		} else if (startY == endY) {
			//Line is horizontal
			if (startX > endX) {
				//Swap startY and endY
				int temp = endX;
				endX = startX;
				startX = temp;
			}
			createHitbox (startX, startY, endX - startX, 1); //Create hitbox for line
		} else {
			//Line is not horizontal or vertical
			throw new IllegalArgumentException ("The given line must be either vertical or horizontal");
		}
		
		//Get and return the relevant switches
		ArrayList<GameObject> hitSwitches = getCollidingObjects ("puzzle.LightSwitch");
		destroyHitbox (); //Get rid of the hitbox to avoid messing with future collisions
		return hitSwitches;
	}
	
	public boolean isFinished () {
		return finished;
	}
}
