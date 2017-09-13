package editormapa;

import editormapa.fsm.AmbienteFSM;

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
import javax.swing.JScrollBar;
import javax.swing.JToolBar;

import mapa.*;
import grafo.*;

import java.awt.Color;

import java.util.List;

import javax.persistence.Query;

import javax.swing.JDesktopPane;

public class frmPrincipal extends JFrame {
    private AmbienteFSM AmbFSM;
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuMapa = new JMenu();
    private JMenuItem menuNovoMapa = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JToolBar toolBar = new JToolBar();
    private ImageIcon imageOpen = new ImageIcon(frmPrincipal.class.getResource("openfile.gif"));
    private ImageIcon imageClose = new ImageIcon(frmPrincipal.class.getResource("closefile.gif"));
    private ImageIcon imageHelp = new ImageIcon(frmPrincipal.class.getResource("help.gif"));
    private JDesktopPane desktop = new JDesktopPane();
    private BorderLayout borderLayout1 = new BorderLayout();
    JScrollBar scrlVertical = new JScrollBar();
    JScrollBar scrlHorizontal = new JScrollBar();
    private JMenuItem menuAbrirMapa = new JMenuItem();    
    private JMenu jMenu1 = new JMenu();
        
    /* Objetos visíveis para outras classes do pacote */
    JMenuItem menuSalvar = new JMenuItem();
    JMenuItem menuEditarRotas = new JMenuItem();
    JMenuItem menuPropMapa = new JMenuItem();
    JMenuItem menuEditCon = new JMenuItem();
    JMenuItem menuFechar = new JMenuItem();
    JMenuItem menuSair = new JMenuItem();
    JButton btnAtualizarImg = new JButton();
    JMenuItem menuGerarImg = new JMenuItem();

    public frmPrincipal(Ambiente nAmb) {
        this.AmbFSM = (AmbienteFSM) nAmb;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setJMenuBar( menuBar );
        this.getContentPane().setLayout( layoutMain );
        panelCenter.setLayout(borderLayout1);
        this.setSize(new Dimension(485, 412));
        this.setTitle("Editor Mapa");
        menuMapa.setText("Mapa");
        menuMapa.setActionCommand("Mapa");
        menuNovoMapa.setText("Novo");
        menuNovoMapa.setActionCommand("menuNovoMapa");
        menuHelp.setText("Ajuda");
        menuHelpAbout.setText("Sobre");
        menuHelpAbout.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { helpAbout_ActionPerformed( ae ); } } );
        statusBar.setText( "" );
        menuMapa.add( menuNovoMapa );
        menuMapa.add(menuAbrirMapa);
        menuMapa.add(menuSalvar);
        menuMapa.add(menuPropMapa);
        menuMapa.add(menuGerarImg);
        menuMapa.add(menuFechar);
        menuMapa.add(menuSair);
        jMenu1.add(menuEditarRotas);
        jMenu1.add(menuEditCon);
        menuBar.add(menuMapa);
        menuBar.add(jMenu1);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.WEST);
        toolBar.add(btnAtualizarImg, null);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
        this.getContentPane().add(scrlVertical, BorderLayout.EAST);
        this.getContentPane().add(scrlHorizontal, BorderLayout.SOUTH);
        desktop.setBackground(Color.DARK_GRAY);
        scrlHorizontal.setOrientation(JScrollBar.HORIZONTAL);
        menuAbrirMapa.setText("Abrir");
        menuAbrirMapa.setActionCommand("menuAbrirMapa");
        menuSalvar.setText("Salvar");
        menuSalvar.setActionCommand("menuSalvar");

        menuSalvar.setEnabled(false);
        panelCenter.add(desktop, BorderLayout.CENTER);

        /* Atribui a classe manipuladora de enventos aos objetos */
        menuAbrirMapa.addActionListener(AmbFSM);
        menuNovoMapa.addActionListener(AmbFSM);
        menuSalvar.addActionListener(AmbFSM);
        menuEditarRotas.addActionListener(AmbFSM);
        menuPropMapa.addActionListener(AmbFSM);
        menuEditCon.addActionListener(AmbFSM);
        menuFechar.addActionListener(AmbFSM);
        menuSair.addActionListener(AmbFSM);
        menuGerarImg.addActionListener(AmbFSM);
        jMenu1.setText("Editar");
        menuEditarRotas.setText("Rotas");
        menuEditarRotas.setActionCommand("menuEditarRotas");
        menuEditarRotas.setEnabled(false);
        menuPropMapa.setText("Propriedades");
        menuPropMapa.setActionCommand("menuPropMapa");
        menuPropMapa.setEnabled(false);
        menuEditCon.setText("Conexões");
        menuEditCon.setActionCommand("menuEditCon");
        menuEditCon.setEnabled(false);
        menuFechar.setText("Fechar");
        menuFechar.setActionCommand("menuFechar");
        menuFechar.setEnabled(false);
        menuSair.setText("Sair");
        menuSair.setActionCommand("menuSair");
        btnAtualizarImg.setText("Atualizar");
        btnAtualizarImg.setActionCommand("btnAtualizarImg");
        btnAtualizarImg.setSize(new Dimension(50, 25));
        this.addComponentListener(AmbFSM);
        btnAtualizarImg.setVisible(false);
        btnAtualizarImg.addActionListener(AmbFSM);
        menuGerarImg.setText("Gerar Imagens");
        menuGerarImg.setActionCommand("menuGerarImg");
        menuGerarImg.setEnabled(false);
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new frmPrincipal_AboutBoxPanel1(), "About", JOptionPane.PLAIN_MESSAGE);
    }


    public JDesktopPane getDesktop() {
        return desktop;
    }
}
