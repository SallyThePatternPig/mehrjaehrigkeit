package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil;
import selina.praxisarbeit.mehrjaehrigkeit.dto.PersonDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.PersonService;
import selina.praxisarbeit.mehrjaehrigkeit.view.AuwahlBestehenderAntragstellerGui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.*;

@Controller
public class AuswahlBestehenderAntragstellerController {

    @Autowired
    private StartseiteAntragstellerController startseiteAntragstellerController;

    @Autowired
    private AntragstellerJahr1Controller antragstellerController;

    @Autowired
    private StartseiteAntraegeController startseiteAntraegeCotroller;

    @Autowired
    private PersonService personService;

    private DefaultTableModel tableModel = new DefaultTableModel();

    private JFrame myFrame;

    private Long personId;

    private AuwahlBestehenderAntragstellerGui gui = new AuwahlBestehenderAntragstellerGui();

    public AuswahlBestehenderAntragstellerController(){

        gui.getZurueckButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getAuswahlBestehenderAntragstellerPanel());
                startseiteAntragstellerController.drawGui(myFrame);
            }
        });

        gui.getBearbeitenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getAntragstellerTable())) {
                    Long personId = getIdFromRowSelection(tableModel, gui.getAntragstellerTable());
                    myFrame.remove(gui.getAuswahlBestehenderAntragstellerPanel());
                    antragstellerController.drawGui(myFrame, personId);
                }
            }
        });

        gui.getAuswaehlenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getAntragstellerTable())) {
                    Long personId = getIdFromRowSelection(tableModel, gui.getAntragstellerTable());
                    myFrame.remove(gui.getAuswahlBestehenderAntragstellerPanel());
                    startseiteAntraegeCotroller.drawGui(myFrame, personId);
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

        Object[] columnTopic = new Object[]{"ID", "Nachname", "Vorname", "Geschlecht", "Geburtsdatum"};

        tableModel.setColumnIdentifiers(columnTopic);

        List<PersonDto> personList = personService.readAllPersons();

        for (PersonDto personDto: personList) {
            Object[] rowInput = new Object[]{personDto.getId(), personDto.getNachname(), personDto.getVorname(), personDto.getGeschlecht(), getDateFormat().format(personDto.getGeburtstdatum())};
            tableModel.addRow(rowInput);
        }

        gui.getAntragstellerTable().setModel(tableModel);
    }
}
