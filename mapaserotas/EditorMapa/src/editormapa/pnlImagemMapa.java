package editormapa;

import editormapa.fsm.AmbienteFSM;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.Shape;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Iterator;

import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;

import javax.imageio.stream.ImageInputStream;

import javax.swing.JButton;
import javax.swing.JPanel;

import mapa.Mapa;
import mapa.NivelZoom;
import java.util.List;

import java.util.Map;

import javax.imageio.stream.FileCacheImageInputStream;

import javax.swing.JScrollBar;

import mapa.Ponto;
import mapa.Rota;
import mapa.Segmento;
import java.util.jar.Attributes;

public class pnlImagemMapa extends JPanel {
    private AmbienteFSM AmbFSM;
    /* Objeto que será o buffer do trecho atual de imagem de mapa */
    private BufferedImage imgMapa;
    /* Objeto que será o buffer do trecho atual de imagem de rotas */
    private BufferedImage imgRotas;
    private NivelZoom NivelAtual;
    /* Deslocamento X e Y em relação à origem do mapa */
    int desX=0;
    int desY=0;
    private pnlImagemMapaListener opnlImagemMapaListener = new pnlImagemMapaListener();
    private ControlePersistencia oCtrlPersist;
    private int iLarguraImg;
    private int iAlturaImg;
    private int iLarguraExibida;
    private int iAlturaExibida;
    JScrollBar scrlVertical;
    JScrollBar scrlHorizontal;
    private Iterator readers ;
    private ImageReader reader;
    private ImageInputStream iis;
    private ImageReadParam param;
    
    public pnlImagemMapa(Ambiente nAmb) {
        this.AmbFSM = (AmbienteFSM) nAmb;
        imgMapa = null;
        imgRotas = null;
        oCtrlPersist = AmbFSM.getCtrlPersist();
        //imgRotas = new BufferedImage(this.getWidth(), this.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.addMouseListener(AmbFSM);
        this.addMouseMotionListener(AmbFSM);
    }
    
    public void fechar(){
        scrlVertical.removeAdjustmentListener(opnlImagemMapaListener);
        scrlHorizontal.removeAdjustmentListener(opnlImagemMapaListener);
        opnlImagemMapaListener = null;
    }
    
    public void inicializaScrolls(){
        scrlVertical.addAdjustmentListener(opnlImagemMapaListener);
        scrlVertical.setUnitIncrement(50);
        scrlVertical.setBlockIncrement(50);
        scrlVertical.setMinimum(0);
        scrlHorizontal.addAdjustmentListener(opnlImagemMapaListener);
        scrlHorizontal.setUnitIncrement(50);
        scrlHorizontal.setBlockIncrement(50);
        scrlHorizontal.setMinimum(0);
        
        iLarguraExibida = (iLarguraImg > this.getWidth()) ? this.getWidth() : iLarguraImg;
        iAlturaExibida = (iAlturaImg > this.getHeight()) ? this.getHeight() : iAlturaImg;
             
        scrlVertical.setMaximum(iAlturaImg);// +
        scrlHorizontal.setMaximum(iLarguraImg);// + tamanho dos objetos alem do painel
        scrlVertical.setVisibleAmount(iAlturaExibida);
        scrlHorizontal.setVisibleAmount(iLarguraExibida);
        
    }

    private void jbInit() throws Exception {
        this.setLayout( null );
  //      this.addComponentListener(opnlImagemMapaListener);
  //talvez nao precise
    }

    public void paint(Graphics g){
        super.paint(g);

        if(imgMapa!=null)
            g.drawImage(imgMapa, 0, 0, this.getWidth(), this.getHeight(), null);
        if(imgRotas!=null)
            g.drawImage(imgRotas, 0, 0, this.getWidth(), this.getHeight(), null);
        
        
        System.out.println("paint do painel de imagem");
    }
    


    public void redimensionar(Dimension d){
        this.setSize(d);
        
     //   atualizarImagens();

        iLarguraExibida = (iLarguraImg > this.getWidth()) ? this.getWidth() : iLarguraImg;
        iAlturaExibida = (iAlturaImg > this.getHeight()) ? this.getHeight() : iAlturaImg;
             
        scrlVertical.setMaximum(iAlturaImg);// +
        scrlHorizontal.setMaximum(iLarguraImg);// + tamanho dos objetos alem do painel
        scrlVertical.setVisibleAmount(iAlturaExibida);
        scrlHorizontal.setVisibleAmount(iLarguraExibida);
        
        this.repaint();
    }

    private void abrirArquivoImagem(){
        String imgFileName = NivelAtual.getImagem();
        File imgFile = new File(imgFileName);
        //alterar para ser dinamico o tipo de arquivo de entrada
        readers = ImageIO.getImageReadersByFormatName("jpg");
        reader = (ImageReader)readers.next();
        param = reader.getDefaultReadParam();

        try {
            iis = ImageIO.createImageInputStream(imgFile);
         //   iis = new FileCacheImageInputStream(new FileInputStream(imgFile),null);
            reader.setInput(iis, true);
            iAlturaImg = reader.getHeight(0);
            iLarguraImg = reader.getWidth(0);
        } catch (IOException e) {
            // TODO
        }

        
    }

