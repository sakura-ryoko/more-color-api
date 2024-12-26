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

package com.sakuraryoko.morecolors.api.modinit;

import java.util.*;

import net.minecraft.DetectedVersion;
import net.minecraft.network.chat.Component;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import com.sakuraryoko.morecolors.impl.MoreColors;
import com.sakuraryoko.morecolors.impl.text.TextUtils;

public class ModData
{
    public static final List<String> BASIC_INFO = List.of("ver", "auth", "desc");
    public static final List<String> ALL_INFO = List.of("ver", "auth", "con", "lic", "home", "src", "iss", "desc");
    private String MOD_ID;
    private String mcVersion;
    private FabricLoader instance;
    private ModContainer modContainer;
    private ModMetadata modMetadata;
    private EnvType envType;
    private String modName;
    private String modVersion;
    private String description;
    private Collection<Person> authors;
    private Collection<Person> contrib;
    private ContactInformation contacts;
    private Collection<String> licenses;
    private String authorString;
    private String contribString;
    private String licenseString;
    private String homepage;
    private String source;
    private String issues;

    // Server mode
    private boolean integratedServer;
    private boolean dedicatedServer;
    private boolean openToLan;

    public ModData(String modID)
    {
        if (modID.isEmpty())
        {
            return;
        }

        this.mcVersion = DetectedVersion.BUILT_IN.getName();
        this.instance = FabricLoader.getInstance();
        this.MOD_ID = modID;
        this.envType = this.instance.getEnvironmentType();
        this.integratedServer = false;
        this.dedicatedServer = false;
        this.openToLan = false;

        if (this.instance.getModContainer(this.MOD_ID).isPresent())
        {
            this.modContainer = this.instance.getModContainer(this.MOD_ID).get();
            this.modMetadata = this.modContainer.getMetadata();
            this.modVersion = this.modMetadata.getVersion().getFriendlyString();
            this.modName = this.modMetadata.getName();
            this.description = this.modMetadata.getDescription();
            this.authors = this.modMetadata.getAuthors();
            this.contrib = this.modMetadata.getContributors();
            this.contacts = this.modMetadata.getContact();
            this.licenses = this.modMetadata.getLicense();
            this.homepage = this.contacts.asMap().get("homepage");
            this.source = this.contacts.asMap().get("sources");
            this.issues = this.contacts.asMap().get("issues");

            if (this.authors.isEmpty())
            {
                this.authorString = "";
            }
            else
            {
                StringBuilder authoString = new StringBuilder();
                final Iterator<Person> personIterator = this.authors.iterator();

                while (personIterator.hasNext())
                {
                    if (authoString.isEmpty())
                    {
                        authoString = new StringBuilder(personIterator.next().getName());
                    }
                    else
                    {
                        authoString.append(", ").append(personIterator.next().getName());
                    }
                }

                this.authorString = authoString.toString();
            }

            if (this.contrib.isEmpty())
            {
                this.contribString = "";
            }
            else
            {
                StringBuilder contribStr = new StringBuilder();
                final Iterator<Person> personIterator = this.contrib.iterator();

                while (personIterator.hasNext())
                {
                    if (contribStr.isEmpty())
                    {
                        contribStr = new StringBuilder(personIterator.next().getName());
                    }
                    else
                    {
                        contribStr.append(", ").append(personIterator.next().getName());
                    }
                }

                this.contribString = contribStr.toString();
            }
            if (this.licenses.isEmpty())
            {
                this.licenseString = "";
            }
            else
            {
                StringBuilder licString = new StringBuilder();
                final Iterator<String> stringIterator = this.licenses.iterator();

                while (stringIterator.hasNext())
                {
                    if (licString.isEmpty())
                    {
                        licString = new StringBuilder(stringIterator.next());
                    }
                    else
                    {
                        licString.append(", ").append(stringIterator.next());
                    }
                }

                this.licenseString = licString.toString();
            }
        }
    }

    public String getMCVersion() {return this.mcVersion;}

    public String getModID() {return this.MOD_ID;}

    public FabricLoader getModInstance() {return this.instance;}

    public ModContainer getModContainer() {return this.modContainer;}

    public ModMetadata getModMetadata() {return this.modMetadata;}

    public EnvType getModEnv() {return this.envType;}

    public boolean isClient() {return this.envType == EnvType.CLIENT;}

