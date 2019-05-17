package items;

import main.GameAPI;

public class Apple extends ConsumableItem {
	public Apple () {
		super ();
	}
	@Override
	public boolean use () {
		GameAPI.getPlayer ().setHealth (GameAPI.getPlayer ().getHealth () + 10);
		GameAPI.getGui ().getItemMenu ().removeItem (this);
		return true;
	}
}