package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.OpeningDto;

import java.util.List;

public interface OpeningService {

    List<OpeningDto> getOpenings();
}
