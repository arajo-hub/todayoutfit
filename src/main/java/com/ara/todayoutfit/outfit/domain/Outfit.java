package com.ara.todayoutfit.outfit.domain;

import com.ara.todayoutfit.outfit.request.OutfitUpdateRequest;
import com.ara.todayoutfit.outfit.response.OutfitShow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="outfit")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Outfit {

    @Id
    @Column(name="outfit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outfitId;

    @Column
    private String name;

    @Column(name="max_temperature")
    private int maxTemperature;

    @Column(name="min_temperature")
    private int minTemperature;

    @CreatedDate
    private LocalDateTime writeDate;

    @Builder
    public Outfit(String name, int maxTemperature, int minTemperature) {
        this.name = name;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public OutfitShow toOutfitShow() {
        return OutfitShow.builder()
            .name(name)
            .build();
    }

    public void updateOutfit(OutfitUpdateRequest request) {
        this.name = request.getName();
        this.maxTemperature = request.getMaxTemperature();
        this.minTemperature = request.getMinTemperature();
    }
}
