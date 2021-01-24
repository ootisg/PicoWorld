package gameObjects;

import items.Berry;
import resources.Sprite;
import visualEffects.Outline;
import gui.Interactable;

public class BerryBush extends Saveable implements Interactable {
	
	public static final long REGROW_TIME = 9000;
	
	private Outline selectOutline;
	
	public BerryBush () {
		createHitbox (3, 7, 12, 10);
		selectOutline = new Outline (this, new int[] {0xFF, 0xFF, 0xFF, 0xFF});
		selectOutline.declare (0, 0);
		selectOutline.setHidden (true);
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

	@Override
	public void hover () {
		selectOutline.setHidden (false);
	}
	
	@Override
	public void unhover () {
		selectOutline.setHidden (true);
	}
	
	@Override
	public void click () {
		if (!getVariantAttribute ("harvested").equals ("true")) {
			setVariantAttribute ("harvested", "true");
			refreshSprite ();
			new ItemDrop (new Berry ()).declare (getX (), getY ());
			save (String.valueOf (GlobalSave.getGameTime ()));
		}
	}

	@Override
	public boolean useDefaultHover () {
		return true;
	}

}
