package webserver;
/*
 * deve usar o java-deflater para fazer a compactacao da lista
 * de pontos a serem passados.
 * Deve tambem fazser o urlencoding da string de pontos compactada antes de 
 * adiciona-la a url final, de forma que se algum caracter especial
 * for incluido na string compactada este passe normalmente
 * como parametro da url
 * */

import java.io.IOException;
import java.io.PrintWriter;

import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.util.List;

import java.util.ListIterator;
import java.util.zip.Deflater;

import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URLEncoder;

import mapa.Mapa;
import mapa.NivelZoom;
import mapa.Ponto;


public class WEBPrincipal extends HttpServlet {
    private conexao con;
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private String urlDesenhaRota = "http://141.144.105.79:8988/mapaserotas-WebServer-context-root/desenharota";
    private int iMapaID = 5148;
    private Mapa oMapa;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        con = new conexao();
        oMapa = (Mapa)con.executaQuery("from Mapa where id = " + iMapaID).get(0);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);

                       String endereco = request.getParameter("endereco");
                       String ini = request.getParameter("ini");
                       String end = request.getParameter("end");

                       PrintWriter out;
                       out = response.getWriter();

                       if ( endereco != null ){
                          pesquisalocalidade local = new pesquisalocalidade(con);
                          List resultado = local.executarpesquisa(endereco);
                          montapaginaendereco(out, resultado, endereco);
                       }
                       else{
                           pesquisarota rota = new pesquisarota(con);
                           List lPontos = rota.executarpesquisa(ini, end);
                           if(lPontos!=null){
                                if(lPontos.size()!=0){
                                    MontaLista ml = new MontaLista(con);
                                    List listaRotas = ml.geraListagemRotas(lPontos);
                                    montapaginarota(out, listaRotas, ini, end);
                                    gerarDesenhoRota(out, lPontos);
                                }else{
                               out.println("<br>Não foi possível achar uma rota entre ");
                               out.println(ini  +  " e " + end);
                               
                           }
                                
                                
                           }else{
                               out.println("<br>Não foi possível achar uma rota entre ");
                               out.println(ini  +  " e " + end);
                               
                           }
                       }
                       
        out.close();

    }
    public void montapaginaendereco(PrintWriter out, List resultado, String endereco){

           out.println("Endereço:<br>" + endereco);
        out.close();
    }
    public void montapaginarota(PrintWriter out, List resultado, String ini, String end){

        out.println("<table border=\"1\" width=\"100%\" cellspacing=\"1\" id=\"table1\" style=\"border-collapse: collapse\" bordercolor=\"#0000FF\">");
        out.println("<tr><td><p style=\"margin-top: 0; margin-bottom: 0\" align=\"center\"><b><font size=\"4\">Rota</font></b></td></tr>");
        out.println("</table><br><table border=\"1\" width=\"100%\" cellspacing=\"1\" id=\"table1\" style=\"border-collapse: collapse\" bordercolor=\"#0000FF\">");
        out.println("<tr><td><p style=\"margin-top: 0; margin-bottom: 0\">Inicia em:<p style=\"margin-top: 0; margin-bottom: 0\"><b>" + ini + "</b></td></tr></table><br>");
        out.println("<table border=\"1\" width=\"100%\" cellspacing=\"1\" id=\"table1\" style=\"border-collapse: collapse\" bordercolor=\"#0000FF\">");
        for (int i = 0; i < resultado.size(); i++){
            out.println("<tr><td><p style=\"margin-top: 0; margin-bottom: 0\">" + resultado.get(i));
            out.println("</td></tr>");
        }
         out.println("</table>");
         out.println("<table border=\"1\" width=\"100%\" cellspacing=\"1\" id=\"table1\" style=\"border-collapse: collapse\" bordercolor=\"#0000FF\"><br>");
         out.println("<tr><td><p style=\"margin-top: 0; margin-bottom: 0\">Termino em:<p style=\"margin-top: 0; margin-bottom: 0\"><b> " + end + "</b></td></tr>");
         out.println("</tablel>");

    }
    
    public void gerarDesenhoRota(PrintWriter out, List pontos){
        Ponto pTemp;
        String coord;
        String outCoord;
        coord = "";
        outCoord = "";
        ListIterator itrPontos = pontos.listIterator();
        Double escala = ((NivelZoom)oMapa.getNiveisZoom().get(0)).getEscala();
        
        while(itrPontos.hasNext()){
            pTemp = (Ponto)itrPontos.next();
            coord = coord + (pTemp.getLatitude()/escala) + ";" + 
                    (pTemp.getLongitude()/escala) + ";";
        }
       
       out.println("<img src=\"" + urlDesenhaRota + "?len=1&coord=" + URLEncoder.encode(coord) + "\">");
        
    }

}
