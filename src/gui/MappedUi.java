package gui;

import main.MainLoop;
import resources.Sprite;
import resources.Spritesheet;

public class MappedUi extends GuiComponent {
	private int[][] map;
	private Spritesheet tileSheet;
	private Sprite tiles;
	public MappedUi (Spritesheet tileSheet, int[][] map) {
		super ();
		this.map = map;
		this.tileSheet = tileSheet;
		this.tiles = new Sprite (tileSheet, 16, 16);
	}
	public void setMap (int[][] map) {
		this.map = map;
	}
	public int[][] getMap () {
		return map;
	}
	public void setTileSheet (Spritesheet tileSheet) {
		this.tileSheet = tileSheet;
		this.tiles = new Sprite (tileSheet, 16, 16);
	}
	public Spritesheet getTileSheet () {
		return tileSheet;
	}
	@Override
	public void draw () {
		if (getGui ().guiOpen () && map != null) {
			if (map.length > 0) {
				for (int i = 0; i < map.length; i ++) {
					for (int j = 0; j < map[0].length; j ++) {
						tiles.draw ((int)this.getX () + j * 16, (int)this.getY () + i * 16, map [i][j]);
					}
				}
			}
			renderGui ();
		}
	}
	public void renderGui () {
		
	}
	public int getWidth () {
		return map [0].length;
	}
	public int getHeight () {
		return map.length;
	}
}