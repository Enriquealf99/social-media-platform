package com.socialmedia.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostUpdateDTO {

    private String title;
    private String content;
    private String tags;

}
