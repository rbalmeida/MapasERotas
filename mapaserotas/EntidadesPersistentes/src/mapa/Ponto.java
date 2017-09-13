package mapa;

import grafo.No;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@Entity
public class Ponto {
    
    private java.lang.Integer Id;
    private java.lang.Double Latitude;
    private java.lang.Double Longitude;
    private No NoSentidoDireto;
    private No NoSentidoInverso;

    public Ponto() {
        Latitude = 0.0;
        Longitude = 0.0;
    }
    
    public Ponto(Double latitude, Double longitude){
        this.Latitude = latitude;
        this.Longitude = longitude;        
    }
    
    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }

    public void setLatitude(Double latitude) {
        this.Latitude = latitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLongitude(Double longitude) {
        this.Longitude = longitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setNoSentidoDireto(No noSentidoDireto) {
        this.NoSentidoDireto = noSentidoDireto;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public No getNoSentidoDireto() {
        return NoSentidoDireto;
    }

    public void setNoSentidoInverso(No noSentidoInverso) {
        this.NoSentidoInverso = noSentidoInverso;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public No getNoSentidoInverso() {
        return NoSentidoInverso;
    }
}
