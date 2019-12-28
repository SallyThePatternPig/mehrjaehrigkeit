package selina.praxisarbeit.mehrjaehrigkeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.converter.ConverterAntrag;
import selina.praxisarbeit.mehrjaehrigkeit.converter.ConverterPerson;
import selina.praxisarbeit.mehrjaehrigkeit.dto.AntragDto;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.AntragEntity;
import selina.praxisarbeit.mehrjaehrigkeit.entity.PersonEntity;
import selina.praxisarbeit.mehrjaehrigkeit.validation.AntragEntityValidator;
import selina.praxisarbeit.mehrjaehrigkeit.validation.PersonEntityValidator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AntragService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AntragEntityValidator antragEntityValidator;

    public Set<AntragDto> readAllAntraege(Long personId){
        Set<AntragEntity> antraege = entityManager.find(PersonEntity.class, personId).getAntraege();
        return new ConverterAntrag().convertToDto(antraege);
    }

    public void saveAntrag(AntragDto antragDto){
        PersonEntity personEntity = entityManager.find(PersonEntity.class, antragDto.getPersonId());
        AntragEntity antragEntity;
        if(antragDto.getId() == null){
            antragEntity = new AntragEntity();
            personEntity.getAntraege().add(new ConverterAntrag().convertToEntity(antragDto, antragEntity));
            antragEntity.setAntragsteller(personEntity);
        }else{
            antragEntity = entityManager.find(AntragEntity.class, antragDto.getId());
            new ConverterAntrag().convertToEntity(antragDto, antragEntity);
        }
        antragEntityValidator.validate(antragEntity);
        entityManager.persist(antragEntity);
    }

    public AntragDto readAntragFromId(Long antragId){
        AntragEntity antragEntity = entityManager.find(AntragEntity.class, antragId);
        return new ConverterAntrag().convertToDto(antragEntity);
    }
}
