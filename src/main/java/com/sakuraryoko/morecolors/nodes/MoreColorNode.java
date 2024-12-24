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
import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

//#if MC >= 12004
//$$ import com.mojang.serialization.DataResult;
//#else
//#endif
import net.minecraft.network.chat.TextColor;

import com.sakuraryoko.morecolors.MoreColors;

@ApiStatus.Internal
public class MoreColorNode
{
    private final String name;
    private final String hexCode;
    private List<String> aliases = new ArrayList<>();
    private final TextColor color;

    //#if MC >= 12004
    //$$ public MoreColorNode(String name, String hexCode)
    //$$ {
        //$$ DataResult<TextColor> dr;
        //$$ dr = TextColor.parseColor(hexCode);
        //$$ if (dr.error().isEmpty())
        //$$ {
            //$$ this.color = dr.result().orElse(null);
            //$$ if (this.color != null)
            //$$ {
                //$$ this.name = name;
                //$$ this.hexCode = hexCode;
            //$$ }
            //$$ else
            //$$ {
                //$$ MoreColors.LOGGER.warn("MoreColor({}) unhandled error (color is null)", name);
                //$$ this.name = "";
                //$$ this.hexCode = "";
            //$$ }
        //$$ }
        //$$ else
        //$$ {
            //$$ MoreColors.LOGGER.warn("MoreColor({}) is Invalid, error: {}", name, dr.error().toString());
            //$$ this.name = "";
            //$$ this.hexCode = "";
            //$$ this.color = null;
        //$$ }
    //$$ }

    //$$ public MoreColorNode(String name, String hexCode, @Nullable List<String> aliases)
    //$$ {
        //$$ DataResult<TextColor> dr;
        //$$ dr = TextColor.parseColor(hexCode);
        //$$ if (dr.error().isEmpty())
        //$$ {
            //$$ this.color = dr.result().orElse(null);
            //$$ if (this.color != null)
            //$$ {
                //$$ this.name = name;
                //$$ this.hexCode = hexCode;
                //$$ this.aliases = aliases;
            //$$ }
            //$$ else
            //$$ {
                //$$ MoreColors.LOGGER.warn("MoreColor({}) unhandled error (color is null)", name);
                //$$ this.name = "";
                //$$ this.hexCode = "";
            //$$ }
        //$$ }
        //$$ else
        //$$ {
            //$$ MoreColors.LOGGER.warn("MoreColor({}) is Invalid, error: {}", name, dr.error().toString());
            //$$ this.name = "";
            //$$ this.hexCode = "";
            //$$ this.color = null;
        //$$ }
    //$$ }
    //#else
    public MoreColorNode(String name, String hexCode)
    {
        this.color = TextColor.parseColor(hexCode);
        if (this.color != null)
        {
            this.name = name;
            this.hexCode = hexCode;
        }
        else {
            MoreColors.LOGGER.warn("MoreColor({}) unhandled error (color is null)", name);
            this.name = "";
            this.hexCode = "";
        }
    }

    public MoreColorNode(String name, String hexCode, @Nullable List<String> aliases)
    {
        this.color = TextColor.parseColor(hexCode);
        if (this.color != null)
        {
            this.name = name;
            this.hexCode = hexCode;
            this.aliases = aliases;
        }
        else {
            MoreColors.LOGGER.warn("MoreColor({}) unhandled error (color is null)", name);
            this.name = "";
            this.hexCode = "";
        }
    }
    //#endif

    public String getName()
    {
        return this.name;
    }

    public String getHexCode()
    {
        return this.hexCode;
    }

    @Nullable
    public List<String> getAliases()
    {
        return this.aliases;
    }

    public TextColor getColor()
    {
        return this.color;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("MoreColorNode{name={");

        result.append(this.name).append("}, hexCode={").append(this.hexCode).append("}");

        if (this.color != null)
        {
            result.append(", color={").append(this.color).append("}");
        }

        if (!this.aliases.isEmpty())
        {
            StringBuilder result2 = new StringBuilder("[");
            int i = 0;

            result.append(", aliases=");

            for (String entry : this.aliases)
            {
                if (i > 0)
                {
                    result2.append(",");
                }

                result2.append("{").append(entry).append("}");
                i++;
            }

            result2.append("]");
            result.append(result2);
        }

        result.append("}");
        return result.toString();
    }
}
