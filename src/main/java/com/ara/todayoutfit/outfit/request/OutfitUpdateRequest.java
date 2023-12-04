package com.ara.todayoutfit.outfit.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutfitUpdateRequest {

    private String name;
    private int maxTemperature;
    private int minTemperature;

}
