package selina.praxisarbeit.mehrjaehrigkeit.entity;

import lombok.Getter;
import lombok.Setter;
import selina.praxisarbeit.mehrjaehrigkeit.common.GeschlechtEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class PersonEntity {

    @Id
    @GeneratedValue
    private Long id; //gib ein die personId, sonst findest du die person nie

    private int aktualisierungsjahr;

    private String nachname;

    private String vorname;

    //wird benötigt für Jahr 2019
    private Date geburtstdatum;

    @Enumerated(EnumType.STRING)
    private GeschlechtEnum geschlecht;

    //wird ab 2020 benötigt
    private String standort;

    @OneToMany(mappedBy = "antragsteller")
    private Set<ProtokollEntity> protokolle;
}
