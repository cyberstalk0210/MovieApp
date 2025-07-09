package com.example.movieapp.mapper;

import com.example.movieapp.dto.SeriesDto;
import com.example.movieapp.entities.Series;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface SeriesMapper {

    SeriesDto toDto(Series series);

    Series toEntity(SeriesDto dto);

    @Named("seriesDtoToEntityById")
    default Series seriesDtoToEntityById(SeriesDto dto) {
        if (dto == null || dto.getId() == null) return null;
        Series series = new Series();
        series.setId(dto.getId());
        return series;
    }
}
