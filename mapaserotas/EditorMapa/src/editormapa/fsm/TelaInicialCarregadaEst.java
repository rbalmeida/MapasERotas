package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class TelaInicialCarregadaEst extends AmbienteEstado {
    public TelaInicialCarregadaEst() {
    }

    public void executar(AmbienteFSM AmbFSM) {
    }

    public void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e) {
        String cmd;
        cmd = e.getActionCommand();
        System.out.println("action Performaem em TelaInicalCarregadaEst " + cmd);
        /* **** Eventos de MENU **** */
            /* Menu "Arquivo > Novo Mapa" da Tela principal do editor */
            if (cmd=="menuNovoMapa"){
                AmbFSM.adicionarMapa();
                AmbFSM.setEstado(AmbienteEstado.oAdicionandoMapaEst);
            }
        
            /* Menu "Arquivo > Abrir Mapa" da Tela principal do editor */
            if (cmd=="menuAbrirMapa"){
                AmbFSM.abrirMapa();
                AmbFSM.setEstado(AmbienteEstado.oPesquisandoMapaEst);
            }
            
        if (cmd=="menuSair"){
            AmbFSM.sair();
        }

    }

    public void componentResized(AmbienteFSM AmbFSM, ComponentEvent e) {
    }

    public void mousePressed(AmbienteFSM AmbFSM, MouseEvent e) {
    }

    public void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e) {
    }
}
