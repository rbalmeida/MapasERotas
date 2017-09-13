package webserver;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class WAPPrincipal extends HttpServlet {
    conexao con;
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        con = new conexao();
    }

    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
        
        String ini = request.getParameter("varRotaOri");
        String end = request.getParameter("varRotaDes");   
        
        PrintWriter out = response.getWriter();
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\" \"http://www.wapforum.org/DTD/wml_1.1.xml\">");
        out.println("<wml>");
        out.println("<card id=\"card_index\" title=\"Mlogic - Resultado da Pesquisa\">");
        out.println("<p align=\"center\"><b><big>Mlogic - Resultado da Pesquisa</big></b></p>");
        
        
        pesquisarota rota = new pesquisarota(con);
        List lPontos = rota.executarpesquisa(ini, end);
        if(lPontos!=null){
            MontaLista ml = new MontaLista(con);
            List listaRotas = ml.geraListagemRotas(lPontos);       
            montapaginarota(out, listaRotas, ini, end);
        }
        else{
            out.println("<p></p><p>Não foi possível achar uma rota entre");
            out.println(ini  +  " e " + end + "</p>");
        }
        
        out.println("</card>");
        out.println("\n</wml>\n");
        out.close();
   
    }
    
    public void montapaginarota(PrintWriter out, List resultado, String ini, String end){
    
        out.println("<p></p><p>Inicia em:</p>");
        out.println("<p><b><big>" + ini  +  "</big></b></p><p></p>");
        for (int i = 0; i < resultado.size(); i++){
            out.println("<p>" + resultado.get(i)  +  "</p>");            
        }
        out.println("<p></p><p>Termino em:</p>");
        out.println("<p><b><big>" + end  +  "</big></b></p>");

        
    }
    
}
