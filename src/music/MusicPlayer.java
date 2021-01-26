package music;

import main.GameAPI;
import main.GameWindow;

public class MusicPlayer {

	static GameWindow.SoundClip clip;
	
	public static void playSong (String filepath) {
		clip = GameAPI.getWindow ().playSound (filepath);
	}
	
	public static void stop () {
		clip.stop ();
	}
	
}
