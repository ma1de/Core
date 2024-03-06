package me.ma1de.core.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import lombok.Getter;
import me.ma1de.core.Core;

@Getter
public class ProfileHandler {
    private final List<Profile> profiles = Lists.newArrayList();
    private final MongoCollection<Document> collection = Core.INSTANCE.getDatabase().getCollection("profiles");

    public Profile getProfile(UUID uuid) {
        return profiles.stream().filter(profile -> profile.getUuid().equals(uuid)).findAny().orElse(null);
    }

    public void addProfile(UUID uuid) {
        if (getProfile(uuid) != null) {
            return;
        }

        if (collection.find(Filters.eq("uuid", uuid.toString())).first() != null) {
            return;
        }

        profiles.add(new Profile(uuid, new ArrayList<>(), System.currentTimeMillis()));
        collection.insertOne(getProfile(uuid).toBson());
    }

    public void removeProfile(UUID uuid) {
        if (getProfile(uuid) == null) {
            return;
        }

        if (collection.find(Filters.eq("uuid", uuid.toString())).first() == null) {
            return;
        }

        profiles.remove(getProfile(uuid));
        collection.findOneAndDelete(Filters.eq("uuid", uuid.toString()));
    }

    public void load() {
        for (Document doc : collection.find()) {
            Profile profile = Core.INSTANCE.getGson().fromJson(doc.toJson(), new TypeToken<Profile>() {}.getType());
            profiles.add(profile);
        }
    }
}
