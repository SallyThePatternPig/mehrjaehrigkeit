package selina.praxisarbeit.mehrjaehrigkeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.converter.ConverterPerson;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.PersonEntity;
import selina.praxisarbeit.mehrjaehrigkeit.validation.PersonEntityValidator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PersonEntityValidator personEntityValidator;

    public List<PersonDto> readAllPersons(){
       TypedQuery<PersonEntity> p = entityManager.createQuery("select p from PersonEntity p", PersonEntity.class);
       List<PersonEntity> resultEntity = p.getResultList();
       return new ConverterPerson().convertToDto(resultEntity);
    }

    public void savePerson(PersonDto personDto){
        PersonEntity personEntity;
        if(personDto.getId() == null) {
            personEntity =  new PersonEntity();
        } else{
            personEntity = entityManager.find(PersonEntity.class, personDto.getId());
        }
        new ConverterPerson().convertToEntity(personDto, personEntity);
        personEntityValidator.validate(personEntity);
        entityManager.persist(personEntity);
    }

    public PersonDto readPersonFromId (Long personId){
        PersonEntity personEntity = entityManager.find(PersonEntity.class, personId);
        return new ConverterPerson().convertToDto(personEntity);
    }
}
