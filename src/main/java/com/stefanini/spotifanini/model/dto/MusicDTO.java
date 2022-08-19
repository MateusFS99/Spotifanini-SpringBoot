package com.stefanini.spotifanini.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicDTO {

    private String name;

    private String release;

    private String image;

    private String audio;

    private Long genreId;

    private Long artistId;
}
