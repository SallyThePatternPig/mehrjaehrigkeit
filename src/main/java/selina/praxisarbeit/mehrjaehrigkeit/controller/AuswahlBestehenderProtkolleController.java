package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.controller.jahresSwitcher.ProtokollJahresSwitcher;
import selina.praxisarbeit.mehrjaehrigkeit.dto.ProtokollDto;
import selina.praxisarbeit.mehrjaehrigkeit.service.ProtokollService;
import selina.praxisarbeit.mehrjaehrigkeit.view.AuswahlBestehenderProtokolleGui;
import selina.praxisarbeit.mehrjaehrigkeit.view.MessageDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.getIdFromRowSelection;
import static selina.praxisarbeit.mehrjaehrigkeit.common.CommonUtil.isRowSelected;

@Controller
public class AuswahlBestehenderProtkolleController {

    @Autowired
    private StartseiteProtokolleController startseiteProtokolleCotroller;

    @Autowired
    private ProtokollJahrController protokollJahrController;

    @Autowired
    private ProtokollService protokollService;

    private JFrame myFrame;

    private Long personId;

    private DefaultTableModel tableModel = new DefaultTableModel();

    private AuswahlBestehenderProtokolleGui gui = new AuswahlBestehenderProtokolleGui();

    public AuswahlBestehenderProtkolleController(){

        gui.getZurueckButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getAuswahlBestehenderProtokollePanel());
                startseiteProtokolleCotroller.drawGui(myFrame, personId);
            }
        });
        gui.getLoeschenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getProtokollTable())){
                    Long protokollId = getIdFromRowSelection(tableModel, gui.getProtokollTable());
                    ProtokollDto protokollDto = protokollService.readProtokollFromId(protokollId);
                    if(protokollService.isHatFolgejahre(personId, protokollDto.getErfassungsjahr())){
                        new MessageDialog(gui.getAuswahlBestehenderProtokollePanel(), "Da bereits Anträge in " +
                                "folgenden Jahren existieren, kann das Protokoll nicht gelöscht werden.");
                    }else {
                        protokollService.removeProtokoll(protokollId);
                        myFrame.remove(gui.getAuswahlBestehenderProtokollePanel());
                        drawGui(myFrame, personId);
                    }
                }
            }
        });
        gui.getBearbeitenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getProtokollTable())){
                    Long protokollId = getIdFromRowSelection(tableModel, gui.getProtokollTable());
                    ProtokollDto protokollDto = protokollService.readProtokollFromId(protokollId);
                    if(protokollService.isHatFolgejahre(personId, protokollDto.getErfassungsjahr())){
                        new MessageDialog(gui.getAuswahlBestehenderProtokollePanel(), "Da bereits Anträge in " +
                                "folgenden Jahren existieren, kann das Protokoll nicht bearbeitet werden.");
                    }else {
                        myFrame.remove(gui.getAuswahlBestehenderProtokollePanel());
                        protokollJahrController.activateGui(myFrame, personId, protokollId, true);
                    }
                }
            }
        });

        gui.getAnzeigenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRowSelected(gui.getProtokollTable())){
                    Long protokollId = getIdFromRowSelection(tableModel, gui.getProtokollTable());
                    myFrame.remove(gui.getAuswahlBestehenderProtokollePanel());
                    protokollJahrController.activateGui(myFrame, personId, protokollId, false);

                }
            }
        });
    }

    public void drawGui(JFrame frame, Long personId){
        this.personId = personId;
        frame.add(gui.getAuswahlBestehenderProtokollePanel());
        fillTable();
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;
    }

    public void fillTable(){

        tableModel = new DefaultTableModel();

        gui.getProtokollTable().setDefaultEditor(Object.class, null);

        Object[] columnTopic = new Object[]{"ID", "Erfassungsjahr", "Mindestens 100 qm Grünfläche \n beantragbar: ab 2019",
                "Feldhamster \n beantragbar: ab 2020","Keine Nutzung von Pflanzenschutzmitteln \n beantragbar: in 2019" };

        tableModel.setColumnIdentifiers(columnTopic);

        Set<ProtokollDto> protokollDtoSet = protokollService.readAllProtokolle(personId);

        for(ProtokollDto protokollDto : protokollDtoSet){
            Object[] rowInput = new Object[]{protokollDto.getId(), protokollDto.getErfassungsjahr(), protokollDto.getMin100qmGruenflaecheEnum(),
                    protokollDto.getFeldhamsterEnum(), protokollDto.getKeinePflanzenschutzmittelEnum()};
            tableModel.addRow(rowInput);
        }

        gui.getProtokollTable().setModel(tableModel);
    }
}
