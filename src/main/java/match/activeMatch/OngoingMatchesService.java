package match.activeMatch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OngoingMatchesService {

    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private final Map<UUID, ActiveMatchDto> activeMatches = new ConcurrentHashMap<>();

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }

    public Optional<ActiveMatchDto> getActiveMatch(UUID uuid) {
        return Optional.ofNullable(activeMatches.get(uuid));
    }

    public void addActiveMatch(UUID uuid, ActiveMatchDto activeMatch) {
        activeMatches.put(uuid, activeMatch);
    }

    public void deleteActiveMatch(UUID uuid) {
        activeMatches.remove(uuid);
    }

}
