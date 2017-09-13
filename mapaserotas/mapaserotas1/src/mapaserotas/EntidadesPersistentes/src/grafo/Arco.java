package grafo;

import grafo.No;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Arco {

    private java.lang.Integer Id;
    private java.lang.Double Custo;
    private grafo.No Destino;
    
    public Arco() {
    }
    
    public Arco(grafo.No destino) {
        this.Destino = destino;
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

    public void setDestino(grafo.No destino) {
        this.Destino = destino;
    }

    @OneToOne(cascade = {CascadeType.PERSIST}, targetEntity=No.class)
    public grafo.No getDestino() {
        return Destino;
    }
}
