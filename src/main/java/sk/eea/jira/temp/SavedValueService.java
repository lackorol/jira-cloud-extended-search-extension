package sk.eea.jira.temp;

import java.util.Optional;

public interface SavedValueService {
    Optional<SavedValueEntity> getByClientKey(String clientKey);
    void setForClientKey(String clientKey, String value);
}
