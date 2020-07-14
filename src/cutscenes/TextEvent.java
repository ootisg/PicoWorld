package cutscenes;

import java.awt.Graphics2D;

import json.JSONObject;
import main.MainLoop;

public class TextEvent extends TimedEvent {
	
	@Override
	public void start () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFrame () {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void end () {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw () {
		Graphics2D g = (Graphics2D)MainLoop.getWindow ().getBufferGraphics ();
		JSONObject params = getArgs ().getJSONObject ("params");
		g.drawString (params.getString ("text"), params.getInt ("xPos"), params.getInt ("yPos"));
	}
}
