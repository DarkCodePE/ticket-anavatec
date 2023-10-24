package com.peterson.helpdesk.services;

import com.peterson.helpdesk.domain.*;
import com.peterson.helpdesk.domain.dtos.*;
import com.peterson.helpdesk.repositories.*;
import com.peterson.helpdesk.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.peterson.helpdesk.domain.enums.Status.ENCERRADO;

@Service
@Slf4j(topic = "SOLUTION_SERVICE")
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final ChamadoRepository chamadoRepository;
    private final ImageRepository imageRepository;
    private final ImageSolutionRepository imageSolutionRepository;
    private final RecommendationRepository recommendationRepository;
    public SolutionService(SolutionRepository solutionRepository, ChamadoRepository chamadoRepository, ImageRepository imageRepository, ImageSolutionRepository imageSolutionRepository, RecommendationRepository recommendationRepository) {
        this.solutionRepository = solutionRepository;
        this.chamadoRepository = chamadoRepository;
        this.imageRepository = imageRepository;
        this.imageSolutionRepository = imageSolutionRepository;
        this.recommendationRepository = recommendationRepository;
    }
    public List<SolutionResponseDTO> solutions() {
        return solutionRepository.findAll()
                .stream()
                .map(sol -> SolutionResponseDTO.builder()
                        .id(sol.getId())
                        .productId(sol.getChamado().getProduct().getId())
                        .summary(sol.getSummary())
                        .status(sol.isStatus())
                        .title(sol.getTitle())
                        .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(sol.getImageSolution().getImageData())))
                .build())
                .toList();

    }
    public List<SolutionResponseDTO> getSolutionsByProductId(Integer productID) {
        List<Recommendation> recommendation = recommendationRepository.findAllBySolution_Chamado_Product_Id(productID);
        return solutionRepository.findByChamado_Product_Id(productID)
                .stream()
                .map(sol -> SolutionResponseDTO.builder()
                        .summary(sol.getSummary())
                        .status(sol.isStatus())
                        .title(sol.getTitle())
                        .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(sol.getImageSolution().getImageData())))
                        .recommendations(recommendation.stream()
                                .map(rec -> RecommendationResponseDTO.builder()
                                        .id(rec.getId())
                                        .description(rec.getDescription())
                                        .solutionTitle(sol.getTitle())
                                        .created_at(rec.getCreatedAt())
                                        .tecnicoName(rec.getTecnico().getNome())
                                        .comments(rec.getComments().stream()
                                                .map(comment -> CommentResponseDTO.builder()
                                                        .description(comment.getDescription())
                                                        .createdAt(comment.getCreatedAt())
                                                        .tecnicoName(comment.getTecnico().getNome())
                                                        .build())
                                                .toList())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .toList();

    }
    public List<SolutionResponseDTO> getSolutionsByTicketId(Integer ticketID) {
        List<Recommendation> recommendation = recommendationRepository.findAllBySolution_Chamado_Id(ticketID);
        return solutionRepository.findByChamado_Id(ticketID)
                .stream()
                .map(sol -> SolutionResponseDTO.builder()
                        .id(sol.getId())
                        .productId(sol.getChamado().getProduct().getId())
                        .summary(sol.getSummary())
                        .status(sol.isStatus())
                        .title(sol.getTitle())
                        .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(sol.getImageSolution().getImageData())))
                        .recommendations(recommendation.stream()
                                .map(rec -> RecommendationResponseDTO.builder()
                                        .id(rec.getId())
                                        .description(rec.getDescription())
                                        .solutionTitle(sol.getTitle())
                                        .created_at(rec.getCreatedAt())
                                        .tecnicoName(rec.getTecnico().getNome())
                                        .comments(rec.getComments().stream()
                                                .map(comment -> CommentResponseDTO.builder()
                                                        .description(comment.getDescription())
                                                        .createdAt(comment.getCreatedAt())
                                                        .tecnicoName(comment.getTecnico().getNome())
                                                        .build())
                                                .toList())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

    }
    public List<SolutionResponseDTO> searchSolution(SearchSolutionDTO searchSolutionDTO) {
        List<Recommendation> recommendation = recommendationRepository.findAllBySolution_Chamado_Product_Id(searchSolutionDTO.getProductId());
        return solutionRepository.searchSolutions(searchSolutionDTO.getTitle(), searchSolutionDTO.getProductId())
                .stream()
                .map(sol -> SolutionResponseDTO.builder()
                        .id(sol.getId())
                        .productId(sol.getChamado().getProduct().getId())
                        .summary(sol.getSummary())
                        .status(sol.isStatus())
                        .title(sol.getTitle())
                        .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(sol.getImageSolution().getImageData())))
                        .recommendations(recommendation.stream()
                                .map(rec -> RecommendationResponseDTO.builder()
                                        .id(rec.getId())
                                        .description(rec.getDescription())
                                        .solutionTitle(sol.getTitle())
                                        .created_at(rec.getCreatedAt())
                                        .tecnicoName(rec.getTecnico().getNome())
                                        .comments(rec.getComments().stream()
                                                .map(comment -> CommentResponseDTO.builder()
                                                        .description(comment.getDescription())
                                                        .createdAt(comment.getCreatedAt())
                                                        .tecnicoName(comment.getTecnico().getNome())
                                                        .build())
                                                .toList())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }



    public List<Chamado> getTicketsByProductSku(Integer productID) {
        return chamadoRepository.findByProduct_Id(productID);
    }
    public List<Chamado> searchChamados(String titulo, Integer productID) {
        if (titulo == null || titulo.isEmpty()) return chamadoRepository.findByProduct_Id(productID);
        return chamadoRepository.searchChamados(titulo, productID);
    }
    public List<SolutionListDTO> createSolution(SolutionRequestDTO solutionRequestDTO, MultipartFile file) throws IOException {
        Chamado chamado = chamadoRepository.findById(solutionRequestDTO.getTicketId()).orElseThrow(() -> new RuntimeException("no existe el ticket"));
        Solution solution = new Solution();
        ImageSolution imageDB = null;
        if (file != null){
            ImageSolution image = new ImageSolution();
            image.setName(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setImageData(ImageUtil.compressImage(file.getBytes()));
            imageDB = imageSolutionRepository.save(image);
        }
        solution.setChamado(chamado);
        solution.setSummary(solutionRequestDTO.getSummary());
        solution.setTitle(solutionRequestDTO.getTitle());
        solution.setStatus(true);
        solution.setImageSolution(imageDB);
        solutionRepository.save(solution);
        chamado.setSolution(true);
        chamado.setStatus(ENCERRADO);
        chamadoRepository.save(chamado);
        List<SolutionListDTO> response = new ArrayList<>();
        solutionRepository.findAll().forEach(solutionDB -> {
            response.add(SolutionListDTO.builder()
                    .ticketID(solutionDB.getChamado().getId())
                    .title(solutionDB.getTitle())
                    .summary(solutionDB.getSummary())
                    .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(solutionDB.getImageSolution().getImageData())))
                    .build());
        });
        return response;
    }
}
