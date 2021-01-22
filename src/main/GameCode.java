package main;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import ai.PathDisplayer;
import ai.Pathfinder;
import cutscenes.Cutscene;
import gameObjects.AnimationTester;
import gameObjects.BoundsMarker;
import gameObjects.FlameElemental;
import gameObjects.FrostElemental;
import gameObjects.GlobalSave;
import gameObjects.InfusionAltar;
import gameObjects.Isopod;
import gameObjects.MagicDrop;
import gameObjects.MagicSlime;
import gameObjects.MapStructure;
import gameObjects.Particle;
import gameObjects.Player;
import gameObjects.Saveable;
import gameObjects.SmallCollider;
import gameObjects.TestEnemy;
import gameObjects.Tree;
import items.*;
import projectiles.PlayerMagic;
import puzzle.LightFocuser;
import puzzle.LightSwitch;
import resources.Sprite;
import resources.Spritesheet;
import visualEffects.ColorOverlay;
import visualEffects.FlashOverlay;
import visualEffects.ParticleOverlay;

public class GameCode extends GameAPI {
	private GameWindow gameWindow;
	public void initialize () {
		//Set the save file path
		getSave ().setFile ("saves/save.txt");
		//Create the global save data
		new GlobalSave ().declare (0, 0);
		//Load the starting room
		try {
			//getRoom ().loadRoom ("resources/maps/testmap3.cmf");
			getRoom ().loadRoom ("resources/maps/overworld.rmf");
			//getRoom ().loadRoom ("resources/maps/cave_1.rmf");
			//getRoom ().loadRoom ("resources/maps/testermap.rmf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Declare the player
		getPlayer ().declare (288, 32);
		//new InfusionAltar ().declare (32, 32);
		//Create enemies (TODO)
		//new TestEnemy ().declare (128, 160);
		//new FlameElemental ().declare (32, 32);
		//new Isopod ().declare (128, 64);
		//new MagicDrop (new MagicGem ()).declare (32, 48);
		//new MagicSlime ().declare (32, 64);
		//new LightFocuser ().declare (16, 16);
		getGui ().getItemMenu ().addItem (new LightBoltSpell ());
		/*for (int i = 0; i < 576; i ++) {
		 //MAKES APPLES
			for (int j = 0; j < 576; j ++) {
				new ItemDrop (new Apple ()).declare (429 + i * 1, 601 + j * 1);
			}
		}*/
		/**for (int i = 0; i < 3; i ++) {
			//new ItemDrop (new Apple ()).declare (429 + i * 16, 200);
			//new ItemDrop (new GoldSword ()).declare (429 + i * 16, 232);
			//new ItemDrop (new SilverSword ()).declare (429 + i * 16, 264);
			new ItemDrop (new Apple ()).declare (429 + i * 16, 296);
			new ItemDrop (new GoldBar ()).declare (429 + i * 16, 328);
			new ItemDrop (new SilverBar ()).declare (429 + i * 16, 360);
			new ItemDrop (new MythrilBar ()).declare (429 + i * 16, 392);
			new ItemDrop (new WoodPlanks ()).declare (429 + i * 16, 424);
			new ItemDrop (new WoodPlanks ()).declare (429 + i * 16, 440);
		}**/
		MainLoop.getWindow ().setResolution (512, 288);
		MainLoop.getWindow ().setSize (1024, 576);
		//new Cutscene ("bestscene.txt");
		//FlashOverlay ov = new FlashOverlay (new Color (0, 0, 255, 127), null, 1000, 500, 1000);
		//ov.declare (0, 0);
		//ParticleOverlay pov = new ParticleOverlay (null);
		//pov.declare (0, 0);
		//MainLoop.getWindow ().playSoundForever ("resources/sounds/copyrighted_placeholder_music.wav");
	}
	public void gameLoop () {
		//Saveable.printSaves ();
		//Runs once per frame
	}
}