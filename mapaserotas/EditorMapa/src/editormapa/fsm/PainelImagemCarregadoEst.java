package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class PainelImagemCarregadoEst extends AmbienteEstado {
    public PainelImagemCarregadoEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        
        /* Menu "Editar > Rotas"  da Tela principal do editor */
          if (cmd=="menuEditarRotas") {
            AmbFSM.editarRotas();
            AmbFSM.setEstado(AmbienteEstado.oEditandoRotaEst);
        }
        
        if (cmd=="menuPropMapa") {
          AmbFSM.editarPropriedadesMapa();
          AmbFSM.setEstado(AmbienteEstado.oEditandoPropMapaEst);
        }
        
        
        if (cmd=="menuEditCon"){
            AmbFSM.editarConexoes();
            AmbFSM.setEstado(AmbienteEstado.oEditandoConexaoEst);
        }
        
        if (cmd=="menuAbrirMapa"){
            AmbFSM.fecharMapa();
            AmbFSM.setEstado(AmbienteEstado.oTelaInicialCarregadaEst);
            AmbFSM.abrirMapa();
            AmbFSM.setEstado(AmbienteEstado.oPesquisandoMapaEst);
        }
        
        
        if (cmd=="menuFechar"){
            AmbFSM.fecharMapa();
            AmbFSM.setEstado(AmbienteEstado.oTelaInicialCarregadaEst);
        }
        
        if (cmd=="menuSair"){
            AmbFSM.sair();
        }
        
        if (cmd=="menuSalvar"){
                if(AmbFSM.validarPropMapa() == true){
                    AmbFSM.salvarMapa(false); 
                }
                    
        }        
        if (cmd=="btnAtualizarImg"){
            AmbFSM.atualizarPainelImagem();
        }
        
        if (cmd=="menuGerarImg"){
            AmbFSM.gerarImagens();
        }        
        
    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
        System.out.println("Evendo de resize no painel imagem carregado ");
        AmbFSM.redimensionar();
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
