package selina.praxisarbeit.mehrjaehrigkeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.converter.ConverterProtokoll;
import selina.praxisarbeit.mehrjaehrigkeit.dto.ProtokollDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.ProtokollEntity;
import selina.praxisarbeit.mehrjaehrigkeit.entity.PersonEntity;
import selina.praxisarbeit.mehrjaehrigkeit.validation.ProtokollEntityValidator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class ProtokollService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProtokollEntityValidator protokollEntityValidator;

    public Set<ProtokollDto> readAllProtokolle(Long personId){
        Set<ProtokollEntity> protokolle = entityManager.find(PersonEntity.class, personId).getProtokolle();
        return new ConverterProtokoll().convertToDto(protokolle);
    }

    public void saveProtokoll(ProtokollDto protokollDto){
        PersonEntity personEntity = entityManager.find(PersonEntity.class, protokollDto.getPersonId());
        ProtokollEntity protokollEntity;
        if(protokollDto.getId() == null){
            protokollEntity = new ProtokollEntity();
            personEntity.getProtokolle().add(new ConverterProtokoll().convertToEntity(protokollDto, protokollEntity));
            protokollEntity.setAntragsteller(personEntity);
        }else{
            protokollEntity = entityManager.find(ProtokollEntity.class, protokollDto.getId());
            new ConverterProtokoll().convertToEntity(protokollDto, protokollEntity);
        }
        protokollEntityValidator.validate(protokollEntity);
        entityManager.persist(protokollEntity);
    }

    public ProtokollDto readProtokollFromId(Long protokollId){
        ProtokollEntity protokollEntity = entityManager.find(ProtokollEntity.class, protokollId);
        return new ConverterProtokoll().convertToDto(protokollEntity);
    }

    public void removeProtokoll(Long protokollId){
        ProtokollEntity protokollEntity = entityManager.find(ProtokollEntity.class, protokollId);
        entityManager.remove(protokollEntity);
    }

    public void removeAllProtokolle(PersonEntity personEntity){
        Set<ProtokollEntity> protokollEntitySet = personEntity.getProtokolle();
        for(ProtokollEntity protokollEntity: protokollEntitySet) {
           entityManager.remove(protokollEntity);
        }
    }
}
