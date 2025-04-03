package server.place.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import server.place.api.dto.response.NaverSearchResponse;

@Service
public class NaverSearchService {
    private final WebClient webClient;

    private static final String BASE_URL = "https://openapi.naver.com";
    private static final String SEARCH_PATH = "/v1/search/local.json";

    public NaverSearchService(
            @Value("${spring.naver.client_id}") String clientId,
            @Value("${spring.naver.secret}") String clientSecret
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();
    }

    public NaverSearchResponse searchPlace(String query) {
        String uri = UriComponentsBuilder.fromPath("/v1/search/local.json")
                .queryParam("query", query)
                .queryParam("display", 5)
                .queryParam("start", 1)
                .queryParam("sort", "comment")
                .build()
                .toUriString();

        System.out.println("[API 요청 URI] " + uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NaverSearchResponse.class)
                .doOnNext(res -> System.out.println("[API 응답 total] " + res.getTotal()))
                .doOnError(e -> System.out.println("[API 요청 실패] " + e.getMessage()))
                .block();
    }
}
