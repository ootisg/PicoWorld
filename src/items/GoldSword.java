package items;

import main.GameAPI;
import main.MainLoop;
import resources.Sprite;

public class GoldSword extends WeaponItem {
	public GoldSword () {
		super ();
		setProperty ("displayName", "Gold Sword");
		setProperty ("attack", "10");
	}
	@Override
	public boolean use () {
		MainLoop.getWindow ().playSound ("resources/sounds/Swoosh 3-SoundBible.com-1573211927.wav");
		GameAPI.getPlayer ().useSword (this);
		return true;
	}
}