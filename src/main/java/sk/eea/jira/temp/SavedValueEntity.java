package sk.eea.jira.temp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SavedValueEntity {
    @Id
    private String clientKey;
    private String value;

    public SavedValueEntity() {
    }

    public SavedValueEntity(String clientKey, String value) {
        this.clientKey = clientKey;
        this.value = value;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
