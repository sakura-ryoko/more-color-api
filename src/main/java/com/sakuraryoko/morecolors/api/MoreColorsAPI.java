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

import eu.pb4.placeholders.api.node.TextNode;

import net.minecraft.network.chat.Component;

import com.sakuraryoko.morecolors.impl.MoreColor;
import com.sakuraryoko.morecolors.impl.config.ConfigWrap;
import com.sakuraryoko.morecolors.impl.nodes.MoreColorNode;
import com.sakuraryoko.morecolors.impl.nodes.NodeManager;
import com.sakuraryoko.morecolors.impl.text.FormattingExample;
import com.sakuraryoko.morecolors.impl.text.ITextNodeHandler;
import com.sakuraryoko.morecolors.impl.text.TextUtils;

public interface MoreColorsAPI
{
    static boolean isMoreColorNode(@Nonnull String node)
    {
        return NodeManager.isMoreColorNode(node);
    }

    static Component addColorNode(@Nonnull String name, @Nonnull String hexCode, @Nullable List<String> aliases)
    {
        if (name.isEmpty())
        {
            return Component.empty();
        }
        if (hexCode.isEmpty())
        {
            return Component.empty();
        }
        if (NodeManager.isMoreColorNode(name))
        {
            return Component.empty();
        }

        if (hexCode.charAt(0) != '#')
        {
            hexCode = "#" + hexCode.toUpperCase(Locale.ROOT);
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
            MoreColor.debugLog("New Node Debug: [{}]", newNode.toString());

            ConfigWrap.colors().add(newNode);
            NodeManager.registerColor(newNode);

            return TextUtils.getInstance().formatText("More Color Node added successfully\n"
                                                      + "Test (Click to copy): "
                                                      + "<r><copy:'<" + newNode.getName() + ">'><" + newNode.getName() + ">" + newNode.getName() + "<r>");
        }

        return Component.empty();
    }

    static Component formatTextSafe(@Nonnull String str)
    {
        return TextUtils.getInstance().formatTextSafe(str);
    }

    static Component formatText(@Nonnull String str)
    {
        return TextUtils.getInstance().formatText(str);
    }

    static Component of(@Nonnull String str)
    {
        return TextUtils.getInstance().of(str);
    }

    static TextNode toTextNode(@Nonnull String str)
    {
        return TextUtils.getInstance().toTextNode(str);
    }

    static TextNode toTextNode(@Nonnull Component text)
    {
        return TextUtils.getInstance().toTextNode(text);
    }

    static Component formatTextNode(@Nonnull TextNode node)
    {
        return TextUtils.getInstance().formatTextNode(node);
    }

    static ITextNodeHandler getTextNodeHandler()
    {
        return TextUtils.getInstance();
    }

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
}
