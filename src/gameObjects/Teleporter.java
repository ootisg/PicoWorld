package gameObjects;

import java.util.ArrayList;

import main.GameObject;
import main.MainLoop;
import resources.Sprite;
import resources.Spritesheet;

public class Teleporter extends GameObject {

	public static Spritesheet tpsheet = new Spritesheet ("resources/sprites/teleport_point.png");
	public static Spritesheet tpsheetsel = new Spritesheet ("resources/sprites/teleport_select.png");
	public static Spritesheet tpsheetact = new Spritesheet ("resources/sprites/teleport_active.png");
	public static Sprite tpsprite = new Sprite (tpsheet, 16, 16);
	public static Sprite tpspritesel = new Sprite (tpsheetsel, 16, 16);
	public static Sprite tpspriteact = new Sprite (tpsheetact, 16, 16);
	
	private boolean selected = false;
	private boolean wasColliding = false;
	
	private ArrayList<Teleporter> pairedTeleporters;
	
	public Teleporter () {
		setSprite (tpsprite);
		createHitbox (-4, -4, 24, 24);
	}
	
	@Override
	public void frameEvent () {
	
		//Handle player collision
		boolean wasSelected = selected;
		if (isColliding (getPlayer ())) {
			
			//Set the current teleporter's sprite accordingly
			int currentFrame = getAnimationHandler ().getFrame ();
			setSprite (tpspriteact);
			getAnimationHandler ().setFrame (currentFrame);
			
			//Find the paired teleporters
			if (pairedTeleporters == null) {
				pairedTeleporters = new ArrayList<Teleporter> ();
				ArrayList<GameObject> teleporters = MainLoop.getObjectMatrix ().getObjects ("gameObjects.Teleporter");
				String tpid = getVariantAttribute ("linkId");
				for (int i = 0; i < teleporters.size (); i++) {
					if (teleporters.get (i) != null && teleporters.get (i) != this && tpid.equals (teleporters.get (i).getVariantAttribute ("linkId"))) {
						pairedTeleporters.add ((Teleporter)teleporters.get (i));
					}
				}
			}
			
			//Handle teleporter selection
			for (int i = 0; i < pairedTeleporters.size (); i++) {
				Teleporter workingTeleporter = pairedTeleporters.get (i);
				if (workingTeleporter != null) {
					int adjustedMouseX = getRoom ().getViewX () + getMouseX ();
					int adjustedMouseY = getRoom ().getViewY () + getMouseY ();
					if (workingTeleporter.getHitbox ().contains (adjustedMouseX, adjustedMouseY)) {
						if (!workingTeleporter.selected) {
							//Select the teleporter
							currentFrame = workingTeleporter.getAnimationHandler ().getFrame ();
							workingTeleporter.setSprite (tpspritesel);
							workingTeleporter.getAnimationHandler ().setFrame (currentFrame);
							workingTeleporter.selected = true;
						}
						if (mouseClicked ()) {
							//Teleport the player
							getPlayer ().setX (workingTeleporter.getX ());
							getPlayer ().setY (workingTeleporter.getY ());
							getPlayer ().setVariantAttribute ("layer", workingTeleporter.getVariantAttribute ("layer"));
						}
					} else {
						if (workingTeleporter.selected || workingTeleporter.getSprite () == tpsprite) {
							//Deselect the teleporter
							currentFrame = workingTeleporter.getAnimationHandler ().getFrame ();
							workingTeleporter.setSprite (tpspriteact);
							workingTeleporter.getAnimationHandler ().setFrame (currentFrame);
							workingTeleporter.selected = false;
						}
					}
				}
			}
			
			//Upadate wasColliding
			wasColliding = true;
			
		} else if (wasColliding) {
			
			//Set the current teleporter's sprite accordingly
			int currentFrame = getAnimationHandler ().getFrame ();
			setSprite (tpsprite);
			getAnimationHandler ().setFrame (currentFrame);
			
			//De-select all other teleporters
			if (pairedTeleporters != null) {
				for (int i = 0; i < pairedTeleporters.size (); i++) {
					Teleporter workingTeleporter = pairedTeleporters.get (i);
					if (workingTeleporter != null) {
						currentFrame = workingTeleporter.getAnimationHandler ().getFrame ();
						workingTeleporter.setSprite (tpsprite);
						workingTeleporter.getAnimationHandler ().setFrame (currentFrame);
						workingTeleporter.selected = false;
					}
				}
			}
			
			//Update wasColliding
			wasColliding = false;
		}
		
	}
	
	public void doTeleport (Teleporter dest) {
		
	}
	
}
