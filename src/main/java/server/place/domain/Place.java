package server.place.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "place")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACE_ID")
    private Long id;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "ADDRESS", length = 50, nullable = false)
    private String address;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "MAPX", nullable = false)
    private double mapx;  // 위도

    @Column(name = "MAPY", nullable = false)
    private double mapy;  // 경도

    @Builder
    public Place(String name, String address, String description, double mapx, double mapy) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.mapx = mapx;
        this.mapy = mapy;
    }
}
