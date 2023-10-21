package com.peterson.helpdesk.resources;

import com.peterson.helpdesk.domain.Recommendation;
import com.peterson.helpdesk.domain.dtos.RecommendationDTO;
import com.peterson.helpdesk.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @PostMapping
    public Recommendation createRecommendation(@RequestBody RecommendationDTO request) {
        return recommendationService.saveRecommendation(request);
    }

    @GetMapping("/solution")
    public Recommendation getRecommendationBySolutionId(@RequestParam Integer id) {
        return recommendationService.getRecommendationBySolutionId(id);
    }
    @GetMapping("/{id}")
    public Recommendation getRecommendationById(@PathVariable Integer id) {
        return recommendationService.getRecommendationById(id);
    }

    @GetMapping
    public List<Recommendation> getAllRecommendations() {
        return recommendationService.getAllRecommendations();
    }

    @DeleteMapping("/{id}")
    public void deleteRecommendationById(@PathVariable Integer id) {
        recommendationService.deleteRecommendationById(id);
    }
}
