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

package com.sakuraryoko.morecolors.impl.text;

import java.util.List;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import com.sakuraryoko.morecolors.impl.MoreColor;
import com.sakuraryoko.morecolors.impl.config.ConfigWrap;
import com.sakuraryoko.morecolors.impl.nodes.MoreColorNode;
import com.sakuraryoko.morecolors.impl.nodes.NodeManager;

@ApiStatus.Internal
public class FormattingExample
{
    public static Component runBuiltInTest()
    {
        StringBuilder testString = new StringBuilder();
        testString.append("<r><bold><i><gray>*** BUILT-IN (Vanilla Formatters):\n");

        for (ChatFormatting fmt : ChatFormatting.values())
        {
            if (fmt.equals(ChatFormatting.OBFUSCATED))
            {
                //#if MC >= 11904
                //$$ testString.append("<r> <u><copy:'<").append(fmt.getName()).append(">'>").append(fmt.getName()).append(":<r> <").append(fmt.getName()).append(">").append(fmt.getName());
                //#else
                testString.append("<r> <underline><copy:'<").append(fmt.getName()).append(">'>").append(fmt.getName()).append(":<r> <").append(fmt.getName()).append(">").append(fmt.getName());
                //#endif
            }
            else if (!fmt.equals(ChatFormatting.RESET))
            {
                testString.append("<r> <copy:'<").append(fmt.getName()).append(">'><").append(fmt.getName()).append(">").append(fmt.getName());
            }
        }

        MoreColor.debugLog("FormatTest.runBuiltInTest() --> testString: {}", testString.toString());
        return TextUtils.getInstance().formatText(testString.toString());
    }

    public static Component runPlaceholderAPITest()
    {
        StringBuilder testString = new StringBuilder();
        testString.append("<r><b><i><rainbow>*** PlaceholderAPI Common nodes:\n");

        testString.append("<r> <copy:'<rainbow>'><rainbow>A rainbow full of colors\n");
        testString.append("<r> <copy:'<gradient:#76C610:#DE8BB4>'><gradient:#76C610:#DE8BB4>gradient:#76C610:#DE8BB4\n");
        testString.append("<r> <green><hover:show_text:'hover text'>hover:show_text:'hover text'\n");
        //#if MC >= 11904
        //$$ testString.append("<r> <u><aqua><url:'https://github.io'>url:'https://github.io'\n");
        //#else
        testString.append("<r> <underline><aqua><url:'https://github.io'>url:'https://github.io'\n");
        //#endif
        testString.append("<r><b><i><rainbow>*** PlaceholderAPI Registered Colors:\n");

        List<String> allColors = NodeManager.getAllColorNodes();

        for (String node : allColors)
        {
            testString.append("<r> <copy:'<").append(node).append(">'><").append(node).append(">").append(node);
        }

        MoreColor.debugLog("FormatTest.runAliasTest() --> testString: {}", testString.toString());
        return TextUtils.getInstance().formatText(testString.toString());
    }

    public static Component runMoreColorsTest()
    {
        StringBuilder testString = new StringBuilder();

        //#if MC >= 11904
        //$$ testString.append("<r><b><i><salmon>*** <u>More-Colors-API Test:</u>\n");
        //#else
        testString.append("<r><b><i><salmon>*** <underline>More-Colors-API Test:</underline>\n");
        //#endif

        for (MoreColorNode iNode : ConfigWrap.colors())
        {
            testString.append("<r> <copy:'<").append(iNode.getName()).append(">'><").append(iNode.getName()).append(">").append(iNode.getName());
        }

        MoreColor.debugLog("FormatTest.runColorsTest() --> testString: {}", testString.toString());
        return TextUtils.getInstance().formatText(testString.toString());
    }

    public static Component getClipboardMessage()
    {
        String testString;

        //#if MC >= 11904
        //$$  testString = "<r>\n<i><u><gray>* You can click on most of these options to copy the tag to your Clipboard ***";
        //#else
        testString = "<r>\n<i><underline><gray>* You can click on most of these options to copy the tag to your Clipboard ***";
        //#endif

        return TextUtils.getInstance().formatText(testString);
    }
}
