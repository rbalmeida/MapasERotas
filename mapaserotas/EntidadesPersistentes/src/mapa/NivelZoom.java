package mapa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NivelZoom {
    private java.lang.Integer Id;
    private java.lang.Integer Nivel;
    private java.lang.String Descricao;
    private java.lang.Double Escala;
    private java.lang.String Imagem;
    private java.lang.Integer AlturaImg;
    private java.lang.Integer LarguraImg;

    public NivelZoom() {
        Descricao = "";
        Escala = 0.0;
        Imagem = "";
    }
   
    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }

    public void setNivel(Integer nivel) {
        this.Nivel = nivel;
    }

    public Integer getNivel() {
        return Nivel;
    }

    public void setDescricao(String descricao) {
        this.Descricao = descricao;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setEscala(Double escala) {
        this.Escala = escala;
    }

    public Double getEscala() {
        return Escala;
    }

    public void setImagem(String imagem) {
        this.Imagem = imagem;
    }

    public String getImagem() {
        return Imagem;
    }

    public void setAlturaImg(Integer alturaImg) {
        this.AlturaImg = alturaImg;
    }

    public Integer getAlturaImg() {
        return AlturaImg;
    }

    public void setLarguraImg(Integer larguraImg) {
        this.LarguraImg = larguraImg;
    }

    public Integer getLarguraImg() {
        return LarguraImg;
    }
}
