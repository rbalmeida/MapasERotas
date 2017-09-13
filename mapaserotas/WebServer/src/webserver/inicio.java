package webserver;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import java.util.ListIterator;

import javax.servlet.*;
import javax.servlet.http.*;

import mapa.Localidade;
import mapa.Ponto;
import mapa.Rota;
import mapa.Segmento;


public class inicio extends HttpServlet {
    private conexao con;

    private static final String CONTENT_TYPE = "text/html";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        con = new conexao();
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
       
        PrintWriter out = response.getWriter();
    
        
        BuscaAEstrela busca = new BuscaAEstrela(con);
    
        String stOrigem;
        String stDestino;
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
        List rsBusca;
        List rsListaRotas;
        ListIterator itrBusca;
    
        stOrigem = "RHO_01, 1";
        stDestino = "RHO_02, 260";
    
        /* Strings com as rotas de origem e destino */
        valsOrigem = stOrigem.split(",");
        valsDestino = stDestino.split(",");
        
        List rs;
        Object [] linhaRs;
       
        out.print("<html><head></head><body>");
       
       try{
       
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
        out.print("rs.size ORIG " + rs.size());
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
         out.print("<BR>rs.size DEST " + rs.size());
        linhaRs = (Object[])rs.get(0);   
        rotaDest = (Rota)con.executaQuery("from Rota where id = " +linhaRs[0]).get(0);
        segDest = (Segmento)con.executaQuery("from Segmento where id = " +linhaRs[1]).get(0);
        locDest = (Localidade)con.executaQuery("from Localidade where id = " +linhaRs[2]).get(0);
           
           out.print("ACHOU DEST ");
        
        /* Configura os estados iniciais da busca */
        if((segOrig.getSentido()==1) || (segOrig.getSentido()==3)){
            pTemp = (Ponto)rotaOrig.getPontos().get(segOrig.getIndicePontoFinal());
            busca.addEstadoInicial(pTemp.getNoSentidoDireto(), pTemp);
        }        
        if((segOrig.getSentido()==2) || (segOrig.getSentido()==3)){
            pTemp = (Ponto)rotaOrig.getPontos().get(segOrig.getIndicePontoInicial());
            busca.addEstadoInicial(pTemp.getNoSentidoInverso(), pTemp);
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
    
        busca.setPontoDestino(locDest.getPosicao());
        
           out.print("<br> configurou nos iniciais busca ");
    
        /* Executa a busca */
        rsBusca = busca.buscarCaminho();
        itrBusca = rsBusca.listIterator();
        
        rsListaRotas = geraListagemRotas(rsBusca);
               
        out.print("<br>Pontos Caminho<br>");
        out.print("<br>Origem: " + locOrig.getPosicao().getLatitude() + ", " + locOrig.getPosicao().getLongitude());
        while(itrBusca.hasNext()){
            pTemp = (Ponto)itrBusca.next();
            out.print("<br>" + pTemp.getLatitude() + ", " + pTemp.getLongitude());
        
        }
        out.print("<br>Destino: " + locDest.getPosicao().getLatitude() + ", " + locDest.getPosicao().getLongitude());
        
        
        
        out.print("<br>Rotas Caminho<br>");        
        itrBusca = rsListaRotas.listIterator();
        while(itrBusca.hasNext()){               
               out.print("<br>" + itrBusca.next());           
        }
    
    
        out.print("</body></html>");
        
       }
       catch(Exception e){
           out.print("<br>Erro: " + e);
          // out.print("<br>Erro: " + e.getStackTrace());
           out.print("</body></html>");
       }
      
    }
    
    public List geraListagemRotas(List pontos){
        ListIterator itrPontos;
        itrPontos = pontos.listIterator();
        List rs;
        Ponto pTemp;
        List lRotas;
        Object [] linhaRs;
        String st;
        
        st = "select r.nome, r.id from rota r, rota_ponto rp, ponto p " +
        " where rp.pontos_id = p.id " +
        " and r.id = rp.rota_id ";
        
        lRotas = new ArrayList();
        while(itrPontos.hasNext()){
            pTemp = (Ponto)itrPontos.next();
            rs = con.executaNativeQuery(st + " " +
            "and p.id = " + pTemp.getId());
            
            System.out.println("Buscando rota: " + st + " " +
            "and p.id = " + pTemp.getId());
            
            if(rs.size()==1){
                linhaRs = (Object [])rs.get(0);
                 lRotas.add((String)linhaRs[0]);
             }
            
        }
        
        return lRotas;
        
    }
    
}
