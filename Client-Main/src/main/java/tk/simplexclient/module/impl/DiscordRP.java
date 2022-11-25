package tk.simplexclient.module.impl;

import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.ServerJoinEvent;
import tk.simplexclient.event.impl.SinglePlayerJoinEvent;

import java.time.OffsetDateTime;

public class DiscordRP {
    //private static IPCClient client;

    public static void start() {
        /*
        client.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient client) {
                //update("Starting 1.8.9...", "");
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setDetails("test")
                        .setState("test")
                        .setButton1Text("Discord")
                        .setButton1Url("https://discord.gg/simplexclient")
                        .setButton2Text("Download (Offline)")
                        .setButton2Url("https://simplexclient.tk")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("big", "SimplexClient v2");

                client.sendRichPresence(builder.build());
            }
        });
        client.connect();

         */
        /*
        client = new IPCClient(1002546968880496660L);
        client.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient client) {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setDetails("Starting 1.8.9...")
                        .setButton1Text("Discord")
                        .setButton1Url("https://discord.gg/simplexclient")
                        .setButton2Text("Download (Not Online)")
                        .setButton2Url("http://simplexclient.tk")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("big", "SimplexClient");
                client.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            e.printStackTrace();
        }

         */
    }

    public static void shutdown() {
        //client.close();
    }

    public static void update(String firstLine, String secondLine) {
        /*
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setDetails(firstLine)
                .setState(secondLine)
                .setButton1Text("Discord")
                .setButton1Url("https://discord.gg/simplexclient")
                .setButton2Text("Download (Offline)")
                .setButton2Url("https://simplexclient.tk")
                .setStartTimestamp(OffsetDateTime.now())
                .setLargeImage("big", "SimplexClient v2");

        client.sendRichPresence(builder.build());

         */

        /*
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setDetails("Starting 1.8.9...")
                .setButton1Text("Discord")
                .setButton1Url("https://discord.gg/simplexclient")
                .setButton2Text("Download (Not Online)")
                .setButton2Url("http://simplexclient.tk")
                .setStartTimestamp(OffsetDateTime.now())
                .setLargeImage("big", "SimplexClient");
        client.sendRichPresence(builder.build());

         */
    }

    @EventTarget
    public void onServerJoinEvent(ServerJoinEvent event) {
        //update("Playing " + event.ip + (event.port != 25565 ? event.port : ""), "In Game");
    }

    @EventTarget
    public void onSinglePlayerJoinEvent(SinglePlayerJoinEvent event) {
        //update("Playing Singleplayer", "In Game");
    }

}
