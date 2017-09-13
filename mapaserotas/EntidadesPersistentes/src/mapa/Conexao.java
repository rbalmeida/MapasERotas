package mapa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;

@Entity
public class Conexao {
    
    private java.lang.Integer Id;
    private Rota RotaA;
    private Rota RotaB;
    private Segmento SegmentoA;
    private Segmento SegmentoB;
    private Ponto PontoRotaAInt;
    private Ponto PontoRotaBInt;
    private Integer Tipo;
    private List<Ligacao> Ligacoes;
    private mapa.Mapa mMapa;
    /* ligacoes dos pontos dos segmetos para o PontoRotaX e 
     * tambem ligacoes do PontoRotaX para os sentidos permitidos
     * da outra rota */

    public Conexao() {
    }
    
    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }

    public void setRotaA(Rota rotaA) {
        this.RotaA = rotaA;
    }

    @OneToOne(targetEntity=Rota.class)
    public Rota getRotaA() {
        return RotaA;
    }

    public void setRotaB(Rota rotaB) {
        this.RotaB = rotaB;
    }
    
    @OneToOne(targetEntity=Rota.class)
    public Rota getRotaB() {
        return RotaB;
    }

    public void setPontoRotaAInt(Ponto pontoRotaA) {
        this.PontoRotaAInt = pontoRotaA;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public Ponto getPontoRotaAInt() {
        return PontoRotaAInt;
    }

    public void setPontoRotaBInt(Ponto pontoRotaB) {
        this.PontoRotaBInt = pontoRotaB;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public Ponto getPontoRotaBInt() {
        return PontoRotaBInt;
    }

    public void setTipo(Integer tipo) {
        this.Tipo = tipo;
    }

    public Integer getTipo() {
        return Tipo;
    }

    public void setLigacoes(List ligacoes) {
        this.Ligacoes = ligacoes;
    }

    @OneToMany(cascade = {CascadeType.ALL}, targetEntity=Ligacao.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
    @IndexColumn(base=0, name="indice")
    public List getLigacoes() {
        return Ligacoes;
    }

    public void setSegmentoA(Segmento segmentoA) {
        this.SegmentoA = segmentoA;
    }

    @OneToOne(targetEntity=Segmento.class)
    public Segmento getSegmentoA() {
        return SegmentoA;
    }

    public void setSegmentoB(Segmento segmentoB) {
        this.SegmentoB = segmentoB;
    }

    @OneToOne(targetEntity=Segmento.class)
    public Segmento getSegmentoB() {
        return SegmentoB;
    }
    
    public void setMapa(Mapa mMapa) {
        this.mMapa = mMapa;
    }

    @ManyToOne(targetEntity=Mapa.class)
    public Mapa getMapa() {
        return mMapa;
    }
}
