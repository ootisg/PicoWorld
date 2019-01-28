package items;

import resources.Sprite;

public abstract class GameItem {
	private String name;
	private String properties;
	private Sprite icon;
	private ItemType type;
	public static enum ItemType {
		WEAPON, EQUIPMENT, CONSUMABLE, MATERIAL, SPELL;
	}
	protected GameItem (ItemType type) {
		this.name = this.getClass ().getSimpleName ();
		this.icon = new Sprite ("resources/sprites/items/" + this.name + ".png");
		this.type = type;
		this.properties = "";
	}
	protected GameItem (String name, Sprite icon, ItemType type) {
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.properties = "";
	}
	protected GameItem (GameItem item) {
		this.name = item.name;
		this.icon = item.icon;
		this.type = item.type;
		this.properties = item.properties;
	}
	public String getProperty (String propertyName) {
		int index = getPropertyIndex (propertyName);
		if (index != -1) {
			return getProperties ().split (",")[index].split (":")[1];
		}
		return "";
	}
	public void setProperty (String propertyName, String value) {
		if (!propertyName.contains (":") && !propertyName.contains (",") && !value.contains (":") && !value.contains (",") && !propertyName.equals ("") && !propertyName.equals ("")) {
			int index = getPropertyIndex (propertyName);
			if (index != -1) {
				String[] propertyArray = getProperties ().split (",");
				String[] workingProperty = propertyArray [index].split (":");
				workingProperty [1] = value;
				propertyArray [index] = String.join (":", workingProperty);
				setProperties (String.join (",", propertyArray));
			} else if (!getProperties ().equals ("")) {
				setProperties (getProperties () + "," + propertyName + ":" + value);
			} else {
				setProperties (propertyName + ":" + value);
			}
		}
	}
	private int getPropertyIndex (String propertyName) {
		if (getProperties () != null) {
			String[] propertyArray = getProperties ().split (",");
			for (int i = 0; i < propertyArray.length; i ++) {
				if (propertyArray [i].split (":")[0].equals (propertyName)) {
					return i;
				}
			}
		}
		return -1;
	}
	public String getName () {
		return name;
	}
	public Sprite getIcon () {
		return icon;
	}
	public String getProperties () {
		return properties;
	}
	public ItemType getType () {
		return type;
	}
	public void setProperties (String properties) {
		this.properties = properties;
	}
	public static int getValue (ItemType type) {
		switch (type) {
			case WEAPON:
				return 0;
			case EQUIPMENT:
				return 1;
			case CONSUMABLE:
				return 2;
			case MATERIAL:
				return 3;
			case SPELL:
				return 4;
			default:
				return 0;
		}
			
	}
	public static GameItem getItemByName (String name, GameItem[] items) {
		for (int i = 0; i < items.length; i ++) {
			if (items [i].getName ().equals (name)) {
				return items [i];
			}
		}
		return null;
	}
	protected void setName (String name) {
		this.name = name;
	}
	protected void setIcon (Sprite icon) {
		this.icon = icon;
	}
}