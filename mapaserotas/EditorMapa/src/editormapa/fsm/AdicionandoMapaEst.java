package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class AdicionandoMapaEst extends AmbienteEstado {
    public AdicionandoMapaEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
      
        if (cmd=="menuSalvar"){
            if(AmbFSM.validarPropMapa() == true){
                AmbFSM.salvarMapa(true); 
                AmbFSM.setEstado(AmbienteEstado.oEditandoPropMapaEst);
            }
        }
        
        if (cmd=="btnPropMapaCancelar"){
               AmbFSM.cancelarAdicMapa();
               AmbFSM.setEstado(AmbienteEstado.oTelaInicialCarregadaEst);
        }
        
        if (cmd=="btnPropMapaOk"){
            if(AmbFSM.validarPropMapa() == true){
                AmbFSM.salvarMapa(true); 
                AmbFSM.setEstado(AmbienteEstado.oEditandoPropMapaEst);
                AmbFSM.propMapaOk();
                AmbFSM.setEstado(AmbienteEstado.oCarregandoPainelImagemEst);
                AmbFSM.carregarPainelImagem();
                AmbFSM.setEstado(AmbienteEstado.oPainelImagemCarregadoEst);
            }
        }
                
    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
