package com.agrismart.backend.config;

import com.agrismart.backend.entity.Crop;
import com.agrismart.backend.repository.CropRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CropCatalogConfig {

    @Bean
    @ConditionalOnProperty(name = "app.catalog.seed", havingValue = "true", matchIfMissing = true)
    CommandLineRunner seedCropCatalog(CropRepository cropRepository) {
        return args -> {
            List<CropSeed> seeds = List.of(
                    new CropSeed("Soybean", "Glycine max", "Oilseed", "Common kharif oilseed crop"),
                    new CropSeed("Wheat", "Triticum aestivum", "Cereal", "Common rabi cereal crop"),
                    new CropSeed("Cotton", "Gossypium", "Fibre", "Widely cultivated fibre crop")
            );

            for (CropSeed seed : seeds) {
                if (!cropRepository.existsByNameIgnoreCase(seed.name())) {
                    Crop crop = new Crop();
                    crop.setName(seed.name());
                    crop.setScientificName(seed.scientificName());
                    crop.setCategory(seed.category());
                    crop.setDescription(seed.description());
                    crop.setActive(true);
                    cropRepository.save(crop);
                }
            }
        };
    }

    private record CropSeed(String name, String scientificName, String category, String description) {
    }
}
