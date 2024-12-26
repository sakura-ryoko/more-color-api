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

package com.sakuraryoko.morecolors.impl.modinit;

import com.sakuraryoko.morecolors.api.modinit.IModInitDispatcher;
import com.sakuraryoko.morecolors.api.modinit.ModData;
import com.sakuraryoko.morecolors.impl.MoreColors;
import com.sakuraryoko.morecolors.impl.Reference;
import com.sakuraryoko.morecolors.coreimpl.commands.CommandManager;
import com.sakuraryoko.morecolors.impl.commands.MoreColorsCommand;
import com.sakuraryoko.morecolors.coreimpl.config.ConfigManager;
import com.sakuraryoko.morecolors.impl.config.ConfigWrap;
import com.sakuraryoko.morecolors.impl.config.MoreColorConfigHandler;
//#if MC >= 12006
//$$ import com.sakuraryoko.morecolors.impl.text.TextParser;
//#else
//#endif

public class MoreColorsInit implements IModInitDispatcher
{
    private static final MoreColorsInit INSTANCE = new MoreColorsInit();
    public static MoreColorsInit getInstance() { return INSTANCE; }
    private static final ModData MOD_DATA = new ModData(Reference.MOD_ID);

    @Override
    public ModData getModInit()
    {
        return MOD_DATA;
    }

    @Override
    public String getModId()
    {
        return Reference.MOD_ID;
    }

    @Override
    public boolean isDebug()
    {
        return Reference.DEBUG || ConfigWrap.opt().debugMode;
    }

    @Override
    public void reset()
    {
        // NO-OP
    }

    @Override
    public void onModInit()
    {
        MoreColors.debugLog("Initializing Mod.");
        for (String s : this.getBasic(ModData.BASIC_INFO))
        {
            MoreColors.LOGGER.info(s);
        }

        MoreColors.debugLog("Config Initializing.");
        ConfigManager.getInstance().registerConfigDispatcher(MoreColorConfigHandler.getInstance());

        //#if MC >= 12006
        //$$ MoreColors.debugLog("Building Text Parser.");
        //$$ TextParser.build();
        //#else
        //#endif

        MoreColors.debugLog("Registering Commands.");
        CommandManager.getInstance().registerCommandHandler(new MoreColorsCommand());
    }
}
