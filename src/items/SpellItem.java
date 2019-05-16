package items;

import resources.Sprite;

public abstract class SpellItem extends GameItem {
	public SpellItem () {
		super (ItemType.SPELL);
	}
	public SpellItem (Sprite icon) {
		super (icon, ItemType.SPELL);
	}
}