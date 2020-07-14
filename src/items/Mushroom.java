package items;

import main.GameAPI;

public class Mushroom extends ConsumableItem {

	public Mushroom () {
		super ();
	}
	
	@Override
	public boolean use () {
		GameAPI.getPlayer ().damage (5);
		GameAPI.getGui ().getItemMenu ().removeItem (this);
		return true;
	}
}
