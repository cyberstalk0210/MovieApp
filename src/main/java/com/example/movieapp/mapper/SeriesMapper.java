package com.example.movieapp.mapper;
import com.example.movieapp.dto.EpisodeDto;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.entities.Episode;
import com.example.movieapp.entities.Series;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface SeriesMapper {

    SeriesMapper INSTANCE = Mappers.getMapper(SeriesMapper.class);

    SeriesDto toDto(Series series);
    Series toEntity(SeriesDto dto);

    EpisodeDto toEpisodeDto(Episode episode);
    Episode toEpisode(EpisodeDto dto);
}