package shagold.wifwaf.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WifWafUserBirthday {

    private String value;

    public WifWafUserBirthday(String value) {
        this.value = value;
    }

    private String[] getValues() {
        String[] s = new String[0];

        if(value.contains("T"))
            s = value.split("T");
        else if(value.contains(" "))
            s = value.split(" ");
        else
            System.out.println("BUG type time : " + value);

        return s;
    }

    public String getDate() {
        String dt = getValues()[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        try { // TODO probleme avaec la BD retourne 1 jour de moins que la date prévue
              // TODO pourtant bien sauvegardé dans la BD
              // TODO du coup j'ajoute 1 jour à la date pour compenser
            c.setTime(sdf.parse(dt));
            c.add(Calendar.DATE, 1);
            dt = sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt;
    }
}
