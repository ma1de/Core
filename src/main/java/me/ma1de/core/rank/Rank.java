package me.ma1de.core.rank;

import java.util.List;

import org.bson.Document;
import org.bukkit.ChatColor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.core.Core;

@Getter @AllArgsConstructor
public class Rank {
    private String id, displayName;
    private ChatColor color;
    private List<String> permissions, inheritance;
    private int weight;
    private boolean staff, hidden, enabled;

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public Document toBson() {
        return Document.parse(Core.INSTANCE.getGson().toJson(this));
    }
}
