package com.lielamar.lielsutils.bukkit.commands;

import com.lielamar.lielsutils.bukkit.callbacks.CheckPermissionCallback;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CommandWithSubCommands extends Command {

    public CommandWithSubCommands(@NotNull String name, @Nullable String permission) { super(name, permission); }
    public CommandWithSubCommands(@NotNull String name, @Nullable CheckPermissionCallback checkPermissionCallback) { super(name, checkPermissionCallback); }


    public abstract void subCommandNotFoundEvent(@NotNull CommandSender cs);
    public abstract @NotNull Command[] getSubCommands();


    public final @Nullable Command getSubCommand(String name) {
        for(Command subCommand : getSubCommands()) {
            if(subCommand.getCommandName().equalsIgnoreCase(name))
                return subCommand;

            if(subCommand.getAliases() != null) {
                for(String alias : subCommand.getAliases()) {
                    if(alias.equalsIgnoreCase(name))
                        return subCommand;
                }
            }
        }
        return null;
    }
}