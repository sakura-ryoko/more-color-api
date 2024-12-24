/*
 * This file is part of the AfkPlus project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024  Sakura Ryoko and contributors
 *
 * AfkPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AfkPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AfkPlus.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sakuraryoko.morecolors.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;

import com.sakuraryoko.morecolors.MoreColors;
import com.sakuraryoko.morecolors.config.ConfigWrap;
import com.sakuraryoko.morecolors.nodes.MoreColorNode;
import com.sakuraryoko.morecolors.nodes.NodeManager;
import com.sakuraryoko.morecolors.text.FormattingExample;
import com.sakuraryoko.morecolors.text.TextUtils;

public interface MoreColorsAPI
{
    static Component getBuiltInFormattingTest()
    {
        return FormattingExample.runBuiltInTest();
    }

    static Component getPlaceholderAPIFormattingTest()
    {
        return FormattingExample.runPlaceholderAPITest();
    }

    static Component getMoreColorsFormattingTest()
    {
        return FormattingExample.runMoreColorsTest();
    }

    static Component getEndFormattingTest()
    {
        return FormattingExample.getClipboardMessage();
    }

    static List<Component> getAllFormattingTests()
    {
        List<Component> list = new ArrayList<>();

        list.add(FormattingExample.runBuiltInTest());
        list.add(FormattingExample.runPlaceholderAPITest());
        list.add(FormattingExample.runMoreColorsTest());
        list.add(FormattingExample.getClipboardMessage());

        return list;
    }

    static boolean isMoreColorNode(String node)
    {
        return NodeManager.isMoreColorNode(node);
    }

    static Component addColorNode(@Nonnull String name, @Nonnull String hexCode, @Nullable List<String> aliases)
    {
        if (name.isEmpty()) return Component.empty();
        if (hexCode.isEmpty()) return Component.empty();
        if (NodeManager.isMoreColorNode(name)) return Component.empty();

        if (hexCode.charAt(0) != '#')
        {
            hexCode = "#"+hexCode.toUpperCase(Locale.ROOT);
        }

        MoreColorNode newNode;

        if (aliases == null || aliases.isEmpty())
        {
            newNode = new MoreColorNode(name, hexCode);
        }
        else
        {
            newNode = new MoreColorNode(name, hexCode, aliases);
        }

        if (newNode.getColor() != null)
        {
            MoreColors.debugLog("New Node Debug: [{}]", newNode.toString());

            ConfigWrap.colors().add(newNode);
            NodeManager.registerColor(newNode);

            return TextUtils.formatText("More Color Node added successfully\n"
                                            + "Test (Click to copy): "
                                            + "<r><copy:'<" + newNode.getName() + ">'><" + newNode.getName() + ">" + newNode.getName() + "<r>");
        }

        return Component.empty();
    }
}
