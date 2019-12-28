package selina.praxisarbeit.mehrjaehrigkeit.view;

import javax.swing.*;

public class MessageDialog {

    public MessageDialog (JComponent component, String message){
        JOptionPane.showMessageDialog(component, message);
    }
}
