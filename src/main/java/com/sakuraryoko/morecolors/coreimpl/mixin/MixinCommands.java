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

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sakuraryoko.morecolors.coreimpl.commands.CommandManager;

@ApiStatus.Internal
@Mixin(Commands.class)
public class MixinCommands
{
    @Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(method = "<init>",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/server/commands/WhitelistCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V",
                     shift = At.Shift.AFTER))
    private void corelib$injectDedicatedCommands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci)
    {
        ((CommandManager) CommandManager.getInstance()).registerCommands(this.dispatcher, commandBuildContext, commandSelection);
    }

    @Inject(method = "<init>",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/server/commands/PublishCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V",
                     shift = At.Shift.AFTER))
    private void corelib$injectIntegratedCommands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci)
    {
        ((CommandManager) CommandManager.getInstance()).registerCommands(this.dispatcher, commandBuildContext, commandSelection);
    }
}
