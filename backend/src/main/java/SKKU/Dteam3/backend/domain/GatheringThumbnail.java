package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GatheringThumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;

    private String originalName;

    public GatheringThumbnail(Gathering gathering, String originalName) {
        this.gathering = gathering;
        this.originalName = originalName;
    }

    public boolean changeThumbnail(){  //사진 저장 방식 결정 해야함
        return true;
    }
}
