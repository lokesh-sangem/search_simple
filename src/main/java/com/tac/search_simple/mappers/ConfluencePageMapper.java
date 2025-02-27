package com.tac.search_simple.mappers;

import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.entity.ConfluencePage;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;


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
            confluencePageDTO.setTags(confluencePage.getTags());
            confluencePageDTO.setLinks(confluencePage.getLinks().stream().map(ConfluencePageMapper::toLinkDTO).collect(Collectors.toList()));
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
            confluencePage.setCreatedAt(ZonedDateTime.now());
//            confluencePage.setUpdatedAt(confluencePageDTO.getUpdatedAt());
            confluencePage.setTags(confluencePageDTO.getTags());
            confluencePage.setLinks(confluencePageDTO.getLinks().stream().map(ConfluencePageMapper::toLinkEntity).collect(Collectors.toList()));

        }
        return confluencePage;
    }

    public static ConfluencePageDTO.LinkDTO toLinkDTO(ConfluencePage.Link link){
        ConfluencePageDTO.LinkDTO linkDTO = new ConfluencePageDTO.LinkDTO();
        linkDTO.setType(link.getType());
        linkDTO.setId(link.getId());
        linkDTO.setTitle(link.getTitle());
        linkDTO.setReporter(link.getReporter());
        return linkDTO;
    }

    public static ConfluencePage.Link toLinkEntity(ConfluencePageDTO.LinkDTO linkDTO) {
        ConfluencePage.Link link = new ConfluencePage.Link();
        link.setType(linkDTO.getType());
        link.setId(linkDTO.getId());
        link.setTitle(linkDTO.getTitle());
        link.setReporter(linkDTO.getReporter());
        return link;
    }

}
