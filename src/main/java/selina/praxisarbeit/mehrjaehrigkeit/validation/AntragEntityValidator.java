package selina.praxisarbeit.mehrjaehrigkeit.validation;

import org.springframework.stereotype.Service;
import selina.praxisarbeit.mehrjaehrigkeit.entity.AntragEntity;


import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.parseInt;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.aumGruenflaeche;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.qmProTier;

@Service
public class AntragEntityValidator {

    public void validate(AntragEntity antragEntity) {
        int tieranzahl = parseInt(antragEntity.getTierAnzahl());
        int weideflaeche = parseInt(antragEntity.getWeideflaeche());
        int mindestQmTiere = tieranzahl*qmProTier;
        int anbauflaeche = parseInt(antragEntity.getAnbauflaeche());
        int gesamtflaeche = parseInt(antragEntity.getGesamtflaeche());
        int korrekteGesamtflaeche = anbauflaeche + weideflaeche;
        int noetigeAUMGesamtflaeche = korrekteGesamtflaeche + aumGruenflaeche;
        boolean etwasAngebaut = (antragEntity.isAnderes() || antragEntity.isBluehpflanzen() || antragEntity.isGetreide());


        if (antragEntity.getTiereVorhanden() == null){
            throw new ValidationException("Es muss 'Ja' oder 'Nein' ausgewählt sein.");
        }
        if(antragEntity.getTiereVorhanden() == Boolean.TRUE){
            if(antragEntity.getTierAnzahl().equals(0)){
                throw new ValidationException("Da Tiere vorhanden sind, darf die Anzahl nicht 0 sein.");

            } else if(antragEntity.getWeideflaeche() == null || weideflaeche < mindestQmTiere) {
                throw new ValidationException("Die minimale Quadratmeterzahl (" + qmProTier + ") pro Tier wurde nicht eingehalten. Die Zahl muss bei "
                        + tieranzahl + " Tieren mindestens " + mindestQmTiere + " qm betragen.");
            }
        }

        if (antragEntity.getTiereVorhanden() == Boolean.FALSE && antragEntity.getWeideflaeche() == null) {
            throw new ValidationException("Wenn keine Weidefläche vorhanden ist, muss 0 eingetragen werden.");
        }

        if(!antragEntity.isNichts() && !etwasAngebaut){
            throw new ValidationException("Es muss mindestens eine CheckBox ausgewählt sein.");
        }

        if(antragEntity.isNichts() && etwasAngebaut){
            throw new ValidationException("Die CheckBox 'Nichts' darf nicht mit anderen CheckBoxen zusammen ausgewählt sein.");
        }

        if (antragEntity.getAnbauflaeche() == null && antragEntity.isNichts()){
            throw new ValidationException("Wenn keine Fläche, auf der etwas angebaut wird, existiert, muss 0 in das Feld eingtragen werden.");
        }

        if ((anbauflaeche == 0 || antragEntity.getAnbauflaeche() == null) && etwasAngebaut){
            throw new ValidationException("Das Feld für die Größe der Anbaufläche muss befüllt und  größer als 0 sein.");
        }

        if(korrekteGesamtflaeche == 0 && antragEntity.getGesamtflaeche() == null) {
            throw new ValidationException("Wenn keine Fläche existiert, muss 0 eingetragen werden.");
        }

        if( gesamtflaeche < korrekteGesamtflaeche){
            throw new ValidationException("Die Gesamtfläche muss mindestens " + korrekteGesamtflaeche + " betragen.");
        }

        if (antragEntity.isMin100qmGruenflaeche() && gesamtflaeche < noetigeAUMGesamtflaeche){
            throw new ValidationException("Um die AUM zu beantragen müssen mindestens 100 qm ungenutzte Fläche existieren." +
                    "Die Quadratmeterzahl muss also mindestens " + noetigeAUMGesamtflaeche + " betragen.");
        }
    }
}
