package gui;

import java.util.ArrayList;

import main.GameObject;
import main.MainLoop;
import visualEffects.Outline;

public class InteractBubble extends GameObject {
	
	private double radius;
	
	private GameObject hoveredObj;
	
	private Outline defaultHover;
	private int[] defaultHoverColor;
	
	public InteractBubble (double radius) {
		
		//Set properties
		setRadius (radius);
		setPersistent (true);
		setDefaultHoverColor (0xFF, 0xFF, 0xFF, 0xFF);
		
		//Setup the hover outline
		defaultHover = new Outline (this, defaultHoverColor);
		defaultHover.declare (0, 0);
		defaultHover.setHidden (true);
		defaultHover.setPersistent (true);
	}
	
	public double getRadius () {
		return radius;
	}
	
	public GameObject getHoveredObject () {
		return hoveredObj;
	}
	
	public int[] getDefaultHoverColor () {
		return defaultHoverColor;
	}
	
	public void setRadius (double radius) {
		this.radius = radius;
		createHitbox ((int)-radius, (int)-radius, (int)radius * 2, (int)radius * 2);
	}
	
	public void setCenter (double x, double y) {
		double xOff = getCenterX () - getX ();
		double yOff = getCenterY () - getY ();
		setX (x - xOff);
		setY (y - yOff);
	}
	
	public void setDefaultHoverColor (int r, int g, int b, int a) {
		defaultHoverColor = new int[] {r, g, b, a};
	}
	
	@Override
	public void frameEvent () {
		
		//Get the interactables and mouse coords
		ArrayList<GameObject> interactables = MainLoop.getObjectMatrix ().getAll (Interactable.class);
		int worldMouseX = getMouseX () + getRoom ().getViewX ();
		int worldMouseY = getMouseY () + getRoom ().getViewY ();
		
		//Interact with objects
		for (int i = 0; i < interactables.size (); i++) {
			Interactable curr = (Interactable)interactables.get (i);
			if (interactables.get (i).isColliding (this) && this.getDistance (interactables.get (i)) < radius) {
				if (((GameObject)curr).getHitbox ().contains (worldMouseX, worldMouseY)) {
					if (hoveredObj != curr) {
						hoveredObj = (GameObject)curr;
						if (curr.useDefaultHover ()) {
							defaultHover (curr);
						} else {
							curr.hover ();
						}
					}
				} else {
					if (hoveredObj == curr) {
						hoveredObj = null;
						if (curr.useDefaultHover ()) {
							defaultUnhover (curr);
						} else {
							curr.unhover ();
						}
					}
				}
				if (hoveredObj == curr && mouseClicked ()) {
					curr.click ();
				}
			} else {
				if (interactables.get (i) == hoveredObj) {
					hoveredObj = null;
					if (curr.useDefaultHover ()) {
						defaultUnhover (curr);
					} else {
						curr.unhover ();
					}
				}
			}
		}
	}
	
	private void defaultHover (Interactable obj) {
		if (obj instanceof GameObject) {
			GameObject gobj = (GameObject)obj;
			defaultHover.setTracedObject (gobj);
			defaultHover.setHidden (false);
		}
	}
	
	private void defaultUnhover (Interactable obj) {
		if (obj instanceof GameObject) {
			defaultHover.setHidden (true);
		}
	}

}
