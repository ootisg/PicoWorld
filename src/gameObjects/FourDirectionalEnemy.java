package gameObjects;

import resources.Sprite;

public abstract class FourDirectionalEnemy extends Enemy {
	
	private double speed;
	private Direction direction;
	
	private Sprite upMoving;
	private Sprite upIdle;
	private Sprite leftMoving;
	private Sprite leftIdle;
	private Sprite downMoving;
	private Sprite downIdle;
	private Sprite rightMoving;
	private Sprite rightIdle;
	
	public enum Direction {
		UP, LEFT, DOWN, RIGHT;
	}
	
	protected FourDirectionalEnemy () {
		Sprite[] usedSprites = getUsedSprites ();
		this.upMoving = usedSprites [0];
		this.upIdle = usedSprites [1];
		this.leftMoving = usedSprites [2];
		this.leftIdle = usedSprites [3];
		this.downMoving = usedSprites [4];
		this.downIdle = usedSprites [5];
		this.rightMoving = usedSprites [6];
		this.rightIdle = usedSprites [7];
	}
	
	public void setDirection (Direction direction) {
		this.direction = direction;
		updateSprite ();
	}
	
	public void setSpeed (double speed) {
		this.speed = speed;
		updateSprite ();
	}
	
	public Direction getDirection () {
		return direction;
	}
	
	public double getSpeed () {
		return speed;
	}
	
	private void updateSprite () {
		if (speed == 0) {
			switch (getDirection ()) {
				case UP:
					setSprite (upIdle);
					break;
				case LEFT:
					setSprite (leftIdle);
					break;
				case DOWN:
					setSprite (downIdle);
					break;
				case RIGHT:
					setSprite (rightIdle);
					break;
				default:
					setSprite (rightIdle);
					break;
			}
		} else {
			switch (getDirection ()) {
				case UP:
					setSprite (upMoving);
					break;
				case LEFT:
					setSprite (leftMoving);
					break;
				case DOWN:
					setSprite (downMoving);
					break;
				case RIGHT:
					setSprite (rightMoving);
					break;
				default:
					setSprite (rightMoving);
			}
		}
	}
	
	protected abstract Sprite[] getUsedSprites ();
	
	public abstract void move ();
}