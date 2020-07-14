package items;

import main.GameAPI;
import resources.Sprite;

public class GoldSword extends WeaponItem {
	public GoldSword () {
		super ();
		setProperty ("displayName", "Gold Sword");
		setProperty ("attack", "10");
	}
	@Override
	public boolean use () {
		GameAPI.getPlayer ().useSword (this);
		return true;
	}
}