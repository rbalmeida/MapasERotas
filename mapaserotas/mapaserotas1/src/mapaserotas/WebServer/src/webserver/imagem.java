package webserver;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import javax.servlet.*;
import javax.servlet.http.*;

public class imagem extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
    
        
        OutputStream os = response.getOutputStream();
        BufferedImage bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            
        Graphics2D g = bufferedImage.createGraphics();
           
        int i, x1, y1, x2, y2;
        x1=10; y1=10;
        x2=12; y2=15;
        BasicStroke st;
        st = new BasicStroke(4);
       // Color transparent = new Color(0, 0, 0, 0);        
      //  g.setColor(transparent);
     //  g.setColor(Color.BLACK);
  //     g.setComposite(AlphaComposite.Clear);
  //      g.fillRect(0, 0, 500, 500);
 //       g.setComposite(AlphaComposite.SrcOver);
          g.setStroke(st);     
        for( i=1; i<20; i++){
                 
            g.setColor(Color.YELLOW);       
            g.drawLine(x1, y1, x2, y2);
            g.setColor(Color.RED);
            g.fillOval(x1,y1,1,1);
             x1 = x2;
            y1 = y2;
            x2 = x2 + 2;
            y2 = y2+ 5;
                
        }
        g.dispose();
        
        response.setContentType("image/png");        
        ImageIO.write(bufferedImage, "png", os);
        os.close();
    }
}
