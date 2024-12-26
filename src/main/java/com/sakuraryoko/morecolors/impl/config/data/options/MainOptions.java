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

package com.sakuraryoko.morecolors.impl.config.data.options;

import org.jetbrains.annotations.ApiStatus;

import com.sakuraryoko.corelib.api.config.IConfigOption;

@ApiStatus.Internal
public class MainOptions implements IConfigOption
{
    public int moreColorsCommandPermissions;
    public int moreColorsAddCommandPermissions;
    public int moreColorsDefaultsCommandPermissions;
    public int moreColorsReloadCommandPermissions;
    public int moreColorsSaveCommandPermissions;
    public int moreColorsTestCommandPermissions;
    public boolean debugMode;

    public MainOptions()
    {
        this.defaults();
    }

    @Override
    public void defaults()
    {
        this.moreColorsCommandPermissions = 0;
        this.moreColorsAddCommandPermissions = 3;
        this.moreColorsDefaultsCommandPermissions = 4;
        this.moreColorsReloadCommandPermissions = 3;
        this.moreColorsSaveCommandPermissions = 3;
        this.moreColorsTestCommandPermissions = 0;
        this.debugMode = false;
    }

    @Override
    public MainOptions copy(IConfigOption other)
    {
        MainOptions opts = (MainOptions) other;
        this.moreColorsCommandPermissions = opts.moreColorsCommandPermissions;
        this.moreColorsAddCommandPermissions = opts.moreColorsAddCommandPermissions;
        this.moreColorsDefaultsCommandPermissions = opts.moreColorsDefaultsCommandPermissions;
        this.moreColorsReloadCommandPermissions = opts.moreColorsReloadCommandPermissions;
        this.moreColorsSaveCommandPermissions = opts.moreColorsSaveCommandPermissions;
        this.moreColorsTestCommandPermissions = opts.moreColorsTestCommandPermissions;
        this.debugMode = opts.debugMode;

        return this;
    }
}
