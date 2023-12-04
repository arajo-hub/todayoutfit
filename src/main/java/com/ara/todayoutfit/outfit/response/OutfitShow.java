package com.ara.todayoutfit.outfit.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutfitShow {

    private String name;
    private int maxTemperature;
    private int minTemperature;

}
