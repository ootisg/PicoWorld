package gameObjects;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import main.GameAPI;
import main.GameObject;
import main.MainLoop;
import resources.Sprite;

public class MapExit extends GameObject {
	private boolean active = true;
	private boolean locked = false;
	public MapExit () {
		createHitbox (-2, -2, 20, 20);
	}
	@Override
	public void onDeclare () {
		//Set the sprite appropriately
		String iconName = getVariantAttribute ("type");
		setSprite (new Sprite ("resources/sprites/" + iconName + ".png"));
	}
	@Override
	public void frameEvent () {
		if (isColliding (GameAPI.getPlayer ())) {
			if (active && !locked) {
				this.setPersistent (true);
				try {
					getRoom ().loadRoom ("resources/maps/" + getVariantAttribute ("destination"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<GameObject> entrances = MainLoop.getObjectMatrix ().getObjects ("gameObjects.MapExit");
				for (int i = 0; i < entrances.size (); i ++) {
					if (entrances.get (i) != null && entrances.get (i) != this && entrances.get (i).getVariantAttribute ("id").equals (getVariantAttribute ("id"))) {
						MapExit workingEntrance = (MapExit)entrances.get (i);
						workingEntrance.deactivate ();
						
						//Parse out the entrance offsets
						double offsetX;
						double offsetY;
						try {
							offsetX = Double.parseDouble (workingEntrance.getVariantAttribute ("xoffset"));
							offsetY = Double.parseDouble (workingEntrance.getVariantAttribute ("yoffset"));
						} catch (NumberFormatException e) {
							//Set to defaults
							offsetX = 0;
							offsetY = 0;
						}
						
						//Move the player to the correct entrance
						getPlayer ().setX (workingEntrance.getX () + offsetX);
						getPlayer ().setY (workingEntrance.getY () + offsetY);
						getPlayer ().setVariantAttribute ("layer", workingEntrance.getVariantAttribute ("layer"));
						getPlayer ().focusView ();
					}
				}
				this.forget ();
			}
		} else if (!active) {
			active = true;
		}
	}
	public void deactivate () {
		active = false;
	}
	public void setLocked (boolean locked) {
		this.locked = locked;
	}
	public boolean isLocked () {
		return locked;
	}
}
