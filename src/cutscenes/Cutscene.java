package cutscenes;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import json.JSONUtil;
import main.GameObject;
import main.MainLoop;
import main.ObjectMatrix;

public class Cutscene extends GameObject {

	private String path;
	private JSONObject source;
	
	private JSONArray objDecs;
	private JSONArray eventDecs;
	
	private ArrayList<Event> events;
	private HashMap<String, GameObject> sceneObjects;
	
	private ArrayList<Event> eventsInProgress = null;
	
	/**
	 * Makes a new cutscene from the JSON file at the given filepath
	 * 
	 * JSON tags/formatting/whatnot:
	 * Objects: Array of JSON objects
	 * Object format:
	 * {
	 * 		"id":String
	 * 		"type":String - Must be a Class name
	 * 		"gen":Object - See format below
	 * }
	 * Gen object format:
	 * {
	 * 		"genMethod":String - make or hijack
	 * 		"startX":int - optional
	 * 		"startY":int - optional
	 * 		"params":Array - list of params to pass to constructor (optional)
	 * }
	 * Event format:
	 * {
	 * 		"id":String
	 * 		"type":String - Must be an event type
	 * 		"duration":int - The number of ms this event lasts (optional for some events)
	 * 		"params":Object - See format below
	 * 		"trigger":Object - See format below
	 * }
	 * Trigger object format:
	 * {
	 * 		"eventId":String - The event to listen for the trigger from
	 * 		"type":String - The type of trigger to listen for; must be "end"
	 * }
	 * Params object format (varies based on event):
	 * Descriptions in respective event classes
	 * 
	 * @param filepath the filepath to use
	 */
	public Cutscene (String filepath) {
		
		//Set the filepath and cutscene contents accordingly
		path = filepath;
		try {
			source = JSONUtil.loadJSONFile (filepath);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Get object and event source
		objDecs = source.getJSONArray ("objs");
		eventDecs = source.getJSONArray ("events");
		
		//Make all the scene objects
		sceneObjects = new HashMap<String, GameObject> ();
		for (int i = 0; i < objDecs.getContents ().size (); i++) {
			JSONObject jobj = (JSONObject)objDecs.get (i);
			genObject (jobj);
		}
		
		//Make all the events
		for (int i = 0; i < eventDecs.getContents ().size (); i++) {
			Event newEvent = null;
			JSONObject currEvent = (JSONObject)eventDecs.get (i);
			Class<?> eventClass = null;
			try {
				eventClass = Class.forName ("cutscenes." + currEvent.getString ("type"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Constructor<?> eventConstructor = eventClass.getConstructors ()[0]; //Add support for better constructor stuffs?
			try {
				newEvent = (Event)eventConstructor.newInstance (currEvent);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		declare (0, 0);
	}
	
	public void genObject (JSONObject obj) {
		
		//Extract object info from the given JSONObject
		String id = obj.getString ("id");
		String type = obj.getString ("type");
		JSONObject gen = obj.getJSONObject ("gen");
		String genMethod;
		int startX;
		int startY;
		JSONArray params;
		if (id != null && type != null && gen != null) {
			
			//Extract gen info
			genMethod = gen.getString ("genMethod");
			startX = gen.getInt ("startX");
			startY = gen.getInt ("startY");
			params = gen.getJSONArray ("params"); //Currently non-functional
			
			//Generate the object
			if (genMethod.equals ("make")) {
				
				//Make the object and add to scene objects if successful
				GameObject newObj = null;
				try {
					newObj = ObjectMatrix.makeInstance (type);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					//Do nothing
				}
				if (newObj != null) {
					//Instance was successfully made
					newObj.declare (startX, startY);
					sceneObjects.put (id, newObj);
				} else {
					//Error handling
				}
			} else if (genMethod.equals ("hijack")) {
				
				//Hijack objects here
				//TODO add more flexible hijack parameters
				ObjectMatrix mat = MainLoop.getObjectMatrix ();
				ArrayList<GameObject> objs = mat.getObjects (type);
				GameObject newObj = objs.get (0);
				sceneObjects.put (id, newObj); //TODO add support for startX and startY parameters
			} else {
				//Invalid genMethod
			}
		} else {
			
		}
	}
	
	public void startEvent (Event e) {
		eventsInProgress.add (e);
		e.start ();
	}
	
	public void endEvent (Event e) {
		e.end ();
		
		//Start events with the ended event as a trigger
		JSONObject eArgs = e.getArgs ();
		JSONObject eTrigger = eArgs.getJSONObject ("trigger");
		String eTriggerId = eTrigger.getString ("eventId"); //TODO make helper method
		for (int i = 0; i < events.size (); i++) {
			Event curr = events.get (i);
			if (curr == e && i < events.size () - 1) {
				Event next = events.get (i + 1);
				JSONObject nextArgs = next.getArgs ();
				JSONObject nextTrigger = nextArgs.getJSONObject ("trigger");
				if (nextTrigger != null) {
					startEvent (curr);
				}
			} else {
				JSONObject args = curr.getArgs ();
				String currId = curr.getId ();
				if (eTriggerId.equals (currId)) {
					startEvent (curr);
				}
			}
		}
	}
	
	@Override
	public void frameEvent () {
		if (eventsInProgress == null) {
			eventsInProgress = new ArrayList<Event> ();
			startEvent (events.get (0));
		}
		for (int i = 0; i < eventsInProgress.size (); i++) {
			Event curr = eventsInProgress.get (i);
			if (curr.isOver ()) {
				//End event if it's finished
				endEvent (curr);
				eventsInProgress.remove (curr);
				i--; //Decrements the index to correct the position
			} else {
				curr.doFrame ();
			}
		}
	}
	
	@Override
	public void draw () {
		for (int i = 0; i < eventsInProgress.size (); i++) {
			eventsInProgress.get (i).draw ();
		}
	}
}
