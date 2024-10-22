package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.opening.OpeningResponseDto;

import java.util.List;

public interface OpeningService {

    List<OpeningResponseDto> getOpenings();
}
