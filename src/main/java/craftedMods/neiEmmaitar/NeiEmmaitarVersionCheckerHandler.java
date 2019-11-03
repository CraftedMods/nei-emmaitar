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

import craftedMods.recipes.api.RegisteredHandler;
import craftedMods.recipes.api.VersionCheckerHandler;
import craftedMods.utils.EnumVersionState;
import craftedMods.utils.SemanticVersion;

@RegisteredHandler
public class NeiEmmaitarVersionCheckerHandler implements VersionCheckerHandler {

	public static final SemanticVersion NEI_EMMAITAR_VERSION = new SemanticVersion(EnumVersionState.BETA, 0, 1, 0);

	@Override
	public SemanticVersion getCurrentVersion() {
		return NeiEmmaitarVersionCheckerHandler.NEI_EMMAITAR_VERSION;
	}

	@Override
	public String getLocalizedHandlerName() {
		return "NEI Emmaitar";
	}

	@Override
	public String getVersionFileURL() {
		return "https://raw.githubusercontent.com/CraftedMods/nei-emmaitar/master/version.txt";
	}

}
