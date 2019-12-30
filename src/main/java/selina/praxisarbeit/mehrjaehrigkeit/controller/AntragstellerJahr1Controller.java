package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.common.GeschlechtEnum;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.PersonService;
import selina.praxisarbeit.mehrjaehrigkeit.validation.ValidationException;
import selina.praxisarbeit.mehrjaehrigkeit.view.AntragstellerJahr1Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.*;

@Controller
public class AntragstellerJahr1Controller {

    @Autowired
    private StartseiteAntragstellerController startseiteAntragstellerController;

    @Autowired
    private PersonService personService;

    private JFrame myFrame;

    private PersonDto personDto;

    private AntragstellerJahr1Gui gui = new AntragstellerJahr1Gui();

    public AntragstellerJahr1Controller(){
        for(GeschlechtEnum geschlecht: GeschlechtEnum.values()) {
            gui.getGeschlechtComboBox().addItem(geschlecht);
        }

        gui.getAbbrechenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getAntragstellerJahr1Panel());
                startseiteAntragstellerController.drawGui(myFrame);
            }
        });

        gui.getSpeichernButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updatePersonDto();
                    personService.savePerson(personDto);
                    myFrame.remove(gui.getAntragstellerJahr1Panel());
                    startseiteAntragstellerController.drawGui(myFrame);
                }
                catch (ParseException exception){
                    JOptionPane.showMessageDialog(gui.getAntragstellerJahr1Panel(), "Wer das liest, ist doof.");
                }
                catch (ValidationException exception){
                    JOptionPane.showMessageDialog(gui.getAntragstellerJahr1Panel(), "Alle Felder müssen ausgefüllt werden.");
                }
            }
        });
    }

    public void drawGui(JFrame frame, Long personId){
        if(personId == null){
            this.personDto = new PersonDto();
        } else {
            this.personDto = personService.readPersonFromId(personId);
        }
        fillAntragstellerGui();
        frame.add(gui.getAntragstellerJahr1Panel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;
    }

    private void updatePersonDto() throws ParseException{
        personDto.setNachname(gui.getNachnameTextField().getText());
        personDto.setVorname(gui.getVornameTextField().getText());
        personDto.setGeschlecht(((GeschlechtEnum) gui.getGeschlechtComboBox().getSelectedItem()));
        personDto.setGeburtstdatum(getDateFormat().parse(gui.getGeburtsdatumTextField().getText()));
    }

    private void fillAntragstellerGui(){
        if(personDto.getId() == null){
            gui.getBearbeitenErstellenLabel().setText("Neuen Antragsteller erstellen");
        } else{
            gui.getBearbeitenErstellenLabel().setText("Antragsteller (ID: " + personDto.getId() + ") bearbeiten");
        }
        gui.getNachnameTextField().setText(nullToEmptyString(personDto.getNachname()));
        gui.getVornameTextField().setText(nullToEmptyString(personDto.getVorname()));
        if(personDto.getGeschlecht() == null){
            gui.getGeschlechtComboBox().setSelectedItem(GeschlechtEnum.WEIBLICH);
        }else{
            gui.getGeschlechtComboBox().setSelectedItem(personDto.getGeschlecht());
        }
        gui.getGeburtsdatumTextField().setText(formatDate(personDto.getGeburtstdatum()));
    }
}
