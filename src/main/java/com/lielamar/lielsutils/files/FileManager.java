package com.lielamar.lielsutils.files;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    private final Plugin plugin;
    private final Map<String, Config> configs;

    public FileManager(Plugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
    }

    /**
     * Returns a config by name
     *
     * @param name    Name of the config to get
     * @return        A {@link com.lielamar.lielsutils.files.FileManager.Config} instance
     */
    public Config getConfig(String name) {
        name = fixConfigName(name);

        if(!configs.containsKey(name))
            configs.put(name, new Config(name));

        return configs.get(name);
    }

    /**
     * Saves a config by name
     *
     * @param name    Name of the config to save
     * @return        A {@link com.lielamar.lielsutils.files.FileManager.Config} instance
     */
    public Config saveConfig(String name) {
        name = fixConfigName(name);

        return getConfig(name).save();
    }

    /**
     * Reloads a config by name
     *
     * @param name    Name of the config to reload
     * @return        A {@link com.lielamar.lielsutils.files.FileManager.Config} instance
     */
    public Config reloadConfig(String name) {
        name = fixConfigName(name);

        return getConfig(name).reload();
    }

    /**
     * Fixes a given name to a <name>.yml
     *
     * @param name     Name to fix
     * @return         Fixed name
     */
    public String fixConfigName(String name) {
        if(!name.toLowerCase().endsWith(".yml"))
            name += ".yml";

        return name;
    }

    public class Config {

        private String name;
        private File file;
        private YamlConfiguration config;

        public Config(String name) {
            this.name = name;
            this.file = new File(plugin.getDataFolder(), this.name);

            this.save();
        }

        public Config save() {
            if(!this.file.exists())
                plugin.saveResource(this.name, false);

            if(this.config == null)
                this.config = YamlConfiguration.loadConfiguration(file);

            try {
                config.save(this.file);
                this.config = YamlConfiguration.loadConfiguration(file);
            } catch(IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public YamlConfiguration getConfig() {
            if(this.config == null)
                save();

            return this.config;
        }

        /**
         * Reloads the config
         *
         * @return       A {@link com.lielamar.lielsutils.files.FileManager.Config} instance
         */
        public Config reload() {
            if(file == null || config == null)
                return null;

            return save();
        }

        /**
         * Copies a resource to the config
         *
         * @return       A {@link com.lielamar.lielsutils.files.FileManager.Config} instance
         */
        public Config copyDefaults(String source) {
            if(file == null)
                this.file = new File(plugin.getDataFolder(), this.name);

            this.config = YamlConfiguration.loadConfiguration(file);

            try {
                Reader reader = new InputStreamReader(plugin.getResource(fixConfigName(source)), StandardCharsets.UTF_8);
                YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);

                this.config.setDefaults(config);
                this.config.options().copyDefaults(true);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
            return this;
        }
    }
}
