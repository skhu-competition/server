package server.place.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class NaverSearchResponse {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Getter
    public static class Item {
        private String title;
        private String link;
        private String category;
        @JsonProperty("description")
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private String mapx;  // TM128 좌표 (String으로 내려옴)
        private String mapy;
    }
}
