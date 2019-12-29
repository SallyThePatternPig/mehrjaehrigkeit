package selina.praxisarbeit.mehrjaehrigkeit.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class ProtokollEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int erfassungsjahr;

    private Boolean tiereVorhanden;
    private Integer tierAnzahl;
    private BigDecimal weideflaeche;
    private boolean getreide;
    private boolean bluehpflanzen;
    private boolean anderes;
    private boolean nichts;
    private BigDecimal anbauflaeche;
    private BigDecimal gesamtflaeche;
    private boolean keinePflanzenschutzmittel;
    private boolean min100qmGruenflaeche;

    @ManyToOne
    private PersonEntity antragsteller;
}
