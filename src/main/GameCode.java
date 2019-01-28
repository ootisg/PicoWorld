package main;

import java.awt.Color;
import java.io.FileNotFoundException;

import gameObjects.FrostElemental;
import gameObjects.Particle;
import gameObjects.Player;
import items.Apple;
import items.GoldSword;
import items.ItemDrop;

public class GameCode extends GameAPI {
	private GameWindow gameWindow;
	public void initialize () {
		//Runs on initialization
		//MainLoop.getWindow ().setResolution (480, 480);
		try {
			room.loadRoom ("resources/maps/gamemap.cmf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Player (256, 16);
		new FrostElemental ().declare (32, 32);
		new ItemDrop (new GoldSword ()).declare (128, 128);
		new ItemDrop (new Apple ()).declare (128, 150);
		for (int i = 0; i < 36; i ++) {
			new ItemDrop (new Apple ()).declare (429 + i * 16, 601);
		}
		MainLoop.getWindow ().setResolution (512, 288);
		MainLoop.getWindow ().setSize (1024, 576);
	}
	public void gameLoop () {
		room.frameEvent ();
		//Runs once per frame
	}
}