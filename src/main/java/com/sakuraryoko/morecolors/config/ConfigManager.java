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

package com.sakuraryoko.morecolors.config;

import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.ApiStatus;

import com.sakuraryoko.morecolors.MoreColors;
import com.sakuraryoko.morecolors.Reference;
import com.sakuraryoko.morecolors.config.interfaces.IConfigData;
import com.sakuraryoko.morecolors.config.interfaces.IConfigDispatch;

@ApiStatus.Internal
public class ConfigManager
{
    private static final ConfigManager INSTANCE = new ConfigManager();
    public static ConfigManager getInstance() { return INSTANCE; }
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public void initAllConfigs()
    {
        // NO-OP
    }

    public void loadAllConfigs()
    {
        // More Colors
        MoreColorConfigHandler.getInstance().onPreLoadConfig();
        this.loadEach(MoreColorConfigHandler.getInstance());
        MoreColorConfigHandler.getInstance().onPostLoadConfig();
    }

    public void defaultAllConfigs()
    {
        // More Colors
        MoreColorConfigHandler.getInstance().onPreLoadConfig();
        MoreColorConfigHandler.getInstance().update(MoreColorConfigHandler.getInstance().defaults());
        MoreColorConfigHandler.getInstance().onPostLoadConfig();
        MoreColorConfigHandler.getInstance().execute();
    }

    public void saveAllConfigs()
    {
        // More Colors
        if (MoreColorConfigHandler.getInstance().isLoaded())
        {
            MoreColorConfigHandler.getInstance().onPreSaveConfig();
            this.saveEach(MoreColorConfigHandler.getInstance());
            MoreColorConfigHandler.getInstance().onPostSaveConfig();
        }
        else
        {
            // Also saves the file
            MoreColorConfigHandler.getInstance().onPreLoadConfig();
            this.loadEach(MoreColorConfigHandler.getInstance());
            MoreColorConfigHandler.getInstance().onPostLoadConfig();
        }
    }

    public void reloadAllConfigs()
    {
        this.loadEach(MoreColorConfigHandler.getInstance());
    }

    private void loadEach(IConfigDispatch config)
    {
        IConfigData conf = config.newConfig();
        MoreColors.debugLog("loadEach(): --> [{}/{}.json]", config.getConfigRoot(), config.getConfigName());

        try
        {
            Path dir;

            if (config.useRootDir())
            {
                dir = Reference.CONFIG_DIR;
            }
            else
            {
                dir = Reference.CONFIG_DIR.resolve(config.getConfigRoot());
            }

            if (!Files.isDirectory(dir))
            {
                Files.createDirectory(dir);
            }

            Path file = dir.resolve(config.getConfigName() + ".json");

            if (Files.exists(file))
            {
                JsonElement data = JsonParser.parseString(Files.readString(file));
                conf = GSON.fromJson(data, conf.getClass());
                MoreColors.LOGGER.info("loadEach(): Read config for [{}/{}]", dir.getFileName().toString(), file.getFileName().toString());
            }
            else
            {
                conf = config.defaults();
                MoreColors.LOGGER.info("loadEach(): Config defaults for [{}/{}.json]", config.getConfigRoot(), config.getConfigName());
            }

            conf = config.update(conf);
            Files.writeString(file, GSON.toJson(conf));
            config.execute();
        }
        catch (Exception e)
        {
            MoreColors.LOGGER.error("loadEach(): Error reading config [{}/{}.json] // {}", config.getConfigRoot(), config.getConfigName(), e.getMessage());
        }
    }

    private void saveEach(IConfigDispatch config)
    {
        IConfigData conf;

        MoreColors.debugLog("saveEach(): --> [{}/{}.json]", config.getConfigRoot(), config.getConfigName());

        try
        {
            Path dir;

            if (config.useRootDir())
            {
                dir = Reference.CONFIG_DIR;
            }
            else
            {
                dir = Reference.CONFIG_DIR.resolve(config.getConfigRoot());
            }

            if (!Files.isDirectory(dir))
            {
                Files.createDirectory(dir);
            }

            Path file = dir.resolve(config.getConfigName() + ".json");

            if (Files.exists(file))
            {
                MoreColors.LOGGER.info("saveEach(): Deleting existing config file: [{}/{}]", dir.getFileName().toString(), file.getFileName().toString());
                Files.delete(file);
            }

            conf = config.getConfig();

            if (conf != null)
            {
                Files.writeString(file, GSON.toJson(conf));
                MoreColors.LOGGER.info("saveEach(): Wrote config for [{}/{}.json]", config.getConfigRoot(), config.getConfigName());
            }
            else
            {
                MoreColors.LOGGER.error("saveEach(): Error saving config file [{}/{}.json] // config is empty!", config.getConfigRoot(), config.getConfigName());
            }
        }
        catch (Exception e)
        {
            MoreColors.LOGGER.error("saveEach(): Error saving config file [{}/{}.json] // {}", config.getConfigRoot(), config.getConfigName(), e.getMessage());
        }
    }
}
