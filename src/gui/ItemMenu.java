package gui;

import java.awt.event.KeyEvent;

import items.GameItem;
import items.GameItem.ItemType;
import resources.Sprite;
import resources.Spritesheet;

public class ItemMenu extends GuiComponent implements ItemContainer {
	private GameItem[][] items;
	private Sprite[] pageIcons;
	private Sprite itemHealth;
	public static final int selectionHeight = 4;
	public static final int selectionWidth = 6;
	public static final int numPages = 5;
	private int selectIndex;
	private int pageIndex;
	private MappedUi statWindow;
	public ItemMenu (int x, int y) {
		super (sprites.itemUi);
		declare (x, y);
		items = new GameItem[numPages][selectionWidth * selectionHeight];
		pageIcons = new Sprite[numPages];
		pageIcons [0] = new Sprite ("resources/sprites/weapon_icon.png");
		pageIcons [1] = new Sprite ("resources/sprites/equipment_icon.png");
		pageIcons [2] = new Sprite ("resources/sprites/consumable_icon.png");
		pageIcons [3] = new Sprite ("resources/sprites/material_icon.png");
		pageIcons [4] = new Sprite ("resources/sprites/spell_icon.png");
		itemHealth = new Sprite (new Spritesheet ("resources/sprites/itemhealth.png"), 16, 1);
		selectIndex = 4;
		pageIndex = 0;
		setPriority (-2);
		statWindow = new MappedUi (new Spritesheet ("resources/sprites/gui_background.png"), new int[0][0]);
		statWindow.setX (x + 128);
		statWindow.setY (y);
		statWindow.setPriority (-2);
	}
	public boolean addItem (GameItem item) {
		for (int i = 0; i < items [GameItem.getValue (item.getType ())].length; i ++) {
			if (items [GameItem.getValue (item.getType ())][i] == null) {
				items [GameItem.getValue (item.getType ())][i] = item;
				return true;
			}
		}
		return false;
	}
	@Override
	public void keyEvent (char c) {
		if (keyCheck (KeyEvent.VK_SHIFT)) {
			switch (c) {
				case 'W':
					if (pageIndex == 0) {
						pageIndex = numPages - 1;
					} else {
						pageIndex --;
					}
					break;
				case 'A':
					selectIndex = selectIndex % selectionHeight;
					break;
				case 'S':
					if (pageIndex == numPages - 1) {
						pageIndex = 0;
					} else {
						pageIndex ++;
					}
					break;
				case 'D':
					selectIndex = selectIndex % selectionHeight + (selectionWidth - 1) * selectionHeight;
					break;
			}
		} else {
			switch (c) {
				case 'W':
					if (selectIndex % selectionHeight == 0) {
						selectIndex += selectionHeight - 1;
						if (pageIndex == 0) {
							pageIndex = numPages - 1;
						} else {
							pageIndex --;
						}
					} else {
						selectIndex --;
					}
					break;
				case 'A':
					if (selectIndex / selectionHeight == 0) {
						selectIndex += selectionHeight * (selectionWidth - 1);
					} else {
						selectIndex -= selectionHeight;
					}
					break;
				case 'S':
					if (selectIndex % selectionHeight == selectionHeight - 1) {
						selectIndex -= selectionHeight - 1;
						if (pageIndex == numPages - 1) {
							pageIndex = 0;
						} else {
							pageIndex ++;
						}
					} else {
						selectIndex ++;
					}
					break;
				case 'D':
					if (selectIndex / selectionHeight == selectionWidth - 1) {
						selectIndex -= selectionHeight * (selectionWidth - 1);
					} else {
						selectIndex += selectionHeight;
					}
					break;
			}
		}
	}
	@Override
	public void renderBackground () {
		this.getSprite ().draw ((int)this.getX (), (int)this.getY ());
	}
	@Override
	public void renderElements () {
		for (int i = 0; i < items [0].length; i ++) {
			if (items [pageIndex][i] != null) {
				if (pageIndex == GameItem.getValue (ItemType.WEAPON)) {
					if (!items [pageIndex][i].getProperty ("maxHealth").equals ("null")) {
						itemHealth.draw ((int)getX () + (1 + i / selectionHeight) * 16, (int)getY () + (1 + i % selectionHeight) * 16 + 14, (int)(Math.ceil (Double.parseDouble (items [pageIndex][i].getProperty ("health")) / Double.parseDouble (items [pageIndex][i].getProperty ("maxHealth")) * 14)) - 1);
					}
				}
				items [pageIndex][i].getIcon ().draw ((int)getX () + (1 + i / selectionHeight) * 16, (int)getY () + (1 + i % selectionHeight) * 16);
			}
		}
		for (int i = 0; i < pageIcons.length; i ++) {
			if (pageIcons [i] != null) {
				pageIcons [i].draw ((int)getX (), (int)getY () + i * 16);
			}
		}
		sprites.selectedBorder.draw ((int)getX () + (1 + selectIndex / selectionHeight) * 16, (int)getY () + (1 + selectIndex % selectionHeight) * 16);
		sprites.selectedBorder.draw ((int)getX (), (int)getY () + pageIndex * 16);
		if (items [pageIndex][selectIndex] != null) {
			String itemName;
			if (items [pageIndex][selectIndex].getProperty ("displayName").equals ("")) {
				itemName = items[pageIndex][selectIndex].getName ();
			} else {
				itemName = items[pageIndex][selectIndex].getProperty ("displayName");
			}
			drawText (itemName.toUpperCase (), 18, 4);
		}
		if (pageIndex == GameItem.getValue (ItemType.WEAPON) && items [pageIndex][selectIndex] != null) {
			String healthText = items [pageIndex][selectIndex].getProperty ("health") + "/" + items [pageIndex][selectIndex].getProperty ("maxHealth");
			if (items [pageIndex][selectIndex].getProperty ("health").equals ("null")) {
				statWindow.setMap (CraftingMenu.buildTileMap (3, 2));
			} else {
				int statWidth = ((int)Math.ceil (((double)(2 + healthText.length () * 8) / 16)));
				if (statWidth < 3) {
					statWidth = 3;
				}
				statWindow.setMap (CraftingMenu.buildTileMap (statWidth, 4));
			}
			statWindow.draw ();
			statWindow.drawText ("ATK:", 2, 6);
			statWindow.drawText (items [pageIndex][selectIndex].getProperty ("attack"), 2, 16);
			if (!items [pageIndex][selectIndex].getProperty ("health").equals ("null")) {
				statWindow.drawText ("DRB:", 2, 36);
				statWindow.drawText (healthText, 2, 46);
			}
		}
	}
	@Override
	public boolean hasItem (GameItem item) {
		//TODO deep compare
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j].getName ().equals (item.getName ())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	@Override
	public int numItems (GameItem item) {
		int count = 0;
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j].getName ().equals (item.getName ())) {
						count ++;
					}
				}
			}
		}
		return count;
	}
	@Override
	public boolean removeItem (GameItem item) {
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j].getName ().equals (item.getName ())) {
						items [i][j] = null;
						for (int k = j; k < items [i].length - 1; k ++) {
							items [i][k] = items [i][k + 1];
							if (k == items [i].length - 2) {
								items [i][items [i].length - 1] = null;
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	@Override
	public void setItem (int index, GameItem item) {
		//TODO index mapping
	}
	@Override
	public GameItem getItem (int index) {
		//TODO index mapping
		return null;
	}
}