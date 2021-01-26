package cutscenes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import visualEffects.ScreenOverlay;

public class OverlayEvent extends TimedEvent {
	
	private ScreenOverlay overlay;
	
	@Override
	public void doFrame () {
		
		//Generate the overlay if it's not already made
		if (overlay == null) {
			String ovType = getArgs ().getJSONObject ("params").getString ("type");
			try {
				Class<?> ovClass = Class.forName ("visualEffects." + ovType);
				Constructor<?> ovConstructor = ovClass.getConstructors ()[0];
				overlay = (ScreenOverlay)ovConstructor.newInstance ();
				overlay.setProperties (getArgs ().getJSONObject ("params"));
				overlay.declare (0, 0);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void end () {
		overlay.forget ();
	}

	@Override
	public void draw () {
		//Do nothing intentionally
	}

}
