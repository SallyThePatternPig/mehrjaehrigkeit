package selina.praxisarbeit.mehrjaehrigkeit.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import selina.praxisarbeit.mehrjaehrigkeit.view.StartseiteAntragstellerGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
@Controller
public class StartseiteAntragstellerController {

    @Autowired
    private AuswahlBestehenderAntragstellerController auswahlAntragstellerController;

    @Autowired
    private AntragstellerJahr1Controller antragstellerController;

    private StartseiteAntragstellerGui gui = new StartseiteAntragstellerGui();

    private JFrame myFrame;

    public StartseiteAntragstellerController(){
        gui.getBestehendenAntragstellerAuswaehlenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteAntragstellerPanel());
                auswahlAntragstellerController.drawGui(myFrame);
            }
        });

        gui.getNeuenAntragstellerAnlegenButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(gui.getStartseiteAntragstellerPanel());
                antragstellerController.drawGui(myFrame, null);
            }
        });

    }

    public void drawGui(JFrame frame){
        frame.add(gui.getStartseiteAntragstellerPanel());
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.myFrame = frame;
    }


}
