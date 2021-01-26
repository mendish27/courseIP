package prosmotr.vspomogat;

public class Tovar {
    public int id;
    public String name;
    public String category;
    public String type;
    public int kolvo;
    public double price;



    public Tovar(int id, String name, String category, String type, int kolvo, double price){
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.kolvo = kolvo;
        this.price = price;
    }
}
