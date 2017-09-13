package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class SelecionandoRotaBEst extends AmbienteEstado {
    public SelecionandoRotaBEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
    
        String cmd;
        cmd = e.getActionCommand();
    
        if(cmd=="btnPesqRotaPesq") {
            AmbFSM.pesquisarRota();
        }
        
        if(cmd=="btnPesqRotaOk"){
            AmbFSM.rotaBSelecionada();
            AmbFSM.setEstado(AmbienteEstado.oEditandoConexaoEst);
        }
    
        if(cmd=="btnPesqRotaCancel"){            
            AmbFSM.cancelarPesquisaRota();
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
