package sk.eea.jira.temp;

import org.springframework.data.repository.CrudRepository;

public interface SavedValueRepository extends CrudRepository<SavedValueEntity, String> {
}
