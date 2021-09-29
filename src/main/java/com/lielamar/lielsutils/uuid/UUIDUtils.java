package com.lielamar.lielsutils.uuid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lielamar.lielsutils.callbacks.JSONCallback;
import com.lielamar.lielsutils.callbacks.UUIDCallback;
import com.lielamar.lielsutils.exceptions.InvalidResponseException;
import com.lielamar.lielsutils.exceptions.UUIDNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class UUIDUtils {

    public static final long STARTUP_TIMESTAMP = System.currentTimeMillis();
    private static final Type gsonType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();

    /**
     * Generates a UUID based on current timestamp & startup time
     *
     * @return   Generated time-based uuid
     */
    public static @NotNull UUID generateTimeBasedUUID() { return generateTimeBasedUUID(STARTUP_TIMESTAMP); }
    public static @NotNull UUID generateTimeBasedUUID(long timestamp) { return new UUID(System.currentTimeMillis(), timestamp); }

    /**
     * Fetches a GET request to the given URL
     *
     * @param url        URL to fetch data from
     * @param callback   Callback to call when a response is received
     */
    public static void fetch(@NotNull String url, @NotNull JSONCallback callback) throws InvalidResponseException {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();

            if(response.length() == 0)
                throw new InvalidResponseException("Returned data from the GET request did not include a UUID!");

            callback.run(response.toString());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches a Minecraft user's UUID from Mojang's API
     *
     * @param username                    Username of the Minecraft user
     * @param callback                    Callback to call when a response is received
     * @throws InvalidResponseException   Throws an exception if the given response was invalid
     */
    public static void fetchUUIDFromMojang(@NotNull String username, @NotNull UUIDCallback callback) throws InvalidResponseException {
        fetch(String.format("https://api.mojang.com/users/profiles/minecraft/%s", username), (response) -> {
            Map<String, String> res = new Gson().fromJson(response, gsonType);

            try {
                String uuid = res.getOrDefault("id", null);
                if(uuid == null)
                    throw new UUIDNotFoundException("UUID with not found for username: " + username);

                callback.run(UUID.fromString(uuid));
            } catch (UUIDNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Fixes a uuid (adds dashes)
     *
     * @param uuid   UUID to fix
     * @return       Fixed uuid
     */
    public static @NotNull String fixUUID(@NotNull String uuid) {
        if(uuid.contains("-"))
            return uuid;

        return uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }
}