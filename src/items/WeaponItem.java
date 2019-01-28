package items;

public abstract class WeaponItem extends GameItem {
	public WeaponItem () {
		super (ItemType.WEAPON);
		setProperty ("attack", "0");
	}
}
