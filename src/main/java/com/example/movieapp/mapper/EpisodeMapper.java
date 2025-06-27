package com.example.movieapp.mapper;

import com.example.movieapp.dto.EpisodeDto;
import com.example.movieapp.dto.EpisodePartDto;
import com.example.movieapp.entities.Episode;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {

    EpisodePartDto toPartDto(Episode episode);

    List<EpisodePartDto> toPartDtoList(List<Episode> episodes);

    EpisodeDto toEpisodeDto(Episode episode);
}
