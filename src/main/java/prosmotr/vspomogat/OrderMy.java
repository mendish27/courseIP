package prosmotr.vspomogat;

public class OrderMy {
    public int id;
    public int idCli;
    public int idVel;
    public int idLoc;
    public int timeForUse;
    public int price;
    public OrderMy(int id, int idCli, int idVel, int idLoc, int timeForUse, int price){
        this.id = id;
        this.idCli = idCli;
        this.idVel = idVel;
        this.idLoc = idLoc;
        this.timeForUse = timeForUse;
        this.price = price;
    }
}
