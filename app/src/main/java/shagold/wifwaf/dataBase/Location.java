package shagold.wifwaf.dataBase;

public class Location {
    private int idLocation;
    private double lattitude;
    private double longitude;
    private int ordering;

    public Location(int idLocation, double latt, double longitude, int o){
        this.idLocation = idLocation;
        this.lattitude = latt;
        this.longitude = longitude;
        this.ordering = o;
    }

    public Location(double latt, double longitude, int o){
        this.lattitude = latt;
        this.longitude = longitude;
        this.ordering = o;
    }

}
