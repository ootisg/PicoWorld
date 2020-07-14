package cutscenes;

public abstract class TimedEvent extends Event {

	int ms = 0;
	
	@Override
	public void doFrame () {
		ms++;
	}

	@Override
	public boolean isOver () {
		if (ms >= getDuration ()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getElapsedTimeMs () {
		return ms;
	}
	
	public int getDuration () {
		return getArgs ().getInt ("duration");
	}

}
