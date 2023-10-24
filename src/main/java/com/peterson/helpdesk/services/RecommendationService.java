package com.peterson.helpdesk.services;

import com.peterson.helpdesk.domain.Comment;
import com.peterson.helpdesk.domain.Recommendation;
import com.peterson.helpdesk.domain.Solution;
import com.peterson.helpdesk.domain.Tecnico;
import com.peterson.helpdesk.domain.dtos.*;
import com.peterson.helpdesk.repositories.CommentRepository;
import com.peterson.helpdesk.repositories.RecommendationRepository;
import com.peterson.helpdesk.repositories.SolutionRepository;
import com.peterson.helpdesk.repositories.TecnicoRepository;
import com.peterson.helpdesk.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;
    private final TecnicoRepository tecnicoRepository;

    public RecommendationService(RecommendationRepository recommendationRepository, SolutionRepository solutionRepository, CommentRepository commentRepository, TecnicoRepository tecnicoRepository) {
        this.recommendationRepository = recommendationRepository;
        this.solutionRepository = solutionRepository;
        this.commentRepository = commentRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    public SolutionResponseDTO saveRecommendation(RecommendationDTO recommendation) {
        Solution solution = solutionRepository.findById(recommendation.getSolutionId()).orElseThrow(() -> new RuntimeException("Solution not found"));
        Tecnico tecnico = tecnicoRepository.findByEmail(recommendation.getTecnicoEmail()).orElseThrow(() -> new RuntimeException("Tecnico not found"));
        recommendationRepository.save(Recommendation.builder()
                .description(recommendation.getDescription())
                .solution(solution)
                .tecnico(tecnico)
                .build());
        return SolutionResponseDTO.builder()
                .id(solution.getId())
                .summary(solution.getSummary())
                .status(solution.isStatus())
                .title(solution.getTitle())
                .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(solution.getImageSolution().getImageData())))
                .recommendations(recommendationRepository.findBySolution_Id(solution.getId()).stream()
                        .map(rec -> RecommendationResponseDTO.builder()
                                .id(rec.getId())
                                .description(rec.getDescription())
                                .solutionTitle(rec.getSolution().getTitle())
                                .created_at(rec.getCreatedAt())
                                .tecnicoName(rec.getTecnico().getNome())
                                .comments(commentRepository.findAllByRecommendation_Id(rec.getId()).stream()
                                        .map(commentDB1 -> CommentResponseDTO.builder()
                                                .id(commentDB1.getId())
                                                .description(commentDB1.getDescription())
                                                .createdAt(commentDB1.getCreatedAt())
                                                .tecnicoName(commentDB1.getTecnico().getNome())
                                                .build())
                                        .toList())
                                .build())
                        .toList())
                .build();
    }

    public Recommendation getRecommendationById(Integer id) {
        return recommendationRepository.findById(id).orElse(null);
    }
    public List<Recommendation> getRecommendationBySolutionId(Integer id) {
        return recommendationRepository.findBySolution_Id(id);
    }
    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }
    public List<Comment> getCommentsByRecommendationId(Integer id) {
        return commentRepository.findAllByRecommendation_Id(id);
    }
    public SolutionResponseDTO saveComment(CommentDTO comment) {
        Recommendation recommendation = recommendationRepository.findById(comment.getRecommendationId()).orElseThrow(() -> new RuntimeException("Recommendation not found"));
        Tecnico tecnico = tecnicoRepository.findByEmail(comment.getTecnicoEmail()).orElseThrow(() -> new RuntimeException("Tecnico not found"));
        commentRepository.save(Comment.builder()
                .description(comment.getDescription())
                .recommendation(recommendation)
                .tecnico(tecnico)
                .createdAt(new Timestamp(new Date().getTime()))
                .build());
        return  SolutionResponseDTO.builder()
                .id(recommendation.getSolution().getId())
                .summary(recommendation.getSolution().getSummary())
                .status(recommendation.getSolution().isStatus())
                .title(recommendation.getSolution().getTitle())
                .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(recommendation.getSolution().getImageSolution().getImageData())))
                .recommendations(recommendationRepository.findBySolution_Id(recommendation.getSolution().getId()).stream()
                        .map(rec -> RecommendationResponseDTO.builder()
                                .id(rec.getId())
                                .description(rec.getDescription())
                                .solutionTitle(rec.getSolution().getTitle())
                                .created_at(rec.getCreatedAt())
                                .tecnicoName(rec.getTecnico().getNome())
                                .comments(commentRepository.findAllByRecommendation_Id(rec.getId()).stream()
                                        .map(commentDB1 -> CommentResponseDTO.builder()
                                                .id(commentDB1.getId())
                                                .description(commentDB1.getDescription())
                                                .createdAt(commentDB1.getCreatedAt())
                                                .tecnicoName(commentDB1.getTecnico().getNome())
                                                .build())
                                        .toList())
                                .build())
                        .toList())
                .build();

    }
    public void deleteRecommendationById(Integer id) {
        recommendationRepository.deleteById(id);
    }
}