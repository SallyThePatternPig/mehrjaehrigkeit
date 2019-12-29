package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.view.StartseiteProtokolleGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Controller
public class StartseiteProtokolleController {

    @Autowired
    private StartseiteAntragstellerController startseiteAntragstellerController;

    @Autowired
    private AuswahlBestehenderProtkolleController auswahlProtokolleController;

    @Autowired
    private ProtokollJahrController protokollController;

    private JFrame myFrame;

    private Long personId;

    private StartseiteProtokolleGui gui = new StartseiteProtokolleGui();

    public StartseiteProtokolleController(){

        gui.getAbbrechenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteProtokollePanel());
                startseiteAntragstellerController.drawGui(myFrame);
            }
        });

        gui.getBestehendenProtokollBearbeitenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteProtokollePanel());
                auswahlProtokolleController.drawGui(myFrame, personId);
            }
        });

        gui.getNeuenProtokollErstellenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteProtokollePanel());
                protokollController.drawGui(myFrame, personId, null);
            }
        });
    }

    public void drawGui(JFrame frame, Long personId){
        frame.add(gui.getStartseiteProtokollePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;
        this.personId = personId;
    }
}
