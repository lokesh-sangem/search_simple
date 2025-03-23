package com.tac.search_simple.mappers;

import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.entity.ConfluencePage;
import com.tac.search_simple.entity.Link;
import com.tac.search_simple.entity.Tag;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
            List<String>tagList=confluencePage.getTags().stream().map(tag->tag.getTag()).collect(Collectors.toList());
            confluencePageDTO.setTags(tagList);
            List<ConfluencePageDTO.LinkDTO>linkDTOList =confluencePage.getLinks().stream().map(link->toLinkDTO(link)).collect(Collectors.toList());
            confluencePageDTO.setLinks(linkDTOList );
        }
        return confluencePageDTO;

    }

    public static ConfluencePage toEntity(ConfluencePageDTO confluencePageDTO){
        ConfluencePage confluencePage = new ConfluencePage();
        if(confluencePageDTO!=null){
            if(confluencePageDTO.getId()!=null && !confluencePageDTO.getId().trim().isEmpty()){
                confluencePage.setId(confluencePageDTO.getId());
            }
            confluencePage.setDescription(confluencePageDTO.getDescription());
            confluencePage.setContent(confluencePageDTO.getContent());
            confluencePage.setTitle(confluencePageDTO.getTitle());
            confluencePage.setCreatedAt(ZonedDateTime.now());
//            confluencePage.setUpdatedAt(confluencePageDTO.getUpdatedAt());
//            Set<String>existingTags = new HashSet<>();
            if(confluencePageDTO.getTags()!=null && !confluencePageDTO.getTags().isEmpty()){
                for(String tagStr:confluencePageDTO.getTags()){
                    if(tagStr!=null && !tagStr.trim().isEmpty()){
                        Tag tagEntity = new Tag();
                        tagEntity.setTagId(UUID.randomUUID());
                        tagEntity.setTag(tagStr);
//                        tagEntity.setConfluencePage(confluencePage);
                        confluencePage.addTag(tagEntity);
                    }
                }
            }
//            List<Tag>tags=confluencePageDTO.getTags().stream().filter(tagStr ->tagStr!=null&& !tagStr.trim().isEmpty()).map(tagStr->{
//
//                Tag tagEntity = new Tag();
//                tagEntity.setTagId(UUID.randomUUID());
//                tagEntity.setTag(tagStr);
//                tagEntity.setConfluencePage(confluencePage);
////                existingTags.add(tagStr);
//                return tagEntity;
//
//            }).collect(Collectors.toList());
//            confluencePage.setTags(tags);

//            confluencePage.setLinks(confluencePageDTO.getLinks().stream().map(linkDTO->toLinkEntity(linkDTO,confluencePage)).collect(Collectors.toList()));
            if(confluencePageDTO.getLinks()!=null && !confluencePageDTO.getLinks().isEmpty()){
                for(ConfluencePageDTO.LinkDTO linkDTO:confluencePageDTO.getLinks()){
//                 Link link = toLinkEntity(linkDTO,confluencePage);
                    Link link = toLinkEntity(linkDTO);
                 confluencePage.addLink(link);
                }
            }

        }
        return confluencePage;
    }

    public static ConfluencePageDTO.LinkDTO toLinkDTO(Link link){
        ConfluencePageDTO.LinkDTO linkDTO = new ConfluencePageDTO.LinkDTO();
        linkDTO.setType(link.getType());
        linkDTO.setId(link.getId());
        linkDTO.setTitle(link.getTitle());
        linkDTO.setReporter(link.getReporter());
        return linkDTO;
    }

    public static Link toLinkEntity(ConfluencePageDTO.LinkDTO linkDTO) {

        Link link = new Link();
        link.setLinkId(UUID.randomUUID());
        link.setType(linkDTO.getType());
        link.setId(linkDTO.getId());
        link.setTitle(linkDTO.getTitle());
        link.setReporter(linkDTO.getReporter());
//        link.setConfluencePage(confluencePage);
        return link;
    }

}
