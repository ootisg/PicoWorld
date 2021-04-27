package gui;

import java.awt.Color;

public class HealthBar extends GuiBar {

	public HealthBar () {
		super (makeHealthBarColorMap ());
		setPersistent (true);
		setPriority (-420);
	}
	
	private static ColorMap makeHealthBarColorMap () {
		ColorMap healthBarMap = new ColorMap ();
		healthBarMap.addColor (new Color (0x008000), 0.7);
		healthBarMap.addColor (new Color (0xFFC000), 0.5);
		healthBarMap.addColor (new Color (0xC00000), 0.2);
		return healthBarMap;
	}

}
