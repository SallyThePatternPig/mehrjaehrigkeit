package selina.praxisarbeit.mehrjaehrigkeit.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Contants {

    public static int erfassungsjahr1 = 2019;
    public static int erfassungsjahr2 = 2020;
    //das höchste Jahr ist immer das aktuelle Jahr!
    public static List<Integer> erfassungsjahre = Arrays.asList(erfassungsjahr2, erfassungsjahr1);
    public static int aumGueltigkeit = 2;
    public static int qmProTier = 3;
    public static int aumGruenflaeche = 100;
    public static int defaultZahl = 0;
    public static String leererString = "";
    public static String deleteMessage = "Zu diesem Antragsteller existieren bereits Protokolle. Wenn Sie\n" +
            "fortfahren werden auch diese gelöscht. Gelöschte Daten\n" + "können nicht wieder hergestellt werden.\n \n"
           + "Wollen Sie den Antragsteller dennoch löschen?";

    static{
        Collections.sort(erfassungsjahre);
    }
}
