
package editormapa;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import mapa.*;
import grafo.*;

import java.util.List;

import javax.persistence.Query;

public class frmPrincipal extends JFrame {
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JToolBar toolBar = new JToolBar();
    private JButton buttonOpen = new JButton();
    private JButton buttonClose = new JButton();
    private JButton buttonHelp = new JButton();
    private ImageIcon imageOpen = new ImageIcon(frmPrincipal.class.getResource("openfile.gif"));
    private ImageIcon imageClose = new ImageIcon(frmPrincipal.class.getResource("closefile.gif"));
    private ImageIcon imageHelp = new ImageIcon(frmPrincipal.class.getResource("help.gif"));
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();

    public frmPrincipal() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setJMenuBar( menuBar );
        this.getContentPane().setLayout( layoutMain );
        panelCenter.setLayout( null );
        this.setSize( new Dimension(400, 300) );
        menuFile.setText( "File" );
        menuFileExit.setText( "Exit" );
        menuFileExit.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { fileExit_ActionPerformed( ae ); } } );
        menuHelp.setText( "Help" );
        menuHelpAbout.setText( "About" );
        menuHelpAbout.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { helpAbout_ActionPerformed( ae ); } } );
        statusBar.setText( "" );
        buttonOpen.setToolTipText( "Open File" );
        buttonOpen.setIcon( imageOpen );
        buttonClose.setToolTipText( "Close File" );
        buttonClose.setIcon( imageClose );
        buttonHelp.setToolTipText( "About" );
        buttonHelp.setIcon( imageHelp );
        jButton1.setText("GerarDadosTeste");
        jButton1.setBounds(new Rectangle(105, 50, 130, 25));
        jButton1.setActionCommand("GerarDadosTeste");
        jButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton1_actionPerformed(e);
                    }
                });
        jButton2.setText("RodarTesteConsulta");
        jButton2.setBounds(new Rectangle(105, 90, 150, 25));
        jButton2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton2_actionPerformed(e);
                    }
                });
        menuFile.add( menuFileExit );
        menuBar.add( menuFile );
        menuHelp.add( menuHelpAbout );
        menuBar.add( menuHelp );
        this.getContentPane().add( statusBar, BorderLayout.SOUTH );
        toolBar.add( buttonOpen );
        toolBar.add( buttonClose );
        toolBar.add( buttonHelp );
        this.getContentPane().add( toolBar, BorderLayout.NORTH );
        panelCenter.add(jButton2, null);
        panelCenter.add(jButton1, null);
        this.getContentPane().add( panelCenter, BorderLayout.CENTER);
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new frmPrincipal_AboutBoxPanel1(), "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
    
        try {
            
            System.out.println("Gerando dados...");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbContext");
            System.out.println("EntityManagerFactory Criada...");
            EntityManager em = emf.createEntityManager();
            System.out.println("EntityManager Criado...");
            EntityTransaction tx = em.getTransaction();
            System.out.println("EntityTransaction Criada...");
            
            Grafo grafo;
            Mapa mapa;
            Rota rota;
            Segmento segmento;
            Ponto ponto;
            No no;
            Arco arco;
            ArrayList rotas;
            ArrayList pontos;
            ArrayList segmentos;
            ArrayList nos;
            ArrayList arcos;
            
            grafo = new Grafo();
            nos = new ArrayList();
            
            pontos = new ArrayList();
            
            ponto = new Ponto(5.0,5.0);
            no = new No();
            nos.add(no);
            ponto.setNoSentidoDireto(no);
            no = new No();
            nos.add(no);
            ponto.setNoSentidoInverso(no);            
            pontos.add(ponto);
            
            ponto = new Ponto(10.0,15.0);
            no = new No();
            nos.add(no);
            ponto.setNoSentidoDireto(no);
            no = new No();
            nos.add(no);
            ponto.setNoSentidoInverso(no);            
            pontos.add(ponto);
            
            segmentos = new ArrayList();
            
            segmento = new Segmento();
            segmento.setIndicePontoInicial(0);
            segmento.setIndicePontoFinal(1);
            arco = new Arco();
            arco.setDestino( ((Ponto) pontos.get(1)).getNoSentidoDireto());
            arcos = new ArrayList();
            arcos.add(arco);
            ((No) ((Ponto) pontos.get(0)).getNoSentidoDireto()).setArcos(arcos);
            
            segmentos.add(segmento);
            
            rotas = new ArrayList();
            
            rota = new Rota();
            rota.setNome("Taquari");
            rota.setPontos(pontos);
            rota.setSegmentos(segmentos);
            rotas.add(rota);
            
            grafo.setNos(nos);
            
            mapa = new Mapa();
            mapa.setNome("Sao Paulo");
            mapa.setDescricao("Mapa da Cidade de Sao Paulo");
            mapa.setRotas(rotas);
            mapa.setGrafoMapa(grafo);
            
            tx.begin();
            em.persist(mapa);
            
            tx.commit();
            em.close();
            
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    
    }

    private void jButton2_actionPerformed(ActionEvent e) {
    
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbContext");
            EntityManager em = emf.createEntityManager();
            Query query = em.createQuery("from Mapa mapa order by mapa.id");
            List<Mapa> mapas = query.getResultList();
            int i,j;
        
            Mapa oMapa = (Mapa) mapas.get(0);
        
            System.out.println("Nome: " + oMapa.getNome());
            System.out.println("Descricao: " + oMapa.getDescricao());
            
            Rota rota;
            List pontos;
            Ponto ponto;
            List rotas = oMapa.getRotas();
            System.out.println("Rotas:");
            for(i=0; i < rotas.size(); i++){
                rota = (Rota) rotas.get(i);
                System.out.println("    Nome: " + rota.getNome());  
                pontos = rota.getPontos();
                    System.out.println("    Pontos:");
                for(j=0; j<pontos.size(); j++){
                    ponto = (Ponto) pontos.get(j);
                    System.out.println("        Ponto: (" + ponto.getLatitude() + 
                                                    ", " +
                                                    ponto.getLongitude() +
                                                    ")" );
                    
                }
            }
        
            em.close();
        
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    
    }
}
