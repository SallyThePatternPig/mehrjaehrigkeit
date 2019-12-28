package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.dto.AntragDto;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.AntragService;
import selina.praxisarbeit.mehrjaehrigkeit.service.PersonService;
import selina.praxisarbeit.mehrjaehrigkeit.validation.ValidationException;
import selina.praxisarbeit.mehrjaehrigkeit.view.AntragJahr1Gui;
import selina.praxisarbeit.mehrjaehrigkeit.view.MessageDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.*;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.defaultZahl;

@Controller
public class AntragJahrController {

    @Autowired
    private StartseiteAntraegeController startseiteAntraegeCotroller;

    @Autowired
    private AntragService antragService;

    @Autowired
    private PersonService personService;

    private JFrame myFrame;

    private Long personId;

    private AntragDto antragDto;

    private PersonDto personDto;

    private int erfassungsjahr1 = 2019;

    private AntragJahr1Gui gui = new AntragJahr1Gui();

    public AntragJahrController() {

        gui.getAbbrechenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getAntragJahr1Panel());
                startseiteAntraegeCotroller.drawGui(myFrame, personId);
            }
        });

        gui.getSpeichernButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateAntragDto();
                    antragService.saveAntrag(antragDto);
                    myFrame.remove(gui.getAntragJahr1Panel());
                    startseiteAntraegeCotroller.drawGui(myFrame, personId);
                } catch (ParseException exception) {
                    new MessageDialog(gui.getAntragJahr1Panel(), "Alle Felder müssen ausgefüllt werden.");
                } catch (ValidationException exception) {
                    new MessageDialog(gui.getAntragJahr1Panel(), exception.getMessage());
                }

            }
        });
    }

    public void drawGui(JFrame frame, Long personId, Long antragId) {
        if (antragId == null) {
            this.antragDto = new AntragDto();
            antragDto.setPersonId(personId);
            antragDto.setTierAnzahl(defaultZahl);
        } else {
            this.antragDto = antragService.readAntragFromId(antragId);
        }
        this.personId = personId;
        this.personDto = personService.readPersonFromId(personId);
        fillAntragGUI();
        frame.add(gui.getAntragJahr1Panel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;

    }

    private void updateAntragDto() throws ParseException {
        if (gui.getTiereVorhandenJaRadioButton().isSelected()) {
            antragDto.setTiereVorhanden(Boolean.TRUE);
        } else if (gui.getTiereVorhandenNeinRadioButton().isSelected()) {
            antragDto.setTiereVorhanden(Boolean.FALSE);
        }
        antragDto.setErfassungsjahr(erfassungsjahr1);
        antragDto.setTierAnzahl((Integer) gui.getTieranzahlSpinner().getValue());
        antragDto.setWeideflaeche(parseBigDecimal(gui.getWeideflaecheTextField().getText()));
        antragDto.setGetreide(gui.getGetreideCheckBox().isSelected());
        antragDto.setBluehpflanzen(gui.getBluehpflanzenCheckBox().isSelected());
        antragDto.setAnderes(gui.getAnderesCheckBox().isSelected());
        antragDto.setNichts(gui.getNichtsCheckBox().isSelected());
        antragDto.setAnbauflaeche(parseBigDecimal(gui.getAnbauflaecheTextField().getText()));
        antragDto.setGesamtflaeche(parseBigDecimal(gui.getGesamtFlaecheTextField().getText()));
        antragDto.setKeinePflanzenschutzmittel(gui.getKeineNutzungPflanzenschutzmittelnCheckBox().isSelected());
        antragDto.setMin100qmGruenflaeche(gui.getMin100QmGruenflaecheCheckBox().isSelected());
    }

    private void fillAntragGUI() {
        if(antragDto.getId() == null){
            gui.getBearbeitenErstellenLabel().setText("Neues Protokoll erstellen");
        } else {
            gui.getBearbeitenErstellenLabel().setText("Antrag (ID: " + antragDto.getId() + ") bearbeiten");
        }
        gui.getIdLabel().setText(personDto.getId().toString());
        gui.getNachnameLabel().setText(personDto.getNachname());
        gui.getVornameLabel().setText(personDto.getVorname());
        gui.getGeschlechtLabel().setText(personDto.getGeschlecht().toString());
        gui.getGeburtsdatumLabel().setText(formatDate(personDto.getGeburtstdatum()));

        if (antragDto.getTiereVorhanden() == Boolean.TRUE) {
            gui.getTiereVorhandenJaRadioButton().setSelected(true);
        } else if (antragDto.getTiereVorhanden() == Boolean.FALSE) {
            gui.getTiereVorhandenNeinRadioButton().setSelected(true);
        }
        gui.getTieranzahlSpinner().setValue(antragDto.getTierAnzahl());
        gui.getWeideflaecheTextField().setText(formatBigDecimal(antragDto.getWeideflaeche()));
        gui.getGetreideCheckBox().setSelected(antragDto.isGetreide());
        gui.getBluehpflanzenCheckBox().setSelected(antragDto.isBluehpflanzen());
        gui.getAnderesCheckBox().setSelected(antragDto.isAnderes());
        gui.getNichtsCheckBox().setSelected(antragDto.isNichts());
        gui.getAnbauflaecheTextField().setText(formatBigDecimal(antragDto.getAnbauflaeche()));
        gui.getGesamtFlaecheTextField().setText(formatBigDecimal(antragDto.getGesamtflaeche()));
        gui.getKeineNutzungPflanzenschutzmittelnCheckBox().setSelected(antragDto.isKeinePflanzenschutzmittel());
        gui.getMin100QmGruenflaecheCheckBox().setSelected(antragDto.isMin100qmGruenflaeche());
    }
}
