package gameObjects;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import main.GameAPI;
import main.GameObject;
import main.MainLoop;
import resources.Sprite;

public class Hole extends GameObject {
	private boolean active = true;
	public Hole () {
		setSprite (new Sprite ("resources/sprites/hole.png"));
		createHitbox (0, 0, 16, 16);
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
				ArrayList<GameObject> ladders = MainLoop.getObjectMatrix ().getObjects ("gameObjects.Ladder");
				for (int i = 0; i < ladders.size (); i ++) {
					if (ladders.get (i) != null && ladders.get (i).getVariantAttribute ("id").equals (getVariantAttribute ("id"))) {
						Ladder workingLadder = (Ladder)ladders.get (i);
						workingLadder.deactivate ();
						getPlayer ().setX (workingLadder.getX () + Double.parseDouble (workingLadder.getVariantAttribute ("xoffset")));
						getPlayer ().setY (workingLadder.getY () + Double.parseDouble (workingLadder.getVariantAttribute ("yoffset")));
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
