package com.tac.search_simple.dto;

import java.util.List;
import java.util.UUID;

public class BulkUpdateRequestConfluence {
    private List<ConfluencePageDTO>confluencePageDTOList;
    private List<UUID>uniqueIds;

    public List<UUID> getUniqueIds() {
        return uniqueIds;
    }

    public void setUniqueIds(List<UUID> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    public List<ConfluencePageDTO> getConfluencePageDTOList() {
        return confluencePageDTOList;
    }

    public void setConfluencePageDTOList(List<ConfluencePageDTO> confluencePageDTOList) {
        this.confluencePageDTOList = confluencePageDTOList;
    }
}
