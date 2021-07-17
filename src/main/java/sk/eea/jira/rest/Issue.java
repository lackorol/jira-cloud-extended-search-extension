package sk.eea.jira.rest;

public record Issue(String assignee,
                    String created,
                    String reporter,
                    String resolution,
                    String status,
                    String summary,
                    String updated,
                    String key) {
}


