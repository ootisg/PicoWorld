package gameObjects;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import main.GameObject;

public abstract class Saveable extends GameObject {
	
	private static ArrayList<String> saveData = new ArrayList<String> ();
	
	protected Saveable () {
		super ();
	}
	
	public void save (String data) {
		int objectIndex = -1;
		for (int i = 0; i < saveData.size (); i ++) {
			if (saveData.get (i).split (":") [0].equals (getRoom ().getRoomName ()) && Integer.toString (getId ()).equals (saveData.get (i).split (":")[1])) {
				objectIndex = i;
			}
		}
		String objectSave = getRoom ().getRoomName () + ":" + getId () + ":" + data;
		if (objectIndex == -1) {
			objectIndex = saveData.size ();
			saveData.add (objectSave);
		} else {
			saveData.set (objectIndex, objectSave);
		}
	}
	
	protected String getSaveData () {
		int objectIndex = -1;
		for (int i = 0; i < saveData.size (); i ++) {
			//System.out.println (saveData.get (i).split (":") [0] + ", " + getRoom ().getRoomName () + ", " + Integer.toString (getId ()) + ", " + saveData.get (i).split (":")[1]);
			if (saveData.get (i).split (":") [0].equals (getRoom ().getRoomName ()) && Integer.toString (getId ()).equals (saveData.get (i).split (":")[1])) {
				objectIndex = i;
			}
		}
		if (objectIndex == -1) {
			return null;
		} else {
			return saveData.get (objectIndex);
		}
	}
	
	public int getId () {
		return (((int)getStartPos ()[0] & 0xFFFF) << 16) + (((int)getStartPos ()[1] & 0xFFFF));
	}
	
	public static void writeData (String filename) throws IOException {
		PrintWriter writer = new PrintWriter (new File (filename));
		for (int i = 0; i < saveData.size (); i ++) {
			if (i != saveData.size () - 1) {
				writer.println (saveData.get (i));
			} else {
				writer.print (saveData.get (i));
			}
		}
		writer.close ();
	}
	
	public static void loadData (String filename) throws IOException {
		saveData = new ArrayList<String> ();
		Scanner in = new Scanner (new File (filename));
		while (in.hasNextLine ()) {
			saveData.add (in.nextLine ());
		}
		in.close ();
	}
	
	public static void printSaves () {
		for (int i = 0; i < saveData.size (); i ++) {
			System.out.println (saveData.get (i));
		}
	}
	
	public abstract void load ();
}
