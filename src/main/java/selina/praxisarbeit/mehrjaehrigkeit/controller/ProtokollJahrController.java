package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.common.AumBeantragungEnum;
import selina.praxisarbeit.mehrjaehrigkeit.dto.ProtokollDto;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.ProtokollService;
import selina.praxisarbeit.mehrjaehrigkeit.service.PersonService;
import selina.praxisarbeit.mehrjaehrigkeit.validation.ValidationException;
import selina.praxisarbeit.mehrjaehrigkeit.view.ProtokollJahr1Gui;
import selina.praxisarbeit.mehrjaehrigkeit.view.MessageDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.*;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.defaultZahl;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.erfassungsjahr1;

@Controller
public class ProtokollJahrController {

    @Autowired
    private StartseiteProtokolleController startseiteProtokolleCotroller;

    @Autowired
    private ProtokollService protokollService;

    @Autowired
    private PersonService personService;

    private JFrame myFrame;

    private Long personId;

    private ProtokollDto protokollDto;

    private PersonDto personDto;

    private ProtokollJahr1Gui gui = new ProtokollJahr1Gui();

    public ProtokollJahrController() {

        gui.getAbbrechenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getProtokollJahr1Panel());
                startseiteProtokolleCotroller.drawGui(myFrame, personId);
            }
        });

        gui.getSpeichernButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateProtokollDto();
                    protokollService.saveProtokoll(protokollDto);
                    myFrame.remove(gui.getProtokollJahr1Panel());
                    startseiteProtokolleCotroller.drawGui(myFrame, personId);
                } catch (ParseException exception) {
                    new MessageDialog(gui.getProtokollJahr1Panel(), "Alle Felder müssen ausgefüllt werden.");
                } catch (ValidationException exception) {
                    new MessageDialog(gui.getProtokollJahr1Panel(), exception.getMessage());
                }

            }
        });
    }

    public void drawGui(JFrame frame, Long personId, Long protokollId) {
        if (protokollId == null) {
            this.protokollDto = new ProtokollDto();
            protokollDto.setPersonId(personId);
            protokollDto.setKeinePflanzenschutzmittel(AumBeantragungEnum.NICHT_BEANTRAGT);
            protokollDto.setMin100qmGruenflaeche(AumBeantragungEnum.NICHT_BEANTRAGT);
            protokollDto.setTierAnzahl(defaultZahl);
        } else {
            this.protokollDto = protokollService.readProtokollFromId(protokollId);
        }
        this.personId = personId;
        this.personDto = personService.readPersonFromId(personId);
        fillProtokollGUI();
        frame.add(gui.getProtokollJahr1Panel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;

    }

    private void updateProtokollDto() throws ParseException {
        if (gui.getTiereVorhandenJaRadioButton().isSelected()) {
            protokollDto.setTiereVorhanden(Boolean.TRUE);
        } else if (gui.getTiereVorhandenNeinRadioButton().isSelected()) {
            protokollDto.setTiereVorhanden(Boolean.FALSE);
        }
        protokollDto.setErfassungsjahr(erfassungsjahr1);
        protokollDto.setTierAnzahl((Integer) gui.getTieranzahlSpinner().getValue());
        protokollDto.setWeideflaeche(parseBigDecimal(gui.getWeideflaecheTextField().getText()));
        protokollDto.setGetreide(gui.getGetreideCheckBox().isSelected());
        protokollDto.setBluehpflanzen(gui.getBluehpflanzenCheckBox().isSelected());
        protokollDto.setAnderes(gui.getAnderesCheckBox().isSelected());
        protokollDto.setNichts(gui.getNichtsCheckBox().isSelected());
        protokollDto.setAnbauflaeche(parseBigDecimal(gui.getAnbauflaecheTextField().getText()));
        protokollDto.setGesamtflaeche(parseBigDecimal(gui.getGesamtFlaecheTextField().getText()));
        protokollDto.setKeinePflanzenschutzmittel(booleanToAumEnum(gui.getKeineNutzungPflanzenschutzmittelnCheckBox().isSelected()));
        protokollDto.setMin100qmGruenflaeche(booleanToAumEnum(gui.getMin100QmGruenflaecheCheckBox().isSelected()));
    }

    private void fillProtokollGUI() {
        if(protokollDto.getId() == null){
            gui.getBearbeitenErstellenLabel().setText("Neues Protokoll erstellen");
        } else {
            gui.getBearbeitenErstellenLabel().setText("Protokoll (ID: " + protokollDto.getId() + ") bearbeiten");
        }
        gui.getIdLabel().setText(personDto.getId().toString());
        gui.getNachnameLabel().setText(personDto.getNachname());
        gui.getVornameLabel().setText(personDto.getVorname());
        gui.getGeschlechtLabel().setText(personDto.getGeschlecht().toString());
        gui.getGeburtsdatumLabel().setText(formatDate(personDto.getGeburtstdatum()));

        if (protokollDto.getTiereVorhanden() == Boolean.TRUE) {
            gui.getTiereVorhandenJaRadioButton().setSelected(true);
        } else if (protokollDto.getTiereVorhanden() == Boolean.FALSE) {
            gui.getTiereVorhandenNeinRadioButton().setSelected(true);
        }
        gui.getTieranzahlSpinner().setValue(protokollDto.getTierAnzahl());
        gui.getWeideflaecheTextField().setText(formatBigDecimal(protokollDto.getWeideflaeche()));
        gui.getGetreideCheckBox().setSelected(protokollDto.isGetreide());
        gui.getBluehpflanzenCheckBox().setSelected(protokollDto.isBluehpflanzen());
        gui.getAnderesCheckBox().setSelected(protokollDto.isAnderes());
        gui.getNichtsCheckBox().setSelected(protokollDto.isNichts());
        gui.getAnbauflaecheTextField().setText(formatBigDecimal(protokollDto.getAnbauflaeche()));
        gui.getGesamtFlaecheTextField().setText(formatBigDecimal(protokollDto.getGesamtflaeche()));
        gui.getKeineNutzungPflanzenschutzmittelnCheckBox().setSelected(aumEnumToboolean(protokollDto.getKeinePflanzenschutzmittel()));
        gui.getMin100QmGruenflaecheCheckBox().setSelected(aumEnumToboolean(protokollDto.getMin100qmGruenflaeche()));
    }

    private boolean aumEnumToboolean(AumBeantragungEnum aumEnum){
            return aumEnum.equals(AumBeantragungEnum.NEU_BEANTRAGT);
    }

    private AumBeantragungEnum booleanToAumEnum(boolean checkboxWert){
        AumBeantragungEnum aumEnum;
        if(checkboxWert){
            aumEnum = AumBeantragungEnum.NEU_BEANTRAGT;
        } else{
            aumEnum = AumBeantragungEnum.NICHT_BEANTRAGT;
        }
        return aumEnum;
    }
}
