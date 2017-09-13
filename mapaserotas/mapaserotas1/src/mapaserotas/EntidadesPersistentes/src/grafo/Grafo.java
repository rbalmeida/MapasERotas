package grafo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.OrderBy;
import org.hibernate.annotations.IndexColumn;

@Entity
public class Grafo {

    private java.lang.Integer Id;
    private List Nos;
    
    public Grafo() {
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return Id;
    }

    public void setNos(List nos) {
        this.Nos = nos;
    }

    @OneToMany(cascade = {CascadeType.PERSIST}, targetEntity=No.class)
    @IndexColumn(base=0, name="indice")    
    public List getNos() {
        return Nos;
    }
}


