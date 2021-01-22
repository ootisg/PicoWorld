package visualEffects;

import java.awt.Color;

import gameObjects.Particle;
import main.MainLoop;

public class ParticleOverlay extends ScreenOverlay {
	
	private ParticleMaker maker;
	
	public ParticleOverlay (ParticleMaker maker) {
		this.maker = new ParticleMaker ();
		this.maker.setMinAng (Math.PI / 3);
		this.maker.setMaxAng (Math.PI * 2 / 3);
		this.maker.setMinSpeed (1);
		this.maker.setMaxSpeed (2);
		this.maker.setMinSize (1);
		this.maker.setMaxSize (3);
		this.maker.setColor1 (new Color (0x600040));
		this.maker.setColor2 (new Color (0x000000));
	}
	
	@Override
	public void frameEvent () {
		int[] res = MainLoop.getWindow ().getResolution ();
		for (int i = 0; i < 10; i++) {
			int x = (int)(res[0] * Math.random ());
			int y = (int)(res[1] * Math.random ());
			Particle p = maker.makeParticle (x, y);
		}
	}
	
	@Override
	public void draw () {
		//Do nothing
	}
	

	
}
