package gui;

import items.GameItem;

public interface ItemContainer {
	/**
	 * Returns true if this object contains the given GameItem
	 * @param item the item to search for
	 * @return true if the item is found; false otherwise
	 */
	boolean hasItem (GameItem item);
	/**
	 * Returns the number of the given item this object contains
	 * @param item the item to search for
	 * @return the number of items found
	 */
	int numItems (GameItem item);
	/**
	 * Adds the given item to this item container
	 * @param item the item to add
	 * @return true if the item is added successfully; false otherwise
	 */
	boolean addItem (GameItem item);
	/**
	 * Removes the first occurance of the given item from this container
	 * @param item the item to remove
	 * @return true if the item is removed successfully; false otherwise
	 */
	boolean removeItem (GameItem item);
	/**
	 * Sets the item at the given index to the give item
	 * @param index the index to set
	 * @param item the item to set to the given index
	 */
	void setItem (int index, GameItem item);
	/**
	 * Returns the GameItem at the given index
	 * @param index the index of the given item
	 * @return the item at the given index
	 */
	GameItem getItem (int index);
}