package com.lielamar.lielsutils.bukkit.callbacks;

import org.bukkit.command.CommandSender;

public interface CheckPermissionCallback {

    boolean hasPermission(CommandSender commandSender);
}
