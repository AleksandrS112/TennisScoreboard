package service;

import dto.ActiveMatch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OngoingMatchesService {

    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private final Map<UUID, ActiveMatch> activeMatches = new HashMap<>();

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }

    public Optional<ActiveMatch> getActiveMatch(UUID uuid) {
        return Optional.ofNullable(activeMatches.get(uuid));
    }

    public void addActiveMatch(UUID uuid, ActiveMatch activeMatch) {
        activeMatches.put(uuid, activeMatch);
    }

    public void deleteActiveMatch(UUID uuid) {
        activeMatches.remove(uuid);
    }

}
