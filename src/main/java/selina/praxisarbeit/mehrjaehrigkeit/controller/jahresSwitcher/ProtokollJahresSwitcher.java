package selina.praxisarbeit.mehrjaehrigkeit.controller.jahresSwitcher;

import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.dto.ProtokollDto;
import selina.praxisarbeit.mehrjaehrigkeit.view.ProtokollJahrGui;

import javax.swing.*;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.formatDate;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.*;

public class ProtokollJahresSwitcher {

    private ProtokollJahrGui protokollGui;

    public ProtokollJahresSwitcher(ProtokollJahrGui protokollGui){
        this.protokollGui = protokollGui;
    }

    public void activateErfassungsjahrGui(int erfassungsjahr){
        setAllUnterschiedeInvisible();
        if(erfassungsjahr == erfassungsjahr1) {
            activateErfassungsjahr1Gui();
        } else if(erfassungsjahr == erfassungsjahr2){
            activateErfassungsjahr2Gui();
        }
    }

    public void fillErfassungsJahrGui(ProtokollDto protokollDto, PersonDto personDto){
        if(protokollDto.getErfassungsjahr() == erfassungsjahr1){
            fillErfassungsjahr1Gui(protokollDto, personDto);
            setGuiBeantragungsjahrAum(protokollGui.getBeantragungsJahrKeinePflanzenschutzmittelLabel(), protokollDto.getKeinePflanzenschutzmittelAbJahr());
            setGuiBeantragungsjahrAum(protokollGui.getBeantragungsJahrmin100QmGruenflaecheLabel(), protokollDto.getMin100qmGruenflaecheAbJahr());
        }else if(protokollDto.getErfassungsjahr() == erfassungsjahr2){
            fillErfassungsjahr2Gui(protokollDto, personDto);
            setGuiBeantragungsjahrAltAum(protokollGui.getBeantragungsJahrKeinePflanzenschutzmittelLabel(),
                    protokollGui.getKeineNutzungPflanzenschutzmittelLabel(), protokollDto.getKeinePflanzenschutzmittelAbJahr());
            setGuiBeantragungsjahrAum(protokollGui.getBeantragungsJahrmin100QmGruenflaecheLabel(), protokollDto.getMin100qmGruenflaecheAbJahr());
            setGuiBeantragungsjahrAum(protokollGui.getBeantragungsJahrFeldhamsterLabel(), protokollDto.getFeldhamsterAbJahr());
        }
    }

    public void updateDtoErfassungsjahrFelder(ProtokollDto protokollDto, ProtokollJahrGui protokollGui){
        if(protokollDto.getErfassungsjahr() == erfassungsjahr1){
            updtateDtoErfassungsjahr1(protokollDto, protokollGui);
        }else if(protokollDto.getErfassungsjahr() == erfassungsjahr2){
            updateDtoErfassungsjahr2(protokollDto, protokollGui);
        }
    }

    private void activateErfassungsjahr1Gui(){
        protokollGui.getGeburtsdatumLabel().setVisible(true);
        protokollGui.getGeburtsdatumDtoFillLabel().setVisible(true);
        protokollGui.getNichtsCheckBox().setVisible(true);
        protokollGui.getKeineNutzungPflanzenschutzmittelnCheckBox().setVisible(true);
        protokollGui.getKeineNutzungPflanzenschutzmittelLabel().setVisible(true);
    }

    private void activateErfassungsjahr2Gui(){
        protokollGui.getStandortLabel().setVisible(true);
        protokollGui.getStandortDtoFillLabel().setVisible(true);
        protokollGui.getAnbauflaecheVorhandenFrageLabel().setVisible(true);
        protokollGui.getAnbauflaecheVorhandenJaRadioButton().setVisible(true);
        protokollGui.getAnbauFlaecheVorhandenNeinRadioButton().setVisible(true);
        protokollGui.getFeldhamsterCheckBox().setVisible(true);
        protokollGui.getFeldhamsterLabel().setVisible(true);
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

    private void updtateDtoErfassungsjahr1(ProtokollDto protokollDto, ProtokollJahrGui gui){
        protokollDto.setNichts(gui.getNichtsCheckBox().isSelected());
        protokollDto.setKeinePflanzenschutzmittel(gui.getKeineNutzungPflanzenschutzmittelnCheckBox().isSelected());
    }

    private void updateDtoErfassungsjahr2(ProtokollDto protokollDto, ProtokollJahrGui gui){
        protokollDto.setFeldhamster(gui.getFeldhamsterCheckBox().isSelected());
        if(gui.getAnbauflaecheVorhandenJaRadioButton().isSelected()){
            protokollDto.setAnbauflaecheVorhanden(Boolean.TRUE);
        }else if(gui.getAnbauFlaecheVorhandenNeinRadioButton().isSelected()){
            protokollDto.setAnbauflaecheVorhanden(Boolean.FALSE);
        }
    }

    private void setGuiBeantragungsjahrAltAum(JLabel beantragungsjahrLabel, JLabel aumText, Integer beantragungsjahr){
        if(beantragungsjahr == null){
            beantragungsjahrLabel.setVisible(false);
            aumText.setVisible(false);
        }else{
            beantragungsjahrLabel.setText(getAumBeantragungsJahrText(beantragungsjahr));
        }
    }

    private void setGuiBeantragungsjahrAum(JLabel beantragungsjahrLabel, Integer beantragungsjahr){
        if(beantragungsjahr == null){
            beantragungsjahrLabel.setVisible(false);
        }else{
            beantragungsjahrLabel.setVisible(true);
            beantragungsjahrLabel.setText(getAumBeantragungsJahrText(beantragungsjahr));
        }
    }

    private String getAumBeantragungsJahrText(Integer beantragungsjahr){
        String beantragungsjahrText = leererString;
        if(beantragungsjahr != null){
            beantragungsjahrText = "Beantragt in" + beantragungsjahr;
        }
        return beantragungsjahrText;
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
        protokollGui.getKeineNutzungPflanzenschutzmittelLabel().setVisible(false);
        protokollGui.getBeantragungsJahrKeinePflanzenschutzmittelLabel().setVisible(false);
        protokollGui.getFeldhamsterCheckBox().setVisible(false);
        protokollGui.getFeldhamsterLabel().setVisible(false);
        protokollGui.getBeantragungsJahrFeldhamsterLabel().setVisible(false);
        protokollGui.getBeantragungsJahrmin100QmGruenflaecheLabel().setVisible(false);
    }
}
