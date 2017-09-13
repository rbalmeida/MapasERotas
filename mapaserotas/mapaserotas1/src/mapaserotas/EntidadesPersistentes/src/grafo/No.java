package grafo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.IndexColumn;
import java.util.List;

@Entity
public class No{

    private java.lang.Integer Id;
    private List Arcos;

    public No(){
        
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    @Id
    @GeneratedValue 
    public Integer getId() {
        return Id;
    }
    
    @OneToMany(cascade = {CascadeType.PERSIST}, targetEntity=Arco.class)
    @IndexColumn(base=0, name="indice")
      public List getArcos() {
        return this.Arcos;
    }           
        
    public void setArcos(List arcos) {
        this.Arcos = arcos;
    }
    
}
