package com.tac.search_simple.service;


import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.entity.ConfluencePage;
import com.tac.search_simple.entity.JiraTicket;
import com.tac.search_simple.mappers.ConfluencePageMapper;
import com.tac.search_simple.mappers.JiraTicketMapper;
import com.tac.search_simple.repo.ConfluencePageRepo;
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
             confluencePageRepo.save(confluencePage);
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

    @Transactional
    public Map<String, ConfluencePageDTO> updateById(UUID uniqueId, ConfluencePageDTO confluencePageDTO) {
        Map<String, ConfluencePageDTO> map = new HashMap<>();

        ConfluencePage existingConfluenceTicket = confluencePageRepo.findByUniqueId(uniqueId)
                .orElseThrow(() -> new RuntimeException("Confluence Ticket Not Found with given: " + uniqueId));

        boolean isUpdated = false;

        if (confluencePageDTO.getTitle() != null&&
                !confluencePageDTO.getTitle().trim().equals(existingConfluenceTicket.getTitle().trim())) {
            existingConfluenceTicket.setTitle(confluencePageDTO.getTitle().trim());
            isUpdated = true;
        }

        if (confluencePageDTO.getDescription() != null &&
                !confluencePageDTO.getDescription().trim().equals(existingConfluenceTicket.getDescription().trim())) {
            existingConfluenceTicket.setDescription(confluencePageDTO.getDescription().trim());
            isUpdated = true;
        }

        if (confluencePageDTO.getContent() != null &&
                !confluencePageDTO.getContent().trim().equals(existingConfluenceTicket.getContent().trim())) {
            existingConfluenceTicket.setContent(confluencePageDTO.getContent().trim());
            isUpdated = true;
        }

        if (confluencePageDTO.getId() != null &&
                !confluencePageDTO.getId().trim().equals(existingConfluenceTicket.getId().trim())) {
            existingConfluenceTicket.setId(confluencePageDTO.getId().trim());
            isUpdated = true;
        }

        //Fix: Convert `List` to `Set` before comparison to avoid unnecessary updates
        if (confluencePageDTO.getTags() != null &&
                !new HashSet<>(confluencePageDTO.getTags()).equals(new HashSet<>(existingConfluenceTicket.getTags()))) {
            existingConfluenceTicket.setTags(confluencePageDTO.getTags());
            isUpdated = true;
        }

        if (confluencePageDTO.getLinks() != null) {
            // Convert links to DTO form for proper comparison
            Set<ConfluencePageDTO.LinkDTO> existingLinks = existingConfluenceTicket.getLinks().stream()
                    .map(ConfluencePageMapper::toLinkDTO)
                    .collect(Collectors.toSet());

            Set<ConfluencePageDTO.LinkDTO> newLinks = confluencePageDTO.getLinks().stream()
                    .map(ConfluencePageMapper::toLinkDTO)
                    .collect(Collectors.toSet());

            if (!existingLinks.equals(newLinks)) {
                existingConfluenceTicket.setLinks(confluencePageDTO.getLinks().stream()
                        .map(ConfluencePageMapper::toLinkEntity)
                        .collect(Collectors.toList()));
                isUpdated = true;
            }
        }

        if (isUpdated) {
            log.info("Value is updated: {}", isUpdated);
            existingConfluenceTicket.setUpdatedAt(ZonedDateTime.now());
            map.put("Confluence Ticket Updated Successfully with id: " + confluencePageDTO.getId(),
                    ConfluencePageMapper.toDTO(confluencePageRepo.save(existingConfluenceTicket)));
        } else {
            map.put("Confluence Ticket Not Updated with id: " + confluencePageDTO.getId(),
                    ConfluencePageMapper.toDTO(existingConfluenceTicket));
        }

        return map;
    }

//    @Transactional
//    public Map<String,ConfluencePageDTO> updateById(UUID uniqueId, ConfluencePageDTO confluencePageDTO) {
//          Map<String,ConfluencePageDTO> map=new HashMap<>();
//        ConfluencePage existingConfluenceTicket =confluencePageRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Confluence Ticket Not Found with given:"+uniqueId));
//        boolean isUpdated =false;
//        if(existingConfluenceTicket!=null){
//
//            if(!Objects.equals(confluencePageDTO.getTitle().trim(),existingConfluenceTicket.getTitle().trim())&&confluencePageDTO.getTitle()!=null&& !confluencePageDTO.getTitle().isEmpty()){
//                existingConfluenceTicket.setTitle(confluencePageDTO.getTitle());
//                isUpdated =true;
//            }
//            if(!Objects.equals(confluencePageDTO.getDescription().trim(),existingConfluenceTicket.getDescription().trim())&&confluencePageDTO.getDescription()!=null&& !confluencePageDTO.getDescription().isEmpty()){
//                existingConfluenceTicket.setDescription(confluencePageDTO.getDescription());
//                isUpdated=true;
//            }
//            if(!Objects.equals(confluencePageDTO.getContent().trim(),existingConfluenceTicket.getContent().trim())&&confluencePageDTO.getContent()!=null&&!confluencePageDTO.getContent().isEmpty()){
//                existingConfluenceTicket.setContent(confluencePageDTO.getContent());
//                isUpdated=true;
//            }
//            if(!Objects.equals(confluencePageDTO.getId().trim(),existingConfluenceTicket.getId().trim())&&confluencePageDTO.getId()!=null&&!confluencePageDTO.getId().isEmpty()){
//                existingConfluenceTicket.setId(confluencePageDTO.getId());
//                isUpdated=true;
//            }
//            if(!Objects.equals(new HashSet<>(confluencePageDTO.getTags()),new HashSet<>(existingConfluenceTicket.getTags())) && confluencePageDTO.getTags()!=null && !confluencePageDTO.getTags().isEmpty()){
//                existingConfluenceTicket.setTags(confluencePageDTO.getTags());
//                isUpdated=true;
//            }
//            if(!Objects.equals(new HashSet<>(confluencePageDTO.getLinks()),new HashSet<>(existingConfluenceTicket.getLinks()))&&confluencePageDTO.getLinks()!=null && !confluencePageDTO.getLinks().isEmpty()){
//                existingConfluenceTicket.setLinks(confluencePageDTO.getLinks().stream().map(ConfluencePageMapper::toLinkEntity).collect(Collectors.toList()));
//                isUpdated=true;
//            }
//            if(isUpdated){
//                log.info("value is updated to{}",isUpdated);
//                existingConfluenceTicket.setUpdatedAt(ZonedDateTime.now());
//                map.put("Confluence Ticket Updated Succcessfully with id:"+confluencePageDTO.getId(),ConfluencePageMapper.toDTO(confluencePageRepo.save(existingConfluenceTicket)));
//                isUpdated=false;
//            }else{
//                map.put("No changes detected,Confluence Ticket Not Updated with id:"+confluencePageDTO.getId(),ConfluencePageMapper.toDTO(existingConfluenceTicket));
//            }
//        }
//
//        return map;
//    }

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
                  existingConfluencePage.setTags(confluencePageDTO.getTags());
                  isUpdated=true;
              }
              if(!existingConfluencePage.getLinks().equals(confluencePageDTO.getLinks())){
               existingConfluencePage.setLinks(confluencePageDTO.getLinks().stream().map(ConfluencePageMapper::toLinkEntity).collect(Collectors.toList()));
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
                confluencePage.setTags(confluencePageDTO.getTags());
                confluencePage.setLinks(confluencePageDTO.getLinks().stream().map(ConfluencePageMapper::toLinkEntity).collect(Collectors.toList()));
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
