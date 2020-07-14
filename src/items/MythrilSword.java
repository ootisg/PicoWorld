package items;

import main.GameAPI;

public class MythrilSword extends WeaponItem {
	public MythrilSword () {
		super ();
		setProperty ("displayName", "Mthrl Sword");
		setProperty ("attack", "15");
		setProperty ("maxHealth", "500");
		setProperty ("health", "500");
	}
	@Override
	public boolean use () {
		GameAPI.getPlayer ().useSword (this);
		//Use hit check later
		damage (1);
		//Aaaand add break checks later too
		return true;
	}
}