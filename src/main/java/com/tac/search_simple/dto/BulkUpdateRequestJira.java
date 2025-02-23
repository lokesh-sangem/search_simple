package com.tac.search_simple.dto;

import java.util.List;
import java.util.UUID;


public class BulkUpdateRequestJira {
    private List<JiraTicketDTO> jiraTicketDTOList;
    private List<UUID>uniqueIds;

    public List<JiraTicketDTO> getJiraTicketDTOList() {
        return jiraTicketDTOList;
    }

    public void setJiraTicketDTOList(List<JiraTicketDTO> jiraTicketDTOList) {
        this.jiraTicketDTOList = jiraTicketDTOList;
    }

    public List<UUID> getUniqueIds() {
        return uniqueIds;
    }

    public void setUniqueIds(List<UUID> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }
}
