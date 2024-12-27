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

package com.sakuraryoko.morecolors.impl.commands;

import java.util.*;
import javax.annotation.Nullable;
import me.lucko.fabric.api.permissions.v0.Permissions;
import org.jetbrains.annotations.ApiStatus;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import com.sakuraryoko.corelib.api.commands.IServerCommand;
import com.sakuraryoko.corelib.api.modinit.ModInitData;
import com.sakuraryoko.corelib.impl.config.ConfigManager;
import com.sakuraryoko.morecolors.impl.MoreColor;
import com.sakuraryoko.morecolors.impl.Reference;
import com.sakuraryoko.morecolors.impl.config.ConfigWrap;
import com.sakuraryoko.morecolors.impl.config.MoreColorConfigHandler;
import com.sakuraryoko.morecolors.impl.modinit.MoreColorInit;
import com.sakuraryoko.morecolors.impl.nodes.MoreColorNode;
import com.sakuraryoko.morecolors.impl.nodes.NodeManager;
import com.sakuraryoko.morecolors.impl.text.FormattingExample;
import com.sakuraryoko.morecolors.impl.text.TextUtils;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

@ApiStatus.Internal
public class MoreColorsCommand implements IServerCommand
{
    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment)
    {
        dispatcher.register(
                literal(this.getName())
                        .requires(Permissions.require(this.getNode(), ConfigWrap.opt().moreColorsCommandPermissions))
                        .executes(ctx -> this.about(ctx.getSource(), ctx))
                        .then(literal("test")
                                      .requires(Permissions.require(this.getNode() + ".test", ConfigWrap.opt().moreColorsTestCommandPermissions))
                                      .executes(ctx -> this.test(ctx.getSource(), "all", ctx))
                                      .then(literal("all")
                                                    .executes(ctx -> this.test(ctx.getSource(), "all", ctx))
                                      )
                                      .then(literal("vanilla")
                                                    .executes(ctx -> this.test(ctx.getSource(), "vanilla", ctx))
                                      )
                                      .then(literal("placeholder")
                                                    .executes(ctx -> this.test(ctx.getSource(), "placeholder", ctx))
                                      )
                                      .then(literal("morecolors")
                                                    .executes(ctx -> this.test(ctx.getSource(), "morecolors", ctx))
                                      )
                        )
                        .then(literal("add")
                                      .requires(Permissions.require(this.getNode() + ".add", ConfigWrap.opt().moreColorsAddCommandPermissions))
                                      .then(argument("node", StringArgumentType.word())
                                                    .then(argument("hexCode", StringArgumentType.word())
                                                                  .executes(ctx -> this.add(ctx.getSource(), StringArgumentType.getString(ctx, "node"), StringArgumentType.getString(ctx, "hexCode"), null, ctx))
                                                                  .then(argument("alias_list", StringArgumentType.greedyString())
                                                                                .executes(ctx -> this.add(ctx.getSource(), StringArgumentType.getString(ctx, "node"), StringArgumentType.getString(ctx, "hexCode"), StringArgumentType.getString(ctx, "alias_list"), ctx))
                                                                  )
                                                    )
                                      )
                        )
                        .then(literal("reload")
                                      .requires(Permissions.require(this.getNode() + ".reload", ConfigWrap.opt().moreColorsReloadCommandPermissions))
                                      .executes(ctx -> this.reload(ctx.getSource(), ctx))
                        )
                        .then(literal("save")
                                      .requires(Permissions.require(this.getNode() + ".save", ConfigWrap.opt().moreColorsSaveCommandPermissions))
                                      .executes(ctx -> this.save(ctx.getSource(), ctx))
                        )
                        .then(literal("defaults")
                                      .requires(Permissions.require(this.getNode() + ".defaults", ConfigWrap.opt().moreColorsDefaultsCommandPermissions))
                                      .executes(ctx -> this.defaults(ctx.getSource(), ctx))
                        )
        );
    }

    @Override
    public String getName()
    {
        return "morecolors";
    }

    @Override
    public String getModId()
    {
        return Reference.MOD_ID;
    }

    private int about(CommandSourceStack src, CommandContext<CommandSourceStack> context)
    {
        List<Component> info = MoreColorInit.getInstance().getPlaceholderFormatted(ModInitData.ALL_INFO);
        String user = src.getTextName();

        for (Component entry : info)
        {
            //#if MC >= 12001
            //$$ context.getSource().sendSuccess(() -> entry, false);
            //#else
            context.getSource().sendSuccess(entry, false);
            //#endif
        }

        MoreColor.debugLog("{} has executed /morecolors .", user);
        return 1;
    }

    private int test(CommandSourceStack src, String type, CommandContext<CommandSourceStack> context)
    {
        String user = src.getTextName();

        //#if MC >= 12001
        //$$ switch (type)
        //$$ {
            //$$ case "vanilla" -> context.getSource().sendSuccess(FormattingExample::runBuiltInTest, false);
            //$$ case "placeholder" -> context.getSource().sendSuccess(FormattingExample::runPlaceholderAPITest, false);
            //$$ case "morecolors" -> context.getSource().sendSuccess(FormattingExample::runMoreColorsTest, false);
            //$$ default ->
                //$$ {
                //$$ context.getSource().sendSuccess(FormattingExample::runBuiltInTest, false);
                //$$ context.getSource().sendSuccess(FormattingExample::runPlaceholderAPITest, false);
                //$$ context.getSource().sendSuccess(FormattingExample::runMoreColorsTest, false);
                //$$ }
        //$$ }
        //$$ context.getSource().sendSuccess(FormattingExample::getClipboardMessage, false);
        //#else
        switch (type)
        {
            case "vanilla" -> context.getSource().sendSuccess(FormattingExample.runBuiltInTest(), false);
            case "placeholder" -> context.getSource().sendSuccess(FormattingExample.runPlaceholderAPITest(), false);
            case "morecolors" -> context.getSource().sendSuccess(FormattingExample.runMoreColorsTest(), false);
            default ->
            {
                context.getSource().sendSuccess(FormattingExample.runBuiltInTest(), false);
                context.getSource().sendSuccess(FormattingExample.runPlaceholderAPITest(), false);
                context.getSource().sendSuccess(FormattingExample.runMoreColorsTest(), false);
            }
        }
        context.getSource().sendSuccess(FormattingExample.getClipboardMessage(), false);
        //#endif
        MoreColor.debugLog("{} has executed /morecolors example .", user);
        return 1;
    }

    private int add(CommandSourceStack src, String node, String hexCode, @Nullable String aliases, CommandContext<CommandSourceStack> context)
    {
        String user = src.getTextName();

        if (node == null || node.isEmpty())
        {
            return 0;
        }
        if (hexCode == null || hexCode.isEmpty())
        {
            return 0;
        }

        MoreColorNode newNode;

        if (aliases == null || aliases.isEmpty())
        {
            newNode = new MoreColorNode(node, "#"+hexCode.toUpperCase(Locale.ROOT));
        }
        else
        {
            String[] arrString = aliases.split("\\W+");
            newNode = new MoreColorNode(node, "#"+hexCode.toUpperCase(Locale.ROOT), Arrays.stream(arrString).toList());
        }

        if (newNode.getColor() != null && !NodeManager.checkIfRegistered(newNode))
        {
            MoreColor.debugLog("New Node Debug: [{}]", newNode.toString());

            ConfigWrap.colors().add(newNode);
            NodeManager.registerColor(newNode);

            Component result = TextUtils.getInstance().formatText("More Color Node added successfully\n" + "Test (Click to copy): " + "<r><copy:'<" + newNode.getName() + ">'><" + newNode.getName() + ">" + newNode.getName() + "<r>");

            //#if MC >= 12001
            //$$ context.getSource().sendSuccess(() -> result, false);
            //#else
            context.getSource().sendSuccess(result, false);
            //#endif
            MoreColor.LOGGER.info("{} has added a new color node [{}] to the configuration.", user, newNode.getName());

            return 1;
        }
        else
        {
            return 0;
        }
    }

    private int reload(CommandSourceStack src, CommandContext<CommandSourceStack> context)
    {
        String user = src.getTextName();
        ConfigManager.getInstance().reloadEach(MoreColorConfigHandler.getInstance());
        //#if MC >= 12001
        //$$ context.getSource().sendSuccess(() -> Component.literal("Reloaded config!"), false);
        //#else
        context.getSource().sendSuccess(Component.literal("Reloaded config!"), false);
        //#endif
        MoreColor.LOGGER.info("{} has reloaded the configuration.", user);
        return 1;
    }

    private int save(CommandSourceStack src, CommandContext<CommandSourceStack> context)
    {
        String user = src.getTextName();
        ConfigManager.getInstance().saveEach(MoreColorConfigHandler.getInstance());
        //#if MC >= 12001
        //$$ context.getSource().sendSuccess(() -> Component.literal("Saved config!"), false);
        //#else
        context.getSource().sendSuccess(Component.literal("Saved config!"), false);
        //#endif
        MoreColor.LOGGER.info("{} has saved the configuration.", user);
        return 1;
    }

    private int defaults(CommandSourceStack src, CommandContext<CommandSourceStack> context)
    {
        String user = src.getTextName();
        ConfigManager.getInstance().defaultEach(MoreColorConfigHandler.getInstance());
        //#if MC >= 12001
        //$$ context.getSource().sendSuccess(() -> Component.literal("Loading Config Defaults!"), false);
        //#else
        context.getSource().sendSuccess(Component.literal("Loading Config Defaults!"), false);
        //#endif
        MoreColor.LOGGER.info("{} has loaded the default configuration.", user);
        return 1;
    }
}
