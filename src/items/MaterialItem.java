package items;

import items.GameItem.ItemType;
import resources.Sprite;

public abstract class MaterialItem extends GameItem {
	public MaterialItem () {
		super (ItemType.MATERIAL);
	}
	public MaterialItem (Sprite icon) {
		super (icon, ItemType.MATERIAL);
	}
}
