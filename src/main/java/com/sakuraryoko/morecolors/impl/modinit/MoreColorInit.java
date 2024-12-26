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

import com.sakuraryoko.corelib.api.modinit.IModInitDispatcher;
import com.sakuraryoko.corelib.api.modinit.ModInitData;
import com.sakuraryoko.corelib.api.text.ITextHandler;
import com.sakuraryoko.corelib.impl.commands.CommandManager;
import com.sakuraryoko.corelib.impl.config.ConfigManager;
import com.sakuraryoko.morecolors.impl.MoreColor;
import com.sakuraryoko.morecolors.impl.Reference;
import com.sakuraryoko.morecolors.impl.commands.MoreColorsCommand;
import com.sakuraryoko.morecolors.impl.config.ConfigWrap;
import com.sakuraryoko.morecolors.impl.config.MoreColorConfigHandler;
import com.sakuraryoko.morecolors.impl.text.TextUtils;
//#if MC >= 12006
//$$ import com.sakuraryoko.morecolors.impl.text.TextParser;
//#else
//#endif

public class MoreColorInit implements IModInitDispatcher
{
    private static final MoreColorInit INSTANCE = new MoreColorInit();

    public static MoreColorInit getInstance() {return INSTANCE;}

    private final ModInitData MOD_DATA;
    private boolean INIT = false;

    public MoreColorInit()
    {
        this.MOD_DATA = new ModInitData(Reference.MOD_ID);
        this.MOD_DATA.setTextHandler(this.getTextHandler());
    }

    @Override
    public ModInitData getModInit()
    {
        return this.MOD_DATA;
    }

    @Override
    public String getModId()
    {
        return Reference.MOD_ID;
    }

    @Override
    public ITextHandler getTextHandler()
    {
        return TextUtils.getInstance();
    }

    @Override
    public boolean isDebug()
    {
        return Reference.DEBUG || ConfigWrap.opt().debugMode;
    }

    @Override
    public boolean isInitComplete()
    {
        return this.INIT;
    }

    @Override
    public void reset()
    {
        // NO-OP
    }

    @Override
    public void onModInit()
    {
        MoreColor.debugLog("Initializing Mod.");
        for (String s : this.getBasic(ModInitData.BASIC_INFO))
        {
            MoreColor.LOGGER.info(s);
        }

        MoreColor.debugLog("Config Initializing.");
        ConfigManager.getInstance().registerConfigDispatcher(MoreColorConfigHandler.getInstance());

        //#if MC >= 12006
        //$$ MoreColor.debugLog("Building Text Parser.");
        //$$ TextParser.build();
        //#else
        //#endif

        MoreColor.debugLog("Registering Commands.");
        CommandManager.getInstance().registerCommandHandler(new MoreColorsCommand());
        this.INIT = true;
    }
}
