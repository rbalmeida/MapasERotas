package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class EditandoLocalidadesEst extends AmbienteEstado {
    public EditandoLocalidadesEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
       
        if(cmd=="btnLocalAdi"){
            AmbFSM.adicionarLocalidade();            
        }
        
        if(cmd=="btnLocalRem"){
            AmbFSM.removerLocalidade();
        }
        
        if(cmd=="btnLocalFechar"){
            AmbFSM.fecharEditLocal();
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
