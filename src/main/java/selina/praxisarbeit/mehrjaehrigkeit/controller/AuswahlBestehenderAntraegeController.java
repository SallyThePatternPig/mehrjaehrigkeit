package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.dto.AntragDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.AntragService;
import selina.praxisarbeit.mehrjaehrigkeit.view.AuswahlBestehenderAntraegeGui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.getIdFromRowSelection;
import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.isRowSelected;

@Controller
public class AuswahlBestehenderAntraegeController {

    @Autowired
    private StartseiteAntraegeController startseiteAntraegeCotroller;

    @Autowired
    private AntragJahrController antragJahrController;

    @Autowired
    private AntragService antragService;

    private JFrame myFrame;

    private Long personId;

    private DefaultTableModel tableModel = new DefaultTableModel();

    private AuswahlBestehenderAntraegeGui gui = new AuswahlBestehenderAntraegeGui();

    public AuswahlBestehenderAntraegeController(){

        gui.getZurueckButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getAuswahlBestehenderAntraegePanel());
                startseiteAntraegeCotroller.drawGui(myFrame, personId);
            }
        });

        //TODO: Listener für Tabelle: welche Zeile wurde angeklickt mit dieser dann folgenden Listener durchführen:
        gui.getBearbeitenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getAntragTable())){
                    Long antragId = getIdFromRowSelection(tableModel, gui.getAntragTable());
                    myFrame.remove(gui.getAuswahlBestehenderAntraegePanel());
                    //TODO: laden der bisher gespeicherten Daten!
                    antragJahrController.drawGui(myFrame, personId, antragId);
                }
            }
        });
    }

    public void drawGui(JFrame frame, Long personId){
        this.personId = personId;
        frame.add(gui.getAuswahlBestehenderAntraegePanel());
        fillTable();
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;
    }

    public void fillTable(){

        tableModel = new DefaultTableModel();

        Object[] columnTopic = new Object[]{"ID", "Erfassungsjahr", "Mindestens 100 qm Grünfläche", "Keine Nutzung von Pflanzenschutzmitteln"};

        tableModel.setColumnIdentifiers(columnTopic);

        Set<AntragDto> antragSet = antragService.readAllAntraege(personId);

        for(AntragDto antragDto : antragSet){
            Object[] rowInput = new Object[]{antragDto.getId(), antragDto.getErfassungsjahr(), antragDto.isMin100qmGruenflaeche(), antragDto.isKeinePflanzenschutzmittel()};
            tableModel.addRow(rowInput);
        }

        gui.getAntragTable().setModel(tableModel);
    }
}
