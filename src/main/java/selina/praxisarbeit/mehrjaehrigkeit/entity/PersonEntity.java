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
    private Long id;

    private int aktualisierungsjahr;

    private String nachname;

    private String vorname;

    private Date geburtstdatum;

    @Enumerated(EnumType.STRING)
    private GeschlechtEnum geschlecht;

    @OneToMany(mappedBy = "antragsteller")
    private Set<ProtokollEntity> protokolle;
}
