package com.lielamar.lielsutils.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class StandaloneCommand extends SuperCommand {

    public StandaloneCommand(@NotNull String command, @Nullable String permission) {
        super(command, permission);
    }

    @Override
    public void subCommandNotFoundEvent(@NotNull CommandSender cs) {}

    @Override
    public Command[] getSubCommands() { return new Command[0]; }

    @Override
    public @NotNull String getUsage() { return ""; }

    @Override
    public @NotNull String getDescription() { return ""; }

    @Override
    public @NotNull String[] getAliases() { return new String[0]; }
}