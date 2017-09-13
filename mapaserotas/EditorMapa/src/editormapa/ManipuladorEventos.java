package editormapa;

import editormapa.fsm.AmbienteFSM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ManipuladorEventos implements 
ActionListener, 
MouseListener, MouseMotionListener, 
KeyListener,
ComponentListener,
DocumentListener {
    private AmbienteFSM AmbFSM;

    public ManipuladorEventos() {
    }
    
    public ManipuladorEventos(AmbienteFSM nAmb) {
        this.AmbFSM = nAmb;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    /* **** Eventos de BOTÕES e MENUS **** */
    public void actionPerformed(ActionEvent e) {
    /*    AmbFSM.actionPerformed(e);
        String cmd;
        cmd = e.getActionCommand();
        
        /* **** Eventos de MENU **** */
            /* Menu "Arquivo > Novo Mapa" da Tela principal do editor */
       /*     if (cmd=="menuNovoMapa"){
                   amb.adicionarMapa();
            }
        
            /* Menu "Arquivo > Abrir Mapa" da Tela principal do editor */
        /*    if (cmd=="menuAbrirMapa"){
                   amb.abrirMapa();
            }
        
            /* Menu "Arquivo > Salvar"  da Tela principal do editor */
       /*     if (cmd=="menuSalvar") {
                amb.salvarMapa();
            }
        
        /* Menu "Editar > Rotas"  da Tela principal do editor */
     /*   if (cmd=="menuEditarRotas") {
            amb.editarRotas();
        }
        
        /* **** Eventos de BOTÃO **** */
          
            /* Botao OK da tela de Propriedades do Mapa */
          /*  if (cmd=="btn"){
                           }
        

                */
    
    }

    /* **** Eventos de EDIÇÃO DE CAMPOS **** */
    public void insertUpdate(DocumentEvent e) {
    }

    public void removeUpdate(DocumentEvent e) {
    }

    public void changedUpdate(DocumentEvent e) {
        String cmd;
        cmd = (String)e.getDocument().getProperty("ActionCommand");
    }

    /* *** Eventos de janela **** */
    public void componentResized(ComponentEvent e) {
       //amb.redimensionar();
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}

//criar handler q tb umplemente ComponentListener para eventos de resize