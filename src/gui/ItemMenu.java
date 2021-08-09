package gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import items.Apple;
import items.Berry;
import items.Bottle;
import items.DarkFlameSpell;
import items.GameItem;
import items.GameItem.ItemType;
import items.GoldBar;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.JSONUtil;
import items.GoldSword;
import items.HomingRingSpell;
import items.MagicRingSpell;
import items.Mushroom;
import items.MythrilBar;
import items.SilverBar;
import items.SilverSword;
import items.WoodPlanks;
import resources.Sprite;
import resources.Spritesheet;

public class ItemMenu extends GuiComponent implements ItemContainer {
	private GameItem[][] items;
	private Sprite[] pageIcons;
	private String[] pageNames;
	private Sprite itemHealth;
	public static final int selectionHeight = 4;
	public static final int selectionWidth = 6;
	public static final int numPages = 5;
	private int selectIndex;
	private int pageIndex;
	private MappedUi statWindow;
	
	Layout[] pageLayouts;
	Layout tabLayout;
	
	private int selectedWeapon;
	private int selectedSpell;
	
	public ItemMenu (int x, int y) {
		
		//Declare the menu
		super (null);
		declare (x, y);
		setPriority (-421);
		
		//Read the params file
		JSONObject params = null;
		try {
			params = JSONUtil.loadJSONFile ("resources/config/inventory.json");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Set the background
		setSprite (new Sprite (params.getString ("background")));
		
		//Initialize the item arrays
		JSONArray tabs = params.getJSONArray ("tabs");
		int numTabs = tabs.getContents ().size ();
		items = new GameItem[numTabs][24]; //Size of 24 because problems
		pageIcons = new Sprite[numTabs];
		pageNames = new String[numTabs];
		pageLayouts = new Layout[numTabs];
		
		//Initialize page metadata
		tabLayout = new Layout (tabs);
		for (int i = 0; i < numTabs; i++) {
			JSONObject currentTab = (JSONObject)tabs.get (i);
			String currentName = currentTab.getString ("name");
			String currentIcon = currentTab.getString ("icon");
			Sprite iconSprite = new Sprite (currentIcon);
			String currentLayout = currentTab.getString ("layout");
			if (currentLayout != null) {
				pageLayouts [i] = new Layout (params.getJSONObject ("layouts").getJSONArray (currentLayout));
			} else {
				pageLayouts [i] = new Layout (params.getJSONObject ("layouts").getJSONArray ("default"));
			}
			pageNames [i] = currentName;
			pageIcons [i] = iconSprite;
		}
		itemHealth = new Sprite (new Spritesheet ("resources/sprites/itemhealth.png"), 16, 1);
		selectIndex = 4;
		pageIndex = 0;
		statWindow = new MappedUi (new Spritesheet ("resources/sprites/gui_background.png"), new int[0][0]);
		statWindow.setX (x + 120);
		statWindow.setY (y + 8);
		statWindow.setPriority (-2);
		
		selectedWeapon = -1;
		selectedSpell = -1;
		
		addItem (new GoldSword ());
		addItem (new MagicRingSpell ());
		addItem (new HomingRingSpell ());
		addItem (new DarkFlameSpell ());
		
		addItem (new WoodPlanks ());
		addItem (new WoodPlanks ());
		addItem (new WoodPlanks ());
		addItem (new GoldBar ());
		addItem (new SilverBar ());
		addItem (new SilverBar ());
		addItem (new MythrilBar ());
		addItem (new Apple ());
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
	public void guiFrame () {
		
		//Run the parent's guiFrame
		super.guiFrame ();
		
		//Get the selected cells
		int pageId = tabLayout.getCellContainingPoint (getMouseX (), getMouseY ());
		int cellId = pageLayouts [pageIndex].getCellContainingPoint (getMouseX (), getMouseY ());

		//Set selections accordingly
		//Page selection
		if (pageId != -1 && mouseClicked ()) {
			pageIndex = pageId;
		}
		//Item selection
		if (cellId != -1) {
			//Select the hovered cell
			selectIndex = cellId;
			//Use the item if clicked
			if (mouseClicked () && items [pageIndex][selectIndex] != null) {
				if (pageIndex == GameItem.getValue (ItemType.WEAPON)) {
					selectedWeapon = selectIndex;
				}
				if (pageIndex == GameItem.getValue (ItemType.CONSUMABLE)) {
					items [pageIndex][selectIndex].use ();
				}
				if (pageIndex == GameItem.getValue (ItemType.SPELL)) {
					selectedSpell = selectIndex;
				}
			}
		}
		
	}
	
	public GameItem[][] getItems () {
		return items;
	}
	
	public int getSelectedPageIndex () {
		return pageIndex;
	}
	
	public int getSelectedItemIndex () {
		return selectIndex;
	}
	
	public GameItem getEquippedWeapon () {
		if (selectedWeapon == -1) {
			return null;
		}
		return items [GameItem.getValue (ItemType.WEAPON)][selectedWeapon];
	}
	
	public GameItem getEquippedSpell () {
		if (selectedSpell == -1) {
			return null;
		}
		return items [GameItem.getValue (ItemType.SPELL)][selectedSpell];
	}
	
	public void setItems (GameItem[][] items) {
		this.items = items;
	}
	
	public ArrayList<GameItem> filter (String propertyName, String propertyValue) {
		ArrayList<GameItem> filteredItems = new ArrayList<GameItem> ();
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items[0].length; j ++) {
				if (items [i][j] != null && propertyValue.equals (items[i][j].getProperty (propertyName))) {
					filteredItems.add (items[i][j]);
				}
			}
		}
		return filteredItems;
	}
	
