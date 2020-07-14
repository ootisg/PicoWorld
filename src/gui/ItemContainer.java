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
	 * Returns true if this object contains an item i for which item.equals (i) evaluates to true
	 * @param item the item to search for
	 * @return true if the item is found; false otherwise
	 */
	boolean hasSimilar (GameItem item);
	/**
	 * Returns the number of the given item this object contains
	 * @param item the item to search for
	 * @return the number of items found
	 */
	int numItems (GameItem item);
	/**
	 * Returns the number of items for which item.equals (i) evaluates to true contained by this object
	 * @param item the item to search for
	 * @return the number of items found
	 */
	int numSimilar (GameItem item);
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
	 * Removes the first occurance of a GameItem i, for which item.equals (i) evaluates to true, from this container
	 * @param item the item to use when checking for similarity
	 * @return true if the item is removed successfully; false otherwise
	 */
	boolean removeSimilar (GameItem item);
	/**
	 * Replaces the first occurance of the given item from this container
	 * @param oldItem the item to replace
	 * @param newItem the new item to put in place of oldItem
	 * @return true if the item is replaced successfully; false otherwise
	 */
	boolean replace (GameItem oldItem, GameItem newItem);
	/**
	 * Sets the item at the given index to the give item
	 * @param index the index to set
	 * @param item the item to set to the given index
	 */
	void setItem (int index, GameItem item);
	/**
	 * Finds the first occurance of a GameItem i, for which item.equals (i) evaluates to true, in this container
	 * @param item the item to search for
	 * @return the found item
	 */
	GameItem getSimilar (GameItem item);
	/**
	 * Returns the GameItem at the given index
	 * @param index the index of the given item
	 * @return the item at the given index
	 */
	GameItem getItem (int index);
}