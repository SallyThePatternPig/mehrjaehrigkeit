package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.PersonService;
import selina.praxisarbeit.mehrjaehrigkeit.view.AuswahlBestehenderAntragstellerGui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.*;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.deleteMessage;

@Controller
public class AuswahlBestehenderAntragstellerController {

    @Autowired
    private StartseiteAntragstellerController startseiteAntragstellerController;

    @Autowired
    private AntragstellerJahr1Controller antragstellerController;

    @Autowired
    private StartseiteProtokolleController startseiteProtokolleCotroller;

    @Autowired
    private PersonService personService;

    private DefaultTableModel tableModel = new DefaultTableModel();

    private JFrame myFrame;

    private Long personId;

    private AuswahlBestehenderAntragstellerGui gui = new AuswahlBestehenderAntragstellerGui();

    public AuswahlBestehenderAntragstellerController(){

        gui.getZurueckButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getAuswahlBestehenderAntragstellerPanel());
                startseiteAntragstellerController.drawGui(myFrame);
            }
        });

        gui.getLoeschenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getAntragstellerTable())) {
                    personId = getIdFromRowSelection(tableModel, gui.getAntragstellerTable());
                    PersonDto personDto = personService.readPersonFromId(personId);
                    if(personDto.getProtokolle().size() == 0){
                        personService.removePerson(personId);
                    } else{
                        if(showDeleteOptionPane()){
                            personService.removePersonMitProtokolle(personId);
                        }
                    }
                    myFrame.remove(gui.getAuswahlBestehenderAntragstellerPanel());
                    drawGui(myFrame);
                }
            }
        });
        gui.getBearbeitenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getAntragstellerTable())) {
                    personId = getIdFromRowSelection(tableModel, gui.getAntragstellerTable());
                    myFrame.remove(gui.getAuswahlBestehenderAntragstellerPanel());
                    antragstellerController.drawGui(myFrame, personId);
                }
            }
        });

        gui.getAuswaehlenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getAntragstellerTable())) {
                    personId = getIdFromRowSelection(tableModel, gui.getAntragstellerTable());
                    myFrame.remove(gui.getAuswahlBestehenderAntragstellerPanel());
                    startseiteProtokolleCotroller.drawGui(myFrame, personId);
                }
            }
        });
    }

    public void drawGui(JFrame frame){
        frame.add(gui.getAuswahlBestehenderAntragstellerPanel());
        fillTable();
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;
    }

    private void fillTable(){

        tableModel = new DefaultTableModel();

        gui.getAntragstellerTable().setDefaultEditor(Object.class, null);

        Object[] columnTopic = new Object[]{"ID", "Nachname", "Vorname", "Geschlecht", "Standort", "Protokollanzahl"};

        tableModel.setColumnIdentifiers(columnTopic);

        List<PersonDto> personList = personService.readAllPersons();

        for (PersonDto personDto: personList) {
            Object[] rowInput = new Object[]{personDto.getId(), personDto.getNachname(), personDto.getVorname(),
                    personDto.getGeschlecht(), personDto.getStandort(), personDto.getProtokolle().size()};
            tableModel.addRow(rowInput);
        }

        gui.getAntragstellerTable().setModel(tableModel);
    }

    private boolean showDeleteOptionPane(){
        int result = JOptionPane.showConfirmDialog((Component) null, deleteMessage,
                "Achtung!", JOptionPane.OK_CANCEL_OPTION);
        return result == JOptionPane.OK_OPTION;
    }
}
