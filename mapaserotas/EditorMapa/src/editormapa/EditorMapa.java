package editormapa;

/* criar um novo frame para o desenho do mapa.
 * este frame deve ser optimizado para que nao haja problema de memoria.
 * Para o desenho de rotas, so devem ser carregadas para a memoria 
 * as rotas que forem ser desenhadas. Fazer um pre-filtro com bouding box */

import editormapa.fsm.AmbienteFSM;

import editormapa.fsm.InicioEst;

import javax.swing.UIManager;

public class EditorMapa {
        
    /* Classe correspondente ao ambiente, que é a classe de controle
     * principal do aplicativo */
    AmbienteFSM oAmb;
 //   AmbienteEstado oAmbEst = new AmbienteEstado()

    public EditorMapa() {
    
        oAmb = new AmbienteFSM();
        oAmb.setEstado(new InicioEst());
        oAmb.executar();
        
       }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          //  UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new EditorMapa();
    }
}
