package selina.praxisarbeit.mehrjaehrigkeit.entity;

import lombok.Getter;
import lombok.Setter;
import selina.praxisarbeit.mehrjaehrigkeit.dto.GeschlechtEnum;

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

    private String nachname;

    private String vorname;

    private Date geburtstdatum;

    private GeschlechtEnum geschlecht;

    @OneToMany(mappedBy = "antragsteller")
    private Set<ProtokollEntity> protokolle;
}
