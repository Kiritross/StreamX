package com.kiritokirigami5.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StreamAPI {
    private String channel;
    private URL url;
    private BufferedReader reader;
    private boolean online = false;

    public StreamAPI(String channel) {
        this.channel = channel;
        this.refresh();
    }

    public void refresh() {
        try {
            this.url = new URL("https://api.chisdealhd.co.uk/v1/twitchapi/" + this.channel);
            HttpURLConnection con = (HttpURLConnection)this.url.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            this.reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            if (!this.reader.readLine().contains("\"online\":false")) {
                this.online = true;
            } else {
                this.online = false;
            }

        } catch (MalformedURLException var2) {
            var2.printStackTrace();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public URL getUrl() {
        return this.url;
    }

    public boolean isOnline() {
        return this.online;
    }
}
