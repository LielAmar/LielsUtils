package com.lielamar.lielsutils.fetching;

import com.lielamar.lielsutils.callbacks.JSONCallback;
import com.lielamar.lielsutils.exceptions.InvalidResponseException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchingUtils {

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
}
