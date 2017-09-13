package mapa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@Entity
public class Localidade {

    private java.lang.Integer Id;
    private java.lang.String Numero;
    private Ponto Posicao;

    public Localidade() {
    }
    
    public Localidade(String numero) {
        this.Numero = numero;
    }
    
    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }


    public void setNumero(String numero) {
        this.Numero = numero;
    }

    public String getNumero() {
        return Numero;
    }

    public void setPosicao(Ponto posicao) {
        this.Posicao = posicao;
    }
    
    @OneToOne(cascade = {CascadeType.ALL})
    public Ponto getPosicao() {
        return Posicao;
    }
}
