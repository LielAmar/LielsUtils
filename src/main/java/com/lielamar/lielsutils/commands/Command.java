package com.lielamar.lielsutils.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class Command {

    private final String name;

    public Command(String name) {
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public boolean hasPermissions(CommandSender cs) {
        for(String s : getPermissions()) {
            if(cs.hasPermission(s))
                return true;
        }

        return getPermissions().length == 0;
    }

    public abstract String getDescription();
    public abstract String[] getAliases();
    public abstract String[] getPermissions();

    public abstract void execute(CommandSender sender, String[] args);

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
