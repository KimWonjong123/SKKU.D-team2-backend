package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "town_id")
    private Town town;

    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @NotNull
    private Boolean mon;

    @NotNull
    private Boolean tue;

    @NotNull
    private Boolean wed;

    @NotNull
    private Boolean thu;

    @NotNull
    private Boolean fri;

    @NotNull
    private Boolean sat;

    @NotNull
    private Boolean sun;

    public RoutineInfo(LocalDate startDate, LocalDate endDate, Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }
    public RoutineInfo(Town town, LocalDate startDate, LocalDate endDate, Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun) {
        this.town = town;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }

    public void updateRoutineInfo(LocalDate endDate, Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun) {
        this.endDate = endDate;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }


}
