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

package com.sakuraryoko.morecolors.impl.config;

import java.util.List;

import org.jetbrains.annotations.ApiStatus;

import com.sakuraryoko.morecolors.impl.config.data.options.MainOptions;
import com.sakuraryoko.morecolors.impl.nodes.MoreColorNode;

@ApiStatus.Internal
public class ConfigWrap
{
    public static MainOptions opt()
    {
        return MoreColorConfigHandler.getInstance().getMainOptions();
    }

    public static List<MoreColorNode> colors()
    {
        return MoreColorConfigHandler.getInstance().getColors();
    }
}
