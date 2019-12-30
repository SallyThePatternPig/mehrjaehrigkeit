package selina.praxisarbeit.mehrjaehrigkeit.dto;

import lombok.Getter;
import lombok.Setter;
import selina.praxisarbeit.mehrjaehrigkeit.common.AumBeantragungEnum;
import selina.praxisarbeit.mehrjaehrigkeit.entity.PersonEntity;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Setter
public class ProtokollDto {

    private Long id;
    private Long personId;
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
    private boolean keinePflanzenschutzmittel;
    private Integer keinePflanzenschutzmittelAbJahr;
    //ab 2019:
    private boolean min100qmGruenflaeche;
    private Integer min100qmGruenflaecheAbJahr;
    //ab 2020:
    private Boolean anbauflaecheVorhanden;
    private boolean feldhamster;
    private Integer feldhamsterAbJahr;
    private AumBeantragungEnum keinePflanzenschutzmittel;
    private AumBeantragungEnum min100qmGruenflaeche;
}