    public void atualizarImagens(){
		/* quebrar este metodo em 2. Um para atualizar informacoes de arquivo, qdo se
		muda de nivel, ou muda-se o arquivo de imagem do nivel, e um para reler o arquvio
		conforme a tela eh redimensionada ou qdo os scrools sao mudados, para se navegar pelo mapa */
		 System.out.println("Atualizando imagens inicio");
       
        if ((this.getWidth()==0) ||
		     (this.getHeight()==0)){
                         imgMapa = null;
                         imgRotas = null;
                         return;
        }
 
        try {

            Rectangle rect = new Rectangle(desX, desY, iLarguraExibida, iAlturaExibida);
            System.out.println("desX: " + desX);
            System.out.println("desY: " + desY);            
            param.setSourceRegion(rect);
            imgMapa = reader.read(0, param);

        } catch (IOException e) {
            // TODO
        }
        imgRotas = new BufferedImage(this.getWidth(), this.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        desenhaRotas();
        
        repaint();

        System.out.println("Atualizando imagens fim");
    }

    /* Atualiza qual o nivel de zoom atual, que determina que imagem será utilizada
     * como imagem de mapa de fundo e qual escala será utilizada para desenhar as rotas */
    public void setNivelAtual(NivelZoom nivelAtual) {
        this.NivelAtual = nivelAtual;
        abrirArquivoImagem();
        atualizarImagens();
    }
    
    private void desenhaRotas(){
        
        Rota rota;
        Segmento segmento;
        ListIterator itrRotas, itrSegs, itrPontos;
        List pontos;
        Ponto ponto;
        Ponto pNome;
        Double escala;
        TextLayout txtLayout;
        Font fonte;
        
        
        Graphics2D g2d = (Graphics2D) imgRotas.getGraphics();
        escala = NivelAtual.getEscala();
        g2d.translate(-desX - AmbFSM.getMapa().getPontoInicial().getLongitude()
        , - desY + AmbFSM.getMapa().getPontoInicial().getLatitude());
        g2d.scale(1 /escala , 1 / escala);
        
        /* Seleciona as rotas que estão contidas dentro da área
         * exibida, para isto usando o BoudingBox para optmizar 
         * o processamento */         
        String st = "from Rota rota where mapa = " + AmbFSM.getMapa().getId();
        //adicionar filtro ao select para que selecione
        //somente rotas necessarias para o desenho
        
        /* Configura o TextLayout usado no desenho do nome da rota */

        Map atribFonte = new HashMap();
        atribFonte.put(TextAttribute.SIZE, (float)(12*escala));
        atribFonte.put(TextAttribute.FOREGROUND, Color.GREEN);
        //atribFonte.put(TextAttribute.
        atribFonte.put(TextAttribute.BACKGROUND, Color.LIGHT_GRAY);
        fonte = new Font(atribFonte);
         fonte.getAttributes();
         g2d.setFont(fonte);
  
        List rotas = oCtrlPersist.executaQuery(st);
        
        
        itrRotas = rotas.listIterator();
        while(itrRotas.hasNext()){
            rota = (Rota) itrRotas.next();
            System.out.println("Desenhando Rota: " + rota.getId());
            //fazer aqui um if para nao desenhar a rota que
            // se esta trabalhando, pois como esta está sendo
            //modificada deve ser desenhada a parte
            
            pontos = rota.getPontos();
            itrSegs = rota.getSegmentos().listIterator();
            while(itrSegs.hasNext()){
                System.out.println("Desenhando Segmentos: " + rota.getId());
                segmento = (Segmento)itrSegs.next();
                g2d.setStroke(new BasicStroke((float)segmento.getLargura()));
                g2d.setColor(Color.YELLOW);
                g2d.draw(new Line2D.Double(
                ((Ponto) pontos.get(segmento.getIndicePontoInicial())).getLongitude(),
                -((Ponto) pontos.get(segmento.getIndicePontoInicial())).getLatitude(),
                ((Ponto) pontos.get(segmento.getIndicePontoFinal())).getLongitude(),  
                -((Ponto) pontos.get(segmento.getIndicePontoFinal())).getLatitude()         
                ));
            }
            
            g2d.setColor(Color.RED);
            itrPontos = rota.getPontos().listIterator();
            while(itrPontos.hasNext()){
                ponto = (Ponto)itrPontos.next();
               g2d.draw(new Arc2D.Double(ponto.getLongitude(), -ponto.getLatitude(),(5*escala),(5*escala),0.0, 360.0, Arc2D.PIE));
            }
                        
            
            
            pNome = (Ponto)rota.getPontos().get(1);
          //  g2d.drawString(rota.getNome(), (int)pNome.getLongitude(), -(int)pNome.getLatitude());
           // g2d.getFontRenderContext().
            txtLayout = new TextLayout(rota.getNome(), fonte, g2d.getFontRenderContext());
              g2d.setColor(Color.BLUE);
           txtLayout.draw(g2d, (float)pNome.getLongitude(), (float)-pNome.getLatitude());
            
        }
       
       // oCtrlPersist.desconectaBanco();
        
    }

    private class pnlImagemMapaListener implements MouseListener, MouseMotionListener, AdjustmentListener {
       
        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void adjustmentValueChanged(AdjustmentEvent e) {
            if (e.getSource()==scrlVertical){
                if(!scrlVertical.getValueIsAdjusting()){
                    desY = scrlVertical.getValue();
                    atualizarImagens();
                }
                
            }
            if (e.getSource()==scrlHorizontal){
                if(!scrlHorizontal.getValueIsAdjusting()){
                    desX = scrlHorizontal.getValue();
                    atualizarImagens();
               }
                
                
            }
            
            
        }
    }


}
