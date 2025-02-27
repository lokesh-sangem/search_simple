package com.tac.search_simple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;



import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ConfluencePageDTO {
    @NotBlank(message = "ID cannot be blank")
    @Pattern(regexp="^[A-Z]{2,5}-\\d+$",message="ID must be in the format CONF-XXX (e.g., CONF-317)")
    private String id;
    @NotBlank(message = "description cannot be blank")
    @Size(min = 10, max = 300, message = "Description must be between 10 and 500 characters")
    @Pattern(regexp="^[A-Za-z0-9 .,\\-']+$",message="Description must contain only characters, spaces, and basic punctuation")
    private String description;
    @NotBlank(message = "title cannot be blank")
    @Pattern(regexp="^[A-Za-z0-9 ]+$",message="Title must contain only alphanumeric characters")
    @Size(min = 10, max = 100, message = "Title must be between 10 and 500 characters")
    private String title;
    @NotBlank(message = "content cannot be blank")
    @Size(min=10,max=300,message="Content must be at least 10 characters long ")
    @Pattern(regexp="^[A-Za-z0-9 .,\\-']+$",message="Content must contain only alphanumeric characters")
    private String content;
    @NotNull
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
//    @NotNull
    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
    @NotEmpty(message="Atleast one tag is required")
    private List<@Valid @Pattern(regexp="^[A-Za-z0-9 .,\\-']+$",message="Tags can only contain letters, numbers, and spaces")String> tags;
    @NotEmpty(message="At least one link is needed")
    private List<@Valid LinkDTO>links;

    @Data
    public static class LinkDTO{
        @NotBlank(message="Type cannot be blank")
        @Pattern(regexp="^[A-Za-z0-9 ]+$",message="Type can contain letters,numbers,spaces]")
        private String type;
        @NotBlank(message="Id cannot be blank")
        @Pattern(regexp="^[A-Z]{2,5}-\\d+$", message="Link ID must be in the format PREFIX-XXXX (e.g., JIRA-456, ZSD-1234, BMG-3342)")
        private String id;
        @NotBlank(message="title cannot be blank")
        @Pattern(regexp="^[A-Za-z0-9 ]+$",message="Title can only contain letters and spaces")
        private String title;
        @NotBlank(message="Reporter cannot be blank")
        @Pattern(regexp="^[A-Za-z ]+$",message="Reporter can only contain letters and spaces")
        private String reporter;
    }


}
