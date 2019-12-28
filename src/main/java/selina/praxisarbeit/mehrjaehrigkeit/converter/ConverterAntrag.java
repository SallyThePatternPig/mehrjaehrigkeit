package selina.praxisarbeit.mehrjaehrigkeit.converter;

import selina.praxisarbeit.mehrjaehrigkeit.dto.AntragDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.AntragEntity;

import java.util.*;

public class ConverterAntrag {

    public AntragDto convertToDto(AntragEntity antragEntity){
        AntragDto antragDto = new AntragDto();
        antragDto.setId(antragEntity.getId());
        antragDto.setPersonId(antragEntity.getAntragsteller().getId());
        antragDto.setErfassungsjahr(antragEntity.getErfassungsjahr());
        antragDto.setTiereVorhanden(antragEntity.getTiereVorhanden());
        antragDto.setTierAnzahl(antragEntity.getTierAnzahl());
        antragDto.setWeideflaeche(antragEntity.getWeideflaeche());
        antragDto.setGetreide(antragEntity.isGetreide());
        antragDto.setBluehpflanzen(antragEntity.isBluehpflanzen());
        antragDto.setAnderes(antragEntity.isAnderes());
        antragDto.setNichts(antragEntity.isNichts());
        antragDto.setAnbauflaeche(antragEntity.getAnbauflaeche());
        antragDto.setGesamtflaeche(antragEntity.getGesamtflaeche());
        antragDto.setKeinePflanzenschutzmittel(antragEntity.isKeinePflanzenschutzmittel());
        antragDto.setMin100qmGruenflaeche(antragEntity.isMin100qmGruenflaeche());
        return antragDto;
    }

    public Set<AntragDto> convertToDto(Set<AntragEntity> aEL){
        Set<AntragDto> aDtoL = new HashSet<>();
        for(AntragEntity aE: aEL) {
            aDtoL.add(convertToDto(aE));
        }
        return aDtoL;
    }

    public List<AntragDto> convertToDto(List<AntragEntity> aEL){
        List<AntragDto> aDtoL = new ArrayList<>();
        for(AntragEntity aE: aEL) {
            aDtoL.add(convertToDto(aE));
        }
        return aDtoL;
    }

    public AntragEntity convertToEntity(AntragDto antragDto, AntragEntity antragEntity){
        antragEntity.setErfassungsjahr(antragDto.getErfassungsjahr());
        antragEntity.setTiereVorhanden(antragDto.getTiereVorhanden());
        antragEntity.setTierAnzahl(antragDto.getTierAnzahl());
        antragEntity.setWeideflaeche(antragDto.getWeideflaeche());
        antragEntity.setGetreide(antragDto.isGetreide());
        antragEntity.setBluehpflanzen(antragDto.isBluehpflanzen());
        antragEntity.setAnderes(antragDto.isAnderes());
        antragEntity.setNichts(antragDto.isNichts());
        antragEntity.setAnbauflaeche(antragDto.getAnbauflaeche());
        antragEntity.setGesamtflaeche(antragDto.getGesamtflaeche());
        antragEntity.setKeinePflanzenschutzmittel(antragDto.isKeinePflanzenschutzmittel());
        antragEntity.setMin100qmGruenflaeche(antragDto.isMin100qmGruenflaeche());
        return antragEntity;
    }
}
