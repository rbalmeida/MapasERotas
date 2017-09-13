                                                                     
                                                                     
                                                                     
                                             
package webserver;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javax.servlet.*;
import javax.servlet.http.*;

import mapa.Mapa;

import mapa.Ponto;
import mapa.Rota;

import org.hibernate.ejb.Ejb3Configuration;

public class WAPIndex extends HttpServlet {
    private conexao con;
    private static final String CONTENT_TYPE = "text/vnd.wap.wml";
    private String URLWapPrincipal = "http://141.144.105.79:8988/mapaserotas-WebServer-context-root/wapprincipal";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        con = new conexao();
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        int i, j;
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\" \"http://www.wapforum.org/DTD/wml_1.1.xml\">");
        out.println("<wml>");
        out.println("<card id=\"card_index\" title=\"Mlogic - Pesquisa Rota\">");
        out.println("<p align=\"center\"><b><big>Mlogic - Pesquisa Rota</big></b></p>");
        out.println("<br><br><br><p></p><p>\n Entre com a Rota desejado:</p>\n");
        out.println("<br><p>Origem:<input name=\"varRotaOri\" size=\"9\" emptyok=\"false\"/></p>");
        out.println("<br><p>Destino:<input name=\"varRotaDes\" size=\"9\" emptyok=\"false\"/></p>");
        out.println("<do type=\"accept\" label=\"Pesquisar\">");
        out.println("<go ");
        //Aterar o endereço de pequisa caso mude o IP do servidor
        out.println("href=\"" + URLWapPrincipal + "\" method=\"post\">\n");
        out.println("<postfield name=\"varRotaOri\" value=\"$varRotaOri\"/>");
        out.println("<postfield name=\"varRotaDes\" value=\"$varRotaDes\"/>");
        out.println("</go></do></card>");
        out.println("\n</wml>\n");
        out.close();

        
        
      
    }
}

