package webserver;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import mapa.Localidade;
import mapa.Ponto;
import mapa.Rota;
import mapa.Segmento;

public class pesquisarota {
    private conexao con;

    public pesquisarota() {
    //
    }
    
     public pesquisarota(conexao ncon) {
         con = ncon;
     }
    
     public List executarpesquisa(String ini, String end){

         BuscaAEstrela busca = new BuscaAEstrela(con);
         
         String valsOrigem[];
         String valsDestino[];
         String st;
         Rota rotaOrig;
         Rota rotaDest;
         Segmento segOrig;
         Segmento segDest;
         Localidade locOrig;
         Localidade locDest;
         Ponto pTemp;
         Ponto pCon;
         List rsBusca;
         List rsListaRotas;
         ListIterator itrBusca;
         
         valsOrigem = ini.split(",");
         valsDestino = end.split(",");
         
         List rs;
         Object [] linhaRs;
         
         
          st = "select rota.id as rota_id , rs.segmentos_id, l.id as l_id " + 
              " from rota , rota_segmento rs, segmento_localidade sl, localidade l " + 
              " where rota.id = rs.rota_id " + 
              " and rs.segmentos_id = sl.segmento_id " + 
              " and sl.localidades_id = l.id ";
         
         /* Seleciona os ids dos objetos Segmento e Localidade
          * da localidade de origem */
          
          
          rs = con.executaNativeQuery(st + " and rota.nome = " + 
              "'" + valsOrigem[0].trim() + "'" + 
              " and l.numero = " + 
              "'" + valsOrigem[1].trim() + "'");       
          /* Seleciona os objetos Segmento e Localidade
           * da localidade de origem */
           
           if(rs.size()==0) return null;
           linhaRs = (Object[])rs.get(0);  
           rotaOrig = (Rota)con.executaQuery("from Rota where id = " +linhaRs[0]).get(0);
           segOrig = (Segmento)con.executaQuery("from Segmento where id = " +linhaRs[1]).get(0);
           locOrig = (Localidade)con.executaQuery("from Localidade where id = " +linhaRs[2]).get(0);
           
           /* Seleciona os ids dos objetos Segmento e Localidade
            * da localidade de destino */     
           rs = con.executaNativeQuery(st + " and rota.nome = " + 
               "'" + valsDestino[0].trim() + "'" + 
               " and l.numero = " + 
               "'" + valsDestino[1].trim() + "'");     
           /* Seleciona os objetos Segmento e Localidade
            * da localidade de destino */
            if(rs.size()==0) return null;
            linhaRs = (Object[])rs.get(0);   
            rotaDest = (Rota)con.executaQuery("from Rota where id = " +linhaRs[0]).get(0);
            segDest = (Segmento)con.executaQuery("from Segmento where id = " +linhaRs[1]).get(0);
            locDest = (Localidade)con.executaQuery("from Localidade where id = " +linhaRs[2]).get(0);
         /* Configura os estados iniciais da busca */
         if((segOrig.getSentido()==1) || (segOrig.getSentido()==3)){
             pTemp = (Ponto)rotaOrig.getPontos().get(segOrig.getIndicePontoFinal());
             busca.addEstadoInicial(pTemp.getNoSentidoDireto(), pTemp);
         }        
           
         
         if((segOrig.getSentido()==2) || (segOrig.getSentido()==3)){
             pTemp = (Ponto)rotaOrig.getPontos().get(segOrig.getIndicePontoInicial());
             busca.addEstadoInicial(pTemp.getNoSentidoInverso(), pTemp);
         }
         
         /* Adiciona eventual conexão que esteja no seguimento da localidade
          * de origem */
         rs = con.executaNativeQuery("select case when c.segmentoa_id = " +
                                    segOrig.getId() + 
                                    " then c.pontorotaaint_id else c.pontorotabint_id end as ponto_id " +
                                    " from conexao c where " +
                                    " segmentoa_id = " + segOrig.getId() + 
                                    " or segmentob_id = " + segOrig.getId());
        if(rs.size()!=0){
            pCon = (Ponto)con.executaQuery("from Ponto where id = " + rs.get(0)).get(0);
            
            if(this.calculaDistancia((Ponto)rotaOrig.getPontos().get(segOrig.getIndicePontoInicial()),
                locOrig.getPosicao()) >
                this.calculaDistancia((Ponto)rotaOrig.getPontos().get(segOrig.getIndicePontoInicial()),
                pCon)){
                /* Conexao está entre localidade e ponto inicial */
                    if((segOrig.getSentido()==2) || (segOrig.getSentido()==3))
                        busca.addEstadoInicial(pCon.getNoSentidoInverso(), pCon);
           
                } else {/* Localidade está entre conexao e ponto inicial */
                    if((segOrig.getSentido()==1) || (segOrig.getSentido()==3))
                        busca.addEstadoInicial(pCon.getNoSentidoDireto(), pCon);
            }
        }
         
         /* Corrigir aqui
          * precisa considerar conexoes nos estados iniciais e tb nos finais */ 
         
         /* Configura os estados finais da busca */
         System.out.println("segDest.sentido: " + segDest.getSentido());
         if((segDest.getSentido()==1) || (segDest.getSentido()==3)){
             pTemp = (Ponto)rotaDest.getPontos().get(segDest.getIndicePontoInicial());
             busca.addEstadoFinal(pTemp.getNoSentidoDireto(), pTemp);
         }        
         if((segDest.getSentido()==2) || (segDest.getSentido()==3)){
             pTemp = (Ponto)rotaDest.getPontos().get(segDest.getIndicePontoFinal());
             busca.addEstadoFinal(pTemp.getNoSentidoInverso(), pTemp);
         }    
         
         /* Adiciona eventual conexão que esteja no seguimento da localidade
          * de origem */
         rs = con.executaNativeQuery("select case when c.segmentoa_id = " +
                                    segDest.getId() + 
                                    " then c.pontorotaaint_id else c.pontorotabint_id end as ponto_id " +
                                    " from conexao c where " +
                                    " segmentoa_id = " + segDest.getId() + 
                                    " or segmentob_id = " + segDest.getId());
                                    
        System.out.println("select case when c.segmentoa_id = " +
                                    segDest.getId() + 
                                    " then c.pontorotaaint_id else c.pontorotabint_id end as ponto_id " +
                                    " from conexao c where " +
                                    " segmentoa_id = " + segDest.getId() + 
                                    " or segmentob_id = " + segDest.getId());
                                    
         if(rs.size()!=0){
            pCon = (Ponto)con.executaQuery("from Ponto where id = " + rs.get(0)).get(0);
            
            if(this.calculaDistancia((Ponto)rotaDest.getPontos().get(segDest.getIndicePontoInicial()),
                locDest.getPosicao()) >
                this.calculaDistancia((Ponto)rotaDest.getPontos().get(segDest.getIndicePontoInicial()),
                pCon)){
                /* Conexao está entre localidade e ponto inicial */
                    if((segDest.getSentido()==2) || (segDest.getSentido()==3))
                        busca.addEstadoFinal(pCon.getNoSentidoDireto(), pCon);
           
                } else {/* Localidade está entre conexao e ponto inicial */
                    if((segDest.getSentido()==1) || (segDest.getSentido()==3))
                        busca.addEstadoFinal(pCon.getNoSentidoInverso(), pCon);
            }
         
         }
         
         busca.setPontoDestino(locDest.getPosicao());
              
                 /* Executa a busca */
         rsBusca = busca.buscarCaminho();
    
    /* Habilitar o codigo abaixo para adicionar os pontos das localidades
     * na lista final de pontos. A classe MontaLista deve ser alterada tb
     * para contemplar esses dois pontos
     * 
      rsBusca.add(0,locOrig.getPosicao());
         rsBusca.add(locDest.getPosicao());*/
         
        return rsBusca;
        
     }

        
    private double calculaDistancia(Ponto ini, Ponto fim){
        return Math.sqrt(
            Math.pow(fim.getLatitude() - ini.getLatitude(), 2) + 
            Math.pow(fim.getLongitude() - ini.getLongitude(), 2 ));
    }

   
}
