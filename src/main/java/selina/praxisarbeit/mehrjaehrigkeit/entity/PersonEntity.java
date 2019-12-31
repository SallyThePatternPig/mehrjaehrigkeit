package selina.praxisarbeit.mehrjaehrigkeit.entity;

import lombok.Getter;
import lombok.Setter;
import selina.praxisarbeit.mehrjaehrigkeit.common.GeschlechtEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class PersonEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int aktualisierungsjahr;

    private String nachname;

    private String vorname;

    //wird benötigt für Jahr 2019
    private Date geburtstdatum;

    private GeschlechtEnum geschlecht;

    //wird ab 2020 benötigt
    private String standort;

    @OneToMany(mappedBy = "antragsteller")
    private Set<ProtokollEntity> protokolle;
}
