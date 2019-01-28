package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GameObject;
import main.GameWindow;
import main.MainLoop;
import main.TextInterface;
import resources.Sprite;

public class Gui extends GameObject {
	public int pauseCooldown;
	public int[] quickselect;
	public int quickselectIndex;
	public int itemIconX;
	public int itemIconY;
	public boolean drawUnderline;
	public TextInterface textInterface;
	private ItemMenu itemMenu;
	private CraftingMenu craftingMenu;
	//Inventory variables
	public int itemSlotNum;
	public int subsetSlotNum;
	public int quickselectSlotNum;
	public Gui () {
		this.declare (0, 0);
		this.quickselect = new int[]{0, -1, -1, 3, 4};
		this.quickselectIndex = 0;
		this.itemIconX = 128;
		this.itemIconY = 16;
		this.textInterface = new TextInterface (sprites.textSheet);
		this.textInterface.setTextColorARGB (0xFFFFFFFF);
		this.pauseCooldown = 0;
		//Inventory variables
		this.itemSlotNum = 5;
		this.subsetSlotNum = 3;
		this.quickselectSlotNum = 4;
		this.itemMenu = new ItemMenu (0, 0);
		this.craftingMenu = new CraftingMenu ();
		craftingMenu.addItemContainer (itemMenu);
		craftingMenu.declare (0, 96);
		itemMenu.focus ();
		craftingMenu.setHidden (true);
	}
	public void frameEvent () {
		pauseCooldown --;
		if (keyPressed ('E') && pauseCooldown <= 0) {
			MainLoop.pause ();
			pauseCooldown = 10;
		}
		/*if (keyCheck (KeyEvent.VK_SHIFT)) {
			drawUnderline = true;
			if (keyPressed ((int)'A')) {
				quickselectIndex --;
				if (quickselectIndex < 0) {
					quickselectIndex += quickselect.length;
				}
				while (quickselect [quickselectIndex] == -1) {
					quickselectIndex --;
				}
				if (quickselectIndex < 0) {
					quickselectIndex += quickselect.length;
				}
			}
			if (keyPressed ((int)'D')) {
				quickselectIndex ++;
				if (quickselectIndex >= quickselect.length) {
					quickselectIndex = 0;
				}
				while (quickselect [quickselectIndex] == -1) {
					quickselectIndex ++;
				}
				if (quickselectIndex >= quickselect.length) {
					quickselectIndex = 0;
				}
			}
		} else {
			drawUnderline = false;
		}*/
	}
	public void pauseEvent () {
		pauseCooldown --;
		if (keyPressed (KeyEvent.VK_TAB)) {
			if (itemMenu.hasFocus ()) {
				itemMenu.unfocus ();
				craftingMenu.focus ();
				craftingMenu.setHidden (false);
			} else {
				itemMenu.focus ();
				craftingMenu.unfocus ();
				craftingMenu.setHidden (true);
			}
		}
		if (keyPressed ('E') && pauseCooldown <= 0) {
			itemMenu.focus ();
			craftingMenu.unfocus ();
			craftingMenu.setHidden (true);
			pauseCooldown = 10;
			MainLoop.resume ();
		}
	}
	public void render () {
		/*sprites.spellSprites.setFrame (quickselect [quickselectIndex]);
		sprites.spellSprites.draw (itemIconX, itemIconY);
		if (drawUnderline) {
			sprites.underline.draw (itemIconX, itemIconY + sprites.spellSprites.getImageArray () [sprites.spellSprites.getFrame ()].getHeight () + 2);
		}
		renderSelectMenu ();*/
	}
	public void renderSelectMenu () {
		MainLoop.getWindow ().getBuffer ().setColor (new Color (0x000000));
		MainLoop.getWindow ().getBuffer ().fillRect (0, 0, 512, 80);
		drawText ("EQUIPMENT SELECTION", 180, 4);
		drawIconListCentered (sprites.fireballSprites [0].getImageArray (), 0, 32, 4, 512);
	}
	public void drawText (String text, int x, int y) {
		for (int i = 0; i < text.length (); i ++) {
			textInterface.drawChar (text.charAt (i), x + i * 8, y);
		}
	}
	public void drawIconList (Sprite[] icons, int x, int y, int spacing) {
		for (int i = 0; i < icons.length; i ++) {
			sprites.itemBorder.draw (x + i * (16 + spacing), y);
			icons [i].draw (x + i * (16 + spacing), y);
		}
	}
	public void drawIconList (BufferedImage[] icons, int x, int y, int spacing) {
		for (int i = 0; i < icons.length; i ++) {
			sprites.itemBorder.draw (x + i * (16 + spacing), y);
			Sprite.draw (icons [i], x + i * (16 + spacing), y);
		}
	}
	public void drawIconListCentered (Sprite[] icons, int x, int y, int spacing, int width) {
		int startX = x + (width - (icons.length * 16 + (icons.length - 1) * spacing)) / 2;
		drawIconList (icons, startX, y, spacing);
	}
	public void drawIconListCentered (BufferedImage[] icons, int x, int y, int spacing, int width) {
		int startX = x + (width - (icons.length * 16 + (icons.length - 1) * spacing)) / 2;
		drawIconList (icons, startX, y, spacing);
	}
	public ItemMenu getItemMenu () {
		return itemMenu;
	}
}