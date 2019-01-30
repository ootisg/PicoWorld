package items;

import main.GameAPI;

public class SilverSword extends WeaponItem {
	public SilverSword () {
		super ();
		setProperty ("displayName", "Silvr Sword");
		setProperty ("attack", "8");
		setProperty ("maxHealth", "100");
		setProperty ("health", "100");
	}
	@Override
	public boolean use () {
		GameAPI.getPlayer ().useSword (this);
		//Use hit check later
		setProperty ("health", String.valueOf (Integer.parseInt (getProperty ("health")) - 1));
		//Aaaand add break checks later too
		if (getProperty ("health").equals ("0")) {
			GameAPI.getGui ().getItemMenu ().removeItem (this);
		}
		return true;
	}
}