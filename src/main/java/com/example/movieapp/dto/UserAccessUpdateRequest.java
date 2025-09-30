package com.example.movieapp.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserAccessUpdateRequest {
    private Map<Long, Integer> seriesAccessMap;

    private Boolean subscription;
    private Integer subscriptionDays;
}