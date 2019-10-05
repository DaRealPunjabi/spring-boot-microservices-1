package com.darealpunjabi.moviecatalogservice.resources;




import com.darealpunjabi.moviecatalogservice.models.CatalogItem;
import com.darealpunjabi.moviecatalogservice.models.Movie;
import com.darealpunjabi.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {


    @Autowired
    private RestTemplate restTemplate;

    /*
    private final RestTemplate restTemplate;

    public CatalogResource(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    */

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // RestTemplate restTemplate = new RestTemplate();
        // Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);

        // Get all related movie IDs
        List<Rating> ratingsList = Arrays.asList(
                new Rating("1234", 3),
                new Rating("5678", 4)
        );

        // For each movie ID call Movie Info Service and get details

        // Put them all together
        return ratingsList.stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), "Description", rating.getRating());
                })
                .collect(Collectors.toList());

        /*
        return ratingsList.stream()
                .map(rating -> new CatalogItem("Name", "Desc", 4))
                .collect(Collectors.toList());
        */

        /*
        return ratingsList.stream()
                .map(rating -> new CatalogItem("Name", "Desc", rating.getRating()))
                .collect(Collectors.toList());
        */

        /*
        return Collections.singletonList(new CatalogItem("Test", "Test Desc", 4));
         */
    }
}