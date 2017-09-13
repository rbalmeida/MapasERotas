package editormapa.fsm;

import editormapa.Ambiente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AmbienteFSM extends Ambiente implements 
ActionListener, 
MouseListener, MouseMotionListener, 
KeyListener, ComponentListener
{
    private AmbienteEstado mAmbEst;

    public AmbienteFSM() {
        //this.
    }
    
    /* Método para a autalização do estado */
    public void setEstado(AmbienteEstado nEstado){
        this.mAmbEst = nEstado;
    }    
    
    /* *** Eventos que podem ocorrer *** 
     * As classes manipuladoras de eventos disparam estes eventos
     * que geram mudanças na maquina de estado que controla o ambiente
     * e dispara as ações correspondentes implementadas na classe Ambiente */
    /* Ver no documento pdf, ele deve somente repassar as acoes p/ o estado
     * atual, ex.: mAmbEst.actionPerformed(this, e) */     
    public void executar(){
        mAmbEst.executar(this);
    }
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action preformed em AmbienteFSM: " + mAmbEst);
        mAmbEst.actionPerformed(this, e);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mAmbEst.mousePressed(this, e);
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        mAmbEst.mouseMoved(this, e);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        System.out.println("FMS component resized, Estado Atual: " + mAmbEst);
        mAmbEst.componentResized(this, e);
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}
