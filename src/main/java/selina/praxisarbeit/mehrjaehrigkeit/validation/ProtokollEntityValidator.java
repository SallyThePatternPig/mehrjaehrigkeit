package selina.praxisarbeit.mehrjaehrigkeit.validation;

import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.common.AumBeantragungEnum;
import selina.praxisarbeit.mehrjaehrigkeit.entity.ProtokollEntity;
import selina.praxisarbeit.mehrjaehrigkeit.view.ProtokollJahrGui;


import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.parseInt;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.*;

@Service
public class ProtokollEntityValidator {

    public void validate(ProtokollEntity protokollEntity) {
        int tieranzahl = parseInt(protokollEntity.getTierAnzahl());
        int weideflaeche = parseInt(protokollEntity.getWeideflaeche());
        int mindestQmTiere = tieranzahl*qmProTier;
        int anbauflaeche = parseInt(protokollEntity.getAnbauflaeche());
        int gesamtflaeche = parseInt(protokollEntity.getGesamtflaeche());
        int korrekteGesamtflaeche = anbauflaeche + weideflaeche;
        int noetigeAUMGesamtflaeche = korrekteGesamtflaeche + aumGruenflaeche;
        boolean etwasAngebaut = (protokollEntity.isAnderes() || protokollEntity.isBluehpflanzen() || protokollEntity.isGetreide());

        if(protokollEntity.getErfassungsjahr()== erfassungsjahr1){
            validateErfassungsjahr1(protokollEntity);
        }else if(protokollEntity.getErfassungsjahr() == erfassungsjahr2){
            validateErfassungsjahr2(protokollEntity);
        }

        if (protokollEntity.getTiereVorhanden() == null){
            throw new ValidationException("Bei der Frage nach vorhandenen Tieren muss 'Ja' oder 'Nein' ausgewählt sein.");
        }
        if(protokollEntity.getTiereVorhanden() == Boolean.TRUE){
            if(protokollEntity.getTierAnzahl().equals(0)){
                throw new ValidationException("Da Tiere vorhanden sind, darf die Anzahl nicht 0 sein.");

            } else if(protokollEntity.getWeideflaeche() == null || weideflaeche < mindestQmTiere) {
                throw new ValidationException("Die minimale Quadratmeterzahl (" + qmProTier + ") pro Tier wurde nicht eingehalten. Die Zahl muss bei "
                        + tieranzahl + " Tieren mindestens " + mindestQmTiere + " qm betragen.");
            }
        }

        if (protokollEntity.getTiereVorhanden() == Boolean.FALSE && protokollEntity.getWeideflaeche() == null) {
            throw new ValidationException("Wenn keine Weidefläche vorhanden ist, muss 0 eingetragen werden.");
        }

        if ((anbauflaeche == 0 || protokollEntity.getAnbauflaeche() == null) && etwasAngebaut){
            throw new ValidationException("Das Feld für die Größe der Anbaufläche muss befüllt und  größer/gleich als 1 sein.");
        }

        if(korrekteGesamtflaeche == 0 && protokollEntity.getGesamtflaeche() == null) {
            throw new ValidationException("Wenn keine Fläche existiert, muss 0 eingetragen werden.");
        }

        if( gesamtflaeche < korrekteGesamtflaeche){
            throw new ValidationException("Die Gesamtfläche muss mindestens " + korrekteGesamtflaeche + " betragen.");
        }

        if ((protokollEntity.getMin100qmGruenflaeche().equals(AumBeantragungEnum.NEU_BEANTRAGT) ||
                protokollEntity.getMin100qmGruenflaeche().equals(AumBeantragungEnum.LAEUFT)) && gesamtflaeche < noetigeAUMGesamtflaeche){
            throw new ValidationException("Um die AUM zu beantragen müssen mindestens 100 qm ungenutzte Fläche existieren." +
                    "Die Quadratmeterzahl muss also mindestens " + noetigeAUMGesamtflaeche + " betragen.");
        }
    }

    private void validateErfassungsjahr1(ProtokollEntity protokollEntity){
        int anbauflaeche = parseInt(protokollEntity.getAnbauflaeche());
        boolean etwasAngebaut = (protokollEntity.isAnderes() || protokollEntity.isBluehpflanzen() || protokollEntity.isGetreide());
        if(!protokollEntity.isNichts() && !etwasAngebaut){
            throw new ValidationException("Es muss mindestens eine CheckBox ausgewählt sein.");
        }

        if(protokollEntity.isNichts() && etwasAngebaut){
            throw new ValidationException("Die CheckBox 'Nichts' darf nicht mit anderen CheckBoxen zusammen ausgewählt sein.");
        }

        if ((protokollEntity.getAnbauflaeche() == null || anbauflaeche != 0) && protokollEntity.isNichts()){
            throw new ValidationException("Wenn keine Fläche existiert, auf der etwas angebaut wird, muss 0 in das Feld eingtragen werden.");
        }
    }

    private void validateErfassungsjahr2(ProtokollEntity protokollEntity){
        int anbauflaeche = parseInt(protokollEntity.getAnbauflaeche());
        boolean etwasAngebaut = (protokollEntity.isAnderes() || protokollEntity.isBluehpflanzen() || protokollEntity.isGetreide());
        if (protokollEntity.getAnbauflaecheVorhanden() == null){
            throw new ValidationException("Bei der Frage nach vorhandener Anbaufläche muss 'Ja' oder 'Nein' ausgewählt sein.");
        }
        if(protokollEntity.getAnbauflaecheVorhanden().equals(Boolean.TRUE)){
            if(!etwasAngebaut){
                throw new ValidationException("Es muss mindestens eine CheckBox ausgewählt sein.");
            }
        }
        if (protokollEntity.getAnbauflaecheVorhanden().equals(Boolean.FALSE)){
            if(etwasAngebaut){
                throw new ValidationException("Da keine Anbaufläche vorhanden ist, darf auch keineCheckbox ausgewählt sein.");
            }else if(protokollEntity.getAnbauflaeche() == null || anbauflaeche != 0){
                throw new ValidationException("Wenn keine Fläche existiert,, auf der etwas angebaut wird, muss 0 in das Feld eingtragen werden.");
            }
        }
    }
}
