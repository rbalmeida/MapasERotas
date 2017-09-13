package editormapa;

/* Classes utilizadas para trablhar com o framework de persistencia Hibernate */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import javax.persistence.Query;

public class ControlePersistencia {
    
    private Ambiente amb;
    public EntityManagerFactory emf;
    public EntityManager em;
    public EntityTransaction tx;
    public String dbContext;

    public ControlePersistencia() {
    }
    
    public ControlePersistencia(Ambiente nAmb, String dbContext) {
        this.amb = nAmb;
        this.dbContext = dbContext;
        
    }
        
    public void conectaBanco (){
        
        /* Cria Entity Manager Facotry baseado no arquivo de configuracao 
         * persistence.xml */
        emf = Persistence.createEntityManagerFactory(dbContext);
        em = emf.createEntityManager();         
    }
    
    public void desconectaBanco() {
        em.close();
    }
    
    public void iniciaTransacao(){
        // inicia uma transacao no banco
        tx = em.getTransaction();        
        tx.begin();
    }
    
    public void comitaTransacao(){        
        tx.commit();
    }
    
    public void cancelaTransacao(){
        tx.rollback();        
    }
    
    public void persiste(Object obj, boolean novoObj){
    
        if (novoObj)
            em.persist(obj);
        else
            em.merge(obj);        
          
        /* Verificar como trabalhar com Detached entity passed to persist 
         * Depois da 1a vez q o mapa for persistido, tem q tratar de uma maneira
         * diferente
         * */
    }
    
    public void exclui(Object obj){
        em.remove(obj);
    }
    
    public List executaQuery(String st){
        Query query = em.createQuery(st);
        return query.getResultList();
    }
    
    public List executaQueryNativa(String st){
        Query query = em.createNativeQuery(st);
        return query.getResultList();
    }
    
}
