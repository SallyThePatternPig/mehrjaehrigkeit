package selina.praxisarbeit.mehrjaehrigkeit.validation;

import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.PersonEntity;

import javax.persistence.EntityManager;

@Service
public class PersonEntityValidator {

    public void validate(PersonEntity personEntity){
        if (personEntity.getNachname() == null){
            throw new ValidationException("Es muss ein Nachnamen angegeben werden.");

        } else if (personEntity.getVorname() == null){
            throw new ValidationException("Es muss ein Vorname angegeben werden.");

        } else if (personEntity.getGeschlecht() == null){
            throw new ValidationException("Es muss ein Geschlecht angegeben werden.");

        } else if (personEntity.getStandort() == null){
            throw new ValidationException("Es muss ein Standort angegeben werden.");
        }
    }

}
