package gameObjects;

import items.Berry;
import resources.Sprite;

public class BerryBush extends Saveable {
	
	public static final long REGROW_TIME = 9000;
	
	public BerryBush () {
		createHitbox (3, 7, 12, 10);
	}
	
	private void refreshSprite () {
		if (getVariantAttribute ("harvested").equals ("true")) {
			setSprite (new Sprite ("resources/sprites/bush_empty.png"));
		} else {
			setSprite (new Sprite ("resources/sprites/bush_berries.png"));
		}
	}
	
	@Override
	public void onDeclare () {
		load ();
		refreshSprite ();
	}
	
	@Override
	public void frameEvent () {
		if (getPlayer ().swordObject.isColliding (this)) {
			if (!getVariantAttribute ("harvested").equals ("true")) {
				setVariantAttribute ("harvested", "true");
				refreshSprite ();
				new ItemDrop (new Berry ()).declare (getX (), getY ());
				save (String.valueOf (GlobalSave.getGameTime ()));
			}
		}
		if (getSaveData () != null && getVariantAttribute ("harvested").equals ("true")) {
			long elapsed = GlobalSave.getGameTime () - Long.valueOf (getSaveData ());
			if (elapsed >= REGROW_TIME) {
				setVariantAttribute ("harvested", "false");
				refreshSprite ();
			}
		}
	}

	@Override
	public void load () {
		String data = getSaveData ();
		if (getSaveData () != null) {
			long elapsed = GlobalSave.getGameTime () - Long.valueOf (data);
			if (elapsed < REGROW_TIME) {
				setVariantAttribute ("harvested", "true");
				return;
			}
		}
		setVariantAttribute ("harvested", "false");
	}
}
