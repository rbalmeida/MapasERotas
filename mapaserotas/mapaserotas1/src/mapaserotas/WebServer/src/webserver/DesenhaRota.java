package webserver;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;

import javax.servlet.*;
import javax.servlet.http.*;


public class DesenhaRota extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
   
   /* ver:
    * http://java.sun.com/developer/technicalArticles/Programming/compression/ 
    * 
    * */
        int compressedLen = Integer.parseInt(request.getParameter("len"));
        String coord = request.getParameter("coord");
        
        System.out.println("coord: " + coord);
        String [] pontos = coord.split(";");
        
        OutputStream os = response.getOutputStream();
        BufferedImage bufferedImage = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = bufferedImage.createGraphics();
        
        int i, x1, y1, x2, y2;
        x1=10; y1=10;
        x2=12; y2=15;
        BasicStroke st;
        st = new BasicStroke(4);
        g.setColor(Color.YELLOW);
        g.setStroke(st);
        for( i=0; i < pontos.length -2; i= i + 2){
                    
            g.draw(new Line2D.Double(Double.parseDouble(pontos[i+1]),
                - Double.parseDouble(pontos[i]),
                Double.parseDouble(pontos[i+3]),
                - Double.parseDouble(pontos[i+2])));
        
        }
        
        g.dispose();
        
        response.setContentType("image/png");
        ImageIO.write(bufferedImage, "png", os);
        os.close();
   
    }
}
