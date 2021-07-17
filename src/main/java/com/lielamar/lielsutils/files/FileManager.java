package com.lielamar.lielsutils.files;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

public class FileManager {

    public static final String DEFAULT_HEADER_SEPARATOR = "# ===========================================================================================";
    public static String HEADER_SEPARATOR;

    private final JavaPlugin plugin;
    private final Map<String, Config> configs;


    public FileManager(@NotNull JavaPlugin plugin) {
        this(plugin, DEFAULT_HEADER_SEPARATOR);
    }

    public FileManager(@NotNull JavaPlugin plugin, @NotNull String headerSeparator) {
        this.plugin = plugin;
        this.configs = new HashMap<>();

        HEADER_SEPARATOR = headerSeparator;
    }


    /**
     * Returns a Config object representing a single configuration
     *
     * @param path       Path to the config file
     * @param fileName   Name of the config file
     * @return           Config object representing the given config
     */
    public @Nullable Config getConfig(@NotNull String path, @NotNull String fileName) {
        fileName = this.fixConfigName(fileName);

        Config config = this.configs.get(fileName);
        if(config != null)
            return config;

        File configFile = this.getConfigFile(path, fileName);

        if(configFile == null)
            return null;

        // If the config doesn't already exist, we want to create it, copy the resource and set it's header
        if(!configFile.exists())
            this.createFile(configFile, this.plugin.getResource(fileName));

        config = new Config(configFile);

        this.configs.put(fileName, config);
        return config;
    }

    public @Nullable Config getConfig(@NotNull String fileName) {
        return this.getConfig(this.plugin.getDataFolder().getPath(), fileName);
    }


    /**
     * Returns the File object of a config file
     *
     * @param path       Path to the config file
     * @param fileName   Name of the config file
     * @return           File object of the given config
     */
    private @Nullable File getConfigFile(@NotNull String path, @NotNull String fileName) {
        if(fileName.isEmpty())
            return null;

        if(path.isEmpty())
            return new File(this.plugin.getDataFolder() + File.separator + fileName);

        if(!path.endsWith("/"))
            path += File.separator;

        return new File(path + fileName);
    }

