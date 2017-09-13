package editormapa;

import mapa.Ponto;

public class FuncLib {
    public FuncLib() {
    }
    
   /* http://www.meridianworlddata.com/Distance-Calculation.asp
    * 29/07 - 10:50AM
    * */
    public static double calculaDistancia(Ponto ini, Ponto fim){
        return Math.sqrt(
            Math.pow(fim.getLatitude() - ini.getLatitude(), 2) + 
            Math.pow(fim.getLongitude() - ini.getLongitude(), 2 ));
    }
    
    /* http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/ 
     * 13/10 3:00PM */
     /* testar cenarios diferentes, pra ver se acha sempre o segmento 
      * correto */
    public static Ponto calculaIntersecao(Ponto r1Ini, Ponto r1Fim, Ponto r2Ini, Ponto r2Fim){
      
        double denom;
        double nume_a;
        double nume_b;
        double ua;
        double ub;
        Ponto inte;
                
        denom = ((r2Fim.getLongitude() - r2Ini.getLongitude())*(r1Fim.getLatitude() - r1Ini.getLatitude())) -
                            ((r2Fim.getLatitude() - r2Ini.getLatitude())*(r1Fim.getLongitude() - r1Ini.getLongitude()));

        nume_a = ((r2Fim.getLatitude() - r2Ini.getLatitude())*(r1Ini.getLongitude() - r2Ini.getLongitude())) -
                             ((r2Fim.getLongitude() - r2Ini.getLongitude())*(r1Ini.getLatitude() - r2Ini.getLatitude()));

        nume_b = ((r1Fim.getLatitude() - r1Ini.getLatitude())*(r1Ini.getLongitude() - r2Ini.getLongitude())) -
                             ((r1Fim.getLongitude() - r1Ini.getLongitude())*(r1Ini.getLatitude() - r2Ini.getLatitude()));
                
                
        ua = nume_a / denom;
        ub = nume_b / denom;   
        
        if(ua >= -1.0 && ua <= 1.0 && ub >= -1.0 && ub <= 1.0){
                    
                    inte = new Ponto();
                    inte.setLatitude(r1Ini.getLatitude() + ua*(r1Fim.getLatitude() - r1Ini.getLatitude()));
                    inte.setLongitude(r1Ini.getLongitude() + ua*(r1Fim.getLongitude() - r1Ini.getLongitude()));

                    return inte;
                } 
                             
        return null;        
        
    }
}
