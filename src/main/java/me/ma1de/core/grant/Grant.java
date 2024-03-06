package me.ma1de.core.grant;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.core.rank.Rank;

@Getter @AllArgsConstructor
public class Grant {
    private UUID grantedTo, grantedBy, removedBy;
    private Rank rank;
    private String reason, removedReason;
    private long expireAt, grantedAt, removedAt;

    public boolean isRemoved() {
        return (System.currentTimeMillis() - expireAt >= 0L) || (removedAt != 0) || (removedBy != null);
    }
}
