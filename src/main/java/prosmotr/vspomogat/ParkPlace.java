package prosmotr.vspomogat;

import java.io.*;

public class ParkPlace extends Product{
    public int id;
    public String street;
    public int numbike;
    public ParkPlace(int id, String street, int numbike){
        this.id = id;
        this.street = street;
        this.numbike = numbike;
    }
}