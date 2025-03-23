package com.tac.search_simple.service;


import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.entity.ConfluencePage;
import com.tac.search_simple.entity.JiraTicket;
import com.tac.search_simple.entity.Link;
import com.tac.search_simple.entity.Tag;
import com.tac.search_simple.mappers.ConfluencePageMapper;
import com.tac.search_simple.mappers.JiraTicketMapper;
import com.tac.search_simple.repo.ConfluencePageRepo;
import com.tac.search_simple.repo.TagRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfluencePageService {

    private final ConfluencePageRepo confluencePageRepo;
    private final TagRepo tagRepo;

      @Transactional
    public Map<String,ConfluencePageDTO>  create(ConfluencePageDTO confluencePageDTO) {
          ConfluencePage   confluencePage=null;
         Map<String,ConfluencePageDTO>map= new HashMap<>();
         if(confluencePageDTO!=null){
//             confluencePage = new ConfluencePage();
//             confluencePage.setDescription(confluencePageDTO.getDescription());
//             confluencePage.setTitle(confluencePageDTO.getTitle());
//             confluencePage.setContent(confluencePageDTO.getContent());
//             confluencePage.setCreatedAt(ZonedDateTime.now());
//             confluencePage.setId(confluencePageDTO.getId());
             confluencePage=ConfluencePageMapper.toEntity(confluencePageDTO);
             confluencePage.setUniqueId(UUID.randomUUID());
             // Log the state of tags
             log.info("Tags before save: {}", confluencePage.getTags());
             confluencePageRepo.save(confluencePage);
             // Log the state of tags after save
             log.info("Tags after save: {}", confluencePage.getTags());
         }else{
             throw new IllegalArgumentException("Confluence Page DTO is null");
         }
        map.put("Confluence Page Ticket Created Successfully ",ConfluencePageMapper.toDTO(confluencePage));
          return map;
    }

    public Map<String,ConfluencePageDTO> getById(UUID uniqueId) {
         Map<String,ConfluencePageDTO>map=new HashMap();
     ConfluencePage result=confluencePageRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("confluence Page Not Found with given:"+uniqueId));
//        System.out.println(result);
        map.put("Confluence Page Ticket Found Successfully with id:"+result.getId(),ConfluencePageMapper.toDTO(result));
     return map;
    }

    public Map<String,List<ConfluencePageDTO>> getAll() {
        Map<String,List<ConfluencePageDTO>>map=new HashMap();
       List<ConfluencePageDTO> result=confluencePageRepo.findAll().stream().map(ConfluencePageMapper::toDTO).collect(Collectors.toList());
        map.put("All Confluence Tickets fetched Successfully",result);
       return  map;
    }

//    @Transactional
//    public Map<String, ConfluencePageDTO> updateById(UUID uniqueId, ConfluencePageDTO confluencePageDTO) {
//        Map<String, ConfluencePageDTO> map = new HashMap<>();
//
//        ConfluencePage existingConfluenceTicket = confluencePageRepo.findByUniqueId(uniqueId)
//                .orElseThrow(() -> new RuntimeException("Confluence Ticket Not Found with given: " + uniqueId));
//
//        boolean isUpdated = false;
//
//        if (confluencePageDTO.getTitle() != null&&
//                !confluencePageDTO.getTitle().trim().equals(existingConfluenceTicket.getTitle().trim())) {
//            existingConfluenceTicket.setTitle(confluencePageDTO.getTitle().trim());
//            isUpdated = true;
//        }
//
//        if (confluencePageDTO.getDescription() != null &&
//                !confluencePageDTO.getDescription().trim().equals(existingConfluenceTicket.getDescription().trim())) {
//            existingConfluenceTicket.setDescription(confluencePageDTO.getDescription().trim());
//            isUpdated = true;
//        }
//
//        if (confluencePageDTO.getContent() != null &&
//                !confluencePageDTO.getContent().trim().equals(existingConfluenceTicket.getContent().trim())) {
//            existingConfluenceTicket.setContent(confluencePageDTO.getContent().trim());
//            isUpdated = true;
//        }
//
//        if (confluencePageDTO.getId() != null &&
//                !confluencePageDTO.getId().trim().equals(existingConfluenceTicket.getId().trim())) {
//            existingConfluenceTicket.setId(confluencePageDTO.getId().trim());
//            isUpdated = true;
//        }
//
//        //Fix: Convert `List` to `Set` before comparison to avoid unnecessary updates
//        if (confluencePageDTO.getTags() != null &&
//                !new HashSet<>(confluencePageDTO.getTags()).equals(new HashSet<>(existingConfluenceTicket.getTags()))) {
//            existingConfluenceTicket.setTags(confluencePageDTO.getTags());
//            isUpdated = true;
//        }
//
//        if (confluencePageDTO.getLinks() != null) {
//            // Convert links to DTO form for proper comparison
//            Set<ConfluencePageDTO.LinkDTO> existingLinks = existingConfluenceTicket.getLinks().stream()
//                    .map(ConfluencePageMapper::toLinkDTO)
//                    .collect(Collectors.toSet());
//
//            Set<ConfluencePageDTO.LinkDTO> newLinks = confluencePageDTO.getLinks().stream()
//                    .map(link ->ConfluencePageMapper.toLinkDTO(link))
//                    .collect(Collectors.toSet());
//
//            if (!existingLinks.equals(newLinks)) {
//                existingConfluenceTicket.setLinks(confluencePageDTO.getLinks().stream()
//                        .map(ConfluencePageMapper::toLinkEntity)
//                        .collect(Collectors.toList()));
//                isUpdated = true;
//            }
//        }
//
//        if (isUpdated) {
//            log.info("Value is updated: {}", isUpdated);
//            existingConfluenceTicket.setUpdatedAt(ZonedDateTime.now());
//            map.put("Confluence Ticket Updated Successfully with id: " + confluencePageDTO.getId(),
//                    ConfluencePageMapper.toDTO(confluencePageRepo.save(existingConfluenceTicket)));
//        } else {
//            map.put("Confluence Ticket Not Updated with id: " + confluencePageDTO.getId(),
//                    ConfluencePageMapper.toDTO(existingConfluenceTicket));
//        }
//
//        return map;
//    }

