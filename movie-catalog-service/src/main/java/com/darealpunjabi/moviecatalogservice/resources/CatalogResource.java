package com.darealpunjabi.moviecatalogservice.resources;




import com.darealpunjabi.moviecatalogservice.models.CatalogItem;
import com.darealpunjabi.moviecatalogservice.models.Movie;
import com.darealpunjabi.moviecatalogservice.models.Rating;
import com.darealpunjabi.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);

        return ratings.getRatings().stream().map(rating -> {

                // For each movie ID call Movie Info Service and get details
                Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

                // Put them all together
                return new CatalogItem(movie.getName(), "Description", rating.getRating());
        })
                .collect(Collectors.toList());

    }
}