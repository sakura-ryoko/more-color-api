/*
 * This file is part of the MoreColorAPI project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  Sakura Ryoko and contributors
 *
 * MoreColorAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoreColorAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MoreColorAPI.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sakuraryoko.morecolors;

//#if MC >= 12006
//$$ import com.sakuraryoko.morecolors.text.TextParser;
//#else
//#endif

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

import com.sakuraryoko.morecolors.commands.CommandManager;
import com.sakuraryoko.morecolors.config.ConfigManager;
import com.sakuraryoko.morecolors.util.ModInfo;

public class MoreColors implements ModInitializer
{
    public static Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    public static void debugLog(String key, Object... args)
    {
        if (Reference.DEBUG)
        {
            LOGGER.info(String.format("[DEBUG] %s", key), args);
        }
    }

    @Override
    public void onInitialize()
    {
        debugLog("Initializing Mod.");
        ModInfo.initModInfo();
        ModInfo.displayModInfo();
        debugLog("Config Initializing.");
        ConfigManager.getInstance().initAllConfigs();
        debugLog("Loading Config.");
        ConfigManager.getInstance().loadAllConfigs();
        //#if MC >= 12006
        //$$ debugLog("Building Text Parser.");
        //$$ TextParser.build();
        //#else
        //#endif
        debugLog("Registering commands.");
        CommandManager.register();
        debugLog("All Tasks Done.");
    }
}
