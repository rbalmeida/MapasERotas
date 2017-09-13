package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class InicioEst extends AmbienteEstado{

    public InicioEst() {    
    }
 
    public void executar(AmbienteFSM AmbFSM) {
        AmbFSM.setEstado(AmbienteEstado.oCarregandoTelaInicialEst);
        AmbFSM.carregar();  
        AmbFSM.setEstado(AmbienteEstado.oTelaInicialCarregadaEst);
    }
    
    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
