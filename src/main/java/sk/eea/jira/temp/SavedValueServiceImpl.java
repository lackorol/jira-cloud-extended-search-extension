package sk.eea.jira.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SavedValueServiceImpl implements SavedValueService {
    @Autowired
    SavedValueRepository savedValueRepository;

    @Override
    public Optional<SavedValueEntity> getByClientKey(String clientKey) {
        return savedValueRepository.findById(clientKey);
    }

    @Override
    public void setForClientKey(String clientKey, String value) {
        SavedValueEntity entity = new SavedValueEntity(clientKey, value);
        savedValueRepository.save(entity);
    }
}
