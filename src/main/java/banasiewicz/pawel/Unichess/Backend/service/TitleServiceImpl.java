package banasiewicz.pawel.Unichess.Backend.service;

import banasiewicz.pawel.Unichess.Backend.model.Title;
import banasiewicz.pawel.Unichess.Backend.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    @Autowired
    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public List<Title> getTitles() {
        return titleRepository.findAll();
    }
}
