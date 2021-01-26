package prosmotr.vspomogat;

public class Client extends Product {
    public int id;
    public String name;
    public String last;
    public String email;
    public String pho;
    public  byte[] imageData;

    public Client (int id, String name, String last, String email, String pho){
        this.id = id;
        this.name = name;
        this.email = email;
        this.last = last;
        this.pho = pho;
    }
    public byte[] getImageData() {
        return imageData;
    }
}
