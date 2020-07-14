package cutscenes;

public abstract class TimedEvent extends Event {

	long startTime;

	protected TimedEvent () {
		startTime = System.currentTimeMillis ();
	}
	
	@Override
	public boolean isOver () {
		if (getElapsedTimeMs () >= getDuration ()) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getElapsedTimeMs () {
		return (int)(System.currentTimeMillis () - startTime);
	}
	
	public int getDuration () {
		return getArgs ().getInt ("duration");
	}

}
