package com.ara.todayoutfit.outfit.controller.api;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.outfit.request.OutfitCreateRequest;
import com.ara.todayoutfit.outfit.request.OutfitUpdateRequest;
import com.ara.todayoutfit.outfit.response.OutfitShow;
import com.ara.todayoutfit.outfit.service.OutfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OutfitApiController {

    private OutfitService outfitService;

    @GetMapping("/outfits/{outfitId}")
    public ObjectResponse<OutfitShow> findOutfitById(@PathVariable Long outfitId) {
        return outfitService.findOutfitById(outfitId);
    }

    /**
     * 기온에 따른 옷차림 전체 조회
     * @param temperature
     * @return
     */
    @GetMapping("/outfits/temperature/{temperature}")
    public ObjectResponse<List<OutfitShow>> findOutfitByTemperature(int temperature) {
        return outfitService.findOutfitByTemperature(temperature);
    }

    /**
     * 옷차림 등록
     * @param request
     * @return
     */
    @PostMapping("/outfits")
    public ObjectResponse<OutfitShow> saveOutfit(@RequestBody OutfitCreateRequest request) {
        return outfitService.saveOutfit(request);
    }

    /**
     * 옷차림 수정
     * @param outfitId
     * @param request
     * @return
     */
    @PutMapping("/outfits/{outfitId}")
    public ObjectResponse<OutfitShow> updateOutfit(@PathVariable Long outfitId, @RequestBody OutfitUpdateRequest request) {
        return outfitService.updateOutfit(outfitId, request);
    }

    /**
     * 옷차림 삭제
     * @param outfitId
     * @return
     */
    @DeleteMapping("/outfits/{outfitId}")
    public BaseResult deleteOutfit(@PathVariable Long outfitId) {
        return outfitService.deleteOutfit(outfitId);
    }

}
