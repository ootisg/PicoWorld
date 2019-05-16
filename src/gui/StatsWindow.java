package gui;

import java.util.ArrayList;

import main.MainLoop;
import resources.Spritesheet;

public class StatsWindow extends MappedUi {
	
	public static final int PADDING_TOP = 4;
	public static final int PADDING_BOTTOM = 4;
	public static final int PADDING_LEFT = 4;
	public static final int PADDING_RIGHT = 4;
	public static final int LINE_BUFFER = 2;
	
	public StatsWindow () {
		super (new Spritesheet ("resources/sprites/gui_background.png"), new int[0][0]);
	}
	
	@Override public void renderGui () {
		ArrayList<String> lines = new ArrayList<String> ();
		//Add stats to the stats window
		lines.add ("HEALTH: " + (int)getPlayer ().getHealth () + "/" + (int)getPlayer ().getMaxHealth ());
		lines.add ("MANA: " + (int)getPlayer ().getMana () + "/" + (int)getPlayer ().getMaxMana ());
		int largestStringLength = 0;
		for (int i = 0; i < lines.size (); i ++) {
			if (lines.get (i).length () > largestStringLength) {
				largestStringLength = lines.get (i).length ();
			}
		}
		int workingWidth = (int)(Math.ceil (((double)(PADDING_LEFT + PADDING_RIGHT + largestStringLength * 8)) / 16));
		int workingHeight = (int)(Math.ceil ((double)((PADDING_TOP + PADDING_BOTTOM + lines.size () * 8 + (lines.size () - 1) * LINE_BUFFER) / 16)));
		if (workingHeight < 2) {
			workingHeight = 2;
		}
		setMap (CraftingMenu.buildTileMap (workingWidth, workingHeight));
		setX (MainLoop.getWindow ().getResolution ()[0] - workingWidth * 16);
		setY (0);
		for (int i = 0; i < lines.size (); i ++) {
			drawText (lines.get (i), PADDING_LEFT, PADDING_TOP + i * (8 + LINE_BUFFER));
		}
	}
}