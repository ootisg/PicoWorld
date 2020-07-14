package cutscenes;

import java.awt.Graphics2D;

import main.MainLoop;

public class TextEvent extends TimedEvent {

	@Override
	public void start () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end () {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw () {
		Graphics2D g = (Graphics2D)MainLoop.getWindow ().getBufferGraphics ();
		g.drawString ("OWO WHAT'S THIS?", 32, 32);
	}

}
