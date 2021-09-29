package com.lielamar.lielsutils.bukkit.commands;

import com.lielamar.lielsutils.arrays.ArraysUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SuperCommand extends Command {

    public SuperCommand(@NotNull String name, @Nullable String permission) {
        super(name, permission);
    }


    public abstract void subCommandNotFoundEvent(@NotNull CommandSender cs);
    public abstract @NotNull Command[] getSubCommands();


    private @Nullable Command getSubCommand(String name) {
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

    public void registerCommand(@NotNull JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(command);

        if(pluginCommand == null) return;

        pluginCommand.setTabCompleter((cs, command, _alias, args) -> {
            List<String> options = new ArrayList<>();

            // If the sender has permissions we want to return one of the following:
            // 1. If the sender started entering an input, we want to return all sub commands that start with.
            //      If we have a subcommand that is equals to an exact sub commands, we want to return the subcommand's tab options.
            // 2. If the sender didn't enter anything, we want to add all of the options of the #tabOptions implementation, and loop over all subcommands and add all of them to the options
            if(super.hasPermission(cs)) {
                if(args.length != 0) {
                    Command subCommand = getSubCommand(args[0]);

                    if(subCommand != null)
                        return subCommand.tabOptions(cs, (getSubCommands().length > 0 ? ArraysUtils.removeFirstElement(args.clone()) : args.clone()));
                }

                List<String> opt = this.tabOptions(cs, (getSubCommands().length > 0 ? ArraysUtils.removeFirstElement(args.clone()) : args.clone()));
                options.addAll(opt == null ? new ArrayList<>() : opt);

                for(Command subCmd : getSubCommands()) {
                    if(subCmd.getPermission() == null || cs.hasPermission(subCmd.getPermission())) {
                        if(subCmd.getCommandName().toLowerCase().startsWith(args[0].toLowerCase()))
                            options.add(subCmd.getCommandName());

                        Arrays.stream(subCmd.getAliases())
                                .filter(alias -> alias.toLowerCase().startsWith(args[0].toLowerCase()))
                                .forEach(options::add);
                    }
                }
            }

            return options;
        });

        pluginCommand.setExecutor((cs, command, label, args) -> {
            if(super.hasPermission(cs)) {
                // If there are arguments we want to check if we have a subcommand corresponding to these arguments.
                //   If so, we want to check if the sender has permission to that subcommand, and if so we want to run it
                //   If not, we'll run the #noPermissionEvent of the subcommand
                // If there are no arguments we want to run the master command, which is this the class extending this SuperCommand class.
                //
                // If the user doesn't have permission at all, we'll run the #noPermissionsEvent implementation of the master command, which is this the class extending this SuperCommand class.
                if(args.length != 0) {
                    Command subCommand = getSubCommand(args[0]);

                    if(subCommand != null) {
                        if(subCommand.hasPermission(cs))
                            return subCommand.runCommand(cs, (getSubCommands().length > 0 ? ArraysUtils.removeFirstElement(args.clone()) : args.clone()));
                        else {
                            subCommand.noPermissionEvent(cs);
                            return false;
                        }
                    }
                }

                return runCommand(cs, args.clone());
            }

            noPermissionEvent(cs);
            return false;
        });
    }
}