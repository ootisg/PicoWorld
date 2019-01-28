package gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import items.*;
import resources.Spritesheet;

public class CraftingMenu extends MappedUi {
	private ArrayList<CraftingRecipe> recipes;
	private ArrayList<CraftingRecipe> workingRecipes;
	private ArrayList<ItemContainer> inventory;
	
	private int selectIndex;
	
	private MappedUi ingredientsWindow;
	
	public CraftingMenu () {
		super (new Spritesheet ("resources/sprites/gui_background.png"), new int[0][0]);
		recipes = new ArrayList<CraftingRecipe> ();
		workingRecipes = new ArrayList<CraftingRecipe> ();
		inventory = new ArrayList<ItemContainer> ();
		instantiateRecipes ();
		this.setPriority (-1);
		ingredientsWindow = new MappedUi (new Spritesheet ("resources/sprites/gui_background.png"), new int[0][0]);
	}
	public void addItemContainer (ItemContainer container) {
		inventory.add (container);
	}
	public void instantiateRecipes () {
		CraftingRecipe recipe;
		
		recipe = new CraftingRecipe (new GoldSword ());
		recipe.addIngredient (new Apple ());
		recipes.add (recipe);
		
		recipe = new CraftingRecipe (new Apple ());
		recipe.addIngredient (new GoldSword ());
		recipes.add (recipe);
	}
	public ArrayList<CraftingRecipe> getWorkingRecipes () {
		ArrayList<CraftingRecipe> workingRecipes = new ArrayList<CraftingRecipe> ();
		for (int i = 0; i < recipes.size (); i ++) {
			if (recipeCraftable (recipes.get (i))) {
				workingRecipes.add (recipes.get (i));
			}
		}
		return workingRecipes;
	}
	public boolean recipeCraftable (CraftingRecipe recipe) {
		return recipeCraftable (recipe, inventory);
	}
	public boolean recipeCraftable (CraftingRecipe recipe, ArrayList<ItemContainer> inventory) {
		for (int i = 0; i < recipe.getIngredients ().size (); i ++) {
			for (int j = 0; j < inventory.size (); j ++) {
				if (!inventory.get (j).hasItem (recipe.getIngredients ().get (i))) {
					return false;
				}
			}
		}
		return true;
	}
	public void buildGui () {
		workingRecipes = getWorkingRecipes ();
		setMap (buildTileMap (2, workingRecipes.size ()));
	}
	public int[][] buildTileMap (int width, int height) {
		if (width < 2 || height < 2) {
			return null;
		}
		int[][] map = new int[height][width];
		map [0][0] = 5;
		map [0][width - 1] = 8;
		map [height - 1][0] = 6;
		map [height - 1][width - 1] = 7;
		for (int i = 1; i < width - 1; i ++) {
			map [0][i] = 1;
			map [height - 1][i] = 3;
		}
		for (int i = 1; i < height - 1; i ++) {
			map [i][0] = 2;
			map [i][width - 1] = 4;
			for (int j = 1; j < width - 1; j ++) {
				map [i][j] = 0;
			}
		}
		return map;
	}
	@Override
	public void onPause () {
		buildGui ();
	}
	@Override
	public void renderGui () {
		if (!hasFocus ()) {
			setHidden (true);
			return;
		}
		for (int i = 0; i < workingRecipes.size (); i ++) {
			workingRecipes.get (i).getResult ().getIcon ().draw ((int)this.getX (), (int)this.getY () + i * 16);
		}
		sprites.itemBorder.draw ((int)this.getX (), (int)this.getY () + selectIndex * 16);
		//TODO adaptave sizing
		int tileHeight = workingRecipes.get (selectIndex).getIngredients ().size ();
		if (tileHeight < 2) {
			tileHeight = 2;
		}
		ingredientsWindow.setMap (buildTileMap (2, tileHeight));
		ingredientsWindow.setX (48);
		ingredientsWindow.setY (96);
		ingredientsWindow.draw ();
		for (int i = 0; i < workingRecipes.get (selectIndex).getIngredients ().size (); i ++) {
			workingRecipes.get (selectIndex).getIngredients ().get (i).getIcon ().draw ((int)ingredientsWindow.getX (), (int)ingredientsWindow.getY ());
		}
	}
	@Override
	public void keyEvent (char c) {
		if (c == 'W') {
			selectIndex --;
			if (selectIndex < 0) {
				selectIndex = workingRecipes.size () - 1;
			}
		}
		if (c == 'S') {
			selectIndex ++;
			if (selectIndex > workingRecipes.size () - 1) {
				selectIndex = 0;
			}
		}
		if (c == (char)KeyEvent.VK_SPACE) {
			for (int i = 0; i < workingRecipes.get (selectIndex).getIngredients ().size (); i ++) {
				for (int j = 0; j < inventory.size (); j ++) {
					if (inventory.get (j).removeItem (workingRecipes.get (selectIndex).getIngredients ().get (i))) {
						break;
					}
				}
			}
			//TODO Janky solution; pls fix
			//Also the inventory full case needs to be accounted for
			inventory.get (0).addItem (workingRecipes.get (selectIndex).getResult ());
			buildGui ();
		}
	}
}