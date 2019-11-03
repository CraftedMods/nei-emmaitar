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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.tuple.Pair;

import craftedMods.recipes.api.RegisteredHandler;
import craftedMods.recipes.api.VanillaCraftingTableRecipeHandlerSupport;
import craftedMods.recipes.base.AbstractRecipe;
import emmaitar.common.RecipePaintings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

@RegisteredHandler
public class EmmaitarPaintingVanillaCTSupport implements VanillaCraftingTableRecipeHandlerSupport {

	@Override
	public Collection<AbstractRecipe> getDynamicCraftingRecipes(ItemStack result) {
		return new ArrayList<>();
	}

	@Override
	public Collection<AbstractRecipe> getDynamicUsageRecipes(ItemStack ingredient) {
		return new ArrayList<>();
	}

	@Override
	public Pair<Collection<AbstractRecipe>, Boolean> undefinedRecipeTypeFound(IRecipe recipe) {
		if (recipe instanceof RecipePaintings) {
			return Pair.of(new ArrayList<>(), true);
		}
		return null;
	}

}