    /**
     * Creates a File object for a config file
     *
     * @param configFile   File to create
     * @param source       Optional source to apply to the config
     */
    private void createFile(@NotNull File configFile, @Nullable InputStream source) {
        if(configFile.exists())
            return;

        try {
            boolean createdDir  = configFile.getParentFile().mkdir();
            boolean createdFile = configFile.createNewFile();

            if(source != null)
                this.loadSource(configFile, source);

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Loads a source stream to a file
     *
     * @param configFile   File to load the source to
     * @param source       Source to load
     */
    private void loadSource(@NotNull File configFile, @NotNull InputStream source) {
        if(!configFile.exists())
            return;

        try {
            OutputStream out = new FileOutputStream(configFile);

            int length;
            byte[] buffer = new byte[1024];

            while((length = source.read(buffer)) > 0)
                out.write(buffer, 0, length);

            out.close();
            source.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Fixes a config name -> turns to lowercase & adds .yml in the end
     *
     * @param fileName   Name to fix
     * @return           Fixed name
     */
    private @NotNull String fixConfigName(@NotNull String fileName) {
        if(!fileName.toLowerCase().endsWith(".yml"))
            fileName += ".yml";

        return fileName.toLowerCase();
    }


    public static class Config {

        private final File configFile;
        private YamlConfiguration configuration;

        private final List<String> header;
        private final Map<String, List<String>> comments;

        public Config(@NotNull File configFile) {
            this.configFile = configFile;

            this.header = new ArrayList<>();
            this.comments = new HashMap<>();

            this.loadConfig();
        }

        /**
         * Loads the config:
         * - Sets the configuration object's value
         * - Loads header & all comments
         */
        private void loadConfig() {
            if(!this.configFile.exists())
                return;

            try {
                String currentLine;
                boolean foundHeader = false; // Used to track when the header starts & stops

                // Getting the content of the config from the file and setting it in an array of lines
                BufferedReader reader = new BufferedReader(new FileReader(this.configFile));
                String[] lines = reader.lines().toArray(String[]::new);
                reader.close();

                StringBuilder configData = new StringBuilder();

                // Looping over the lines to parse the config
                for(int i = 0; i < lines.length; i++) {
                    currentLine = lines[i];

                    // If the current line is a header separator, we want to set foundHeader to the opposite value
                    // This way, when the foundHeader is true, all comments will go to the header, until we find the next header separator
                    if(currentLine.equals(HEADER_SEPARATOR)) {
                        foundHeader = !foundHeader;
                        header.add(currentLine);
                        continue;
                    }

                    // If the current line is a comment, we either want to add it to the header if we're in the foundHeader state,
                    // or add it to the map of comments, under the correct key.
                    // We find the correct key by looping over all lines until we find the next key, and this key is the key the comment belongs to
                    if(currentLine.startsWith("#") || currentLine.trim().startsWith("#")) {
                        if(foundHeader)
                            header.add(currentLine);
                        else {
                            // Trying to find the matching key for this comment by looping over all next lines until we find a line that isn't a comment
                            int j = i;
                            while(j < lines.length && (lines[j].startsWith("#") || lines[j].trim().startsWith("#")))
                                j++;

                            // If we found a key, we want to put the currentLine as a comment for the found key
                            if(j != i) {
                                // The key contains all spaces before it, for example the key for the path test.hello would be "  hello", because it has 1 parent
                                // This way, when saving the file, we can easily get the right key without needing the check the parents
                                String key = lines[j].split(":")[0];

                                // Adding the comment to the list of comments belong to the key
                                if(comments.containsKey(key))
                                    comments.get(key).add(currentLine);
                                else {
                                    List<String> listOfLines = new ArrayList<>();
                                    listOfLines.add(currentLine);

                                    comments.put(key, listOfLines);
                                }
                            }
                        }
                    } else
                        // If the current line is not a comment we want to add it to the config data
                        configData.append(currentLine).append("\n");
                }

                // Loading the configData into this.configuration through a reader
                Reader inputString = new StringReader(configData.toString());
                reader = new BufferedReader(inputString);

                this.configuration = YamlConfiguration.loadConfiguration(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public @Nullable Object get(@NotNull String path)                                    { return this.configuration.get(path); }
        public @Nullable Object get(@NotNull String path, @NotNull Object defaultValue)       { return this.configuration.get(path, defaultValue); }

        public @Nullable String getString(@NotNull String path)                              { return this.configuration.getString(path); }
        public @Nullable String getString(@NotNull String path, @NotNull String defaultValue) { return this.configuration.getString(path, defaultValue); }

        public int getInt(@NotNull String path)                                              { return this.configuration.getInt(path); }
        public int getInt(@NotNull String path, int defaultValue)                            { return this.configuration.getInt(path, defaultValue); }

        public double getDouble(@NotNull String path)                                        { return this.configuration.getDouble(path); }
        public double getDouble(@NotNull String path, double defaultValue)                   { return this.configuration.getDouble(path, defaultValue); }

        public boolean getBoolean(@NotNull String path)                                      { return this.configuration.getBoolean(path); }
        public boolean getBoolean(@NotNull String path, boolean defaultValue)                { return this.configuration.getBoolean(path, defaultValue); }

        public @Nullable List<?> getList(@NotNull String path)                               { return this.configuration.getList(path); }
        public @Nullable List<?> getList(@NotNull String path, @NotNull List<?> defaultValue) { return this.configuration.getList(path, defaultValue); }

        public @NotNull List<String> getStringList(@NotNull String path)                     { return this.configuration.getStringList(path); }
        public @NotNull List<Integer> getIntegerList(@NotNull String path)                   { return this.configuration.getIntegerList(path); }

        public @Nullable Set<String> getKeys()                                               { return this.configuration.getKeys(false); }

        public @NotNull ConfigurationSection createSection(@NotNull String path)             { return this.configuration.createSection(path); }
        public @Nullable ConfigurationSection getConfigurationSection(@NotNull String path)  { return this.configuration.getConfigurationSection(path); }

        public boolean contains(@NotNull String path)                                        { return this.configuration.contains(path); }
        public void removeKey(@NotNull String path)                                          { this.configuration.set(path, null); }
        public void set(@NotNull String path, @Nullable Object value)                        { this.configuration.set(path, value); }


        /**
         * Returns a key from a path.
         *
         * If the path has any parents, we want to only get the children.
         * We also want to add 2 spaces before the key, for every parent it has.
         * This way, the key matches with the one from the initial load of the config (through #loadConfig)
         *
         * Example:
         *   hello.i.am.liel
         *   - 3 parents (hello, i, am)
         *   - key is "liel"
         *   - final key would have 6 spaces (3 parents * 2 spaces for each parent) -> "      liel"
         *
         * @param path   Path to get the key of
         * @return       Key of the path
         */
        private @NotNull String getKeyFromPath(String path) {
            StringBuilder key;

            if(!path.contains("."))
                key = new StringBuilder(path);
            else {
                String[] pathParts = path.split("\\.");
                key = new StringBuilder(pathParts[pathParts.length - 1]);

                // Adding the spaces before the key
                for(int i = 0; i < pathParts.length - 1; i++)
                    key.insert(0, "  ");
            }

            return key.toString();
        }

        /**
         * Sets a comment above a certain key
         *
         * @param path        Path of the value to set comment to
         * @param comment     Comment to set
         */
        public void addComment(@NotNull String path, @NotNull String comment) {
            String key = getKeyFromPath(path);

            if(this.comments.containsKey(key)) {
                this.comments.get(key).add(comment);
            } else {
                List<String> listOfComments = new ArrayList<>();
                listOfComments.add(comment);
                this.comments.put(key, listOfComments);
            }
        }

        /**
         * Sets multiple comments above a certain key
         *
         * @param path         Path of the value to set comments to
         * @param comments     Comment to set
         */
        public void addComments(@NotNull String path, @NotNull String[] comments) {
            String key = getKeyFromPath(path);

            if(this.comments.containsKey(key)) {
                this.comments.get(key).addAll(Arrays.asList(comments));
            } else {
                List<String> listOfComments = new ArrayList<>(Arrays.asList(comments));
                this.comments.put(key, listOfComments);
            }
        }

        /**
         * Sets the header of the config
         *
         * @param header   Header to set
         */
        public void setHeader(List<String> header) {
            this.header.clear();

            this.header.add(HEADER_SEPARATOR);
            this.header.addAll(header);
            this.header.add(HEADER_SEPARATOR);
        }


        /**
         * Reloads the config from the saved file on the disk
         */
        public void reloadConfig() {
            this.loadConfig();
        }

        /**
         * Saves the config
         */
        public void saveConfig() {
            this.saveConfig(this.getConfigAsString());
        }

        /**
         * Saves the config:
         *   - Gets the config string from the YamlConfiguration object
         *   - Applies comments to the config string
         *   - saves it to the config file
         *
         * @param configString string to save to the config
         */
        private void saveConfig(String configString) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
                writer.write(configString);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Returns the config as string
         *
         * @return   String value of the config, including comments
         */
        private String getConfigAsString() {
            String configString = this.configuration.saveToString();
            return this.applyComments(configString);
        }

        /**
         * Gets the config string and edits it to apply comments
         *
         * @param configString   Original configuration string with comments set as normal values
         * @return               Edited configuration string with comments set as normal comments
         */
        private String applyComments(String configString) {
            String[] lines = configString.split("\n"); // Getting all of the lines

            StringBuilder configData = new StringBuilder();

            // Appending all of the header comments
            for(String comment : header)
                configData.append(comment).append("\n");

            List<String> listOfComments;

            // Looping over all lines
            for(String line : lines) {

                // If the line is a key we want to add it its comments
                if(line.contains(":")) {
                    String key = line.split(":")[0]; // Getting only the key (removing the value)

                    // Appending all of the comments for this specific key
                    listOfComments = this.comments.get(key);

                    if(listOfComments != null) {
                        configData.append("\n");
                        for(String comment : listOfComments)
                            configData.append(comment).append("\n");
                    }
                }

                // Appending the line itself
                configData.append(line).append("\n");
            }

            return configData.toString();
        }
    }
}
