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
    private boolean nichts;
    private BigDecimal anbauflaeche;
    private BigDecimal gesamtflaeche;
    private AumBeantragungEnum keinePflanzenschutzmittel;
    private AumBeantragungEnum min100qmGruenflaeche;
}
