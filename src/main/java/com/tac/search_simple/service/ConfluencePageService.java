package com.tac.search_simple.service;


import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.entity.ConfluencePage;
import com.tac.search_simple.entity.JiraTicket;
import com.tac.search_simple.mappers.ConfluencePageMapper;
import com.tac.search_simple.mappers.JiraTicketMapper;
import com.tac.search_simple.repo.ConfluencePageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfluencePageService {

    private final ConfluencePageRepo confluencePageRepo;

      @Transactional
    public Map<String,ConfluencePageDTO>  create(ConfluencePageDTO confluencePageDTO) {
          ConfluencePage   confluencePage=null;
         Map<String,ConfluencePageDTO>map= new HashMap<>();
         if(confluencePageDTO!=null){
             confluencePage = new ConfluencePage();
             confluencePage.setDescription(confluencePageDTO.getDescription());
             confluencePage.setTitle(confluencePageDTO.getTitle());
             confluencePage.setContent(confluencePageDTO.getContent());
             confluencePage.setCreatedAt(ZonedDateTime.now());
             confluencePage.setId(confluencePageDTO.getId());
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
        System.out.println(result);
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
    public Map<String,ConfluencePageDTO> updateById(UUID uniqueId, ConfluencePageDTO confluencePageDTO) {
          Map<String,ConfluencePageDTO> map=new HashMap<>();
        ConfluencePage existingConfluenceTicket =confluencePageRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Confluence Ticket Not Found with given:"+uniqueId));
        if(existingConfluenceTicket!=null){

            if(confluencePageDTO.getTitle()!=null&& !confluencePageDTO.getTitle().isEmpty()){
                existingConfluenceTicket.setTitle(confluencePageDTO.getTitle());
            }
            if(confluencePageDTO.getDescription()!=null&& !confluencePageDTO.getDescription().isEmpty()){
                existingConfluenceTicket.setDescription(confluencePageDTO.getDescription());
            }
            if(confluencePageDTO.getContent()!=null&&!confluencePageDTO.getContent().isEmpty()){
                existingConfluenceTicket.setContent(confluencePageDTO.getContent());
            }
            if(confluencePageDTO.getId()!=null&&!confluencePageDTO.getId().isEmpty()){
                existingConfluenceTicket.setId(confluencePageDTO.getId());
            }
            if(confluencePageDTO.getUpdatedAt()==null) {
                existingConfluenceTicket.setUpdatedAt(ZonedDateTime.now());
            }
        }
        map.put("Confluence Ticket Updated Succcessfully with id:"+confluencePageDTO.getId(),ConfluencePageMapper.toDTO(confluencePageRepo.save(existingConfluenceTicket)));
        return map;
    }

    @Transactional
    public Map<String,String> deleteById(UUID uniqueId) {
//        if(confluencePageRepo.findByUniqueId(uniqueId).isPresent()){
//            confluencePageRepo.deleteByUniqueId(uniqueId);
//            return "Jira Ticket Deleted Sucessfully";
//        }else{
//            return "Jira Ticket not found";
//        }
        Map<String,String>map= new HashMap<>();
        ConfluencePage result=confluencePageRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Confluence Ticket Not Found with given:"+uniqueId));
        confluencePageRepo.delete(result);
//        confluencePageRepo.deleteByUniqueId(uniqueId);
        map.put("Confluence Ticket Deleted Successfully with id:",result.getId());
        return map;

    }

    @Transactional
    public Map<String,List<ConfluencePageDTO>> bulkUpdate(List<ConfluencePageDTO>confluencePageDTOList,List<UUID>uniqueIds){
         Map<String,List<ConfluencePageDTO>> map=new HashMap<>();
        List<ConfluencePage> existingConfluences =confluencePageRepo.findByUniqueIds(uniqueIds);
          if(uniqueIds.size()!=confluencePageDTOList.size()){
              throw new IllegalArgumentException("Mismatch between uniqueIds and confluencePageDTOList size");
          }
          for(int i=0;i<existingConfluences.size();i++){
              ConfluencePage existingConfluencePage=existingConfluences.get(i);
              ConfluencePageDTO confluencePageDTO=confluencePageDTOList.get(i);
              if(confluencePageDTO.getId()!=null && !confluencePageDTO.getId().isEmpty()){
                  existingConfluencePage.setId(confluencePageDTO.getId());
              }
              if(confluencePageDTO.getDescription()!=null && !confluencePageDTO.getDescription().isEmpty()){
                  existingConfluencePage.setDescription(confluencePageDTO.getDescription());
              }
              if(confluencePageDTO.getTitle()!=null&&!confluencePageDTO.getTitle().isEmpty()){
                  existingConfluencePage.setTitle(confluencePageDTO.getTitle());
              }
              if(confluencePageDTO.getContent()!=null &&!confluencePageDTO.getContent().isEmpty()){
                  existingConfluencePage.setContent(confluencePageDTO.getContent());
              }
              existingConfluencePage.setUpdatedAt(ZonedDateTime.now());
          }
        confluencePageRepo.saveAll(existingConfluences);
          map.put("All Confluence Tickets Updated Successfully",existingConfluences.stream().map(ConfluencePageMapper::toDTO).collect(Collectors.toList()));
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
