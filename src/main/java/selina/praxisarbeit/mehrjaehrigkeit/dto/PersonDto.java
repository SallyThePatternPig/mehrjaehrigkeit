package selina.praxisarbeit.mehrjaehrigkeit.dto;

import lombok.Getter;
import lombok.Setter;
import selina.praxisarbeit.mehrjaehrigkeit.common.GeschlechtEnum;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class PersonDto {

    private Long id;

    private String nachname;

    private String vorname;

    private Date geburtstdatum;

    private GeschlechtEnum geschlecht;

    private Set<ProtokollDto> protokolle;
}
