package webserver;

import org.hibernate.ejb.Ejb3Configuration;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javax.servlet.*;
import javax.servlet.http.*;

import mapa.Mapa;

import mapa.Ponto;
import mapa.Rota;


public class conexao {
    EntityManagerFactory emf;
    EntityManager em;

    public conexao() {
    
        Ejb3Configuration ejbconf = new Ejb3Configuration();
        ejbconf.setProperty("hibernate.connection.url", "jdbc:postgresql:Mapas");
        ejbconf.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        ejbconf.setProperty("hibernate.show_sql", "false");
        ejbconf.setProperty("hibernate.connection.username", "postgres");
        ejbconf.setProperty("hibernate.connection.password", "mlogic456Emdbo");
        ejbconf.setProperty("hibernate.hbm2ddl.auto", "update");

        try{       
            ejbconf.addAnnotatedClass(Class.forName("mapa.Mapa"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.Rota"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.Segmento"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.Ponto"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.Localidade"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.NivelZoom"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.BoudingBox"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.Conexao"));
             ejbconf.addAnnotatedClass(Class.forName("mapa.Ligacao"));
             ejbconf.addAnnotatedClass(Class.forName("grafo.Grafo"));
             ejbconf.addAnnotatedClass(Class.forName("grafo.No"));
             ejbconf.addAnnotatedClass(Class.forName("grafo.Arco")); 
        }
        catch (Exception ex){
           
        }
    
        emf = ejbconf.buildEntityManagerFactory();
        em = emf.createEntityManager();
       
    }
    
    public List executaQuery(String st){
        Query query;
        query = em.createQuery(st);
        return query.getResultList();
    }
    
    public List executaNativeQuery(String st){
        Query query;
        query = em.createNativeQuery(st);
        return query.getResultList();
    }
    
}
