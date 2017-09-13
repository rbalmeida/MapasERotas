package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class AbrindoRotaEst extends AmbienteEstado {
    public AbrindoRotaEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        
        if(cmd=="btnPesqRotaCancel"){            
            AmbFSM.cancelarAbrirRota();
            AmbFSM.setEstado(AmbienteEstado.oEditandoRotaEst);
        }
        
        if(cmd=="btnPesqRotaPesq") {
            AmbFSM.pesquisarRota();
        }
        
        if(cmd=="btnPesqRotaOk"){
            AmbFSM.abrindoRotaOk();
            AmbFSM.setEstado(AmbienteEstado.oEditandoRotaEst);
        }
        
    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
        AmbFSM.redimensionar();
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
