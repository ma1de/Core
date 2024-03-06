package me.ma1de.core.profile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.core.Core;
import me.ma1de.core.grant.Grant;
import me.ma1de.core.rank.Rank;

@Getter @AllArgsConstructor
public class Profile {
    private UUID uuid; 
    private List<Grant> grants; 
    private long lastLoggedIn;
    
    public Rank getActiveRank() {
        if (grants.isEmpty()) {
            return null;
        }

        List<Grant> activeGrants = grants.stream().filter(grant -> !grant.isRemoved()).collect(Collectors.toList());

        if (activeGrants.isEmpty()) {
            return null;
        }

        int maxWeight = -1;
        Grant maxWeightGrant = null;

        for (Grant grant : activeGrants) {
            if (grant.getRank().getWeight() > maxWeight) {
                maxWeight = grant.getRank().getWeight();
                maxWeightGrant = grant;
            }
        }

        return maxWeightGrant.getRank();
    }

    public Document toBson() {
        return Document.parse(Core.INSTANCE.getGson().toJson(this));
    }
}
