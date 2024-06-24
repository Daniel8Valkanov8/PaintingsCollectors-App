package com.paintingscollectors.init;

import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.repository.StyleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class InitService implements CommandLineRunner {

    private final Map<StyleName, String> descriptions = Map.of(
            StyleName.IMPRESSIONISM , "Impressionism is a painting style most commonly associated with the 19th century where small brush strokes are used to build up a larger picture.",
            StyleName.ABSTRACT , "Abstract art does not attempt to represent recognizable subjects in a realistic manner. ",
            StyleName.EXPRESSIONISM , "Expressionism is a style of art that doesn't concern itself with realism.",
            StyleName.SURREALISM , "Surrealism is characterized by dreamlike, fantastical imagery that often defies logical explanation.",
            StyleName.REALISM , "Also known as naturalism, this style of art is considered as 'real art' and has been the dominant style of painting since the Renaissance."
    );

     private final StyleRepository styleRepository;

    public InitService(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = this.styleRepository.count();

        if (count > 0) {
            return;
        }
        List<Style> toInsert = Arrays.stream(StyleName.values())
                .map(cat -> new Style(cat, descriptions.get(cat)))
                .toList();

        this.styleRepository.saveAll(toInsert);
    }
}
