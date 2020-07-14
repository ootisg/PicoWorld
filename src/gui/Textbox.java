package gui;

import java.util.Iterator;
import java.util.LinkedList;

import resources.Spritesheet;

public class Textbox extends MappedUi {
	
	public int paddingTop = 4;
	public int paddingBottom = 4;
	public int paddingLeft = 4;
	public int paddingRight = 4;
	public int lineBuffer = 2;
	
	private LinkedList<TextComponent> components;
	
	private Iterator<TextComponent> componentIterator;
	
	private TextComponent currentComponent;
	
	private String[] text;
	
	public Textbox (Spritesheet tileSheet, int width, int height) {
		super (tileSheet, CraftingMenu.buildTileMap (width, height));
		this.components = new LinkedList<TextComponent> ();
	}
	
	public Textbox (Spritesheet tileSheet, int width, int height, LinkedList<TextComponent> components) {
		this (tileSheet, width, height);
		Iterator<TextComponent> iter = components.iterator ();
		while (iter.hasNext ()) {
			this.components.add (iter.next ());
		}
	}
	
	public Textbox (Spritesheet tileSheet, int width, int height, String text) {
		this (tileSheet, width, height);
		addComponent (new ScrollingText (text));
	}
	
	public String[] getLines () {
		return text;
	}
	
	public String getText () {
		return String.join ("\n", text);
	}
	
	public void addComponent (TextComponent component) {
		component.onAdd (this);
		components.add (component);
	}
	
	private void append (String[] lines) {
		
	}
	
	private String[] fitToBox (String text) {
		boolean whitespaceMode = false;
		boolean firstRun = true;
		int workingWidth = paddingLeft + paddingRight;
		String workingText = "";
		String currentRun = "";
		LinkedList<String> lines = new LinkedList<String> ();
		for (int i = 0; i < text.length (); i ++) {
			if (text.charAt (i) == ' ') {
				whitespaceMode = true;
			} else {
				whitespaceMode = false;
			}
			if (workingWidth + 16 <= getWidth () * 16) {
				if (whitespaceMode || i == text.length () - 1) {
					workingText += currentRun;
					workingText += text.charAt (i);
					currentRun = "";
					firstRun = false;
				} else {
					currentRun += text.charAt (i);
				}
				workingWidth += 8;
			} else {
				if (firstRun) {
					workingText = workingText + currentRun;
				}
				lines.add (workingText);
				if (whitespaceMode) {
					workingText = "" + text.charAt (i);
					workingWidth = 8 + paddingLeft + paddingRight;
					while (i < text.length () && text.charAt (i) == ' ') {
						i ++;
					}
				} else {
					if (firstRun) {
						workingText = "" + text.charAt (i);
						workingWidth = 8 + paddingLeft + paddingRight;
					} else {
						workingText = currentRun + text.charAt (i);
						workingWidth = currentRun.length () * 8 + paddingLeft + paddingRight;
					}
					
				}
				firstRun = true;
				currentRun = "";
			}
		}
		lines.add (workingText);
		return lines.toArray (new String[0]);
	}
	
	private String[] fitToBoxExact (String text) {
		int workingWidth = paddingLeft + paddingRight;
		String workingText = "";
		LinkedList<String> lines = new LinkedList<String> ();
		for (int i = 0; i < text.length (); i ++) {
			if (workingWidth + 16 <= getWidth () * 16) {
				workingText += text.charAt (i);
				workingWidth += 8;
			} else {
				lines.add (workingText);
				workingText = "" + text.charAt (i);
				workingWidth = 8;
			}
		}
		lines.add (workingText);
		return lines.toArray (new String[0]);
	}
	
	@Override
	public void guiFrame () {
		if (componentIterator == null) {
			componentIterator = components.iterator ();
			if (componentIterator.hasNext ()) {
				currentComponent = componentIterator.next ();
			}
		}
		currentComponent.update (this);
		if (currentComponent.isFinished ()) {
			if (currentComponent.waitForConfirm () || !componentIterator.hasNext ()) {
				//Add confirmation stuff here
			} else {
				//text += currentComponent.getDisplayText ();
				currentComponent = componentIterator.next ();
			}
		}
	}
	
	@Override
	public void renderGui () {
		String[] displayText = currentComponent.getDisplayText ().split ("\n");
		for (int i = 0; i < displayText.length; i ++) {
			drawText (displayText [i], paddingLeft, i * 8 + paddingTop);
		}
	}
	
	public interface TextComponent {
		/**
		 * Called when this TextComponent is added to a textbox
		 * @param box The textbox this component has been added to
		 */
		public void onAdd (Textbox box);
		/**
		 * Called once a frame when the textbox is rendered
		 */
		public void update (Textbox box);
		/**
		 * Returns true if the textbox should wait for user input before displaying the next element
		 * @return whether to wait or not
		 */
		public boolean waitForConfirm ();
		/**
		 * Returns true if the component is finished displaying
		 * @return the state of the component
		 */
		public boolean isFinished ();
		/**
		 * Get the text to display in the textbox, as a String
		 * @return the text to display
		 */
		public String getDisplayText ();
	}
	
	public class ScrollingText implements TextComponent {
		
		private double scrollSpeed;
		private double displayProgress;
		private String message;
		
		public ScrollingText (String message, double scrollSpeed) {
			this.message = message;
			this.scrollSpeed = scrollSpeed;
		}
		
		public ScrollingText (String message) {
			this (message, .5);
		}
		
		public double getScrollSpeed () {
			return scrollSpeed;
		}
		
		public double getDisplayProgress () {
			return displayProgress;
		}
		
		public String getMessage () {
			return message;
		}
		
		public void setScrollSpeed (double speed) {
			scrollSpeed = speed;
		}
		
		public void setDisplayProgress (double progress) {
			displayProgress = progress;
		}
		
		public void setMessage (String message) {
			this.message = message;
		}
		
		@Override
		public void update (Textbox box) {
			displayProgress += scrollSpeed;
		}
		
		@Override
		public boolean waitForConfirm () {
			return true;
		}
		
		@Override
		public boolean isFinished () {
			return displayProgress > message.length ();
		}
		
		@Override
		public String getDisplayText () {
			int max = (int)Math.min (displayProgress, message.length ());
			return message.substring (0, max);
		}
		
		@Override
		public void onAdd (Textbox box) {
			String[] messageSplits = box.fitToBox (message);
			message = String.join ("\n", messageSplits);
		}
	}
}
