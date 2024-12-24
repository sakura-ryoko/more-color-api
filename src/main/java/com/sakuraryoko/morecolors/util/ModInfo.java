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

package com.sakuraryoko.morecolors.util;

import java.util.Iterator;

import org.jetbrains.annotations.ApiStatus;

import net.minecraft.DetectedVersion;
import net.minecraft.network.chat.Component;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import com.sakuraryoko.morecolors.MoreColors;
import com.sakuraryoko.morecolors.Reference;
import com.sakuraryoko.morecolors.text.TextUtils;

@ApiStatus.Internal
public class ModInfo
{
    private static final FabricLoader INSTANCE = FabricLoader.getInstance();
    private static final ModContainer CONTAINER = INSTANCE.getModContainer(Reference.MOD_ID).get();

    public static void initModInfo()
    {
        Reference.MC_VERSION = DetectedVersion.BUILT_IN.getName();
        Reference.MOD_ENV = INSTANCE.getEnvironmentType();
        ModMetadata AFK_METADATA = CONTAINER.getMetadata();
        Reference.MOD_VERSION = AFK_METADATA.getVersion().getFriendlyString();
        Reference.MOD_NAME = AFK_METADATA.getName();
        Reference.MOD_DESC = AFK_METADATA.getDescription();
        Reference.MOD_AUTHOR = AFK_METADATA.getAuthors();
        Reference.MOD_CONTRIB = AFK_METADATA.getContributors();
        Reference.MOD_CONTACTS = AFK_METADATA.getContact();
        Reference.MOD_LICENSES = AFK_METADATA.getLicense();
        Reference.MOD_AUTHO_STRING = getAuthoString();
        Reference.MOD_CONTRIB_STRING = getContribString();
        Reference.MOD_LICENSES_STRING = getLicenseString();
        Reference.MOD_HOMEPAGE_STRING = getHomepageString();
        Reference.MOD_SOURCES_STRING = getSourcesString();
    }

    public static void displayModInfo()
    {
        MoreColors.LOGGER.info("{}-{}-{}", Reference.MOD_NAME, Reference.MC_VERSION, Reference.MOD_VERSION);
        MoreColors.LOGGER.info("Author: {}", Reference.MOD_AUTHO_STRING);
    }

    public static Component getModInfoText()
    {
        String modInfo = Reference.MOD_NAME + "-" + Reference.MC_VERSION + "-" + Reference.MOD_VERSION
                + "\nAuthor: <pink>" + Reference.MOD_AUTHO_STRING + "</pink>"
                + "\nLicense: <yellow>" + Reference.MOD_LICENSES_STRING + "</yellow>"
                + "\nHomepage: <cyan><url:'" + Reference.MOD_HOMEPAGE_STRING + "'>" + Reference.MOD_HOMEPAGE_STRING + "</url></cyan>"
                + "\nSource: <cyan><url:'" + Reference.MOD_SOURCES_STRING + "'>" + Reference.MOD_SOURCES_STRING + "</url></cyan>"
                + "\nDescription: <light_blue>" + Reference.MOD_DESC;

        Component info = TextUtils.formatText(modInfo);
        MoreColors.debugLog(modInfo);

        return info;
    }

    public static boolean isServer()
    {
        return Reference.MOD_ENV == EnvType.SERVER;
    }

    public static boolean isClient()
    {
        return Reference.MOD_ENV == EnvType.CLIENT;
    }

    private static String getAuthoString()
    {
        StringBuilder authoString = new StringBuilder();

        if (Reference.MOD_AUTHOR.isEmpty())
        {
            return authoString.toString();
        }
        else
        {
            final Iterator<Person> iterator = Reference.MOD_AUTHOR.iterator();

            while (iterator.hasNext())
            {
                if (authoString.isEmpty())
                {
                    authoString = new StringBuilder(iterator.next().getName());
                }
                else
                {
                    authoString.append(", ").append(iterator.next().getName());
                }
            }

            return authoString.toString();
        }
    }

    private static String getContribString()
    {
        StringBuilder contribString = new StringBuilder();

        if (Reference.MOD_CONTRIB.isEmpty())
        {
            return contribString.toString();
        }
        else
        {
            final Iterator<Person> iterator = Reference.MOD_CONTRIB.iterator();

            while (iterator.hasNext())
            {
                if (contribString.isEmpty())
                {
                    contribString = new StringBuilder(iterator.next().getName());
                }
                else
                {
                    contribString.append(", ").append(iterator.next().getName());
                }
            }

            return contribString.toString();
        }
    }

    private static String getLicenseString()
    {
        StringBuilder licsenseString = new StringBuilder();

        if (Reference.MOD_LICENSES.isEmpty())
        {
            return licsenseString.toString();
        }
        else
        {
            final Iterator<String> iterator = Reference.MOD_LICENSES.iterator();
            while (iterator.hasNext())
            {
                if (licsenseString.isEmpty())
                {
                    licsenseString = new StringBuilder(iterator.next());
                }
                else
                {
                    licsenseString.append(", ").append(iterator.next());
                }
            }
            return licsenseString.toString();
        }
    }

    private static String getHomepageString()
    {
        String homepageString = Reference.MOD_CONTACTS.asMap().get("homepage");

        if (homepageString.isEmpty())
        {
            return "";
        }
        else
        {
            return homepageString;
        }
    }

    private static String getSourcesString()
    {
        String sourcesString = Reference.MOD_CONTACTS.asMap().get("sources");

        if (sourcesString.isEmpty())
        {
            return "";
        }
        else
        {
            return sourcesString;
        }
    }
}
