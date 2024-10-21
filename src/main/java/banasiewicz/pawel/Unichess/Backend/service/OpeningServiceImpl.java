package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.dto.OpeningDto;
import banasiewicz.pawel.Unichess.Backend.repository.OpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeningServiceImpl implements OpeningService {

    private final OpeningRepository openingRepository;

    @Autowired
    public OpeningServiceImpl(OpeningRepository openingRepository) {
        this.openingRepository = openingRepository;
    }

    @Override
    public List<OpeningDto> getOpenings() {
        return openingRepository.findAll().stream()
                .map(OpeningDto::from)
                .toList();
    }
}
