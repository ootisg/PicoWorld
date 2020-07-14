package items;

import main.GameAPI;

public class Potion extends ConsumableItem {

	@Override
	public boolean use () {
		switch (getProperty ("type")) {
			case "health":
				GameAPI.getPlayer ().setHealth (GameAPI.getPlayer ().getHealth () + 10);
				GameAPI.getGui ().getItemMenu ().replace (this, new Bottle ());
				break;
			default:
				return false;
		}
		return true;
	}
}
