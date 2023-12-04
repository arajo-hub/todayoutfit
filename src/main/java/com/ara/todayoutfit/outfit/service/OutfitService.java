package com.ara.todayoutfit.outfit.service;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.config.exception.NotFoundException;
import com.ara.todayoutfit.outfit.domain.Outfit;
import com.ara.todayoutfit.outfit.repository.OutfitRepository;
import com.ara.todayoutfit.outfit.request.OutfitCreateRequest;
import com.ara.todayoutfit.outfit.request.OutfitUpdateRequest;
import com.ara.todayoutfit.outfit.response.OutfitShow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutfitService {

    private final OutfitRepository outfitRepository;

    public ObjectResponse<OutfitShow> saveOutfit(OutfitCreateRequest request) {
        Outfit outfit = request.toOutfit();
        Outfit savedOutfit = outfitRepository.saveOutfit(outfit);
        return new ObjectResponse(savedOutfit);
    }

    public ObjectResponse<OutfitShow> findOutfitById(Long outfitId) {
        Outfit outfit = outfitRepository.findOutfitById(outfitId).orElseThrow(() -> new NotFoundException());
        return new ObjectResponse(outfit.toOutfitShow());
    }

    public ObjectResponse<List<OutfitShow>> findOutfitByTemperature(int temperature) {
        List<Outfit> outfits = outfitRepository.findOutfitsByTemperature(temperature);
        List<OutfitShow> outfitShows = outfits.stream().map(Outfit::toOutfitShow).collect(Collectors.toList());
        return new ObjectResponse(outfitShows);
    }

    public ObjectResponse<OutfitShow> updateOutfit(Long id, OutfitUpdateRequest request) {
        Outfit outfit = outfitRepository.findOutfitById(id).orElseThrow(() -> new NotFoundException());
        outfit.updateOutfit(request);
        return new ObjectResponse(outfit);
    }

    public BaseResult deleteOutfit(Long id) {
        outfitRepository.deleteOutfitById(id);
        return new BaseResult(ResultCode.SUCCESS);
    }
}
