package editormapa.fsm;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public abstract class AmbienteEstado {

    protected AmbienteEstado(){}   

    /* Eventos que ocorrem com ambiente e são tratados pela maquina
     * de estados */
    public abstract void executar(AmbienteFSM AmbFSM); 
    public abstract void actionPerformed(AmbienteFSM AmbFSM, ActionEvent e);
    public abstract void componentResized(AmbienteFSM AmbFSM, ComponentEvent e);
    public abstract void mousePressed(AmbienteFSM AmbFSM, MouseEvent e);
    public abstract void mouseMoved(AmbienteFSM AmbFSM, MouseEvent e);
    
    /* Variaveis estáticas de instancia para armazenar os estados 
     * possíveis do ambiente */     
    static final InicioEst oInicioEst = new InicioEst();
    static final CarregandoTelaInicialEst oCarregandoTelaInicialEst = new CarregandoTelaInicialEst();
    static final TelaInicialCarregadaEst oTelaInicialCarregadaEst = new TelaInicialCarregadaEst();
    static final PesquisandoMapaEst oPesquisandoMapaEst = new PesquisandoMapaEst();
    static final AdicionandoMapaEst oAdicionandoMapaEst = new AdicionandoMapaEst();
    static final EditandoPropMapaEst oEditandoPropMapaEst = new EditandoPropMapaEst();
    static final CarregandoPainelImagemEst oCarregandoPainelImagemEst = new CarregandoPainelImagemEst();
    static final PainelImagemCarregadoEst oPainelImagemCarregadoEst = new PainelImagemCarregadoEst();
    static final EditandoRotaEst oEditandoRotaEst = new EditandoRotaEst();
    static final AbrindoRotaEst oAbrindoRotaEst = new AbrindoRotaEst();
    static final EditandoConexaoEst oEditandoConexaoEst = new EditandoConexaoEst();
    static final SelecionandoRotaAEst oSelecionandoRotaAEst = new SelecionandoRotaAEst();
    static final SelecionandoRotaBEst oSelecionandoRotaBEst = new SelecionandoRotaBEst();
    static final CapturandoRotaEst oCapturandoRotaEst = new CapturandoRotaEst();
    static final EditandoLocalidadesEst oEditandoLocalidadesEst = new EditandoLocalidadesEst();
    static final AbrindoConEst oAbrindoConEst= new AbrindoConEst();
}
