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

package com.sakuraryoko.morecolors.nodes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import eu.pb4.placeholders.api.node.TextNode;
import eu.pb4.placeholders.api.node.parent.ColorNode;
import eu.pb4.placeholders.api.parsers.TextParserV1;
//#if MC >= 12006
//$$ import eu.pb4.placeholders.api.parsers.tag.TagRegistry;
//$$ import eu.pb4.placeholders.api.parsers.tag.TextTag;
//#else
//#endif
import eu.pb4.placeholders.impl.textparser.TextParserImpl;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.network.chat.TextColor;

import com.sakuraryoko.morecolors.MoreColors;
import com.sakuraryoko.morecolors.api.MoreColorsEvents;
import com.sakuraryoko.morecolors.config.ConfigWrap;
import com.sakuraryoko.morecolors.text.TextUtils;

//#if MC >= 12006
//$$ @SuppressWarnings("deprecation")
//#else
//#endif
@ApiStatus.Internal
public class NodeManager
{
    private static final List<MoreColorNode> REMOVE = new ArrayList<>();

    @ApiStatus.Internal
    private static void registerColors()
    {
        final Iterator<MoreColorNode> iterator = ConfigWrap.colors().iterator();
        MoreColorNode iColorNode;

        REMOVE.clear();

        while (iterator.hasNext())
        {
            iColorNode = iterator.next();
            registerColor(iColorNode);
        }

        if (!REMOVE.isEmpty())
        {
            REMOVE.forEach((iNode) ->
            {
                MoreColors.LOGGER.warn("Removing Node \"{}\" from config, because it was detected as being in use.", iNode.getName());
                ConfigWrap.colors().remove(iNode);
            });

            REMOVE.clear();
        }
    }

    @ApiStatus.Internal
    public static void registerColor(MoreColorNode iColorNode)
    {
        // DataResult checked at initialization
        MoreColors.debugLog("registerColors(): register ColorNode: {} // {}", iColorNode.getName(), iColorNode.getHexCode());
        TextColor finalIColorNode = iColorNode.getColor();

        if (checkIfRegistered(iColorNode))
        {
            MoreColors.debugLog("registerColors(): A tag named \"{}\" is already registered.", iColorNode.getName());
            REMOVE.add(iColorNode);
            return;
        }

        if (iColorNode.getAliases() != null)
        {
            if (TextUtils.LEGACY)
            {
                // Legacy Parser
                TextParserV1.registerDefault(
                        TextParserV1.TextTag.of(
                                iColorNode.getName(),
                                iColorNode.getAliases(),
                                "color",
                                true,
                                wrap((nodes, arg) -> new ColorNode(nodes, finalIColorNode))
                        )
                );
            }
            // New Code
//#if MC >= 12006
            //$$ TagRegistry.registerDefault(
                //$$ TextTag.enclosing(
                    //$$ iColorNode.getName(),
                    //$$ iColorNode.getAliases(),
                    //$$ "color",
                    //$$ true,
                    //$$ (nodes, data, parser) -> new ColorNode(nodes, finalIColorNode)
                //$$ )
            //$$ );
//#else
//#endif

            MoreColorsEvents.REGISTER_COLOR_NODE.invoker().onRegisterMoreColorNode(iColorNode.getName(), iColorNode.getHexCode(), iColorNode.getAliases());
        }
        else
        {
            if (TextUtils.LEGACY)
            {
                // Legacy Parser
                TextParserV1.registerDefault(
                        TextParserV1.TextTag.of(
                                iColorNode.getName(),
                                List.of(""),
                                "color",
                                true,
                                wrap((nodes, arg) -> new ColorNode(nodes, finalIColorNode))
                        )
                );
            }
            // New Code
//#if MC >= 12006
            //$$ TagRegistry.registerDefault(
                //$$ TextTag.enclosing(
                    //$$ iColorNode.getName(),
                    //$$ List.of(""),
                    //$$ "color",
                    //$$ true,
                    //$$ (nodes, data, parser) -> new ColorNode(nodes, finalIColorNode)
                //$$ )
            //$$ );
//#else
//#endif

            MoreColorsEvents.REGISTER_COLOR_NODE.invoker().onRegisterMoreColorNode(iColorNode.getName(), iColorNode.getHexCode(), null);
        }
    }

    @ApiStatus.Internal
    public static boolean isMoreColorNode(String node)
    {
        AtomicBoolean atomic = new AtomicBoolean(false);

        ConfigWrap.colors().forEach((iNode) ->
                                    {
                                        if (node.equals(iNode.getName()) || (iNode.getAliases() != null && iNode.getAliases().contains(node)))
                                        {
                                            atomic.set(true);
                                        }
                                    });

        return atomic.get();
    }

    @ApiStatus.Internal
    public static void registerNodes()
    {
        registerColors();
    }

    @ApiStatus.Internal
    public static boolean checkIfRegistered(MoreColorNode colorNode)
    {
        if (TextUtils.LEGACY)
        {
            // Check if it already exists
            final String node = colorNode.getName();
            AtomicBoolean exists = new AtomicBoolean(false);

            TextParserV1.DEFAULT.getTags().forEach((tag) ->
                                                   {
                                                       if (tag.name().equals(node))
                                                       {
                                                           exists.set(true);
                                                       }
                                                   });

            if (exists.get())
            {
                return true;
            }
        }
//#if MC >= 12006
        // Check if it already exists
        //$$ if (TagRegistry.DEFAULT.getTag(colorNode.getName()) != null)
        //$$ {
            //$$ return true;
        //$$ }
//#else
//#endif

        return false;
    }

    @ApiStatus.Internal
    public static List<String> getAllColorNodes()
    {
        List<String> nodes = new ArrayList<>();

        if (TextUtils.LEGACY)
        {
            TextParserV1.DEFAULT.getTags().forEach((tag) ->
            {
                if (tag.type().equals("color") && !Objects.equals(tag.name(), "reset") && !Objects.equals(tag.name(), "color"))
                {
                    nodes.add(tag.name());
                }
            });
        }

//#if MC >= 12006
        //$$ TagRegistry.DEFAULT.getTags().forEach((tag) ->
        //$$ {
            //$$ if (Objects.equals(tag.type(), "color") && !nodes.contains(tag.name()) &&
               //$$ !Objects.equals(tag.name(), "reset") && !Objects.equals(tag.name(), "color"))
            //$$ {
                //$$ nodes.add(tag.name());
            //$$ }
        //$$ });
//#else
//#endif

        return nodes;
    }

    // Copied wrap() from TextTags.java
    @ApiStatus.Internal
    private static TextParserV1.TagNodeBuilder wrap(Wrapper wrapper)
    {
        return (tag, data, input, handlers, endAt) ->
        {
            // Legacy Parser
            var out = TextParserImpl.recursiveParsing(input, handlers, endAt);
            return new TextParserV1.TagNodeValue(wrapper.wrap(out.nodes(), data), out.length());
        };
    }

    @ApiStatus.Internal
    interface Wrapper
    {
        TextNode wrap(TextNode[] nodes, String arg);
    }
}
