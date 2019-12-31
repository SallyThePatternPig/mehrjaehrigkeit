package selina.praxisarbeit.mehrjaehrigkeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.common.AumBeantragungEnum;
import selina.praxisarbeit.mehrjaehrigkeit.converter.ConverterProtokoll;
import selina.praxisarbeit.mehrjaehrigkeit.dto.ProtokollDto;
import selina.praxisarbeit.mehrjaehrigkeit.entity.ProtokollEntity;
import selina.praxisarbeit.mehrjaehrigkeit.entity.PersonEntity;
import selina.praxisarbeit.mehrjaehrigkeit.validation.ProtokollEntityValidator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Set;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.getAktuellesJahr;
import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.getFirstListelement;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.*;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.getAktuellesJahr;

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
            personEntity.getProtokolle().add(protokollEntity);
            protokollEntity.setAntragsteller(personEntity);
        }else{
            protokollEntity = entityManager.find(ProtokollEntity.class, protokollDto.getId());
        }
        new ConverterProtokoll().convertToEntity(protokollDto, protokollEntity);
        Integer keineSchutzmittelJahr = getKeineSchutzmittelBeantragungsJahr(protokollDto.isKeinePflanzenschutzmittel(), personEntity.getId());
        Integer min100QmGruenflaecheJahr = getMin100QmBeantragungsJahr(protokollDto.isMin100qmGruenflaeche(), personEntity.getId());
        Integer feldhamsterJahr = getFeldhamsterBeantragungsjahr(protokollDto.isFeldhamster(), personEntity.getId());
        protokollEntity.setKeinePflanzenschutzmittel(convertToAumEnum(keineSchutzmittelJahr));
        protokollEntity.setMin100qmGruenflaeche(convertToAumEnum(min100QmGruenflaecheJahr));
        protokollEntity.setFeldhamster(convertToAumEnum(feldhamsterJahr));
        protokollEntityValidator.validate(protokollEntity);
        entityManager.persist(protokollEntity);
    }

    public ProtokollDto readProtokollFromId(Long protokollId){
        ProtokollEntity protokollEntity = entityManager.find(ProtokollEntity.class, protokollId);
        Long personId = protokollEntity.getAntragsteller().getId();
        ProtokollDto protokollDto = new ConverterProtokoll().convertToDto(protokollEntity);
        protokollDto.setKeinePflanzenschutzmittel(aumEnumToboolean(protokollEntity.getKeinePflanzenschutzmittel()));
        protokollDto.setKeinePflanzenschutzmittelAbJahr(getKeineSchutzmittelBeantragungsJahr(protokollDto.isKeinePflanzenschutzmittel(), personId));
        protokollDto.setMin100qmGruenflaeche(aumEnumToboolean(protokollEntity.getMin100qmGruenflaeche()));
        protokollDto.setMin100qmGruenflaecheAbJahr(getMin100QmBeantragungsJahr(protokollDto.isMin100qmGruenflaeche(), personId));
        protokollDto.setFeldhamster(aumEnumToboolean(protokollEntity.getFeldhamster()));
        protokollDto.setFeldhamsterAbJahr(getFeldhamsterBeantragungsjahr(protokollDto.isFeldhamster(), personId));
        return protokollDto;
    }

    public ProtokollDto createNewProtokollDto(Long personId){
        ProtokollDto protokollDto = new ProtokollDto();
        protokollDto.setPersonId(personId);
        protokollDto.setTierAnzahl(defaultZahl);
        protokollDto.setErfassungsjahr(getAktuellesJahr());
        Integer beantragungsJahrKeineSchutzmittel = getKeineSchutzmittelBeantragungsJahr(false, personId);
        Integer beantragungsJahrMin100QM = getMin100QmBeantragungsJahr(false, personId);
        Integer beantragungsJahrFeldhamster = getFeldhamsterBeantragungsjahr(false, personId);

        if(beantragungsJahrKeineSchutzmittel != null) {
            protokollDto.setKeinePflanzenschutzmittelAbJahr(beantragungsJahrKeineSchutzmittel);
        }
        if( beantragungsJahrMin100QM != null) {
            protokollDto.setMin100qmGruenflaecheAbJahr(getMin100QmBeantragungsJahr(false, personId));
        }
        if( beantragungsJahrFeldhamster != null) {
            protokollDto.setFeldhamsterAbJahr(beantragungsJahrFeldhamster);
        }
        return protokollDto;
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

    public boolean isHatFolgejahre(Long personId, int erfassungsjahr){
        TypedQuery<ProtokollEntity> query = entityManager.createQuery("select p from ProtokollEntity p where " +
                "p.erfassungsjahr > :jahr and p.antragsteller.id = :personid", ProtokollEntity.class);
        query.setParameter("jahr", erfassungsjahr);
        query.setParameter("personid", personId);
        return query.getResultList().size() != 0;
    }

    public boolean isKeinProtokollImJahr(Long personId){
        TypedQuery<ProtokollEntity> query = entityManager.createQuery("select p from ProtokollEntity p where " +
                "p.erfassungsjahr = :jahr and p.antragsteller.id = :personid", ProtokollEntity.class);
        query.setParameter("jahr", getAktuellesJahr());
        query.setParameter("personid", personId);
        return query.getResultList().size() == 0;
    }

    private boolean aumEnumToboolean(AumBeantragungEnum aumEnum){
        if(aumEnum == null){
            return false;
        } else{
            return aumEnum.equals(AumBeantragungEnum.NEU_BEANTRAGT);
        }
    }

    private AumBeantragungEnum convertToAumEnum(Integer beantragungsjahr) {
        if(beantragungsjahr == null){
            return AumBeantragungEnum.NICHT_BEANTRAGT;
        }else if(beantragungsjahr.equals(getAktuellesJahr())){
            return AumBeantragungEnum.NEU_BEANTRAGT;
        }else if(beantragungsjahr > getAktuellesJahr()-aumGueltigkeit){
            return AumBeantragungEnum.LAEUFT;
        }
        throw new IllegalArgumentException(beantragungsjahr.toString());
    }

    private Integer getMin100QmBeantragungsJahr(boolean checkboxWert, Long personId) {
        if (checkboxWert) {
            return getAktuellesJahr();
        } else {
            for (int i = getAktuellesJahr(); i > getAktuellesJahr() - aumGueltigkeit; i--) {
                //TODO: Ergebnis der Datenbankabfrage in einer Klasse zwischenspeichern
                ProtokollEntity protokollEntity = loadVorjahresProtokoll(i, personId);
                if(protokollEntity == null){
                    return null;
                }
                if (protokollEntity.getMin100qmGruenflaeche() == null || protokollEntity.getMin100qmGruenflaeche().equals(AumBeantragungEnum.NICHT_BEANTRAGT)) {
                    return null;
                } else if (protokollEntity.getMin100qmGruenflaeche().equals(AumBeantragungEnum.NEU_BEANTRAGT)) {
                    return i-1; //i ist das eingegebene Jahr und i-1 das vorjahr
                }
            }
            return null;
        }
    }

    private Integer getKeineSchutzmittelBeantragungsJahr(boolean checkboxWert, Long personId){
        if(checkboxWert){
            return getAktuellesJahr();
        }else{
            for(int i = getAktuellesJahr(); i > getAktuellesJahr()-aumGueltigkeit; i--){
                ProtokollEntity protokollEntity = loadVorjahresProtokoll(i, personId);
                if(protokollEntity == null){
                    return null;
                }
                if(protokollEntity.getKeinePflanzenschutzmittel() == null || protokollEntity.getKeinePflanzenschutzmittel().equals(AumBeantragungEnum.NICHT_BEANTRAGT)){
                    return null;
                }else if(protokollEntity.getKeinePflanzenschutzmittel().equals(AumBeantragungEnum.NEU_BEANTRAGT)){
                    return i-1;
                }
            }
            return null;
        }
    }

    private Integer getFeldhamsterBeantragungsjahr(boolean checkboxWert, Long personId){
        if(checkboxWert){
            return getAktuellesJahr();
        }else{
            for(int i = getAktuellesJahr(); i > getAktuellesJahr()-aumGueltigkeit; i--){
                ProtokollEntity protokollEntity = loadVorjahresProtokoll(i, personId);
                if(protokollEntity == null){
                    return null;
                }
                if(protokollEntity.getFeldhamster()== null || protokollEntity.getFeldhamster().equals(AumBeantragungEnum.NICHT_BEANTRAGT)){
                    return null;
                }else if(protokollEntity.getFeldhamster().equals(AumBeantragungEnum.NEU_BEANTRAGT)){
                    return i-1;
                }
            }
            return null;
        }
    }

    private ProtokollEntity loadVorjahresProtokoll(int akutellesJahr, Long personId){
        TypedQuery<ProtokollEntity> query = entityManager.createQuery("select p from ProtokollEntity p where " +
                "p.erfassungsjahr = :jahr and p.antragsteller.id = :personid", ProtokollEntity.class);
        query.setParameter("jahr", akutellesJahr-1);
        query.setParameter("personid", personId);
        return getFirstListelement(query.getResultList());
    }
}
