package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class AbrindoConEst extends AmbienteEstado {
    public AbrindoConEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        
        if(cmd=="btnPesqConCancel"){            
            AmbFSM.cancelarAbrirCon();
            AmbFSM.setEstado(AmbienteEstado.oEditandoConexaoEst);
        }
        
        if(cmd=="btnPesqConPesq") {
            AmbFSM.pesquisarCon();
        }
        
        if(cmd=="btnPesqConOk"){
            AmbFSM.abrindoConOk();
            AmbFSM.setEstado(AmbienteEstado.oEditandoConexaoEst);
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