	public static ArrayList<GameItem> filter (ArrayList<GameItem> items, String propertyName, String propertyValue) {
		ArrayList<GameItem> filteredItems = new ArrayList<GameItem> ();
		for (int i = 0; i < items.size (); i++) {
			if (propertyValue.equals (items.get (i).getProperty (propertyName))) {
				filteredItems.add (items.get (i));
			}
		}
		return filteredItems;
	}
	
	@Override
	public void renderBackground () {
		this.getSprite ().draw ((int)this.getX (), (int)this.getY ());
	}
	
	@Override
	public void renderElements () {
		
		//Render the items in the inventory
		for (int i = 0; i < items [0].length; i ++) {
			if (items [pageIndex][i] != null) {
				if (!Double.isNaN (items [pageIndex][i].getHealth ())) {
					if (!items [pageIndex][i].getProperty ("maxHealth").equals ("")) {
						itemHealth.draw ((int)getX () + (1 + i / selectionHeight) * 16, (int)getY () + (1 + i % selectionHeight) * 16 + 14, (int)(Math.ceil (items [pageIndex][i].getHealth () / Double.parseDouble (items [pageIndex][i].getProperty ("maxHealth")) * 14)) - 1);
					}
				}
				items [pageIndex][i].draw ((int)getX () + (1 + i / selectionHeight) * 16, (int)getY () + (1 + i % selectionHeight) * 16);
			}
		}
		
		//Render the page icons
		for (int i = 0; i < pageIcons.length; i ++) {
			if (pageIcons [i] != null) {
				pageIcons [i].draw ((int)getX (), (int)getY () + i * 16);
			}
		}
		
		//Draw the selection graphic for the item and page
		getSprites ().selectedBorder.draw ((int)getX () + (1 + selectIndex / selectionHeight) * 16, (int)getY () + (1 + selectIndex % selectionHeight) * 16);
		getSprites ().selectedBorder.draw ((int)getX (), (int)getY () + pageIndex * 16);
		
		//Draw 'equipped' icon
		if (pageIndex == GameItem.getValue (ItemType.WEAPON) && selectedWeapon != -1) {
			drawText ("E", (1 + selectedWeapon / selectionHeight) * 16 + 4, (1 + selectedWeapon % selectionHeight) * 16 + 4);
		}
		if (pageIndex == GameItem.getValue (ItemType.SPELL) && selectedSpell != -1) {
			drawText ("E", (1 + selectedSpell / selectionHeight) * 16 + 4, (1 + selectedSpell % selectionHeight) * 16 + 4);
		}
		
		//Draw the selected item's name
		if (items [pageIndex][selectIndex] != null) {
			String itemName;
			if (items [pageIndex][selectIndex].getProperty ("displayName").equals ("")) {
				itemName = items[pageIndex][selectIndex].getName ();
			} else {
				itemName = items[pageIndex][selectIndex].getProperty ("displayName");
			}
			drawText (itemName.toUpperCase (), 18, 4);
		}
		
		//Render the item stat window
		if (pageIndex == GameItem.getValue (ItemType.WEAPON) && items [pageIndex][selectIndex] != null) {
			String healthText = ((int)items [pageIndex][selectIndex].getHealth ()) + "/" + items [pageIndex][selectIndex].getProperty ("maxHealth");
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
			if (!Double.isNaN (items [pageIndex][selectIndex].getHealth ())) {
				statWindow.drawText ("DRB:", 2, 36);
				statWindow.drawText (healthText, 2, 46);
			}
		} else if (items [pageIndex][selectIndex] != null) {
			if (!Double.isNaN (items [pageIndex][selectIndex].getHealth ())) {
				String healthText = ((int)items [pageIndex][selectIndex].getHealth ()) + "/" + items [pageIndex][selectIndex].getProperty ("maxHealth");
				int statWidth = ((int)Math.ceil (((double)(2 + healthText.length () * 8) / 16)));
				if (statWidth < 3) {
					statWidth = 3;
				}
				statWindow.setMap (CraftingMenu.buildTileMap (statWidth, 2));
				statWindow.draw ();
				statWindow.drawText ("DRB:", 2, 6);
				statWindow.drawText (healthText, 2, 16);
			}
		}
	}
	
