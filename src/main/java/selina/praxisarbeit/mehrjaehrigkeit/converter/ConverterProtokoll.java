package selina.praxisarbeit.mehrjaehrigkeit.converter;

import selina.praxisarbeit.mehrjaehrigkeit.dto.ProtokollDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.ProtokollEntity;

import java.util.*;

public class ConverterProtokoll {

    public ProtokollDto convertToDto(ProtokollEntity protokollEntity){
        ProtokollDto protokollDto = new ProtokollDto();
        protokollDto.setId(protokollEntity.getId());
        protokollDto.setPersonId(protokollEntity.getAntragsteller().getId());
        protokollDto.setErfassungsjahr(protokollEntity.getErfassungsjahr());
        protokollDto.setTiereVorhanden(protokollEntity.getTiereVorhanden());
        protokollDto.setTierAnzahl(protokollEntity.getTierAnzahl());
        protokollDto.setWeideflaeche(protokollEntity.getWeideflaeche());
        protokollDto.setGetreide(protokollEntity.isGetreide());
        protokollDto.setBluehpflanzen(protokollEntity.isBluehpflanzen());
        protokollDto.setAnderes(protokollEntity.isAnderes());
        protokollDto.setNichts(protokollEntity.isNichts());
        protokollDto.setAnbauflaeche(protokollEntity.getAnbauflaeche());
        protokollDto.setGesamtflaeche(protokollEntity.getGesamtflaeche());
        protokollDto.setKeinePflanzenschutzmittelEnum(protokollEntity.getKeinePflanzenschutzmittel());
        protokollDto.setMin100qmGruenflaecheEnum(protokollEntity.getMin100qmGruenflaeche());
        protokollDto.setAnbauflaecheVorhanden(protokollEntity.getAnbauflaecheVorhanden());
        protokollDto.setFeldhamsterEnum(protokollEntity.getFeldhamster());
        return protokollDto;
    }

    public Set<ProtokollDto> convertToDto(Set<ProtokollEntity> protokollEntitySet){
        Set<ProtokollDto> protokollDtos = new HashSet<>();
        for(ProtokollEntity protokollEntity: protokollEntitySet) {
            protokollDtos.add(convertToDto(protokollEntity));
        }
        return protokollDtos;
    }

    public List<ProtokollDto> convertToDto(List<ProtokollEntity> protokollEntityList){
        List<ProtokollDto> protokollDtoList = new ArrayList<>();
        for(ProtokollEntity protokollEntity: protokollEntityList) {
            protokollDtoList.add(convertToDto(protokollEntity));
        }
        return protokollDtoList;
    }

    public void convertToEntity(ProtokollDto protokollDto, ProtokollEntity protokollEntity){
        protokollEntity.setErfassungsjahr(protokollDto.getErfassungsjahr());
        protokollEntity.setTiereVorhanden(protokollDto.getTiereVorhanden());
        protokollEntity.setTierAnzahl(protokollDto.getTierAnzahl());
        protokollEntity.setWeideflaeche(protokollDto.getWeideflaeche());
        protokollEntity.setGetreide(protokollDto.isGetreide());
        protokollEntity.setBluehpflanzen(protokollDto.isBluehpflanzen());
        protokollEntity.setAnderes(protokollDto.isAnderes());
        protokollEntity.setNichts(protokollDto.isNichts());
        protokollEntity.setAnbauflaeche(protokollDto.getAnbauflaeche());
        protokollEntity.setGesamtflaeche(protokollDto.getGesamtflaeche());
        protokollEntity.setAnbauflaecheVorhanden(protokollDto.getAnbauflaecheVorhanden());
    }
}