    public boolean isServer() {return this.envType == EnvType.SERVER;}

    public boolean isIntegratedServer() {return this.integratedServer;}

    public boolean isDedicatedServer() {return this.dedicatedServer;}

    public boolean isOpenToLan() {return this.openToLan;}

    public void setIntegratedServer(boolean toggle)
    {
        this.integratedServer = toggle;
    }

    public void setDedicatedServer(boolean toggle)
    {
        this.dedicatedServer = toggle;
    }

    public void setOpenToLan(boolean toggle)
    {
        if (toggle)
        {
            this.openToLan = true;
            this.integratedServer = true;
        }
        else
        {
            this.openToLan = false;
        }
    }

    public String getModVersion() {return this.modVersion;}

    public String getModName() {return this.modName;}

    public String getModDesc() {return this.description;}

    public Collection<Person> getModAuthors() {return this.authors;}

    public Collection<Person> getModContrib() {return this.contrib;}

    public ContactInformation getModContacts() {return this.contacts;}

    public Collection<String> getModLicense() {return this.licenses;}

    public String getModAuthor$String() {return this.authorString;}

    public String getModContrib$String() {return this.contribString;}

    public String getModLicense$String() {return this.licenseString;}

    public String getModHomepage() {return this.homepage;}

    public String getModSources() {return this.source;}

    public String getModIssues() {return this.issues;}

    public Map<String, String> getModBasicInfo()
    {
        Map<String, String> basicInfo = new HashMap<>();

        basicInfo.put("ver", this.modName + "-" + this.mcVersion + "-" + this.modVersion);

        if (!this.authorString.isEmpty())
        {
            basicInfo.put("auth", "Author: " + this.authorString);
        }

        if (!this.contribString.isEmpty())
        {
            basicInfo.put("con", "Contrib: " + this.contribString);
        }

        if (!this.licenseString.isEmpty())
        {
            basicInfo.put("lic", "License: " + this.licenseString);
        }

        if (!this.homepage.isEmpty())
        {
            basicInfo.put("home", "Homepage: " + this.homepage);
        }

        if (!this.source.isEmpty())
        {
            basicInfo.put("src", "Source: " + this.source);
        }

        if (!this.issues.isEmpty())
        {
            basicInfo.put("iss", "Issues: " + this.issues);
        }

        if (!this.description.isEmpty())
        {
            basicInfo.put("desc", "Description: " + this.description);
        }

        return basicInfo;
    }

    public Map<String, Component> getModFormattedInfo()
    {
        Map<String, Component> fmtInfo = new HashMap<>();

        fmtInfo.put("ver", TextUtils.of(this.modName + "-" + this.mcVersion + "-" + this.modVersion));

        if (!this.authorString.isEmpty())
        {
            fmtInfo.put("auth", TextUtils.formatTextSafe("Author: <pink>" + this.authorString + "</pink>"));
        }

        if (!this.contribString.isEmpty())
        {
            fmtInfo.put("con", TextUtils.formatTextSafe("Contrib: <lime>" + this.contribString + "</lime>"));
        }

        if (!this.licenseString.isEmpty())
        {
            fmtInfo.put("lic", TextUtils.formatTextSafe("License: <yellow>" + this.licenseString + "</yellow>"));
        }

        if (!this.homepage.isEmpty())
        {
            fmtInfo.put("home", TextUtils.formatTextSafe("Homepage: <cyan><url:'" + this.homepage + "'>" + this.homepage + "</url></cyan>"));
        }

        if (!this.source.isEmpty())
        {
            fmtInfo.put("src", TextUtils.formatTextSafe("Source: <cyan><url:'" + this.source + "'>" + this.source + "</url></cyan>"));
        }

        if (!this.issues.isEmpty())
        {
            fmtInfo.put("iss", TextUtils.formatTextSafe("Issues: <cyan><url:'" + this.issues + "'>" + this.issues + "</url></cyan>"));
        }

        if (!this.description.isEmpty())
        {
            fmtInfo.put("desc", TextUtils.formatTextSafe("Description: <light_blue>" + this.description + "</light_blue>"));
        }

        return fmtInfo;
    }

    public void reset()
    {
        MoreColors.debugLog("ModInitData: reset()");
        this.integratedServer = false;
        this.openToLan = false;
        this.dedicatedServer = false;
    }
}