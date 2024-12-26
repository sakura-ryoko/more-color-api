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

package com.sakuraryoko.morecolors.impl.config.data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

import com.sakuraryoko.corelib.api.config.IConfigData;
import com.sakuraryoko.morecolors.impl.config.data.options.MainOptions;
import com.sakuraryoko.morecolors.impl.modinit.MoreColorInit;
import com.sakuraryoko.morecolors.impl.nodes.MoreColorNode;

@ApiStatus.Internal
public class MoreColorData implements IConfigData
{
    @SerializedName("___comment")
    public String comment = MoreColorInit.getInstance().getModVersionString() + " Config";

    @SerializedName("config_date")
    public String config_date;

    @SerializedName("main")
    public MainOptions MAIN = new MainOptions();

    @SerializedName("more_colors")
    public List<MoreColorNode> COLORS = new ArrayList<>();
}
