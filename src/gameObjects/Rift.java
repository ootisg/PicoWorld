package gameObjects;

import java.io.FileNotFoundException;

import cutscenes.Callback;
import cutscenes.Cutscene;
import main.GameObject;
import main.MainLoop;
import music.MusicPlayer;
import resources.Sprite;

public class Rift extends GameObject implements Callback {

	public static Sprite riftSprite = new Sprite ("resources/sprites/rift.png");
	
	public Rift () {
		setSprite (riftSprite);
		createHitbox (4, 16, 8, 16);
	}
	
	@Override
	public void declare (double x, double y) {
		super.declare (x, y - 16); //Move the rift up to account for the tall sprite
	}
	
	@Override
	public void frameEvent () {
		if (getPlayer ().isColliding (this)) {
			MusicPlayer.stop ();
			MainLoop.pause ();
			Cutscene scene = new Cutscene ("resources/cutscenes/enter_rift.json");
			scene.setCallback (this);
		}
	}
	
	@Override
	public void call () {
		MainLoop.resume ();
		loadDestination ();
	}
	
	public void loadDestination () {
		try {
			getRoom ().loadRoom ("resources/maps/" + getVariantAttribute ("destination"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
