package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class EditandoConexaoEst extends AmbienteEstado {
    public EditandoConexaoEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        
        if(cmd=="btnNovaCon"){
            AmbFSM.criarNovaConexao();
        }
         
        
       if(cmd=="btnEditConSelA"){
           AmbFSM.setEstado(AmbienteEstado.oSelecionandoRotaAEst);
           AmbFSM.exibirPesquisaRota();
       }
       
        if(cmd=="btnEditConSelB"){
            AmbFSM.setEstado(AmbienteEstado.oSelecionandoRotaBEst);
            AmbFSM.exibirPesquisaRota();
        }
    
       if(cmd=="btnEditConFechar"){
           AmbFSM.fecharEditConexao();
           AmbFSM.setEstado(AmbienteEstado.oPainelImagemCarregadoEst);
           
       }
       
        if(cmd=="btnSalvarCon"){
            AmbFSM.salvarConexao();
        }
        
        if(cmd=="btnEditConGerar"){
            AmbFSM.gerarConexao();
        }
        
        if(cmd=="btnEditConAbrir"){
            AmbFSM.abrirConexao();
            AmbFSM.setEstado(AmbienteEstado.oAbrindoConEst);
            
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
