package mapa;

import grafo.Arco;
import grafo.No;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import javax.persistence.OneToOne;

import javax.persistence.CascadeType;
import org.hibernate.annotations.Cascade;

@Entity
public class Ligacao {
    private java.lang.Integer Id;
    private boolean Habilitada;
    private java.lang.Integer Origem;
    private java.lang.Integer SentidoOrigem;
    private java.lang.Integer Destino;
    private java.lang.Integer SentidoDestino;
    private Arco ArcoOrigem;
    private Arco ArcoDestino;

    public Ligacao() {
    }
    
    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }

    public void setHabilitada(boolean habilitada) {
        this.Habilitada = habilitada;
    }

    public boolean isHabilitada() {
        return Habilitada;
    }

    public void setOrigem(Integer origem) {
        this.Origem = origem;
    }

    public Integer getOrigem() {
        return Origem;
    }

    public void setSentidoOrigem(Integer sentidoOrigem) {
        this.SentidoOrigem = sentidoOrigem;
    }

    public Integer getSentidoOrigem() {
        return SentidoOrigem;
    }

    public void setDestino(Integer destino) {
        this.Destino = destino;
    }

    public Integer getDestino() {
        return Destino;
    }

    public void setSentidoDestino(Integer sentidoDestino) {
        this.SentidoDestino = sentidoDestino;
    }

    public Integer getSentidoDestino() {
        return SentidoDestino;
    }

    public void setArcoOrigem(Arco arcoOrigem) {
        this.ArcoOrigem = arcoOrigem;
    }

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity=Arco.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
    public Arco getArcoOrigem() {
        return ArcoOrigem;
    }

    public void setArcoDestino(Arco arcoDestino) {
        this.ArcoDestino = arcoDestino;
    }

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity=Arco.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
    public Arco getArcoDestino() {
        return ArcoDestino;
    }
}
