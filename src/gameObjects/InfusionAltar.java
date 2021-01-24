package gameObjects;

import java.awt.Color;
import java.util.ArrayList;

import gui.Interactable;
import items.GameItem;
import items.MagicPiece;
import items.SpeckledStone;
import main.GameObject;
import resources.Sprite;

public class InfusionAltar extends GameObject implements Interactable {
	
	GameItem item;
	
	Color particleColor;
	
	int animationTime;
	
	public static final int ANIMATION_FRAME_COUNT = 400;
	public static final int PARTICLES_PER_FRAME = 10;
	public static final int PARTICLE_RANGE = 16;
	
	public InfusionAltar () {
		setSprite (new Sprite ("resources/sprites/infusion_altar.png"));
		animationTime = -1;
		setParticleColor (new Color (0x00FFFF));
		createHitbox (0, 0, 16, 16);
	}
	
	public void infuse () {
		item = new MagicPiece ();
	}
	
	public void setParticleColor (Color color) {
		particleColor = color;
	}
	
	@Override
	public void click () {
		if (item == null) {
			ArrayList<GameItem> infusableItems = getGui ().getItemMenu ().filter ("infusable", "true");
			if (infusableItems.size () != 0) {
				item = infusableItems.get (0);
				getGui ().getItemMenu ().removeItem (item);
			}
		} else if (!"true".equals (item.getProperty ("infusable"))) {
			if (getGui ().getItemMenu ().addItem (item)) {
				item = null;
			}
		} else {
			if (getGui ().getMagicContainer ().full ()) {
				getGui ().getMagicContainer ().setFill (0);
				animationTime = ANIMATION_FRAME_COUNT;
			}
		}
	}
	
	@Override
	public void frameEvent () {
		if (animationTime != -1) {
			int midX = (int)getX () + 8;
			int midY = (int)getY () + 8;
			for (int i = 0; i < (PARTICLES_PER_FRAME * (animationTime / ANIMATION_FRAME_COUNT + 1)); i ++) {
				int usedX = (int)(Math.random () * PARTICLE_RANGE * 2) - PARTICLE_RANGE + midX;
				int usedY = (int)(Math.random () * PARTICLE_RANGE * 2) - PARTICLE_RANGE + midY;
				if ((midX - usedX) * (midX - usedX) + (midY - usedY) * (midY - usedY) <= PARTICLE_RANGE * PARTICLE_RANGE) {
					new Particle (usedX, usedY, particleColor, 1, 20, Math.random () * Math.PI * 2, .5);
				}
			}
		}
		if (animationTime > 0) {
			animationTime--;
		}
		if (animationTime == 0) {
			animationTime = -1;
			infuse ();
		}
	}
	
	@Override
	public void draw () {
		super.draw ();
		if (item != null) {
			item.draw ((int)getX () - getRoom ().getViewX (), (int)getY () - getRoom ().getViewY ());
		}
	}

	@Override
	public void hover () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unhover () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean useDefaultHover () {
		return true;
	}
}
