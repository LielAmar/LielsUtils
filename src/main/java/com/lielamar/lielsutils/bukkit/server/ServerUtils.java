package com.lielamar.lielsutils.bukkit.server;

public class ServerUtils {

    /**
     * Returns the server version as an integer representing the major version (1.8, 1.9 etc.)
     *
     * @param versionRaw   Version as a string given from Bukkit.getVersion()
     * @return             Version as an Integer
     */
    public static int getVersion(String versionRaw) {
        if(versionRaw.contains(".")) versionRaw = versionRaw.split("\\.")[0];
        if(versionRaw.contains(")")) versionRaw = versionRaw.split("\\)")[0];
        return Integer.parseInt(versionRaw);
    }
}
