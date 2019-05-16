package items;

import items.GameItem.ItemType;
import resources.Sprite;

public abstract class EquipmentItem extends GameItem {
	public EquipmentItem () {
		super (ItemType.EQUIPMENT);
	}
	public EquipmentItem (Sprite icon) {
		super (icon, ItemType.EQUIPMENT);
	}
}
