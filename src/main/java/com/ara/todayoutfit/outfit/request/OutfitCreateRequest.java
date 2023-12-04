package com.ara.todayoutfit.outfit.request;

import com.ara.todayoutfit.outfit.domain.Outfit;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutfitCreateRequest {

    private String name;
    private int maxTemperature;
    private int minTemperature;

    public Outfit toOutfit() {
        return Outfit.builder()
                .name(name)
                .maxTemperature(maxTemperature)
                .minTemperature(minTemperature)
                .build();
    }

}
