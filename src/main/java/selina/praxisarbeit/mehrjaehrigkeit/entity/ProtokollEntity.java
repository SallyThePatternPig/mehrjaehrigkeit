package selina.praxisarbeit.mehrjaehrigkeit.entity;


import lombok.Getter;
import lombok.Setter;
import selina.praxisarbeit.mehrjaehrigkeit.common.AumBeantragungEnum;

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

    private BigDecimal anbauflaeche;
    private BigDecimal gesamtflaeche;
    private AumBeantragungEnum keinePflanzenschutzmittel;
    private AumBeantragungEnum min100qmGruenflaeche;
    //nur 2019:
    private boolean nichts;
    private boolean keinePflanzenschutzmittel;
    //ab 2019:
    private boolean min100qmGruenflaeche;
    //ab 2020:
    private Boolean anbauflaecheVorhanden;
    private boolean feldhamster;

    @ManyToOne
    private PersonEntity antragsteller;
}
