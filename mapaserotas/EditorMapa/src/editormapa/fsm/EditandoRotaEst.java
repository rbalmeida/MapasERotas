package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class EditandoRotaEst extends AmbienteEstado {
    public EditandoRotaEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        System.out.println("EditandoRotaEst acion: " + cmd);
        
        if(cmd=="btnEditRotaAbrir"){
            AmbFSM.setEstado(AmbienteEstado.oAbrindoRotaEst);
            AmbFSM.exibirPesquisaRota();
        }
        
         if(cmd=="btnEditRotaFechar"){
            AmbFSM.setEstado(AmbienteEstado.oPainelImagemCarregadoEst);
             AmbFSM.fecharEditarRota();
         }
         
        if(cmd=="btnCapturarRota"){
            AmbFSM.setEstado(AmbienteEstado.oCapturandoRotaEst);
        }
        
        
        if(cmd=="btnEditRotaLocal"){
            AmbFSM.editarLocalidades();
            AmbFSM.setEstado(AmbienteEstado.oEditandoLocalidadesEst);            
            
        }
        
        if(cmd=="btnCapturarRota"){
            AmbFSM.setEstado(AmbienteEstado.oCapturandoRotaEst);            
            
        }
        
        if (cmd=="btnAtualizarImg"){
            AmbFSM.atualizarPainelImagem();
        }
        
        
        if (cmd=="menuGerarImg"){
            AmbFSM.gerarImagens();
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
