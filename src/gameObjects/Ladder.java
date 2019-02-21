package gameObjects;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import main.GameAPI;
import main.GameObject;
import main.MainLoop;
import resources.Sprite;

public class Ladder extends GameObject {
	private boolean active = true;
	public Ladder () {
		setSprite (new Sprite ("resources/sprites/ladder.png"));
		createHitbox (0, 0, 16, 20);
	}
	@Override
	public void frameEvent () {
		if (isColliding (GameAPI.getPlayer ())) {
			if (active) {
				this.setPersistent (true);
				try {
					getRoom ().loadRoom ("resources/maps/" + getVariantAttribute ("destination"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<GameObject> holes = MainLoop.getObjectMatrix ().getObjects ("gameObjects.Hole");
				for (int i = 0; i < holes.size (); i ++) {
					if (holes.get (i).getVariantAttribute ("id").equals (getVariantAttribute ("id"))) {
						Hole workingHole = (Hole)holes.get (i);
						workingHole.deactivate ();
						getPlayer ().setX (workingHole.getX () + Double.parseDouble (workingHole.getVariantAttribute ("xoffset")));
						getPlayer ().setY (workingHole.getY () + Double.parseDouble (workingHole.getVariantAttribute ("yoffset")));
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
}
