package gameObjects;

import main.GameObject;

public class Hole extends GameObject {
	@Override
	public void frameEvent () {
		System.out.println (getVariantAttribute ("destination"));
	}
}
