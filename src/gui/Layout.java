package gui;

import java.awt.Rectangle;
import java.util.ArrayList;

import json.JSONArray;
import json.JSONObject;

public class Layout {

	private ArrayList<Rectangle> cells;
	
	public Layout (JSONArray params) {
		
		//Initialize cells list
		cells = new ArrayList<Rectangle> ();
		
		//Populate the cells
		int numItems = params.getContents ().size ();
		for (int i = 0; i < numItems; i++) {
			JSONObject currentItem = (JSONObject) params.get (i);
			String itemType = currentItem.getString ("type");
			
			//Grid type
			if (itemType.equals ("grid")) {
				
				//Get the bounds
				JSONArray bounds = currentItem.getJSONArray ("bounds");
				JSONArray cellDims = currentItem.getJSONArray ("cells");
				int startX = (int)bounds.get (0) + (int)cellDims.get (0);
				int startY = (int)bounds.get (1) + (int)cellDims.get (1);
				int endX = startX + (int)bounds.get (2);
				int endY = startY + (int)bounds.get (3);
				int cellWidth = (int)cellDims.get (0);
				int cellHeight = (int)cellDims.get (1);
				
				//Separate out the cells of the grid
				for (int wx = startX; wx < endX; wx += cellWidth) {
					for (int wy = startY; wy < endY; wy += cellHeight) {
						Rectangle currCell = new Rectangle (wx - cellWidth, wy - cellHeight, cellWidth, cellHeight);
						cells.add (currCell);
					}
				}
			}
			
			//Cell type
			if (itemType.equals ("cell")) {
				JSONArray bounds = currentItem.getJSONArray ("bounds");
				cells.add (new Rectangle ((int)bounds.get (0), (int)bounds.get (1), (int)bounds.get (2), (int)bounds.get (3)));
			}
			
		}
	}
	
	public ArrayList<Rectangle> getCells () {
		return cells;
	}
	
	public int getCellContainingPoint (int x, int y) {
		for (int i = 0; i < cells.size (); i++) {
			if (cells.get (i).contains (x, y)) {
				return i;
			}
		}
		return -1;
	}
}
