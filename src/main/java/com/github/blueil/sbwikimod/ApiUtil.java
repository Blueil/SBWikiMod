package com.github.blueil.sbwikimod;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiUtil {

    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

    public static CompletableFuture<JsonElement> makeGetRequest(String urlString) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setConnectTimeout(1000);
                con.setReadTimeout(3000);
                if (con.getResponseCode() == 200) {
                    Reader in = new InputStreamReader(con.getInputStream());
                    JsonObject json = SBWikiMod.GSON.fromJson(new InputStreamReader(con.getInputStream()), JsonObject.class);
                    in.close();
                    con.disconnect();
                    return json;
                }
                con.disconnect();
            } catch (IOException e) {
                SBWikiMod.LOGGER.error(e);
            }
            return null;
        }, EXECUTOR_SERVICE);
    }
}
