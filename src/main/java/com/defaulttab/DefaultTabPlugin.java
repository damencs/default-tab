/*
 * Copyright (c) 2022, Damen <gh: damencs>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:

 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.

 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.defaulttab;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.EnumSet;

@Slf4j
@PluginDescriptor(
	name = "Default Tab",
	description = "Set and load a specific tab on every login",
	tags = { "default", "tab", "login", "combat", "skills", "quest", "inventory", "equipment", "prayer", "spellbook", "clan", "chat", "friends", "account", "logout", "settings", "emotes", "music", "damen" },
	enabledByDefault = true,
	loadInSafeMode = false
)
public class DefaultTabPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private DefaultTabConfig config;

	@Provides
	DefaultTabConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DefaultTabConfig.class);
	}

	private static final int TAB_SWITCH_SCRIPT = 915;
	private boolean pushTab = false;

	private int tickDelay = 0;

	@Override
	protected void shutDown()
	{
		pushTab = false;
	}

	@Subscribe
	private void onGameStateChanged(GameStateChanged e)
	{
		if (!config.isOnLoginEnabled() && !config.isOnWorldHop())
		{
			return;
		}

		switch (e.getGameState())
		{
			case LOGGING_IN:
				if (config.isOnLoginEnabled())
				{
					pushTab = true;
					tickDelay = 0;
				}
				break;
			case HOPPING:
				if (config.isOnWorldHop())
				{
					pushTab = true;
					tickDelay = 0;
				}
				break;
		}
	}

	@Subscribe
	private void onGameTick(GameTick e)
	{
		if (!pushTab || client.getGameState() != GameState.LOGGED_IN || client.getLocalPlayer() == null)
		{
			pushTab = false;
			return;
		}

		final EnumSet<WorldType> worldType = client.getWorldType();

		if (client.getVarbitValue(Varbits.IN_WILDERNESS) == 1 || WorldType.isPvpWorld(worldType))
		{
			if (!config.enabledInPvp())
			{
				return;
			}

			if (tickDelay != 3)
			{
				tickDelay++;
				return;
			}
		}

		clientThread.invokeLater(() ->
		{
			client.runScript(TAB_SWITCH_SCRIPT, getTabPreference());
			pushTab = getCurrentTab() != getTabPreference();
		});
	}

	private int getTabPreference()
	{
		return config.getDefaultInterfaceTab().getIndex();
	}

	private int getCurrentTab()
	{
		return client.getVarcIntValue(VarClientInt.INVENTORY_TAB);
	}
}
