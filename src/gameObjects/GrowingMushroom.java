package gameObjects;

import items.Berry;
import resources.Sprite;

public class GrowingMushroom extends ItemDrop {
	
	public static final long REGROW_TIME = 9000;
	private long lastSave;
	
	private Sprite usedSprite;
	
	private boolean collected;
	
	public GrowingMushroom () {
		createHitbox (1, 1, 14, 14);
		collected = false;
	}
	
	@Override
	public void onDeclare () {
		setVariantAttribute ("item", "Mushroom");
		super.onDeclare ();
		load ();
	}
	
	@Override
	public void frameEvent () {
		long elapsed = GlobalSave.getGameTime () - lastSave;
		if (elapsed >= REGROW_TIME && collected) {
			appear ();
		}
		super.frameEvent ();
	}
	
	@Override
	public void collect () {
		save (String.valueOf (GlobalSave.getGameTime ()));
		lastSave = GlobalSave.getGameTime ();
		disappear ();
	}

	@Override
	public void load () {
		String data = getSaveData ();
		if (data != null) {
			lastSave = Long.parseLong (data);
			long elapsed = GlobalSave.getGameTime () - lastSave;
			if (elapsed < REGROW_TIME) {
				disappear ();
			}
		}
	}
	
	@Override
	public void draw () {
		if (!collected) {
			super.draw ();
		}
	}
	
	public void disappear () {
		destroyHitbox ();
		collected = true;
	}
	
	public void appear () {
		createHitbox (1, 1, 14, 14);
		collected = false;
	}
}