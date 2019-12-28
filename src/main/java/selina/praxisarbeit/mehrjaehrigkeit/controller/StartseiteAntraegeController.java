package selina.praxisarbeit.mehrjaehrigkeit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.view.StartseiteAntraegeGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Controller
public class StartseiteAntraegeController {

    @Autowired
    private StartseiteAntragstellerController startseiteAntragstellerController;

    @Autowired
    private AuswahlBestehenderAntraegeController auswahlAntraegeController;

    @Autowired
    private AntragJahrController antragController;

    private JFrame myFrame;

    private Long personId;

    private StartseiteAntraegeGui gui = new StartseiteAntraegeGui();

    public StartseiteAntraegeController(){

        gui.getAbbrechenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteAntraegePanel());
                startseiteAntragstellerController.drawGui(myFrame);
            }
        });

        gui.getBestehendenAntragBearbeitenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteAntraegePanel());
                auswahlAntraegeController.drawGui(myFrame, personId);
            }
        });

        gui.getNeuenAntragErstellenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteAntraegePanel());
                antragController.drawGui(myFrame, personId, null);
            }
        });
    }

    public void drawGui(JFrame frame, Long personId){
        frame.add(gui.getStartseiteAntraegePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        this.myFrame = frame;
        this.personId = personId;
    }
}
