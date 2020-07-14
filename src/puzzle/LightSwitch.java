package puzzle;

import resources.Sprite;
import resources.Spritesheet;

public class LightSwitch extends PuzzleComponent {
	
	private LightPath hitByPath = null;
	
	public LightSwitch () {
		Sprite s = new Sprite ("resources/sprites/light_switch_inactive.png");
		this.setSprite (s);
		createHitbox (0, 0, 16, 16);
	}
	
	public void activate (LightPath path) {
		hitByPath = path;
		Sprite s = new Sprite ("resources/sprites/light_switch_active.png");
		setSprite (s);
	}
	
	public void deactivate () {
		hitByPath = null;
		Sprite s = new Sprite ("resources/sprites/light_switch_inactive.png");
		setSprite (s);
	}
	
	@Override
	public void frameEvent () {
		super.frameEvent ();
		if (hitByPath != null && hitByPath.isFinished ()) {
			deactivate ();
		}
	}
}
