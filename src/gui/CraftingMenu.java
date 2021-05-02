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
		
		/*
		TEST RECIPES
		recipe = new CraftingRecipe (new GoldSword ());
		recipe.addIngredient (new Apple ());
		recipes.add (recipe);
		
		recipe = new CraftingRecipe (new Apple ());
		recipe.addIngredient (new GoldSword ());
		recipes.add (recipe);*/
		
		recipe = new CraftingRecipe (new GoldSword ());
		recipe.addIngredient (new GoldBar ());
		recipe.addIngredient (new WoodPlanks ());
		recipes.add (recipe);
		
		recipe = new CraftingRecipe (new SilverSword ());
		recipe.addIngredient (new SilverBar ());
		recipe.addIngredient (new WoodPlanks ());
		recipes.add (recipe);
		
		recipe = new CraftingRecipe (new MythrilSword ());
		recipe.addIngredient (new MythrilBar ());
		recipe.addIngredient (new SilverBar ());
		recipe.addIngredient (new WoodPlanks ());
		recipes.add (recipe);
		
		recipe = new CraftingRecipe (new MagicPiece ());
		recipe.addIngredient (new MythrilBar ());
		recipes.add (recipe);
		
		recipe = new CraftingRecipe (new Apple ());
		recipe.addIngredient (new Wheat ());
		recipe.addIngredient (new Wheat ());
		recipes.add (recipe);
		
		GameItem working = new Potion ();
		working.setProperty ("type", "health");
		recipe = new CraftingRecipe (working);
		recipe.addIngredient (new Bottle ());
		recipe.addIngredient (new Mushroom ());
		recipe.addIngredient (new Berry ());
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
				if (!inventory.get (j).hasSimilar (recipe.getIngredients ().get (i))) {
					return false;
				}
			}
		}
		return true;
	}
	public void buildGui () {
		workingRecipes = getWorkingRecipes ();
		if (selectIndex > workingRecipes.size () - 1) {
			selectIndex = workingRecipes.size () - 1;
			if (selectIndex < 0) {
				selectIndex = 0;
			}
		}
		if (workingRecipes.size () == 0) {
			setMap (buildTileMap (5, 2));
		}
		int size = workingRecipes.size () + 1;
		if (size < 2) {
			size = 2;
		}
		int tileWidth = (int)Math.ceil ((((double)(20 + 8 * getLongestResultName (workingRecipes).length ())) / 16));
		if (tileWidth < 5) {
			tileWidth = 5;
		}
		setMap (buildTileMap (tileWidth, size));
	}
	public static int[][] buildTileMap (int width, int height) {
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
		drawText ("CRAFTING", 2, 4);
		for (int i = 0; i < workingRecipes.size (); i ++) {
			workingRecipes.get (i).getResult ().getIcon ().draw ((int)this.getX (), (int)this.getY () + i * 16 + 16);
			drawText (getDisplayName (workingRecipes.get (i).getResult ()).toUpperCase (), 18, i * 16 + 20);
		}
		if (workingRecipes.size () > 0) {
			getSprites ().itemBorder.draw ((int)this.getX (), (int)this.getY () + selectIndex * 16 + 16);
			//TODO adaptave sizing
			int tileHeight = workingRecipes.get (selectIndex).getIngredients ().size () + 1;
			if (tileHeight < 2) {
				tileHeight = 2;
			}
			int resultWidth = (int)Math.ceil ((((double)(20 + 8 * getLongestResultName (workingRecipes).length ())) / 16));
			int ingredientWidth = (int)Math.ceil ((((double)(20 + 8 * getLongestIngredientName (workingRecipes.get (selectIndex)).length ())) / 16));
			if (ingredientWidth < 6) {
				ingredientWidth = 6;
			}
			ingredientsWindow.setMap (buildTileMap (ingredientWidth, tileHeight));
			ingredientsWindow.setX (resultWidth * 16 + 8);
			ingredientsWindow.setY (96);
			ingredientsWindow.draw ();
			ingredientsWindow.drawText ("INGREDIENTS", 2, 4);
			for (int i = 0; i < workingRecipes.get (selectIndex).getIngredients ().size (); i ++) {
				workingRecipes.get (selectIndex).getIngredients ().get (i).getIcon ().draw ((int)ingredientsWindow.getX (), (int)ingredientsWindow.getY () + i * 16 + 16);
				ingredientsWindow.drawText (getDisplayName (workingRecipes.get (selectIndex).getIngredients ().get (i)).toUpperCase (), 18, i * 16 + 20);
			}
		}
	}
	@Override
	public void pauseEvent () {
		super.frameEvent ();
		if (getMouseX () > getX () && getMouseX () < getX () + getWidth () * 16 && getMouseY () > getY () && getMouseY () < getY () + getHeight () * 16) {
			int sel = (int)(getMouseY () - getY ()) / 16 - 1;
			if (sel >= 0 && sel < workingRecipes.size ()) {
				selectIndex = sel;
			}
			if (mouseClicked () && workingRecipes.size () != 0) {
				for (int i = 0; i < workingRecipes.get (selectIndex).getIngredients ().size (); i ++) {
					GameItem ingredient = workingRecipes.get (selectIndex).getIngredients ().get (i);
					for (int j = 0; j < inventory.size (); j ++) {
						if (ingredient instanceof CraftingItem) {
							if (((CraftingItem) (inventory.get (j).getSimilar (ingredient))).useInCrafting ()) {
								break;
							}
						}
						if (inventory.get (j).removeSimilar (ingredient)) {
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
	private String getLongestResultName (ArrayList<CraftingRecipe> recipeList) {
		int longestName = 0;
		int longestIndex = 0;
		for (int i = 0; i < recipeList.size (); i ++) {
			if (getDisplayName (recipeList.get (i).getResult ()).length () > longestName) {
				longestName = getDisplayName (recipeList.get (i).getResult ()).length ();
				longestIndex = i;
			}
		}
		if (recipeList.size () == 0) {
			return "NULL";
		}
		return recipeList.get (longestIndex).getResult ().getName ();
	}
	private String getLongestIngredientName (CraftingRecipe workingRecipe) {
		int longestName = 0;
		int longestIndex = 0;
		for (int i = 0; i < workingRecipe.getIngredients ().size (); i ++) {
			if (getDisplayName (workingRecipe.getIngredients ().get (i)).length () > longestName) {
				longestName = getDisplayName (workingRecipe.getIngredients ().get (i)).length ();
				longestIndex = i;
			}
		}
		return workingRecipe.getIngredients ().get (longestIndex).getName ();
	}
	private String getDisplayName (GameItem item) {
		if (!item.getProperty ("displayName").equals ("")) {
			return (item.getProperty ("displayName"));
		}
		return item.getName ();
	}
}