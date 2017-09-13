package mapa;

/* Alterar configuracao de relacionamento com as rotas. ver detalhe na classe rota */

import grafo.Grafo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.IndexColumn;
import java.util.List;

import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.engine.CascadeStyle;

@Entity
public class Mapa {

    private java.lang.Integer Id;
    private java.lang.String Nome;
    private java.lang.String Descricao;
    private List NiveisZoom;
    private Ponto PontoInicial;

    public Mapa() {
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

    public void setDescricao(String descricao) {
        this.Descricao = descricao;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setNiveisZoom(List niveisZoom) {
        this.NiveisZoom = niveisZoom;
    }
   
    @OneToMany(cascade = {CascadeType.ALL}, targetEntity=NivelZoom.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) 
    @IndexColumn(base=0, name="indice")
    public List getNiveisZoom() {
        return NiveisZoom;
    }

    public void setPontoInicial(Ponto pontoInicial) {
        this.PontoInicial = pontoInicial;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public Ponto getPontoInicial() {
        return PontoInicial;
    }
}
