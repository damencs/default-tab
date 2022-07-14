package com.defaulttab;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InterfaceTab
{
    COMBAT_OPTIONS("Combat", 0),
    SKILLS("Skills", 1),
    QUEST("Quest", 2),
    INVENTORY("Inventory", 3),
    EQUIPMENT("Equipment", 4),
    PRAYER("Prayer", 5),
    SPELLBOOK("Spellbook", 6),
    CLAN_CHAT("Clan Chat", 7),
    FILIST("Friends List", 9),
    ACCOUNT("Account", 8),
    LOGOUT("Logout", 10),
    SETTINGS("Settings", 11),
    EMOTES("Emotes", 12),
    MUSIC_PLAYER("Music", 13);

    private final String name;
    private final int index;

    @Override
    public String toString()
    {
        return name;
    }
}