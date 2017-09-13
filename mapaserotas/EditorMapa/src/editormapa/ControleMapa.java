package editormapa;

import java.util.ArrayList;

import java.util.List;

import mapa.Mapa;

public class ControleMapa {
    private Mapa oMapa;
    private List NiveisZoom;

    public ControleMapa() {
    }
    
    public void adicionarNivelZoom(){
         NiveisZoom = oMapa.getNiveisZoom();
         
        if (NiveisZoom != null){            
          //  NiveisZoom.add();
        }
        else{
            NiveisZoom  = new ArrayList();
            
        }
        
        
    }
}
