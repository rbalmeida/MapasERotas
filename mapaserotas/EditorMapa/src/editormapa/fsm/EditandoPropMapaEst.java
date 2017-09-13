package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class EditandoPropMapaEst extends AmbienteEstado {
    public EditandoPropMapaEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        
        if (cmd=="menuSalvar"){
                if(AmbFSM.validarPropMapa() == true){
                    AmbFSM.salvarMapa(false); 
                }
                    
        }
        
        if (cmd=="btnPropMapaOk"){
            if(AmbFSM.validarPropMapa() == true){
               AmbFSM.setEstado(AmbienteEstado.oCarregandoPainelImagemEst);
               AmbFSM.propMapaOk();
               AmbFSM.carregarPainelImagem();
               AmbFSM.setEstado(AmbienteEstado.oPainelImagemCarregadoEst);
            }
        }
        
        if (cmd=="btnPesqMapaCancelar"){
       //     AmbFSM.cancelarPesqMapa();
         //   AmbFSM.setEstado(AmbienteEstado.oPainelImagemCarregadoEst);
        }
    
    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
       // AmbFSM.redimensionar();
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
