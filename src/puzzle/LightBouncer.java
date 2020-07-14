package puzzle;

import java.util.ArrayList;
import java.util.HashMap;

import main.GameObject;
import projectiles.Projectile;
import resources.Sprite;
import resources.Spritesheet;

public class LightBouncer extends GameObject {

	private static final Spritesheet bouncerSheet = new Spritesheet ("resources/sprites/light_bouncer.png");
	public static final Sprite bouncer = new Sprite (bouncerSheet, 16, 16);
	
	private int rotation;
	public static ArrayList<HashMap<Double, Double>> directions = null;
	
	public LightBouncer () {
		setSprite (bouncer);
		getAnimationHandler ().setAnimationSpeed (0);
		createHitbox (0, 0, 16, 16);
		
		//Set direction HashMap if applicable
		if (directions == null) {
			directions = new ArrayList<HashMap<Double, Double>> ();
			//Add empty HashMaps
			for (int i = 0; i < 4; i++) {
				directions.add (new HashMap<Double, Double> ());
			}
			//Populate hashmap
			directions.get (0).put (Projectile.DIRECTION_DOWN, Projectile.DIRECTION_RIGHT);
			directions.get (0).put (Projectile.DIRECTION_LEFT, Projectile.DIRECTION_UP);
			directions.get (1).put (Projectile.DIRECTION_UP, Projectile.DIRECTION_RIGHT);
			directions.get (1).put (Projectile.DIRECTION_LEFT, Projectile.DIRECTION_DOWN);
			directions.get (2).put (Projectile.DIRECTION_RIGHT, Projectile.DIRECTION_DOWN);
			directions.get (2).put (Projectile.DIRECTION_UP, Projectile.DIRECTION_LEFT);
			directions.get (3).put (Projectile.DIRECTION_DOWN, Projectile.DIRECTION_LEFT);
			directions.get (3).put (Projectile.DIRECTION_RIGHT, Projectile.DIRECTION_UP);
		}
	}
	
	@Override
	public void onDeclare () {
		
		rotation = Integer.parseInt (getVariantAttribute ("rotation"));
		switch (rotation) {
			case 0:
				getAnimationHandler ().setFrame (0);
				rotation = 0;
				break;
			case 90:
				getAnimationHandler ().setFrame (1);
				rotation = 1;
				break;
			case 180:
				getAnimationHandler ().setFrame (2);
				rotation = 2;
				break;
			case 270:
				getAnimationHandler ().setFrame (3);
				rotation = 3;
				break;
		}
	}
	
	public Double redirect (double initialDirection) {
		return directions.get (rotation).get (initialDirection);
	}
	
}
