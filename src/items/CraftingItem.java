package items;

public interface CraftingItem {
	/**
	 * Uses the item as it would be used when crafting an item. Meant to allow more flexibility in crafting while preserving regular item usability.
	 * @return Whether the ingredient was successfully used
	 */
	public boolean useInCrafting ();
}
