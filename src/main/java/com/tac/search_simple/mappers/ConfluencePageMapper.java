package com.tac.search_simple.mappers;

import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.entity.ConfluencePage;


public class ConfluencePageMapper {

    public static ConfluencePageDTO toDTO(ConfluencePage confluencePage){

        ConfluencePageDTO confluencePageDTO = new ConfluencePageDTO();
        if(confluencePage!=null){
            confluencePageDTO.setId(confluencePage.getId());
            confluencePageDTO.setDescription(confluencePage.getDescription());
            confluencePageDTO.setTitle(confluencePage.getTitle());
            confluencePageDTO.setContent(confluencePage.getContent());
            confluencePageDTO.setCreatedAt(confluencePage.getCreatedAt());
            confluencePageDTO.setUpdatedAt(confluencePage.getUpdatedAt());
        }
        return confluencePageDTO;

    }

    public static ConfluencePage toEntity(ConfluencePageDTO confluencePageDTO){
        ConfluencePage confluencePage = new ConfluencePage();
        if(confluencePageDTO!=null){
            confluencePage.setId(confluencePageDTO.getId());
            confluencePage.setDescription(confluencePageDTO.getDescription());
            confluencePage.setContent(confluencePageDTO.getContent());
            confluencePage.setTitle(confluencePageDTO.getTitle());
            confluencePage.setCreatedAt(confluencePageDTO.getCreatedAt());
            confluencePage.setUpdatedAt(confluencePageDTO.getUpdatedAt());
        }
        return confluencePage;
    }

}
