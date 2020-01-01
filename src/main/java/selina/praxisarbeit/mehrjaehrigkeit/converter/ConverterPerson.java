package selina.praxisarbeit.mehrjaehrigkeit.converter;

import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.PersonEntity;

import java.util.ArrayList;
import java.util.List;

public class ConverterPerson {

    public PersonDto convertToDto (PersonEntity pE){
        PersonDto pDto = new PersonDto();
        pDto.setId(pE.getId());
        pDto.setAktualisierungsjahr(pE.getAktualisierungsjahr());
        pDto.setVorname(pE.getVorname());
        pDto.setNachname(pE.getNachname());
        ConverterProtokoll cA = new ConverterProtokoll();
        pDto.setProtokolle(cA.convertToDto(pE.getProtokolle()));
        pDto.setStandort(pE.getStandort());
        pDto.setGeschlecht(pE.getGeschlecht());
        pDto.setGeburtstdatum(pE.getGeburtstdatum());
        return pDto;
    }

    public List<PersonDto> convertToDto(List<PersonEntity> pEL){
        List<PersonDto> pDtoL = new ArrayList<>();
        for(PersonEntity pE: pEL){
            pDtoL.add(convertToDto(pE));
        }
        return pDtoL;
    }

    public void convertToEntity (PersonDto personDto, PersonEntity personEntity){
        personEntity.setId(personDto.getId());
        personEntity.setAktualisierungsjahr(personDto.getAktualisierungsjahr());
        personEntity.setVorname(personDto.getVorname());
        personEntity.setNachname(personDto.getNachname());
        personEntity.setStandort(personDto.getStandort());
        personEntity.setGeschlecht(personDto.getGeschlecht());
    }
}
