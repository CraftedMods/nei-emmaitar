/*******************************************************************************
 * Copyright (C) 2019 CraftedMods (see https://github.com/CraftedMods)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package craftedMods.neiEmmaitar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import craftedMods.recipes.api.RecipeHandlerCraftingHelper;
import craftedMods.recipes.api.RecipeHandlerRecipeViewer;
import craftedMods.recipes.api.RecipeHandlerRenderer;
import craftedMods.recipes.api.RegisteredHandler;
import craftedMods.recipes.api.utils.RecipeHandlerRendererUtils;
import craftedMods.recipes.base.AbstractCraftingHelper;
import craftedMods.recipes.base.AbstractRecipe;
import craftedMods.recipes.base.AbstractRecipeViewer;
import craftedMods.recipes.base.CraftingGridRecipeHandler;
import craftedMods.recipes.base.ShapedRecipe;
import emmaitar.client.ClientPaintingCatalogue;
import emmaitar.common.CustomPaintingData;
import emmaitar.common.CustomPaintingReference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

@RegisteredHandler
public class EmmaitarPaintingRecipeHandler extends CraftingGridRecipeHandler {

	private static Field allPaintingsField;

	static {
		try {
			allPaintingsField = ClientPaintingCatalogue.class.getDeclaredField("allPaintings");
			allPaintingsField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private final EmmaitarPaintingRecipeHandlerRenderer renderer = new EmmaitarPaintingRecipeHandlerRenderer();
	private final EmmaitarPaintingRecipeHandlerCraftingHelper craftingHelper = new EmmaitarPaintingRecipeHandlerCraftingHelper();
	private final EmmaitarPaintingRecipeHandlerRecipeViewer recipeViewer = new EmmaitarPaintingRecipeHandlerRecipeViewer(
			this);

	public EmmaitarPaintingRecipeHandler() {
		super("emmaitar.paintings");
	}

	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("neiEmmaitar.handler.paintings.name");
	}

	@Override
	public Collection<AbstractRecipe> getDynamicCraftingRecipes(ItemStack result) {
		Collection<AbstractRecipe> recipes = new ArrayList<>();

		if (CustomPaintingReference.isCustomPaintingItem(result)) {
			recipes.add(new EmmaitarPaintingRecipe(
					ClientPaintingCatalogue.lookup(CustomPaintingReference.getCustomPainting(result), true), result));
		}

		return recipes;
	}

	@Override
	public Collection<AbstractRecipe> getDynamicUsageRecipes(ItemStack ingredient) {
		Collection<AbstractRecipe> recipes = new ArrayList<>();

		for (AbstractRecipe recipe : getAllRecipes()) {
			if (recipe.consumes(ingredient)) {
				recipes.add(recipe);
			}
		}

		return recipes;
	}

	@SuppressWarnings("unchecked")
	public Collection<AbstractRecipe> getAllRecipes() {
		Collection<AbstractRecipe> recipes = new ArrayList<>();

		try {
			List<CustomPaintingData> allPaintings = (List<CustomPaintingData>) allPaintingsField.get(null);

			for (CustomPaintingData painting : allPaintings) {
				ItemStack stack = new ItemStack(Items.painting);
				CustomPaintingReference.setCustomPainting(stack, painting.makeReference());

				recipes.add(new EmmaitarPaintingRecipe(painting, stack));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return recipes;
	}

	@Override
	public int getRecipesPerPage() {
		return 1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EmmaitarPaintingRecipeHandlerRenderer getRenderer() {
		return renderer;
	}

	@Override
	public RecipeHandlerCraftingHelper<AbstractRecipe> getCraftingHelper() {
		return craftingHelper;
	}

	@Override
	public RecipeHandlerRecipeViewer<AbstractRecipe> getRecipeViewer() {
		return recipeViewer;
	}

	private class EmmaitarPaintingRecipe extends ShapedRecipe {

		private final CustomPaintingData data;

		public EmmaitarPaintingRecipe(CustomPaintingData data, ItemStack paintingStack) {
			super(3, 3,
					new Object[] { data.dyes[0].oreDictName, data.dyes[1].oreDictName, data.dyes[2].oreDictName,
							data.dyes[3].oreDictName, Items.painting, data.dyes[4].oreDictName,
							data.dyes[5].oreDictName, data.dyes[6].oreDictName, data.dyes[7].oreDictName },
					paintingStack);
			this.data = data;
		}

		public CustomPaintingData getData() {
			return data;
		}

	}

	private class EmmaitarPaintingRecipeHandlerRenderer
			implements RecipeHandlerRenderer<EmmaitarPaintingRecipeHandler, AbstractRecipe> {

		@Override
		public void renderBackground(EmmaitarPaintingRecipeHandler arg0, AbstractRecipe arg1, int arg2) {
			RecipeHandlerRendererUtils.getInstance().bindTexture(RecipeHandlerRenderer.DEFAULT_GUI_TEXTURE);
			RecipeHandlerRendererUtils.getInstance().drawTexturedRectangle(0, 0, 5, 11, 166, 65);
		}

		@Override
		public void renderForeground(EmmaitarPaintingRecipeHandler arg0, AbstractRecipe arg1, int cycleticks) {
			EmmaitarPaintingRecipe recipe = (EmmaitarPaintingRecipe) arg1;
			CustomPaintingData data = ClientPaintingCatalogue.lookup(recipe.getData().makeReference(), true);

			if (data.clientTexture != null) {

				// This rectangle shows the area where the image will be painted
				// RecipeHandlerRendererUtils.getInstance().drawRectangle(20, 64, 128, 64,
				// 0xFFFF0000);

				int width = data.blockWidth;
				int height = data.blockHeight;

				RecipeHandlerRendererUtils.getInstance().bindTexture(recipe.getData().clientTexture.toString());

				GL11.glPushMatrix();

				float scaleX = 128.0f / 256 / width;
				float scaleY = 64.0f / 256 / height;

				float scale = Math.min(scaleX, scaleY);

				float scale2 = Math.min(128.0f / data.paintingIMG.getWidth(), 64.0f / data.paintingIMG.getHeight());

				float renderedWidth = data.paintingIMG.getWidth() * scale2;
				float renderedHeight = data.paintingIMG.getHeight() * scale2;

				GL11.glTranslatef(20, 64, 0);
				GL11.glTranslatef(0.5f * (128 - renderedWidth), 0.5f * (64 - renderedHeight), 0);
				GL11.glScalef(width, height, 1);

				GL11.glScalef(scale, scale, 1);

				RecipeHandlerRendererUtils.getInstance().drawTexturedRectangle(0, 0, 0, 0, 256, 256);
				GL11.glPopMatrix();
			} else {
				// Render an animated placeholder text

				String placeholderText = StatCollector
						.translateToLocal("neiEmmaitar.handler.paintings.loadingPlaceholder") + " ";

				String points = StringUtils.repeat('.', cycleticks % 60 / 15);

				int placeholderTextWidth = RecipeHandlerRendererUtils.getInstance().getStringWidth(placeholderText);

				RecipeHandlerRendererUtils.getInstance().drawText(placeholderText + points,
						(int) (84 - (placeholderTextWidth / 2.0f)), 91, 0x00FF00, true);
			}

		}

	}

	private class EmmaitarPaintingRecipeHandlerCraftingHelper extends AbstractCraftingHelper<AbstractRecipe> {

		private final Collection<Class<? extends GuiContainer>> guiClasses = new ArrayList<>();

		public EmmaitarPaintingRecipeHandlerCraftingHelper() {
			guiClasses.add(GuiCrafting.class);
		}

		@Override
		public int getOffsetX(Class<? extends GuiContainer> arg0, AbstractRecipe arg1) {
			return 5;
		}

		@Override
		public int getOffsetY(Class<? extends GuiContainer> arg0, AbstractRecipe arg1) {
			return 11;
		}

		@Override
		public Collection<Class<? extends GuiContainer>> getSupportedGUIClasses(AbstractRecipe arg0) {
			return guiClasses;
		}

	}

	private class EmmaitarPaintingRecipeHandlerRecipeViewer
			extends AbstractRecipeViewer<AbstractRecipe, EmmaitarPaintingRecipeHandler> {

		private final Collection<Class<? extends GuiContainer>> supportedGuiClasses = new ArrayList<>();

		public EmmaitarPaintingRecipeHandlerRecipeViewer(EmmaitarPaintingRecipeHandler handler) {
			super(handler);
			this.supportedGuiClasses.addAll(AbstractRecipeViewer.RECIPE_HANDLER_GUIS);
			this.supportedGuiClasses.add(GuiCrafting.class);
		}

		@Override
		public Collection<Class<? extends GuiContainer>> getSupportedGUIClasses() {
			return this.supportedGuiClasses;
		}

		@Override
		public Collection<AbstractRecipe> getAllRecipes() {
			return handler.getAllRecipes();
		}

		@Override
		public String getButtonTooltip(Class<? extends GuiContainer> guiClass) {
			return StatCollector.translateToLocal("neiEmmaitar.handler.paintings.recipeViewer.tooltip");
		}

		@Override
		public ItemStack getButtonIcon(Class<? extends GuiContainer> guiClass) {
			return guiClass == GuiCrafting.class ? new ItemStack(Items.painting) : super.getButtonIcon(guiClass);
		}

		@Override
		public int getOffsetY(Class<? extends GuiContainer> guiClass) {
			return guiClass == GuiCrafting.class ? super.getOffsetY(guiClass) + 18 : super.getOffsetY(guiClass);
		}

	}

}
