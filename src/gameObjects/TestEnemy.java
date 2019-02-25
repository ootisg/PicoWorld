package gameObjects;

import resources.Sprite;

public class TestEnemy extends FourDirectionalEnemy {
	
	private double targetX = -1;
	private double targetY = -1;
	
	private double movementSpeed = 2;
	
	public TestEnemy () {
		super ();
		setDirection (Direction.RIGHT);
		setSpeed (1);
		createHitbox (0, 0, 16, 16);
	}

	@Override
	protected Sprite[] getUsedSprites () {
		Sprite upSprite = getSprites ().playerWalkSprites [0];
		Sprite leftSprite = getSprites ().playerWalkSprites [1];
		Sprite downSprite = getSprites ().playerWalkSprites [2];
		Sprite rightSprite = getSprites ().playerWalkSprites [3];
		Sprite upIdle = new Sprite (getSprites ().playerIdle.getImageArray () [0]);
		Sprite leftIdle = new Sprite (getSprites ().playerIdle.getImageArray () [1]);
		Sprite downIdle = new Sprite (getSprites ().playerIdle.getImageArray () [2]);
		Sprite rightIdle = new Sprite (getSprites ().playerIdle.getImageArray () [3]);
		return new Sprite[] {upSprite, upIdle, leftSprite, leftIdle, downSprite, downIdle, rightSprite, rightIdle};
	}

	@Override
	public void move () {
		if (targetX != -1 && targetY != -1) {
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
			if (Math.abs (getX () - getPlayer ().getX ()) > Math.abs (getY () - getPlayer ().getY ())) {
				targetX = getPlayer ().getX ();
				targetY = getY ();
				if (getX () > targetX) {
					setDirection (Direction.LEFT);
					setSpeed (movementSpeed);
				} else {
					setDirection (Direction.RIGHT);
					setSpeed (movementSpeed);
				}
			} else {
				targetX = getX ();
				targetY = getPlayer ().getY ();
				if (getY () > targetY) {
					setDirection (Direction.UP);
					setSpeed (movementSpeed);
				} else {
					setDirection (Direction.DOWN);
					setSpeed (movementSpeed);
				}
			}
		}
	}
	
	@Override
	public void frameEvent () {
		move ();
		super.frameEvent ();
	}
}
