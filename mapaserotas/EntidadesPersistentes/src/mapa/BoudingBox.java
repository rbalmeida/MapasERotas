package mapa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BoudingBox {
    
    private java.lang.Integer Id;
    private double MinLat;
    private double MinLon;
    private double MaxLat;
    private double MaxLon;    
    

    public BoudingBox() {
    }
    
    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }

    public void setMinLat(double minLat) {
        this.MinLat = minLat;
    }

    public double getMinLat() {
        return MinLat;
    }

    public void setMinLon(double minLon) {
        this.MinLon = minLon;
    }

    public double getMinLon() {
        return MinLon;
    }

    public void setMaxLat(double maxLat) {
        this.MaxLat = maxLat;
    }

    public double getMaxLat() {
        return MaxLat;
    }

    public void setMaxLon(double maxLon) {
        this.MaxLon = maxLon;
    }

    public double getMaxLon() {
        return MaxLon;
    }
}
