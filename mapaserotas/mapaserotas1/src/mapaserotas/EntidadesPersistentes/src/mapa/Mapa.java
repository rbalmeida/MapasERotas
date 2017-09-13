package mapa;

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

@Entity
public class Mapa {

    private java.lang.Integer Id;
    private java.lang.String Nome;
    private java.lang.String Descricao;
    private List Rotas;
    private Grafo GrafoMapa;

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

    public void setRotas(List rotas) {
        this.Rotas = rotas;
    }

    @OneToMany(cascade = {CascadeType.PERSIST}, targetEntity=Rota.class)
    @IndexColumn(base=0, name="indice")
    public List getRotas() {
        return Rotas;
    }

    public void setGrafoMapa(Grafo grafoMapa) {
        this.GrafoMapa = grafoMapa;
    }

    @OneToOne(cascade = {CascadeType.PERSIST})
    public Grafo getGrafoMapa() {
        return GrafoMapa;
    }
}
