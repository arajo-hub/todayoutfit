package com.ara.todayoutfit.outfit.service;

import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.config.exception.NotFoundException;
import com.ara.todayoutfit.outfit.domain.Outfit;
import com.ara.todayoutfit.outfit.repository.OutfitRepository;
import com.ara.todayoutfit.outfit.request.OutfitCreateRequest;
import com.ara.todayoutfit.outfit.request.OutfitUpdateRequest;
import com.ara.todayoutfit.outfit.response.OutfitShow;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class OutfitServiceTest {

    @Autowired
    private OutfitService outfitService;

    @Autowired
    private OutfitRepository outfitRepository;

    @BeforeEach
    void clean() {
        outfitRepository.deleteAll();
    }

    @Test
    @DisplayName("옷차림 저장 성공")
    void outfit_save_success() {
        OutfitCreateRequest request = OutfitCreateRequest.builder()
                .name("테스트")
                .maxTemperature(30)
                .minTemperature(20)
                .build();
        outfitService.saveOutfit(request);

        Outfit outfit = outfitRepository.findOutfitById(1L).orElseThrow(() -> new NotFoundException());

        assertEquals(request.getName(), outfit.getName());
        assertEquals(request.getMaxTemperature(), outfit.getMaxTemperature());
        assertEquals(request.getMinTemperature(), outfit.getMinTemperature());
    }

    @Test
    @DisplayName("옷차림 수정 성공")
    void outfit_update_success() {
        OutfitCreateRequest request = OutfitCreateRequest.builder()
                .name("테스트")
                .maxTemperature(30)
                .minTemperature(20)
                .build();
        outfitService.saveOutfit(request);

        OutfitUpdateRequest updateRequest = OutfitUpdateRequest.builder()
                .name("테스트2")
                .maxTemperature(34)
                .minTemperature(21)
                .build();
        ObjectResponse<OutfitShow> outfitShowObjectResponse = outfitService.updateOutfit(1L, updateRequest);
        OutfitShow outfit = outfitShowObjectResponse.getObject();

        assertEquals(updateRequest.getName(), outfit.getName());
        assertEquals(updateRequest.getMaxTemperature(), outfit.getMaxTemperature());
        assertEquals(updateRequest.getMinTemperature(), outfit.getMinTemperature());
    }

    @Test
    @DisplayName("옷차림 삭제 성공")
    void outfit_delete_success() {
        OutfitCreateRequest request = OutfitCreateRequest.builder()
                .name("테스트")
                .maxTemperature(30)
                .minTemperature(20)
                .build();
        outfitService.saveOutfit(request);

        outfitService.deleteOutfit(1L);

        assertThatThrownBy(() -> outfitRepository.findOutfitById(1L).get())
                .isInstanceOf(NoSuchElementException.class);
    }

}
