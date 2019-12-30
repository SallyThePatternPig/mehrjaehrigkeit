package selina.praxisarbeit.mehrjaehrigkeit.common;

import java.util.Arrays;
import java.util.List;

public class Contants {

    public static int erfassungsjahr1 = 2019;
    public static List<Integer> erfassungsjahre = Arrays.asList(erfassungsjahr1);
    public static int qmProTier = 3;
    public static int aumGruenflaeche = 100;
    public static int defaultZahl = 0;
    public static String leererString = "";
    public static String deleteMessage = "Zu diesem Antragsteller existieren bereits Protokolle. Wenn Sie\n" +
            "fortfahren werden auch diese gelöscht. Gelöschte Daten\n" + "können nicht wieder hergestellt werden.\n \n"
           + "Wollen Sie den Antragsteller dennoch löschen?";
}
