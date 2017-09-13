package webserver;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import mapa.Ponto;

public class MontaLista {
    private conexao con;

    public MontaLista() {
    }
    
    public MontaLista(conexao ncon) {
        con = ncon;
    }
    
    public List geraListagemRotas(List pontos){
        ListIterator itrPontos;
        itrPontos = pontos.listIterator();
        List rs;
        Ponto pTemp;
        Ponto pTemp2;
        List lRotas;
        Object [] linhaRs;
        String st, stFinal;
		String ultRota = "";
		String rota;
		String direcao = "";
		double ang;
        
        st = "select r.nome, r.id from rota r, rota_ponto rp, ponto p " +
        " where rp.pontos_id = p.id " +
        " and r.id = rp.rota_id ";
        
        stFinal = "select r.nome, r.id " +
        " from rota r, rota_segmento rs, segmento_localidade sl, localidade l " +
        " where r.id = rs.rota_id  " +
        " and sl.segmento_id = rs.segmentos_id" +
        " and l.id = sl.localidades_id";
        
        lRotas = new ArrayList();
        /* Descarta o 1o ponto, que e o ponto da localidade inicial
         *  pTemp = (Ponto)itrPontos.next(); */
    
        while(itrPontos.hasNext()){
            pTemp = (Ponto)itrPontos.next();
            rs = con.executaNativeQuery(st + " " +
            "and p.id = " + pTemp.getId());
            
            System.out.println("Buscando rota: " + st + " " +
            "and p.id = " + pTemp.getId());
            
            if(rs.size()==1){
                linhaRs = (Object [])rs.get(0);
                rota = (String)linhaRs[0];
                if (!(rota.equals(ultRota))){
                        direcao = "Siga por: " + rota;
                        ultRota = rota;
                        lRotas.add(direcao);
                }             
             }
            else /* Não há rota, então é um ponto de conexão */
            {
                /* Adicionar tratamento para qdo o ponto aposa conexao
                 * for o ponto final, da localidade de destino */
                 /* TEM Q tratar os dois casos,
                  * se depois da conexao eh o final ou um segmento
                  * e se ele cai aqui vindo de um segmento */
              //   if(itrPontos.hasNext()){
                    pTemp2 = (Ponto)itrPontos.next();
                    ang = calculaAngulo((Ponto) pontos.get(itrPontos.previousIndex()-1), pTemp, pTemp2);
                
                
                    rs = con.executaNativeQuery(st + " " +
                        " and p.id = " + pTemp2.getId());   
              //   }
               /* else 
                * /* O ponto após a conexão é o ponto da localidade de destino */
                /* rs = con.executaNativeQuery(stFinal + " " +
                     " and l.posicao_id = " + pTemp2.getId()); */
            
                linhaRs = (Object [])rs.get(0);
                rota = (String)linhaRs[0];
                ultRota = rota;
                if(ang == 0)
                        direcao = "Siga por: " + rota;
                else
                    if(ang < 0)
                        direcao = "Vire à Direita: " + rota;
                    else
                        direcao = "Vire à Esquerda: " + rota;   
                lRotas.add(direcao);
            }
            
        }
        
        return lRotas;
        
    }
	 
	 public double calculaAngulo(Ponto ini, Ponto meio, Ponto fim){
	 	return 0;
	 
	 }
    
}
