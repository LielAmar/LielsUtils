package com.lielamar.lielsutils.uuid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lielamar.lielsutils.callbacks.UUIDCallback;
import com.lielamar.lielsutils.exceptions.InvalidResponseException;
import com.lielamar.lielsutils.exceptions.UUIDNotFoundException;
import com.lielamar.lielsutils.fetching.FetchingUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
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
     * Fetches a Minecraft user's UUID from Mojang's API
     *
     * @param username                    Username of the Minecraft user
     * @param callback                    Callback to call when a response is received
     * @throws InvalidResponseException   Throws an exception if the given response was invalid
     */
    public static void fetchUUIDFromMojang(@NotNull String username, @NotNull UUIDCallback callback) throws InvalidResponseException {
        FetchingUtils.fetch(String.format("https://api.mojang.com/users/profiles/minecraft/%s", username), (response) -> {
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