//    @Transactional
//    public Map<String,ConfluencePageDTO> updateById(UUID uniqueId, ConfluencePageDTO confluencePageDTO) {
//          Map<String,ConfluencePageDTO> map=new HashMap<>();
//        ConfluencePage existingConfluenceTicket =confluencePageRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Confluence Ticket Not Found with given:"+uniqueId));
//        boolean isUpdated =false;
//
//
//            if(isDifferent(confluencePageDTO.getTitle(),existingConfluenceTicket.getTitle())){
//                existingConfluenceTicket.setTitle(confluencePageDTO.getTitle().trim());
//                isUpdated =true;
//            }
//            if(isDifferent(confluencePageDTO.getDescription(),existingConfluenceTicket.getDescription())){
//                existingConfluenceTicket.setDescription(confluencePageDTO.getDescription().trim());
//                isUpdated=true;
//            }
//            if(isDifferent(confluencePageDTO.getContent(),existingConfluenceTicket.getContent())){
//                existingConfluenceTicket.setContent(confluencePageDTO.getContent().trim());
//                isUpdated=true;
//            }
//            if(isDifferent(confluencePageDTO.getId(),existingConfluenceTicket.getId())){
//                existingConfluenceTicket.setId(confluencePageDTO.getId());
//                isUpdated=true;
//            }
////            if(!Objects.equals(new HashSet<>(confluencePageDTO.getTags()),new HashSet<>(existingConfluenceTicket.getTags())) && (confluencePageDTO.getTags()!=null && !confluencePageDTO.getTags().isEmpty())){
////                List<Tag>updatedTags=confluencePageDTO.getTags().stream().map(tag->{
////                    Tag tagEntity = new Tag();
////                    tagEntity.setTag(tag);
////                    return tagRepo.save(tagEntity);
////                }).collect(Collectors.toList());
////
////                existingConfluenceTicket.setTags(updatedTags);
////                        isUpdated=true;
////            }
////            =====updating tags====
//        if (confluencePageDTO.getTags() != null && !confluencePageDTO.getTags().isEmpty()) {
//            Set<String> newTags = new HashSet<>(confluencePageDTO.getTags());
//
//            // Remove old tags that are not in the new list
//            existingConfluenceTicket.getTags().removeIf(tag -> !newTags.contains(tag.getTag()));
//
//            // Add new tags if they don’t already exist
//            for (String tagStr : newTags) {
//                if (existingConfluenceTicket.getTags().stream().noneMatch(tag -> tag.getTag().equals(tagStr))) {
//                    Tag newTag = new Tag();
//                    newTag.setTagId(UUID.randomUUID());
//                    newTag.setTag(tagStr);
////                    newTag.setConfluencePage(existingConfluenceTicket);
//                    existingConfluenceTicket.addTag(newTag);
//                }
//            }
//            isUpdated = true;
//        }
//
////            if(!Objects.equals(new HashSet<>(confluencePageDTO.getLinks()),new HashSet<>(existingConfluenceTicket.getLinks()))&&(confluencePageDTO.getLinks()!=null && !confluencePageDTO.getLinks().isEmpty())){
////                existingConfluenceTicket.setLinks(confluencePageDTO.getLinks().stream().map(linkDTO ->ConfluencePageMapper.toLinkEntity(linkDTO,existingConfluenceTicket)).collect(Collectors.toList()));
////                isUpdated=true;
////            }
//
//        // Update links (modify existing collection instead of replacing it)
//        if (confluencePageDTO.getLinks() != null && !confluencePageDTO.getLinks().isEmpty()) {
//            Set<ConfluencePageDTO.LinkDTO> newLinks = new HashSet<>(confluencePageDTO.getLinks());
//
//            // Remove old links that are not in the new list
//            existingConfluenceTicket.getLinks().removeIf(link -> newLinks.stream()
//                    .noneMatch(linkDTO -> linkDTO.getId().equals(link.getId())));
//
//            // Add new links if they don’t already exist
//            for (ConfluencePageDTO.LinkDTO linkDTO : newLinks) {
//                if (existingConfluenceTicket.getLinks().stream().noneMatch(link -> link.getId().equals(linkDTO.getId()))) {
//                    Link newLink = ConfluencePageMapper.toLinkEntity(linkDTO, existingConfluenceTicket);
//                    existingConfluenceTicket.addLink(newLink);
//                }
//            }
//            isUpdated = true;
//        }
//
//            if(isUpdated){
//                log.info("value is updated to{}",isUpdated);
//                existingConfluenceTicket.setUpdatedAt(ZonedDateTime.now());
//                map.put("Confluence Ticket Updated Succcessfully with id:"+confluencePageDTO.getId(),ConfluencePageMapper.toDTO(confluencePageRepo.save(existingConfluenceTicket)));
//                isUpdated=false;
//            }else{
//                map.put("No changes detected,Confluence Ticket Not Updated with id:"+confluencePageDTO.getId(),ConfluencePageMapper.toDTO(existingConfluenceTicket));
//            }
//
//
//        return map;
//    }
//    ======updated===
@Transactional
public Map<String, ConfluencePageDTO> updateById(UUID uniqueId, ConfluencePageDTO confluencePageDTO) {
    Map<String, ConfluencePageDTO> map = new HashMap<>();
    ConfluencePage existingConfluenceTicket = confluencePageRepo.findByUniqueId(uniqueId)
            .orElseThrow(() -> new RuntimeException("Confluence Ticket Not Found with given: " + uniqueId));
    boolean isUpdated = false;

    // Update fields if they are different
    if (isDifferent(confluencePageDTO.getTitle(), existingConfluenceTicket.getTitle())) {
        existingConfluenceTicket.setTitle(confluencePageDTO.getTitle().trim());
        isUpdated = true;
    }
    if (isDifferent(confluencePageDTO.getDescription(), existingConfluenceTicket.getDescription())) {
        existingConfluenceTicket.setDescription(confluencePageDTO.getDescription().trim());
        isUpdated = true;
    }
    if (isDifferent(confluencePageDTO.getContent(), existingConfluenceTicket.getContent())) {
        existingConfluenceTicket.setContent(confluencePageDTO.getContent().trim());
        isUpdated = true;
    }
    if (isDifferent(confluencePageDTO.getId(), existingConfluenceTicket.getId())) {
         Optional<ConfluencePage>existingPageWithId=confluencePageRepo.findById(confluencePageDTO.getId());
         if(existingPageWithId.isPresent()){
             throw new IllegalArgumentException("Confluence Ticket with given id already exists hence no duplicate entries are allowed");
         }
//         if(confluencePageDTO.getId()!=null  && !confluencePageDTO.getId().trim().isEmpty()){
             existingConfluenceTicket.setId(confluencePageDTO.getId());
             isUpdated = true;
//         }
//         else{
//             existingConfluenceTicket.setId(existingPageWithId.get().getId());
//         }

    }

    // Update tags (modify existing collection instead of replacing it)
    if (confluencePageDTO.getTags() != null && !confluencePageDTO.getTags().isEmpty()) {
        Set<String> newTags = new HashSet<>(confluencePageDTO.getTags());

        // Remove old tags that are not in the new list
        existingConfluenceTicket.getTags().removeIf(tag -> !newTags.contains(tag.getTag()));

        // Add new tags if they don’t already exist
//        for (String tagStr : newTags) {
//            if (existingConfluenceTicket.getTags().stream().noneMatch(tag -> tag.getTag().equals(tagStr))) {

                // Check if the tag already exists in the database
//                Optional<Tag> existingTag = tagRepo.findByTag(tagStr);
//                Tag newTag;
//                if (existingTag.isPresent()) {
//                    // Reuse the existing tag
//                    newTag = existingTag.get();
//                } else {
//                    // Create a new tag with a new UUID
//                    newTag = new Tag();
//                    newTag.setTagId(UUID.randomUUID()); // Generate UUID for new tag
//                    newTag.setTag(tagStr);
//                }

//                existingConfluenceTicket.addTag(newTag); // Maintain bidirectional relationship

//            }

//        }

        for (String tagStr : newTags) {
            if (existingConfluenceTicket.getTags().stream().noneMatch(tag -> tag.getTag().equals(tagStr))) {
                // Create a new tag with a new UUID
                Tag newTag = new Tag();
                newTag.setTagId(UUID.randomUUID()); // Generate UUID for new tag
                newTag.setTag(tagStr);
                existingConfluenceTicket.addTag(newTag); // Maintain bidirectional relationship
            }
        }
        isUpdated = true;
    }

    // Update links (modify existing collection instead of replacing it)
    if (confluencePageDTO.getLinks() != null && !confluencePageDTO.getLinks().isEmpty()) {
        Set<ConfluencePageDTO.LinkDTO> newLinks = new HashSet<>(confluencePageDTO.getLinks());

        // Remove old links that are not in the new list
        existingConfluenceTicket.getLinks().removeIf(link -> newLinks.stream()
                .noneMatch(linkDTO -> linkDTO.getId().equals(link.getId())));

        // Add new links if they don’t already exist
        for (ConfluencePageDTO.LinkDTO linkDTO : newLinks) {
            Optional<Link>existingLinkOpt=existingConfluenceTicket.getLinks().stream().filter(link ->link.getId().equals(linkDTO.getId())).findFirst();
            if(existingLinkOpt.isPresent()){
                // Update existing link
                Link existingLink = existingLinkOpt.get();
                existingLink.setType(linkDTO.getType());
                existingLink.setTitle(linkDTO.getTitle());
                existingLink.setReporter(linkDTO.getReporter());
            }
            //add new link
            if (existingConfluenceTicket.getLinks().stream().noneMatch(link -> link.getId().equals(linkDTO.getId()))) {
                Link newLink = ConfluencePageMapper.toLinkEntity(linkDTO);
                existingConfluenceTicket.addLink(newLink);
            }
        }
        isUpdated = true;
    }

    // Save the updated entity if changes were made
    if (isUpdated) {
        log.info("value is updated to {}", isUpdated);
        existingConfluenceTicket.setUpdatedAt(ZonedDateTime.now());
        map.put("Confluence Ticket Updated Successfully with id: " + existingConfluenceTicket.getId(),
                ConfluencePageMapper.toDTO(confluencePageRepo.save(existingConfluenceTicket)));
    } else {
        map.put("No changes detected, Confluence Ticket Not Updated with id: " + existingConfluenceTicket.getId(),
                ConfluencePageMapper.toDTO(existingConfluenceTicket));
    }

    return map;
}
    private boolean isDifferent(String newValue,String oldValue){
          return newValue!=null && !newValue.trim().isEmpty() && !newValue.trim().equals(oldValue.trim());
    }

    @Transactional
    public Map<String,String> deleteById(UUID uniqueId) {
        Map<String,String>map= new HashMap<>();
        ConfluencePage result=confluencePageRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Confluence Ticket Not Found with given:"+uniqueId));
        confluencePageRepo.delete(result);
//        confluencePageRepo.deleteByUniqueId(uniqueId);
        map.put("Confluence Ticket Deleted Successfully with id",result.getId());
        return map;

    }

    @Transactional
    public Map<String,List<ConfluencePageDTO>> bulkUpdate(List<ConfluencePageDTO>confluencePageDTOList,List<UUID>uniqueIds){
         Map<String,List<ConfluencePageDTO>> map=new HashMap<>();
        List<ConfluencePage> existingConfluences =confluencePageRepo.findByUniqueIds(uniqueIds);
          if(uniqueIds.size()!=confluencePageDTOList.size()){
              throw new IllegalArgumentException("Mismatch between uniqueIds and confluencePageDTOList size");
          }
          boolean isAnyUpdated=true;
          for(int i=0;i<existingConfluences.size();i++){
              boolean isUpdated=false;
              ConfluencePage existingConfluencePage=existingConfluences.get(i);
              ConfluencePageDTO confluencePageDTO=confluencePageDTOList.get(i);
              if(!existingConfluencePage.getId().equals(confluencePageDTO.getId())){
                  existingConfluencePage.setId(confluencePageDTO.getId());
                  isUpdated=true;
              }
              if(!existingConfluencePage.getDescription().equals(confluencePageDTO.getDescription())){
                  existingConfluencePage.setDescription(confluencePageDTO.getDescription());
                  isUpdated=true;
              }
              if(!existingConfluencePage.getTitle().equals(confluencePageDTO.getTitle())){
                  existingConfluencePage.setTitle(confluencePageDTO.getTitle());
                  isUpdated=true;
              }
              if(!existingConfluencePage.getContent().equals(confluencePageDTO.getContent())){
                  existingConfluencePage.setContent(confluencePageDTO.getContent());
                  isUpdated=true;
              }
              if(!existingConfluencePage.getTags().equals(confluencePageDTO.getTags())){
                  existingConfluencePage.setTags(confluencePageDTO.getTags().stream().map(tag->{
                     Tag tagEntity= new Tag();
                      tagEntity.setTag(tag);
                      return tagEntity;
                  }).toList());
                  isUpdated=true;
              }
              if(!existingConfluencePage.getLinks().equals(confluencePageDTO.getLinks())){
               existingConfluencePage.setLinks(confluencePageDTO.getLinks().stream().map(linkDTO->ConfluencePageMapper.toLinkEntity(linkDTO)).collect(Collectors.toList()));
                  isUpdated=true;
              }
              if(isUpdated) {
                  existingConfluencePage.setUpdatedAt(ZonedDateTime.now());
              }else{
                  isAnyUpdated=false;
              }
          }
          if(isAnyUpdated){
              confluencePageRepo.saveAll(existingConfluences);
              map.put("All Confluence Tickets Updated Successfully",existingConfluences.stream().map(ConfluencePageMapper::toDTO).collect(Collectors.toList()));
          }else{
              map.put("Confluence Tickets Not Updated Make Sure That All Tickets Should be Updated",confluencePageDTOList);
          }

          return map;
    }

    @Transactional
    public Map<String,List<ConfluencePageDTO>> bulkCreate(List<ConfluencePageDTO>confluencePageDTOList){
          Map<String,List<ConfluencePageDTO>>map=new HashMap<>();
        List<ConfluencePage>confluencePageList=confluencePageDTOList.stream().map(confluencePageDTO -> {
            ConfluencePage confluencePage = new ConfluencePage();
            if(confluencePageDTO!=null) {
                confluencePage.setId(confluencePageDTO.getId());
                confluencePage.setContent(confluencePageDTO.getContent());
                confluencePage.setDescription(confluencePageDTO.getDescription());
                confluencePage.setTitle(confluencePageDTO.getTitle());
                confluencePage.setCreatedAt(ZonedDateTime.now());
                confluencePage.setUniqueId(UUID.randomUUID());
                confluencePage.setTags(confluencePageDTO.getTags().stream().map(tag->{
                    Tag tagEntity=new Tag();
                    tagEntity.setTag(tag);
                    return tagEntity;
                }).toList());
                confluencePage.setLinks(confluencePageDTO.getLinks().stream().map(linkDTO->ConfluencePageMapper.toLinkEntity(linkDTO)).collect(Collectors.toList()));
            }
            return confluencePage;
        }).collect(Collectors.toList());

        confluencePageRepo.saveAll(confluencePageList);
        map.put("All Confluence Tickets Created Successfully",confluencePageList.stream().map(confluencePage -> ConfluencePageMapper.toDTO(confluencePage)).collect(Collectors.toList()));
        return map;
    }

    @Transactional
    public Map<String,List<String>> bulkDelete(List<UUID>uniqueIds){
          Map<String,List<String>> map=new HashMap<>();
        List<ConfluencePage>confluencePageList=uniqueIds.stream().map(uniqueId ->confluencePageRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Confluence Ticket Not Found for given id:"+uniqueId)))
                .collect(Collectors.toList());
        confluencePageRepo.deleteAll(confluencePageList);
        List<String>listofIds=confluencePageList.stream()
                .map(confluencePage ->confluencePage.getId()).collect(Collectors.toList());
        map.put("All Confluence Tickets DeletedSuccessfully with ids:",listofIds);
        return map;
    }


}


