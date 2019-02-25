package unused;

import resources.Sprite;

public abstract class FourDirectional extends AnimatedObject {
	
	private double speed;
	private Direction direction;
	
	public enum Direction {
		UP, LEFT, DOWN, RIGHT;
	}
	
	protected FourDirectional (Sprite upMove, Sprite upIdle, Sprite leftMove, Sprite leftIdle, Sprite downMove, Sprite downIdle, Sprite rightMove, Sprite rightIdle) {
		super ();
		addState ("moving_up", upMove);
		addState ("moving_left", leftMove);
		addState ("moving_down", downMove);
		addState ("moving_right", rightMove);
		addState ("idle_up", upIdle);
		addState ("idle_left", leftIdle);
		addState ("idle_down", downIdle);
		addState ("idle_right", rightIdle);
		//Default state
		setState ("idle_right");
	}
	
	protected FourDirectional (Sprite up, Sprite left, Sprite down, Sprite right, int idleFrame) {
		this (up, new Sprite (up.getImageArray () [idleFrame]), left, new Sprite (up.getImageArray () [idleFrame]), down, new Sprite (up.getImageArray () [idleFrame]), right, new Sprite (up.getImageArray () [idleFrame]));
	}
	
	public void setDirection (Direction direction) {
		this.direction = direction;
		updateAnimation ();
	}
	
	public void setSpeed (int speed) {
		this.speed = speed;
		updateAnimation ();
	}
	
	public Direction getDirection () {
		return direction;
	}
	
	public double getSpeed () {
		return speed;
	}
	
	private void updateAnimation () {
		String usedState;
		if (getSpeed () == 0) {
			usedState = "idle_";
		} else {
			usedState = "moving_";
		}
		switch (getDirection ()) {
			case UP:
				usedState += "up";
				break;
			case LEFT:
				usedState += "left";
				break;
			case DOWN:
				usedState += "down";
				break;
			case RIGHT:
				usedState += "right";
				break;
			default:
				usedState += "right";
				break;
		}
		setState (usedState);
	}
	
	public abstract void move ();
}
