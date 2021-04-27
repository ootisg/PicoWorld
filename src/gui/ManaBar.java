package gui;

import java.awt.Color;

public class ManaBar extends GuiBar {

	public ManaBar () {
		super (makeManaBarColorMap ());
		setPersistent (true);
		setPriority (-420);
	}
	
	private static ColorMap makeManaBarColorMap () {
		ColorMap healthBarMap = new ColorMap ();
		healthBarMap.addColor (new Color (0x0000FF), 0.5);
		return healthBarMap;
	}

}
