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

package com.sakuraryoko.morecolors.text;

//#if MC >= 12006
//$$ import eu.pb4.placeholders.api.ParserContext;
//#else
//#endif
import eu.pb4.placeholders.api.TextParserUtils;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.network.chat.Component;

//#if MC >= 12006
//$$ @SuppressWarnings("deprecation")
//#else
//#endif
@ApiStatus.Internal
public class TextUtils
{
    //#if MC >= 12006
    //$$ public static final boolean LEGACY = false;
    //#else
    public static final boolean LEGACY = true;
    //#endif

    //#if MC >= 12006
    //$$ public static Component formatText(String str, ParserContext ctx)
    //$$ {
    //$$ return TextParser.PARSE.parseText(str, ctx);
    //$$ }
    //#else
    //#endif

    @ApiStatus.Internal
    public static Component formatText(String str)
    {
        if (LEGACY)
        {
            return TextParserUtils.formatText(str);
        }

        //#if MC >= 12006
        //$$ return TextParser.PARSE.parseNode(str).toText();
        //#else
        return TextParserUtils.formatText(str);
        //#endif
    }

    @ApiStatus.Internal
    public static Component formatTextSafe(String str)
    {
        if (LEGACY)
        {
            return TextParserUtils.formatTextSafe(str);
        }

        //#if MC >= 12006
        //$$ return TextParser.PARSE.parseNode(str).toText();
        //#else
        return TextParserUtils.formatTextSafe(str);
        //#endif
    }

    @ApiStatus.Internal
    public static Component of(String str)
    {
        return Component.literal(str);
    }
}
