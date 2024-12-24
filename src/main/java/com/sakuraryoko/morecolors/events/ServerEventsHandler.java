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

package com.sakuraryoko.morecolors.events;

import java.util.Collection;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameType;

import com.sakuraryoko.morecolors.MoreColors;
import com.sakuraryoko.morecolors.config.ConfigManager;

@ApiStatus.Internal
public class ServerEventsHandler
{
    private static final ServerEventsHandler INSTANCE = new ServerEventsHandler();
    public static ServerEventsHandler getInstance() { return INSTANCE; }

    public ServerEventsHandler() {}

    @ApiStatus.Internal
    public void onStarting(MinecraftServer server)
    {
        MoreColors.debugLog("onStarting(): Server is starting, {}", server.getServerModName());
    }

    @ApiStatus.Internal
    public void onStarted(MinecraftServer server)
    {
        MoreColors.debugLog("onStarted(): Server has started, {}", server.getServerModName());
    }

    @ApiStatus.Internal
    public void onReloadComplete(MinecraftServer server, Collection<String> resources)
    {
        MoreColors.debugLog("onReloadComplete(): Server has reloaded it's data packs, {}", server.getServerModName());
    }

    @ApiStatus.Internal
    public void onIntegratedStarted(IntegratedServer server)
    {
        MoreColors.debugLog("onIntegratedStarted(): Integrated Server is starting, {}", server.getServerModName());
    }

    @ApiStatus.Internal
    public void onOpenToLan(IntegratedServer server, GameType mode)
    {
        MoreColors.debugLog("onOpenToLan(): Server is open for LAN, {} [Game Mode: {}]", server.getServerModName(), mode.getName());
    }

    @ApiStatus.Internal
    public void onStopping(MinecraftServer server)
    {
        MoreColors.debugLog("onStopping(): Server is stopping, {}", server.getServerModName());
        ConfigManager.getInstance().saveAllConfigs();
    }

    @ApiStatus.Internal
    public void onStopped(MinecraftServer server)
    {
        MoreColors.debugLog("onStopped(): Server has stopped, {}", server.getServerModName());
    }
}
