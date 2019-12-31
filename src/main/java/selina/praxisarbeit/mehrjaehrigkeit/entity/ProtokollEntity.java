package selina.praxisarbeit.mehrjaehrigkeit.entity;


import lombok.Getter;
import lombok.Setter;
import selina.praxisarbeit.mehrjaehrigkeit.common.AumBeantragungEnum;

import javax.persistence.*;
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

    //nur 2019:
    private boolean nichts;
    @Enumerated(EnumType.STRING)
    private AumBeantragungEnum keinePflanzenschutzmittel;
    //ab 2019:
    @Enumerated(EnumType.STRING)
    private AumBeantragungEnum min100qmGruenflaeche;
    //ab 2020:
    private Boolean anbauflaecheVorhanden;
    private AumBeantragungEnum feldhamster;

    @ManyToOne
    private PersonEntity antragsteller;
}
