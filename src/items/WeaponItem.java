package items;

import items.GameItem.ItemType;
import resources.Sprite;

public abstract class WeaponItem extends GameItem {
	public WeaponItem () {
		super (ItemType.WEAPON);
		setProperty ("attack", "0");
	}
	public WeaponItem (Sprite icon) {
		super (icon, ItemType.WEAPON);
	}
}
