package com.stefanini.spotifanini.model.mapper;

import com.stefanini.spotifanini.model.Artist;
import com.stefanini.spotifanini.model.dto.ArtistDTO;

public class ArtistMapper {

    public Artist mapArtist(ArtistDTO artist) {

        return new Artist(null, artist.getName(), artist.getCountry(), artist.getImage(), null);
    }
}
