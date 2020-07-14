package items;

import main.GameAPI;

public class Berry extends ConsumableItem {

	public Berry () {
		super ();
	}
	
	@Override
	public boolean use () {
		GameAPI.getPlayer ().setHealth (GameAPI.getPlayer ().getHealth () + 5);
		GameAPI.getGui ().getItemMenu ().removeItem (this);
		return true;
	}
}
