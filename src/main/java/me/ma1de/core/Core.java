package me.ma1de.core;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import lombok.Getter;
import me.ma1de.core.bukkit.CorePlugin;
import me.ma1de.core.profile.ProfileHandler;
import me.ma1de.core.rank.RankHandler;

@Getter
public enum Core {
    INSTANCE;

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    private MongoClient client;
    private MongoDatabase database;

    private RankHandler rankHandler;
    private ProfileHandler profileHandler;
    
    public void load() { 
        client = new MongoClient(
               CorePlugin.getInstance().getConfig().getString("MONGO.HOST"),
               CorePlugin.getInstance().getConfig().getInt("MONGO.PORT")
        );

        database = client.getDatabase(CorePlugin.getInstance().getConfig().getString("MONGO.DATABASE"));
    
        rankHandler = new RankHandler();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CorePlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                rankHandler.load();
            }
        }, 0L, 0L);

        profileHandler = new ProfileHandler();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CorePlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                profileHandler.load();
            }
        }, 0L, 0L);
    }
}
