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

package com.sakuraryoko.morecolors.coreimpl.mixin;

import org.jetbrains.annotations.ApiStatus;

import com.mojang.datafixers.DataFixer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.sakuraryoko.morecolors.coreimpl.config.ConfigManager;
import com.sakuraryoko.morecolors.coreimpl.events.server.ServerEventsManager;
import com.sakuraryoko.morecolors.coreimpl.modinit.ModInitManager;

@ApiStatus.Internal
@Mixin(DedicatedServer.class)
public class MixinDedicatedServer
{
    @Inject(method = "<init>", at = @At("RETURN"))
    private void corelib$onDedicatedServer(Thread thread, LevelStorageSource.LevelStorageAccess levelStorageAccess,
                                              PackRepository packRepository, WorldStem worldStem,
                                              DedicatedServerSettings dedicatedServerSettings, DataFixer dataFixer,
                                              Services services, ChunkProgressListenerFactory chunkProgressListenerFactory,
                                              CallbackInfo ci)
    {
        ((ModInitManager) ModInitManager.getInstance()).onModInit();
    }


    @Inject(method = "initServer", at = @At("RETURN"))
    private void corelib$onInitServer(CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue())
        {
            ((ModInitManager) ModInitManager.getInstance()).setDedicatedServer(true);
            ((ServerEventsManager) ServerEventsManager.getInstance()).onDedicatedStartedInternal(((DedicatedServer) (Object) this));
        }
    }

    @Inject(method = "stopServer", at = @At("HEAD"))
    private void corelib$onStopServer(CallbackInfo ci)
    {
        ((ServerEventsManager) ServerEventsManager.getInstance()).onDedicatedStoppingInternal(((DedicatedServer) (Object) this));
        ConfigManager.getInstance().saveAllConfigs();
    }
}
