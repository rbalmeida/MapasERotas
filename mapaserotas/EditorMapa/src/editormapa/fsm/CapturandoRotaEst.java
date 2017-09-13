package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class CapturandoRotaEst extends AmbienteEstado {

    public CapturandoRotaEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
    
        if(cmd=="btnCapturarRota"){
            AmbFSM.setEstado(AmbienteEstado.oEditandoRotaEst);
        }
    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
        AmbFSM.pontoCapturado(e);
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
