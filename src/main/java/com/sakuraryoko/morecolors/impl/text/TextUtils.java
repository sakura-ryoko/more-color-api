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

package com.sakuraryoko.morecolors.impl.text;

//#if MC >= 12006
//$$ import eu.pb4.placeholders.api.ParserContext;
//#else
//#endif
import javax.annotation.Nonnull;
import eu.pb4.placeholders.api.TextParserUtils;

import net.minecraft.network.chat.Component;

import com.sakuraryoko.corelib.api.text.ITextHandler;

//#if MC >= 12006
//$$ @SuppressWarnings("deprecation")
//#else
//#endif
public class TextUtils implements ITextHandler
{
    private static final TextUtils INSTANCE = new TextUtils();
    public static TextUtils getInstance() { return INSTANCE; }

    //#if MC >= 12006
    //$$ public static final boolean LEGACY = false;
    //#else
    public static final boolean LEGACY = true;
    //#endif

    //#if MC >= 12006
    //$$ public Component formatText(String str, ParserContext ctx)
    //$$ {
        //$$ return TextParser.PARSE.parseText(str, ctx);
    //$$ }
    //#else
    //#endif

    @Override
    public Component formatText(@Nonnull String str)
    {
        //#if MC <= 12104
        if (LEGACY)
        {
            return TextParserUtils.formatText(str);
        }
        //#endif

        //#if MC >= 12006
        //$$ return TextParser.PARSE.parseNode(str).toText();
        //#else
        return TextParserUtils.formatText(str);
        //#endif
    }

    @Override
    public Component formatTextSafe(@Nonnull String str)
    {
        //#if MC <= 12104
        if (LEGACY)
        {
            return TextParserUtils.formatTextSafe(str);
        }
        //#endif

        //#if MC >= 12006
        //$$ return TextParser.PARSE.parseNode(str).toText();
        //#else
        return TextParserUtils.formatTextSafe(str);
        //#endif
    }

    @Override
    public Component of(@Nonnull String str)
    {
        return Component.literal(str);
    }
}
