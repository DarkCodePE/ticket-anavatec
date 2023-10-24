package com.peterson.helpdesk.resources;

import com.peterson.helpdesk.domain.Comment;
import com.peterson.helpdesk.domain.Recommendation;
import com.peterson.helpdesk.domain.Solution;
import com.peterson.helpdesk.domain.dtos.CommentDTO;
import com.peterson.helpdesk.domain.dtos.CommentResponseDTO;
import com.peterson.helpdesk.domain.dtos.RecommendationDTO;
import com.peterson.helpdesk.domain.dtos.SolutionResponseDTO;
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
    public SolutionResponseDTO createRecommendation(@RequestBody RecommendationDTO request) {
        return recommendationService.saveRecommendation(request);
    }

    @GetMapping("/solution")
    public  List<Recommendation> getRecommendationBySolutionId(@RequestParam Integer id) {
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
    @PostMapping("/comment")
    public SolutionResponseDTO createComment(@RequestBody CommentDTO request) {
        return recommendationService.saveComment(request);
    }
    @GetMapping("/comment")
    public List<CommentResponseDTO> getCommentsByRecommendationId(@RequestParam Integer id) {
        return recommendationService.getCommentsByRecommendationId(id).stream().map(commentDB -> CommentResponseDTO.builder()
                .id(commentDB.getId())
                .description(commentDB.getDescription())
                .createdAt(commentDB.getCreatedAt())
                .tecnicoName(commentDB.getTecnico().getNome())
                .build()).toList();
    }
}
