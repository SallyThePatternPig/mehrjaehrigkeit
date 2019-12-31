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

    private int aktualisierungsjahr;

    private String nachname;

    private String vorname;

    //wird benötigt für Jahr 2019
    private Date geburtstdatum;

    private GeschlechtEnum geschlecht;

    //wird benötigt ab Jahr 2020
    private String standort;

    private Set<ProtokollDto> protokolle;
}
