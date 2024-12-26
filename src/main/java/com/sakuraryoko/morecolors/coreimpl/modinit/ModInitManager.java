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

package com.sakuraryoko.morecolors.coreimpl.modinit;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;

import com.sakuraryoko.morecolors.api.modinit.IModInitDispatcher;
import com.sakuraryoko.morecolors.coreimpl.config.ConfigManager;

public class ModInitManager implements IModInitManager
{
    private static final ModInitManager INSTANCE = new ModInitManager();
    public static IModInitManager getInstance() { return INSTANCE; }
    private final List<IModInitDispatcher> DISPATCH = new ArrayList<>();

    @Override
    public void registerModInitHandler(IModInitDispatcher handler) throws RuntimeException
    {
        if (!this.DISPATCH.contains(handler))
        {
            this.DISPATCH.add(handler);
        }
        else
        {
            throw new RuntimeException("Mod Init Dispatcher already registered!");
        }
    }

    @ApiStatus.Internal
    public void onModInit()
    {
        if (!this.DISPATCH.isEmpty())
        {
            this.DISPATCH.forEach(IModInitDispatcher::onModInit);
        }
    }

    @ApiStatus.Internal
    public void setIntegratedServer(boolean toggle)
    {
        if (!this.DISPATCH.isEmpty())
        {
            this.DISPATCH.forEach((handler) -> handler.setIntegratedServer(toggle));
        }
    }

    @ApiStatus.Internal
    public void setDedicatedServer(boolean toggle)
    {
        if (!this.DISPATCH.isEmpty())
        {
            this.DISPATCH.forEach((handler) -> handler.setDedicatedServer(toggle));
        }
    }

    @ApiStatus.Internal
    public void setOpenToLan(boolean toggle)
    {
        if (!this.DISPATCH.isEmpty())
        {
            this.DISPATCH.forEach((handler) -> handler.setOpenToLan(toggle));
        }
    }

    @ApiStatus.Internal
    public void reset()
    {
        if (!this.DISPATCH.isEmpty())
        {
            this.DISPATCH.forEach(IModInitDispatcher::reset);
        }
    }
}
