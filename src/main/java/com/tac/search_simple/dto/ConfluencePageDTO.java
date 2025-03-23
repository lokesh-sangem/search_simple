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
//    @NotBlank(message = "ID cannot be blank")
    @Pattern(regexp="^[A-Z]{2,5}-\\d+$",message="ID must be in the format CONF-XXX (e.g., CONF-317)")
    private String id;
    @NotBlank(message = "description cannot be blank")
    @Size(min = 10, max = 300, message = "Description must be between 10 and 500 characters")
    @Pattern(regexp = "^[A-Za-z0-9 .,\\-_'\"()!/@#$%^&*+=?<>:;{}\\[\\]\\\\|~`]+$", message = "Description can contain letters, numbers, spaces, and most punctuation marks")
    private String description;//
    @NotBlank(message = "title cannot be blank")
    @Pattern(regexp = "^[A-Za-z0-9 .,\\-_'\"()!/@#$%^&*+=?<>:;{}\\[\\]\\\\|~`]+$", message = "Title can contain letters, numbers, spaces, and most punctuation marks")
    @Size(min = 5, max = 100, message = "Title must be between 10 and 500 characters")
    private String title;//
    @NotBlank(message = "content cannot be blank")
    @Size(min=10,max=300,message="Content must be at least 10 characters long ")
    @Pattern(regexp = "^[A-Za-z0-9 .,\\-_'\"()!/@#$%^&*+=?<>:;{}\\[\\]\\\\|~`]+$", message = "Content can contain letters, numbers, spaces, and most punctuation marks")
    private String content;
//    @NotNull
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
//    @NotNull
    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
    @NotEmpty(message="At least one tag is required")
    private List<@Valid @Pattern(regexp="^[A-Za-z0-9 .,\\-_\"'()!/:;{}\\[\\]\\\\|~`]+$", message="Tags can only contain letters, numbers, spaces, dots, commas, hyphens, underscores, apostrophes, double quotes, and exclamation marks") String> tags;
    @NotEmpty(message="At least one link is needed")
    private List<@Valid LinkDTO>links;//

    @Data
    public static class LinkDTO{
        @NotBlank(message="Type cannot be blank")
        @Pattern(regexp = "^[A-Za-z0-9 .,\\-_'\"()!/]+$", message = "Type can only contain letters, numbers, spaces, dots, commas, hyphens, underscores, parentheses, exclamation marks, double quotes, and slashes")
        private String type;
        @NotBlank(message="Id cannot be blank")
        @Pattern(regexp="^[A-Z]{2,5}-\\d+$", message="Link ID must be in the format PREFIX-XXXX (e.g., JIRA-456, ZSD-1234, BMG-3342)")
        private String id;
        @NotBlank(message = "title cannot be blank")
        @Pattern(regexp = "^[A-Za-z0-9 .,\\-_'\"()!/]+$", message = "Title can only contain letters, numbers, spaces, dots, commas, hyphens, underscores, parentheses, exclamation marks, double quotes, and slashes")
        private String title;
        @NotBlank(message="Reporter cannot be blank")
        @Pattern(regexp = "^[A-Za-z \"-']+$", message = "Reporter can only contain letters, spaces, hyphens, apostrophes, and double quotes")
        private String reporter;
    }


}
