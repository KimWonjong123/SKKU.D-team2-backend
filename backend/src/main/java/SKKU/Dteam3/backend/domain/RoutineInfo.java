package SKKU.Dteam3.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "town_id")
    private Town town;

    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @NotNull
    private boolean mon;

    @NotNull
    private boolean tue;

    @NotNull
    private boolean wed;

    @NotNull
    private boolean thu;

    @NotNull
    private boolean fri;

    @NotNull
    private boolean sat;

    @NotNull
    private boolean sun;

    public RoutineInfo(LocalDate startDate, LocalDate endDate, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun) {
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

    public RoutineInfo(Town town, LocalDate startDate, LocalDate endDate, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun) {
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


}
