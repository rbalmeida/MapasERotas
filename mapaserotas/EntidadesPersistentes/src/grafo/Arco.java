package grafo;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import mapa.NivelZoom;

@Entity
public class Arco {

    private java.lang.Integer Id;
    private No Origem;
    private No Destino;
    private java.lang.Double Custo;
    
    public Arco() {
    }
    
    public Arco(No origem, No destino, Double custo) {
        this.Origem = origem;
        this.Destino = destino;
        this.Custo = custo;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }

    public void setCusto(Double custo) {
        this.Custo = custo;
    }

    public Double getCusto() {
        return Custo;
    }

    public void setOrigem(No origem) {
        this.Origem = origem;
    }

    @OneToOne(targetEntity=No.class)
    public No getOrigem() {
        return Origem;
    }

    public void setDestino(No destino) {
        this.Destino = destino;
    }
    
    @OneToOne(targetEntity=No.class)
    public No getDestino() {
        return Destino;
    }


}
