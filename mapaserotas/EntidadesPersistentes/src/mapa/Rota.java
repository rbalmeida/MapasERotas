package mapa;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;
import java.util.List;

import javax.persistence.ManyToOne;


@Entity
public class Rota {

    private java.lang.Integer Id;
    private java.lang.String Nome;
    private mapa.Mapa mMapa;
    private List<Ponto> Pontos = new ArrayList<Ponto>();
    private List<Segmento> Segmentos = new ArrayList<Segmento>();
    private BoudingBox mBox;
    
    public Rota() {
    }
    
    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }


    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getNome() {
        return Nome;
    }

    public void setPontos(List pontos) {
        this.Pontos = pontos;
    }

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity=Ponto.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
    @IndexColumn(base=0, name="indice")
    public List getPontos() {
        return Pontos;
    }

    public void setSegmentos(List segmentos) {
        this.Segmentos = segmentos;
    }

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity=Segmento.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
    @IndexColumn(base=0, name="indice")
    public List getSegmentos() {
        return Segmentos;
    }

   
    public void setMapa(Mapa mMapa) {
        this.mMapa = mMapa;
    }

    @ManyToOne(targetEntity=Mapa.class)
    public Mapa getMapa() {
        return mMapa;
    }

    public void setBoudingBox(BoudingBox nBox) {
        this.mBox = nBox;
    }

    @OneToOne(cascade = {CascadeType.ALL}, targetEntity=BoudingBox.class)
    public BoudingBox getBoudingBox() {
        return mBox;
    }
}
