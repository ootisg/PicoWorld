package farming;

import java.awt.Color;

import gameObjects.ItemDrop;
import gameObjects.Particle;
import gameObjects.Sword;
import items.Wheat;
import main.GameObject;
import main.MainLoop;
import resources.Sprite;
import visualEffects.ParticleMaker;

public class WheatCrop extends GameObject {

	public static Sprite wheatSprite = new Sprite ("resources/sprites/crops/Wheat.png");
	public static ParticleMaker wheatParticleMaker = null;
	
	public WheatCrop () {
		setSprite (wheatSprite);
		createHitbox (0, 0, 16, 16);
		if (wheatParticleMaker == null) {
			initWheatParticleMaker ();
		}
	}
	
	public void initWheatParticleMaker () {
		wheatParticleMaker = new ParticleMaker ();
		wheatParticleMaker.setMinAng (0);
		wheatParticleMaker.setMaxAng (Math.PI * 2);
		wheatParticleMaker.setMinSpeed (1);
		wheatParticleMaker.setMaxSpeed (2.5);
		wheatParticleMaker.setColor1 (new Color (0xCCBB50));
		wheatParticleMaker.setColor2 (new Color (0xE5BB50));
		wheatParticleMaker.setMinLifespan (80);
		wheatParticleMaker.setMaxLifespan (150);
		wheatParticleMaker.setMinSize (2);
		wheatParticleMaker.setMaxSize (3);
	}
	
	@Override
	public void frameEvent () {
		Sword sword = (Sword)MainLoop.getObjectMatrix ().get ("gameObjects.Sword", 0);
		if (sword != null && sword.isColliding (this)) {
			int numParticles = ((int)(Math.random () * 3)) + 2;
			for (int i = 0; i < numParticles; i++) {
				double px = getX () + Math.random () * 16;
				double py = getY () + Math.random () * 16;
				Particle p = wheatParticleMaker.makeParticle ((int)px, (int)py);
				p.setFriction (.5);
			}
			if (Math.random () < .2) {
				new ItemDrop (new Wheat ()).declare (getX (), getY ());
			}
			forget ();
		}
	}
	
}
