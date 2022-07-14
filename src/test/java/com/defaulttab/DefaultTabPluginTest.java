package com.defaulttab;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DefaultTabPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(DefaultTabPlugin.class);
		RuneLite.main(args);
	}
}