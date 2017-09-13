package mapa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.IndexColumn;
import java.util.List;

@Entity
public class Rota {

    private java.lang.Integer Id;
    private java.lang.String Nome;
    private java.lang.Integer Cor;
    private List Pontos;
    private List Segmentos;
    
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

    public void setCor(Integer cor) {
        this.Cor = cor;
    }

    public Integer getCor() {
        return Cor;
    }

    public void setPontos(List pontos) {
        this.Pontos = pontos;
    }

    @OneToMany(cascade = {CascadeType.PERSIST}, targetEntity=Ponto.class)
    @IndexColumn(base=0, name="indice")
    public List getPontos() {
        return Pontos;
    }

    public void setSegmentos(List segmentos) {
        this.Segmentos = segmentos;
    }

    @OneToMany(cascade = {CascadeType.PERSIST}, targetEntity=Segmento.class)
    @IndexColumn(base=0, name="indice")
    public List getSegmentos() {
        return Segmentos;
    }
}
