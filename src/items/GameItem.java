package items;

import gameObjects.DamageSource;
import gameObjects.Damageable;
import main.GameAPI;
import resources.AnimationHandler;
import resources.Sprite;
import resources.Spritesheet;

public abstract class GameItem implements Damageable {
	private String name;
	private String properties;
	private Sprite icon;
	protected AnimationHandler animationHandler;
	private ItemType type;
	private static boolean usePerciseCompare = false;
	public static enum ItemType {
		WEAPON, EQUIPMENT, CONSUMABLE, MATERIAL, SPELL, OTHER;
	}
	private GameItem () {
		animationHandler = new AnimationHandler (null);
		animationHandler.setIgnorePause (true);
	}
	protected GameItem (ItemType type) {
		this ();
		this.name = this.getClass ().getSimpleName ();
		setIcon (new Sprite ("resources/sprites/items/" + this.name + ".png"));
		this.type = type;
		this.properties = "";
	}
	protected GameItem (Sprite icon, ItemType type) {
		this ();
		this.name = this.getClass ().getSimpleName ();
		setIcon (icon);
		this.type = type;
		this.properties = "";
	}
	protected GameItem (String name, Sprite icon, ItemType type) {
		this ();
		this.name = name;
		setIcon (icon);
		this.type = type;
		this.properties = "";
	}
	protected GameItem (GameItem item) {
		this ();
		this.name = item.name;
		setIcon (item.icon);
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
		if (icon.getWidth () > 16 || icon.getHeight () > 16) {
			Spritesheet sheet = new Spritesheet (icon.getImageArray ()[0]);
			icon = new Sprite (sheet, 16, 16);
		}
		this.icon = icon;
		animationHandler.setSprite (icon);
	}
	protected void setAnimationSpeed (double speed) {
		animationHandler.setAnimationSpeed (speed);
	}
	public boolean use () {
		return false;
	}
	public void draw (int x, int y) {
		animationHandler.animate (x, y, false, false);
	}
	@Override
	public boolean equals (Object o) {
		if (o.getClass ().getName ().equals (this.getClass ().getName ())) {
			//TODO better solution to durability problem please!
			if (this.type == ((GameItem)o).type && this.name.equals (((GameItem)o).getName ())) {
				if (usePerciseCompare) {
					if (this.properties.equals (((GameItem)o).getProperties ())) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void damageEvent (DamageSource source) {
		//Damage source could be the crafting menu used?
		if (getHealth () <= 0) {
			GameAPI.getGui ().getItemMenu ().removeItem (this);
		}
	}
	
	@Override
	public void damage (double amount) {
		if (!getProperty ("health").equals ("")) {
			setProperty ("health", String.valueOf (Double.parseDouble (getProperty ("health")) - amount));
		}
		//TODO damage amount
		damageEvent (null);
	}
	
	@Override
	public double getHealth () {
		if (!getProperty ("health").equals ("")) {
			return Double.parseDouble (getProperty ("health"));
		} else {
			return Double.NaN;
		}
	}
	
	@Override
	public void setHealth (double health) {
		setProperty ("health", String.valueOf (health));
	}
}