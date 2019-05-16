package items;

import items.GameItem.ItemType;
import resources.Sprite;

public abstract class ConsumableItem extends GameItem {
	public ConsumableItem () {
		super (ItemType.CONSUMABLE);
	}
	public ConsumableItem (Sprite icon) {
		super (icon, ItemType.CONSUMABLE);
	}
}
