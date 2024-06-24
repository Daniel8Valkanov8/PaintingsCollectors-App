package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.StyleRepository;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class PaintingService {
    private final PaintingRepository paintingRepository;
    private final StyleRepository styleRepository;
    private final UserRepository userRepository;
    private final UserSession userSession;


    public PaintingService(PaintingRepository paintingRepository, StyleRepository styleRepository, UserRepository userRepository, UserSession userSession) {
        this.paintingRepository = paintingRepository;
        this.styleRepository = styleRepository;
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean create(AddPaintingDTO data){
        if (!userSession.isLoggedIn()) {
            return false;
        }

        Optional<User> byId = userRepository.findById(userSession.id());
        if (byId.isEmpty()) {
            return false;
        }

        Optional<Style> byName = styleRepository.findByName(data.getStyle());
        if (byName.isEmpty()) {
            return false;
        }
        Painting painting = new Painting();
        painting.setName(data.getName());
        painting.setAuthor(data.getAuthor());
        painting.setStyle(byName.get());
        painting.setOwner(byId.get());
        painting.setImageUrl(data.getImageUrl());
        painting.setVotes(0);
        paintingRepository.save(painting);
        return true;
    }

    public Map<StyleName, List<Painting>> findAllByCategory() {
        Map<StyleName, List<Painting>> res = new HashMap<>();

        List<Style> allCategories = styleRepository.findAll();

        for (Style style : allCategories) {
            List<Painting> paintings = paintingRepository.findAllByStyle(style);

            res.put(style.getName(), paintings);
        }

        return res;
    }

    @Transactional
    public void addToFavourites(Long id, long paintingId) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return;
        }

        Optional<Painting> recipe = paintingRepository.findById(paintingId);
        if (recipe.isEmpty()) {
            return;
        }

        userOptional.get().addFavourite(recipe.get());
        userRepository.save(userOptional.get());
    }

}