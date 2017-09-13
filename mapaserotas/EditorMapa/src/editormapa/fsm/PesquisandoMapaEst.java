package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class PesquisandoMapaEst extends AmbienteEstado {
    public PesquisandoMapaEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        
        if (cmd=="btnPesqMapaCancelar"){
            AmbFSM.cancelarPesqMapa();
            AmbFSM.setEstado(AmbienteEstado.oTelaInicialCarregadaEst);
        }
        
        if (cmd=="btnPesqMapaPesquisar"){            
            AmbFSM.pesquisarMapa();            
        }
        
        if (cmd=="btnPesqMapaOk"){            
            AmbFSM.setEstado(AmbienteEstado.oCarregandoPainelImagemEst);
            AmbFSM.pesquisarMapaOk();
            AmbFSM.carregarPainelImagem();
            AmbFSM.setEstado(AmbienteEstado.oPainelImagemCarregadoEst);
        }
    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
    
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
