package gameObjects;

import main.GameObject;

public class LayerCollider extends GameObject {

	private int layer;
	
	public LayerCollider () {
		System.out.println ("IM BEING MADE");
		createHitbox (0, 0, 16, 16);
	}
	
	@Override
	public void onDeclare () {
		try {
			layer = Integer.parseInt (getVariantAttribute ("layer"));
		} catch (NumberFormatException | NullPointerException e) {
			layer = 0;
			return;
		}
	}
	
	public int getLayer () {
		return layer;
	}
	
}
