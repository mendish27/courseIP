package prosmotr.vspomogat;

import forUser.Otziv;

import java.util.Date;

public class Otzivski {
    public int id;
    public String clientOt;
    public String text;
    public int zvezd;
    public Date data;

    public Otzivski (int id, String clientOt,String text, int zvezd, Date data){
        this.id = id;
        this.clientOt = clientOt;
        this.text = text;
        this.zvezd = zvezd;
        this.data = data;
    }
}
