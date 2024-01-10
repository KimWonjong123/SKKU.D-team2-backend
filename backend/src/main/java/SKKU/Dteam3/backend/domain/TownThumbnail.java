package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TownThumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town_id")
    @NotNull
    private Town town;

    @NotNull
    private String originalName;

    @NotNull

    public TownThumbnail(Town town, String originalName) {
        this.town = town;
        this.originalName = originalName;
    }

    public boolean changeThumbnail(){  //사진 저장 방식 결정 해야함
        return true;
    }
}
