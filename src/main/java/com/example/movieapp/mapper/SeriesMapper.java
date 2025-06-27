package com.example.movieapp.mapper;
import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.entities.Series;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SeriesMapper {
    SeriesDto toDto(Series series);
    Series toEntity(SeriesDto dto);
}