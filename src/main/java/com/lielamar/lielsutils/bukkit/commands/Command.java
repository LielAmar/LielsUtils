package com.lielamar.lielsutils.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Command {

    protected String command;
    protected String permission;

    public Command(@NotNull String command, @Nullable String permission) {
        this.command = command;
        this.permission = permission;
    }


    public abstract void noPermissionEvent(@NotNull CommandSender cs);
    public abstract boolean runCommand(@NotNull CommandSender cs, @NotNull String[] args);
    public abstract List<String> tabOptions(@NotNull CommandSender cs, @NotNull String[] args);

    public abstract @NotNull String getUsage();
    public abstract @NotNull String getDescription();
    public abstract @NotNull String[] getAliases();


    public @NotNull String getCommandName() { return command; }
    public @Nullable String getPermission() { return permission; }
    public boolean hasPermission(@NotNull CommandSender cs) {
        if(this.permission == null) return false;

        return cs.hasPermission(this.permission);
    }
}
