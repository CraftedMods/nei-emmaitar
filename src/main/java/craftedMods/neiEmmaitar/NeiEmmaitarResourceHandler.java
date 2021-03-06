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

import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

import craftedMods.recipes.api.RegisteredHandler;
import craftedMods.recipes.api.ResourceHandler;
import craftedMods.recipes.base.ClasspathResourceLoader;
import craftedMods.recipes.base.RecipeHandlerResourceLocation;
import net.minecraft.util.ResourceLocation;

@RegisteredHandler
public class NeiEmmaitarResourceHandler implements ResourceHandler {

	@Override
	public Map<ResourceLocation, Supplier<InputStream>> getResources() {
		ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
		resourceLoader.setPrefix("craftedMods/neiEmmaitar/");

		resourceLoader.registerResource(new RecipeHandlerResourceLocation("lang/en_US.lang"));
		resourceLoader.registerResource(new RecipeHandlerResourceLocation("lang/de_DE.lang"));
		resourceLoader.registerResource(new RecipeHandlerResourceLocation("lang/nl_NL.lang"));
		return resourceLoader.loadResources();
	}

}
