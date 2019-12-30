package selina.praxisarbeit.mehrjaehrigkeit.common;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.*;
import static selina.praxisarbeit.mehrjaehrigkeit.common.Contants.erfassungsjahre;

public class CommonUtil {

    public static boolean isRowSelected(JTable table){
        return table.getSelectedRow() != -1;
    }

    public static Long getIdFromRowSelection(DefaultTableModel tableModel, JTable table){
        Long id = (Long) tableModel.getValueAt(table.getSelectedRow(), 0 );
        return id;
    }

    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat;
    }

    public static BigDecimal parseBigDecimal(String string) throws ParseException {
        if ( string.equals(leererString)){
            return null;
        } else {
            DecimalFormat decimalFormat = new DecimalFormat();
            Number number = decimalFormat.parse(string);
            BigDecimal bigDecimal = new BigDecimal(number.toString());
            return bigDecimal;
        }
    }

    public static String formatBigDecimal(BigDecimal bigDecimal) {
        String bigDecimalString = leererString;
        if (bigDecimal != null) {
            DecimalFormat decimalFormat = new DecimalFormat();
            bigDecimalString = decimalFormat.format(bigDecimal);
        }
        return bigDecimalString;
    }

    public static int parseInt (Number number){
        int kleinInt = defaultZahl;
        if (number != null){
            kleinInt = number.intValue();
        }
        return kleinInt;
    }

    public static String nullToEmptyString (String string){
        String ausgabe = "";
        if (string != null){
            ausgabe = string;
        }
        return ausgabe;
    }

    public static String formatDate(Date date){
        String ausgabe = "";
        if(date != null){
            ausgabe = getDateFormat().format(date);
        }
        return ausgabe;
    }

    public static int getAktuellesJahr(){
        return erfassungsjahre.get(erfassungsjahre.size()-1);
    }

    public static int setErfassungsjahr(int erfassungsjahr){
        if(erfassungsjahr == 0){
            erfassungsjahr = getAktuellesJahr();
        }
        return erfassungsjahr;
    }
}
