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

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("defaulttab")
public interface DefaultTabConfig extends Config
{
	@ConfigItem(
			name = "Set On Login",
			keyName = "enableOnLogin",
			description = "Load your default interface on login",
			position = 0
	)
	default boolean isOnLoginEnabled()
	{
		return false;
	}

	@ConfigItem(
			name = "Set On World Hop",
			keyName = "enableOnWorldHop",
			description = "Load your default interface on world hop",
			position = 1
	)
	default boolean isOnWorldHop()
	{
		return false;
	}

	@ConfigItem(
			name = "Interface Tab",
			keyName = "defaultInterfaceTab",
			description = "",
			position = 2
	)
	default InterfaceTab getDefaultInterfaceTab()
	{
		return InterfaceTab.INVENTORY;
	}

	@ConfigItem(
			name = "Enable in Wilderness/PvP Worlds",
			keyName = "enabledInPvP",
			description = "- Switch to the default tab when in the wilderness and PvP Worlds (3-tick delay)",
			position = 3
	)
	default boolean enabledInPvp()
	{
		return false;
	}
}
