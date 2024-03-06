package me.ma1de.core.rank;

import java.util.List;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import lombok.Getter;
import me.ma1de.core.Core;

@Getter
public class RankHandler {
    private final List<Rank> ranks = Lists.newArrayList();
    private final MongoCollection<Document> collection = Core.INSTANCE.getDatabase().getCollection("ranks");

    public Rank getRank(String id) {
        return ranks.stream().filter(rank -> rank.getId().equalsIgnoreCase(id)).findAny().orElse(null);
    }

    public void addRank(Rank rank) {
        if (ranks.contains(rank)) {
            return;
        }

        if (collection.find(Filters.eq("id", rank.getId())).first() != null) {
            return;
        }

        ranks.add(rank);
        collection.insertOne(rank.toBson());
    }

    public void removeRank(String id) {
        if (getRank(id) == null) {
            return;
        }

        if (collection.find(Filters.eq("id", id)).first() == null) {
            return;
        }

        ranks.remove(getRank(id));
        collection.findOneAndDelete(Filters.eq("id", id));
    }

    public void load() {
        for (Document doc : collection.find()) {
            addRank(Core.INSTANCE.getGson().fromJson(doc.toJson(), new TypeToken<Rank>() {}.getType()));
        }
    }
}
