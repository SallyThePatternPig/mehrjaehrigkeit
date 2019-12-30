package selina.praxisarbeit.mehrjaehrigkeit.validation;

import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.common.AumBeantragungEnum;
import selina.praxisarbeit.mehrjaehrigkeit.entity.ProtokollEntity;


import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.parseInt;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.aumGruenflaeche;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.qmProTier;

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


        if (protokollEntity.getTiereVorhanden() == null){
            throw new ValidationException("Es muss 'Ja' oder 'Nein' ausgewählt sein.");
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

        if(!protokollEntity.isNichts() && !etwasAngebaut){
            throw new ValidationException("Es muss mindestens eine CheckBox ausgewählt sein.");
        }

        if(protokollEntity.isNichts() && etwasAngebaut){
            throw new ValidationException("Die CheckBox 'Nichts' darf nicht mit anderen CheckBoxen zusammen ausgewählt sein.");
        }

        if (protokollEntity.getAnbauflaeche() == null && protokollEntity.isNichts()){
            throw new ValidationException("Wenn keine Fläche, auf der etwas angebaut wird, existiert, muss 0 in das Feld eingtragen werden.");
        }

        if ((anbauflaeche == 0 || protokollEntity.getAnbauflaeche() == null) && etwasAngebaut){
            throw new ValidationException("Das Feld für die Größe der Anbaufläche muss befüllt und  größer als 0 sein.");
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
}
