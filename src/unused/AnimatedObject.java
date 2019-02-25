package unused;

import java.util.ArrayList;

import main.GameObject;
import resources.Sprite;

public abstract class AnimatedObject extends GameObject {
	
	private ArrayList<String> stateNameList;
	private ArrayList<Sprite> stateSpriteList;
	private int state;
	
	protected AnimatedObject () {
		stateNameList = new ArrayList<String> ();
		stateSpriteList = new ArrayList<Sprite> ();
		state = -1;
	}
	
	protected void addState (String name, Sprite animation) {
		stateNameList.add (name);
		stateSpriteList.add (animation);
	}
	
	public void setState (String name) {
		int index = stateNameList.indexOf (name);
		if (index != -1) {
			state = index;
			setSprite (stateSpriteList.get (index));
		}
	}
	
	public String getState () {
		return stateNameList.get (state);
	}
	
	public Sprite getSprite (String stateName) {
		int index = stateNameList.indexOf (stateName);
		if (index != -1) {
			return stateSpriteList.get (index);
		}
		return null;
	}
}