	@Override
	public boolean hasItem (GameItem item) {
		//TODO deep compare
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j] == item) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean hasSimilar (GameItem item) {
		//TODO deep compare
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j].equals (item)) {
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
					if (items [i][j] == item) {
						count ++;
					}
				}
			}
		}
		return count;
	}
	
	@Override
	public int numSimilar (GameItem item) {
		int count = 0;
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j].equals (item)) {
						count ++;
					}
				}
			}
		}
		return count;
	}
	
	@Override
	public boolean removeItem (GameItem item) {
		GameItem equipped = getEquippedWeapon ();
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j] == item) {
						items [i][j] = null;
						for (int k = j; k < items [i].length - 1; k ++) {
							items [i][k] = items [i][k + 1];
							if (k == items [i].length - 2) {
								items [i][items [i].length - 1] = null;
							}
						}
						selectedWeapon = -1;
						for (int k = 0; k < items [0].length; k ++) {
							if (items [GameItem.getValue (ItemType.WEAPON)][k] == equipped && equipped != null) {
								selectedWeapon = k;
								break;
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
	public boolean removeSimilar (GameItem item) {
		GameItem equipped = getEquippedWeapon ();
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j].equals (item)) {
						items [i][j] = null;
						for (int k = j; k < items [i].length - 1; k ++) {
							items [i][k] = items [i][k + 1];
							if (k == items [i].length - 2) {
								items [i][items [i].length - 1] = null;
							}
						}
						selectedWeapon = -1;
						for (int k = 0; k < items [0].length; k ++) {
							if (items [GameItem.getValue (ItemType.WEAPON)][k] == equipped && equipped != null) {
								selectedWeapon = k;
								break;
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
	public GameItem getSimilar (GameItem item) {
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j].equals (item)) {
						return items [i][j];
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean replace (GameItem oldItem, GameItem newItem) {
		for (int i = 0; i < items.length; i ++) {
			for (int j = 0; j < items [0].length; j ++) {
				if (items [i][j] != null) {
					if (items [i][j] == oldItem) {
						items [i][j] = newItem;
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