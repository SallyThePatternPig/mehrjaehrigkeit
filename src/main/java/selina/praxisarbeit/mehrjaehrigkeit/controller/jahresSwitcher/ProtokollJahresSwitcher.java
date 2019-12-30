package selina.praxisarbeit.mehrjaehrigkeit.controller.jahresSwitcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.dto.ProtokollDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.ProtokollService;
import selina.praxisarbeit.mehrjaehrigkeit.view.AuswahlBestehenderProtokolleGui;
import selina.praxisarbeit.mehrjaehrigkeit.view.ProtokollJahr1Gui;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.formatDate;
import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.getAktuellesJahr;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.*;

@Controller
public class ProtokollJahresSwitcher {

    @Autowired
    private ProtokollService protokollService;

    private ProtokollJahr1Gui protokollGui;

    private AuswahlBestehenderProtokolleGui auswahlProtokollGui;

    public ProtokollJahresSwitcher(){
        setAllUnterschiedeInvisible();
    }

    public ProtokollJahr1Gui activateErfassungsjahrGui(int erfassungsjahr, ProtokollJahr1Gui gui){
        this.protokollGui = gui;
        if(erfassungsjahr == erfassungsjahr1) {
            activateErfassungsjahr1Gui();
        } else if(erfassungsjahr == erfassungsjahr2){
            activateErfassungsjahr2Gui();
        }

        return protokollGui;
    }

    public ProtokollJahr1Gui fillErfassungsJahrGui(ProtokollDto protokollDto, PersonDto personDto, ProtokollJahr1Gui gui){
        this.protokollGui = gui;
        if(protokollDto.getErfassungsjahr() == erfassungsjahr1){
            fillErfassungsjahr1Gui(protokollDto, personDto);
        }else if(protokollDto.getErfassungsjahr() == erfassungsjahr2){
            fillErfassungsjahr2Gui(protokollDto, personDto);
        }
        return protokollGui;
    }

    private boolean activateAum(Integer aumJahr, int erfassungsjahr){
        if(aumJahr == null) {
            return false;
        }else{
            return aumJahr + aumGueltigkeit > erfassungsjahr;
            }
    }

    private void activateErfassungsjahr1Gui(){
        protokollGui.getGeburtsdatumLabel().setVisible(true);
        protokollGui.getGeburtsdatumDtoFillLabel().setVisible(true);
        protokollGui.getNichtsCheckBox().setVisible(true);
        protokollGui.getKeineNutzungPflanzenschutzmittelnCheckBox().setVisible(true);
    }

    private void activateErfassungsjahr2Gui(){
        protokollGui.getStandortLabel().setVisible(true);
        protokollGui.getStandortDtoFillLabel().setVisible(true);
        protokollGui.getAnbauflaecheVorhandenFrageLabel().setVisible(true);
        protokollGui.getAnbauflaecheVorhandenJaRadioButton().setVisible(true);
        protokollGui.getAnbauFlaecheVorhandenNeinRadioButton().setVisible(true);
        protokollGui.getFeldhamsterCheckBox().setVisible(true);
    }

    private void fillErfassungsjahr1Gui(ProtokollDto protokollDto, PersonDto personDto){
        protokollGui.getGeburtsdatumDtoFillLabel().setText(formatDate(personDto.getGeburtstdatum()));
        protokollGui.getNichtsCheckBox().setSelected(protokollDto.isNichts());
        protokollGui.getKeineNutzungPflanzenschutzmittelnCheckBox().setSelected(protokollDto.isKeinePflanzenschutzmittel());
    }

    private void fillErfassungsjahr2Gui(ProtokollDto protokollDto, PersonDto personDto){
        protokollGui.getStandortDtoFillLabel().setText(personDto.getStandort());
        if (protokollDto.getAnbauflaecheVorhanden() == Boolean.TRUE) {
            protokollGui.getAnbauflaecheVorhandenJaRadioButton().setSelected(true);
        } else if (protokollDto.getAnbauflaecheVorhanden() == Boolean.FALSE) {
            protokollGui.getAnbauFlaecheVorhandenNeinRadioButton().setSelected(true);
        }
        protokollGui.getFeldhamsterCheckBox().setSelected(protokollDto.isFeldhamster());
    }

    private void setAllUnterschiedeInvisible(){
        protokollGui.getGeburtsdatumLabel().setVisible(false);
        protokollGui.getGeburtsdatumDtoFillLabel().setVisible(false);
        protokollGui.getStandortLabel().setVisible(false);
        protokollGui.getStandortDtoFillLabel().setVisible(false);
        protokollGui.getAnbauflaecheVorhandenFrageLabel().setVisible(false);
        protokollGui.getAnbauflaecheVorhandenJaRadioButton().setVisible(false);
        protokollGui.getAnbauFlaecheVorhandenNeinRadioButton().setVisible(false);
        protokollGui.getNichtsCheckBox().setVisible(false);
        protokollGui.getKeineNutzungPflanzenschutzmittelnCheckBox().setVisible(false);
        protokollGui.getFeldhamsterCheckBox().setVisible(false);
    }
}
