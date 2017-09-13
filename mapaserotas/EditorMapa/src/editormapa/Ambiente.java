package editormapa;

import editormapa.fsm.AmbienteFSM;

import grafo.Arco;
import grafo.No;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import mapa.BoudingBox;
import mapa.Mapa;
import mapa.NivelZoom;
import mapa.Ponto;
import mapa.Rota;
import mapa.Segmento;
import mapa.Conexao;
import mapa.Ligacao;
import mapa.Localidade;

public class Ambiente {

    /* Objetos correspondentes as classes de controle
    */
    ControlePersistencia oCtrlPersist;
    ManipuladorEventos oManipEvent;
    
    /* remover, somente testes */
    boolean novoObj;
    boolean novaRota = false, novaCon = false;
    
    
    /* Objetos correspondentes as classes Boundary, que representam as janelas
     * do aplicativo.
    */
    private frmPrincipal ofrmPrincipal;    
    private frmPesquisarMapa ofrmPesquisarMapa;
    private frmPropriedadesMapa ofrmPropriedadesMapa;
    private frmEditarRota ofrmEditarRota;
    private pnlImagemMapa opnlImagemMapa;
    private frmPesquisarRota ofrmPesquisarRota;
    private frmEditarConexao ofrmEditarConexao;
    private frmLocalidades ofrmLocalidades;
    private frmPesquisarCon ofrmPesquisarCon;
    
    /* Objetos correspondentes as classes persistentes */
    private Mapa oMapa;
    private NivelZoom nivelZoomAtual;
    
    /* Variavel que referencia com qual rota se esta trabalhando */
    private Rota rotaTrab;
    private Conexao conTrab;
    private Segmento segTrab;

    public Ambiente() {
        
    }
    
    public void setMapa(Mapa oMapa) {
        this.oMapa = oMapa;
    }
    
    public void setMapa(int id) {
        int i;
        String st = "from Mapa mapa where mapa.id = " + id;
       // oCtrlPersist.conectaBanco();
        oMapa = (Mapa)oCtrlPersist.executaQuery(st).get(0);
        NivelZoom nivel;
        List niveis = oMapa.getNiveisZoom(); //forca carga de niveis de zoom;
        for (i=0; i < niveis.size(); i++){
            nivel = (NivelZoom)niveis.get(i);
        }
        
   //     oCtrlPersist.desconectaBanco();
    }

    public Mapa getMapa() {
        return oMapa;
    }
    
    public ManipuladorEventos getManipEvent() {
        return oManipEvent;
    }
    
    
    /* ****  Ações que o ambiente pode realizar **** */
    
    /* Carrega o ambiente */
    public void carregar(){
    
        oCtrlPersist = new ControlePersistencia(this, "dbContext");
        oCtrlPersist.conectaBanco();
       // oManipEvent = new ManipuladorEventos(this);
        ofrmPrincipal = new frmPrincipal(this);
        
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = ofrmPrincipal.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        ofrmPrincipal.setLocation( ( screenSize.width - frameSize.width ) / 2, ( screenSize.height - frameSize.height ) / 2 );
        ofrmPrincipal.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        ofrmPrincipal.setVisible(true);
        
    }
    
    /* Inicia a criação de um novo mapa */
    public void adicionarMapa(){
        this.oMapa = new Mapa();
        oMapa.setPontoInicial(new Ponto(0.0, 0.0));
        ofrmPropriedadesMapa  = new frmPropriedadesMapa(this);
        ofrmPrincipal.getDesktop().add(ofrmPropriedadesMapa);
        ofrmPropriedadesMapa.setVisible(true);
        ofrmPrincipal.menuSalvar.setEnabled(true);
    }
    
    /* Inicia a pesquisa para a edição de um mapa existente */
    public void abrirMapa(){
        ofrmPesquisarMapa  = new frmPesquisarMapa(this);
        ofrmPrincipal.getDesktop().add(ofrmPesquisarMapa);
        ofrmPesquisarMapa.setVisible(true);
    }
    
    public void propMapaOk(){
        ofrmPropriedadesMapa.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPropriedadesMapa);
        ofrmPesquisarMapa=null;
        ofrmPrincipal.menuEditarRotas.setEnabled(true);
        ofrmPrincipal.menuEditCon.setEnabled(true);
        ofrmPrincipal.menuFechar.setEnabled(true);
        ofrmPrincipal.menuPropMapa.setEnabled(true);
        ofrmPrincipal.menuGerarImg.setEnabled(true);
    }
    
    public void editarPropriedadesMapa(){
        ofrmPropriedadesMapa  = new frmPropriedadesMapa(this);
        ofrmPrincipal.getDesktop().add(ofrmPropriedadesMapa);
        ofrmPropriedadesMapa.setVisible(true);
        ofrmPrincipal.menuSalvar.setEnabled(true);
    }

    /* Adicionar um novo Nivel de Zoom ao mapa */
    public void adicionarNivelZoom(){
    
        ofrmPropriedadesMapa.atualizarNiveisZoom();
        List niveis = oMapa.getNiveisZoom();
        if(niveis==null){
            niveis = new ArrayList();
            oMapa.setNiveisZoom(niveis);
        }
        
        NivelZoom nivel = new NivelZoom();
        niveis.add(nivel);
        nivel.setNivel(niveis.size());
          
        /* Notifica a janela de Propriedades do Mapa para que esta seja atualizada */
        ofrmPropriedadesMapa.recarregarNiveisZoom();              
    }
    
    /* Remove um Nivel de Zoom do mapa */
    public void removerNivelZoom(int indice){
    
    
        ofrmPropriedadesMapa.atualizarNiveisZoom();
        System.out.println("Indice excluido " + indice);
        List niveis = oMapa.getNiveisZoom();
        if(niveis==null) return;
        
         niveis.remove(indice);
         
        NivelZoom nivel;
         /* Atualiza indices */
         for(int i = indice; i < niveis.size(); i++){
            nivel = (NivelZoom)niveis.get(i);
            nivel.setNivel(i+1);             
         }
              
        /* Notifica a janela de Propriedades do Mapa para que esta seja atualizada */
        ofrmPropriedadesMapa.recarregarNiveisZoom();
        
    }
    
    
    /* Salva o Mapa fazendo a persistencia no banco de dados */
    public void salvarMapa(boolean novoMapa){
     //   oCtrlPersist.conectaBanco();
     
        ofrmPropriedadesMapa.atualizar();
        oCtrlPersist.iniciaTransacao();
        
        oCtrlPersist.persiste(oMapa, novoMapa);
        
        oCtrlPersist.comitaTransacao();
  //      oCtrlPersist.desconectaBanco();
    }
    
    public boolean validarPropMapa(){
        
        
        return true;
    }
    
    public void carregarPainelImagem(){

        if(opnlImagemMapa==null){
            opnlImagemMapa = new pnlImagemMapa(this);
            nivelZoomAtual = (NivelZoom)oMapa.getNiveisZoom().get(0);
            opnlImagemMapa.setNivelAtual(nivelZoomAtual);
            opnlImagemMapa.scrlHorizontal = ofrmPrincipal.scrlHorizontal;
            opnlImagemMapa.scrlVertical = ofrmPrincipal.scrlVertical;
            opnlImagemMapa.inicializaScrolls();
            opnlImagemMapa.redimensionar(ofrmPrincipal.getSize());
            ofrmPrincipal.getDesktop().add(opnlImagemMapa);
        }
        else opnlImagemMapa.redimensionar(ofrmPrincipal.getSize());
        ofrmPrincipal.btnAtualizarImg.setVisible(true);
    }
    
    public void fecharMapa(){
        opnlImagemMapa.fechar();
        opnlImagemMapa.setVisible(false);     
        ofrmPrincipal.getDesktop().remove(opnlImagemMapa);
        opnlImagemMapa = null;
        nivelZoomAtual = null;
        oMapa = null;
        ofrmPrincipal.menuEditarRotas.setEnabled(false);
        ofrmPrincipal.menuEditCon.setEnabled(false);
        ofrmPrincipal.menuSalvar.setEnabled(false);
        ofrmPrincipal.menuFechar.setEnabled(false);    
        ofrmPrincipal.menuPropMapa.setEnabled(false); 
        ofrmPrincipal.btnAtualizarImg.setVisible(false);
        ofrmPrincipal.menuGerarImg.setEnabled(false);
    }
    
    public void cancelarPesqMapa(){
        ofrmPesquisarMapa.setVisible(false);
        ofrmPesquisarMapa=null;
    }
    
    public void pesquisarMapaOk (){
        ofrmPesquisarMapa.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPesquisarMapa);
        ofrmPesquisarMapa=null;
        ofrmPrincipal.menuEditarRotas.setEnabled(true);
        ofrmPrincipal.menuEditCon.setEnabled(true);
        ofrmPrincipal.menuSalvar.setEnabled(true);
        ofrmPrincipal.menuFechar.setEnabled(true);
        ofrmPrincipal.menuPropMapa.setEnabled(true); 
        ofrmPrincipal.menuGerarImg.setEnabled(true);
    }
    
    
    public void cancelarAdicMapa(){
        ofrmPropriedadesMapa.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPropriedadesMapa);
        ofrmPropriedadesMapa=null;
        ofrmPrincipal.menuSalvar.setEnabled(false);
    }
    
    public void pesquisarMapa(){
        ofrmPesquisarMapa.pesquisar();
    }
    
    public void editarRotas(){
            ofrmEditarRota = new frmEditarRota(this);
            ofrmPrincipal.getDesktop().add(ofrmEditarRota);
            ofrmEditarRota.setRotaTrab(null);
            ofrmEditarRota.setVisible(true);
            ofrmPrincipal.menuEditCon.setEnabled(false);
            ofrmPrincipal.menuPropMapa.setEnabled(false);
    }
    
    /* Cria uma nova rota */
    public void novaRota(){
        rotaTrab = new Rota();
        rotaTrab.setMapa(oMapa);
        ofrmEditarRota.setRotaTrab(rotaTrab);
        novaRota = true;
    }
    
    /* Salva a Rota com a qual se esta trabalhando */
    public void salvarRota() {
    
        List pontos;
        Ponto ponto;
        List segmentos;
        Segmento segmento;
        int i;
        double custo;
        double minLat;
        double minLon;
        double maxLat;
        double maxLon;
        
        minLat = 0;
        minLon = 0;
        maxLat = 0;
        maxLon = 0;

        /* Para cada ponto da rota, cria os Nos correspondentes
          * no Grafo
          * So criar para pontos nao salvos, pois se o ponto ja foi salvo
          * entao os nos ja foram criados */
          
         oCtrlPersist.iniciaTransacao();
          
          pontos = rotaTrab.getPontos();
          
                    
          for (i = 0; i < pontos.size(); i++){
          
              ponto = (Ponto) pontos.get(i);
          
              /* Determina os extremos da rota para poder
               * gerar sua BoudingBox correspondente */
              if (i==0){
                  minLat = ponto.getLatitude();
                  minLon = ponto.getLongitude();
                  maxLat = minLat;
                  maxLon = minLon;                
              }
              else {
                  if(ponto.getLatitude() < minLat) minLat = ponto.getLatitude();
                  if(ponto.getLongitude() < minLon) minLon = ponto.getLongitude();
                  if(ponto.getLatitude() > maxLat) maxLat = ponto.getLatitude();
                  if(ponto.getLongitude() > maxLon) maxLon = ponto.getLongitude();
              }
                          
              
              /* Se nao foram criados os nós, cria-os */
              if (ponto.getNoSentidoDireto()==null){
                  ponto.setNoSentidoDireto(new No());
                  ponto.setNoSentidoInverso(new No());           
              }     
              
          }
        
        /* Cria a BoudingBox para a rota */
        BoudingBox mBox = rotaTrab.getBoudingBox();
        if(mBox==null) {
            mBox = new BoudingBox();
            rotaTrab.setBoudingBox(mBox);
        }
        mBox.setMinLat(minLat);
        mBox.setMinLon(minLon);
        mBox.setMaxLat(maxLat);
        mBox.setMaxLon(maxLon);
        
        /* Para cada segmento da rota, cria os Arcos correspondentes
         * no Grafo */
         Ponto pontoIni, pontoFim;
         No noIni, noFim;
         Arco arco;
         segmentos = rotaTrab.getSegmentos();
         for (i = 0; i < segmentos.size(); i++){
             segmento = (Segmento) segmentos.get(i);
             
             pontoIni = (Ponto)pontos.get(segmento.getIndicePontoInicial());
             pontoFim = (Ponto)pontos.get(segmento.getIndicePontoFinal());
             custo = FuncLib.calculaDistancia(pontoIni, pontoFim);

             /* Segmento com sentido direto ou ambos */
            if (segmento.getSentido() == 1 || segmento.getSentido() == 3){
                arco = segmento.getArcoSentidoDireto();
                if (arco == null){
                    arco = new Arco();
                    segmento.setArcoSentidoDireto(arco);
                };
                arco.setOrigem(pontoIni.getNoSentidoDireto());             
                arco.setDestino(pontoFim.getNoSentidoDireto());             
                arco.setCusto(custo);
            } else {
                if(segmento.getArcoSentidoDireto()!=null){
                    oCtrlPersist.exclui(segmento.getArcoSentidoDireto());
                    segmento.setArcoSentidoDireto(null);
                }
            }
            
            
             /* Segmento com sentido inverso ou ambos */
             if (segmento.getSentido() == 2 || segmento.getSentido() == 3){
                 arco = segmento.getArcoSentidoInverso();
                 if (arco == null){
                     arco = new Arco();
                     segmento.setArcoSentidoInverso(arco);
                 };
                 arco.setOrigem(pontoFim.getNoSentidoInverso());             
                 arco.setDestino(pontoIni.getNoSentidoInverso());             
                 arco.setCusto(custo);
             } else {
                if(segmento.getArcoSentidoInverso()!=null){      
                    oCtrlPersist.exclui(segmento.getArcoSentidoInverso());
                    segmento.setArcoSentidoInverso(null);             
             }
         }
        
         }//fim do For
        
        oCtrlPersist.persiste(rotaTrab, novaRota);
        novaRota = false;
        
        oCtrlPersist.comitaTransacao();

        ofrmEditarRota.setRotaTrab(rotaTrab);
    }

    /* Adiciona um novo ponto à rota com a qual se esta trabalhando */
    public void adicionarPontoRota(){
    
        List pontos = rotaTrab.getPontos();
        if(pontos==null) {
            pontos = new ArrayList();
            rotaTrab.setPontos(pontos);        
        }
        
        Ponto ponto = new Ponto();           
        pontos.add(ponto);
        
        /* Notifica as classes de fronteira ativas que houve alteração no modelo */
        if(ofrmEditarRota!=null)
            ofrmEditarRota.recarregarPontos();
        
    }
    
    /* Remove um ponto da rota com a qual se esta trabalhando */
     public void removerPontoRota(int indice){
     
         List pontos = rotaTrab.getPontos();
         if(pontos==null) return;
         
         pontos.remove(indice);
         
         /* Notifica as classes de fronteira ativas que houve alteração no modelo */
         if(ofrmEditarRota!=null)
             ofrmEditarRota.recarregarPontos();
         
     }
    
    /* Adiciona um novo segmento à rota com a qual se esta trabalhando */
    public void adicionarSegmentoRota(){
    
        List segmentos = rotaTrab.getSegmentos();
        if(segmentos==null) {
            segmentos = new ArrayList();
            rotaTrab.setSegmentos(segmentos);        
        }
        Segmento segmento = new Segmento();
        segmentos.add(segmento);
        
        /* Notifica as classes de fronteira ativas que houve alteração no modelo */
        if(ofrmEditarRota!=null)
            ofrmEditarRota.recarregarSegmentos();
        
    }
    
    /* Remove um segmento da rota com a qual se esta trabalhando */
    public void removerSegmentoRota(int indice){
    
        List segmentos = rotaTrab.getSegmentos();
        if(segmentos==null) return;      
        
        segmentos.remove(indice);
        
        /* Notifica as classes de fronteira ativas que houve alteração no modelo */
        if(ofrmEditarRota!=null)
            ofrmEditarRota.recarregarSegmentos();
        
    }
    
    public void redimensionar(){
         if( opnlImagemMapa!=null ) opnlImagemMapa.redimensionar(ofrmPrincipal.getSize());
    //     ofrmPrincipal.scrlHorizontal.setMaximum();
    }

    public ControlePersistencia getCtrlPersist() {
        return oCtrlPersist;
    }
    
    public void exibirPesquisaRota(){
      
        ofrmPesquisarRota = new frmPesquisarRota(this);
    ofrmPrincipal.getDesktop().add(ofrmPesquisarRota);
      ofrmPesquisarRota.setVisible(true);
      
    }
    
    public void cancelarPesquisaRota(){
        ofrmPesquisarRota.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPesquisarRota);
        ofrmPesquisarRota = null;       
    }
    
    public void fecharEditarRota(){
        ofrmEditarRota.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmEditarRota);
        ofrmEditarRota = null;
        ofrmPrincipal.menuEditCon.setEnabled(true);
        ofrmPrincipal.menuPropMapa.setEnabled(true);
    }
    
    public void pesquisarRota(){
        ofrmPesquisarRota.pesquisar();
    }
    
    public void abrindoRotaOk(){
        int idx, idRota;
        idx = ofrmPesquisarRota.tblResult.getSelectedRow();
        idRota = Integer.parseInt((String)ofrmPesquisarRota.oTabelaResultadoModel.getValueAt(idx,0));
        String st = "from Rota rota where rota.id = " + idRota;
        rotaTrab = (Rota)oCtrlPersist.executaQuery(st).get(0);
        ofrmEditarRota.setRotaTrab(rotaTrab);
        ofrmPesquisarRota.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPesquisarRota);
        ofrmPesquisarRota=null;
    }
    
    public void cancelarAbrirRota(){        
        ofrmPesquisarRota.setVisible(false);        
    }
    
    public void editarConexoes(){
        ofrmEditarConexao = new frmEditarConexao(this);
        ofrmPrincipal.getDesktop().add(ofrmEditarConexao);
        ofrmEditarConexao.setVisible(true);
        ofrmEditarConexao.setConexao(conTrab);
        ofrmPrincipal.menuEditarRotas.setEnabled(false);
        ofrmPrincipal.menuPropMapa.setEnabled(false);
    }
    
    public void criarNovaConexao(){        
        conTrab = new Conexao();
        conTrab.setMapa(oMapa);
        ofrmEditarConexao.setConexao(conTrab);
        novaCon = true;
    }
    
    public void salvarConexao(){        
        int i;
        Rota rotaA;
        Rota rotaB;
        Segmento segA;
        Segmento segB;
       
        No NoIni;
        No NoFim;
        Rota rOrig;
        Rota rDest;
        Ponto pInt;
        
        Ligacao ligacao;
        List ligacoes;
        
        ofrmEditarConexao.atualizarLigacoes();
        
        ligacoes = conTrab.getLigacoes();
      
        oCtrlPersist.iniciaTransacao();
        
        for(i=0; i < ligacoes.size(); i++){
            ligacao = (Ligacao)ligacoes.get(i);
            
            
            if (ligacao.isHabilitada()){
            
                /* Ligacoes com origem na rota A */
                if(ligacao.getOrigem()==1){
                    rotaA = conTrab.getRotaA();
                    rotaB = conTrab.getRotaB();
                    segA = conTrab.getSegmentoA();
                    segB = conTrab.getSegmentoB();
                    pInt = conTrab.getPontoRotaAInt();
                }
                else {
                    rotaA = conTrab.getRotaB();
                    rotaB = conTrab.getRotaA();     
                    segA = conTrab.getSegmentoB();
                    segB = conTrab.getSegmentoA();
                    pInt = conTrab.getPontoRotaBInt();
                }
                                   
                    /* Ligacoes partindo do sentido direto */
                if(ligacao.getSentidoOrigem()==1){
                
                    NoIni = ((Ponto)rotaA.getPontos().get(segA.getIndicePontoInicial())).getNoSentidoDireto();
                    
                    /* Sentido de destino é o sentido direto da rota B */
                    if(ligacao.getSentidoDestino()==1)
                        NoFim = ((Ponto)rotaB.getPontos().get(segB.getIndicePontoFinal())).getNoSentidoDireto();
                    else
                        NoFim = ((Ponto)rotaB.getPontos().get(segB.getIndicePontoInicial())).getNoSentidoInverso();
                    
                    ligacao.setArcoOrigem(new Arco(NoIni, pInt.getNoSentidoDireto(), 1.0));
                    ligacao.setArcoDestino(new Arco(pInt.getNoSentidoDireto(), NoFim, 1.0));
                    
                }                    
                else  {
                    
                    NoIni = ((Ponto)rotaA.getPontos().get(segA.getIndicePontoFinal())).getNoSentidoInverso();
                    
                    /* Sentido de destino é o sentido direto da rota B */
                    if(ligacao.getSentidoDestino()==1)
                        NoFim = ((Ponto)rotaB.getPontos().get(segB.getIndicePontoFinal())).getNoSentidoDireto();
                    else
                        NoFim = ((Ponto)rotaB.getPontos().get(segB.getIndicePontoInicial())).getNoSentidoInverso();
                    
                    ligacao.setArcoOrigem(new Arco(NoIni, pInt.getNoSentidoInverso(), 1.0));
                    ligacao.setArcoDestino(new Arco(pInt.getNoSentidoInverso(), NoFim, 1.0));
                    
                }
 
                
            } // Fim do if (ligacao.isHabilitada()){
                            
            else {
                if (ligacao.getArcoOrigem()!=null){                   
                    oCtrlPersist.exclui(ligacao.getArcoOrigem());
                    ligacao.setArcoOrigem(null);
                }
               
                if (ligacao.getArcoDestino()!=null){
                    oCtrlPersist.exclui(ligacao.getArcoDestino());                    
                    ligacao.setArcoDestino(null);  
                }
                              
            }
         
            
        }/* Fim do 'for'*/ 
            
        
        oCtrlPersist.persiste(conTrab, novaCon);
        novaCon = false;
        
        oCtrlPersist.comitaTransacao();
    }
    
    public void fecharEditConexao(){
        conTrab = null;
        ofrmEditarConexao.setVisible(false);
        ofrmEditarConexao = null;        
        ofrmPrincipal.menuEditarRotas.setEnabled(true);
        ofrmPrincipal.menuPropMapa.setEnabled(true);
    }
    
    public void rotaASelecionada(){
        int idx, idRota;
        idx = ofrmPesquisarRota.tblResult.getSelectedRow();
        idRota = Integer.parseInt((String)ofrmPesquisarRota.oTabelaResultadoModel.getValueAt(idx,0));
        String st = "from Rota rota where rota.id = " + idRota;
        conTrab.setRotaA((Rota)oCtrlPersist.executaQuery(st).get(0));
        ofrmEditarConexao.atualizar();
        ofrmPesquisarRota.setVisible(false);
    }
    
    public void rotaBSelecionada(){
        int idx, idRota;
        idx = ofrmPesquisarRota.tblResult.getSelectedRow();
        idRota = Integer.parseInt((String)ofrmPesquisarRota.oTabelaResultadoModel.getValueAt(idx,0));
        String st = "from Rota rota where rota.id = " + idRota;
        conTrab.setRotaB((Rota)oCtrlPersist.executaQuery(st).get(0));
        ofrmEditarConexao.atualizar();
        ofrmPesquisarRota.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPesquisarRota);
        ofrmPesquisarRota = null;
    }
     
    public void gerarConexao(){
        Rota rA;
        Rota rB;
        List segsA;
        List segsB;
        int i;
        int j;
        Ponto pAIni;
        Ponto pAFim;
        Ponto pBIni;
        Ponto pBFim;
        Ponto pInt=null;
        Ponto pTemp;
        Ligacao ligacao;
        List pontosA;
        List pontosB;
        List ligacoes;
        Segmento segA=null;
        Segmento segB=null;
        
        rA = conTrab.getRotaA();
        rB = conTrab.getRotaB();
        
        pontosA = rA.getPontos();
        pontosB = rB.getPontos();
        
        segsA = rA.getSegmentos();
        segsB = rB.getSegmentos();
        
        boolean achou = false;
        
        /* Determina o ponto e os segmentos de intersecao entre as rotas */         
         for (i = 0; i < segsA.size(); i++){
         
             segA = (Segmento) segsA.get(i);             
             pAIni = (Ponto)pontosA.get(segA.getIndicePontoInicial());
             pAFim = (Ponto)pontosA.get(segA.getIndicePontoFinal());
        
            /* Para cada segmento de A testa se há a inersecao com os
             * segmentos de B
            */ 
            for(j=0; j < segsB.size(); j++){
                segB = (Segmento) segsB.get(j);                
                pBIni = (Ponto)pontosB.get(segB.getIndicePontoInicial());
                pBFim = (Ponto)pontosB.get(segB.getIndicePontoFinal());
                
                System.out.println("paIni: (" + pAIni.getLatitude() + ", " + pAIni.getLongitude() + ")");
                System.out.println("pAFim: (" + pAFim.getLatitude() + ", " + pAFim.getLongitude() + ")");
                System.out.println("pBIni: (" + pBIni.getLatitude() + ", " + pBIni.getLongitude() + ")");
                System.out.println("pBFim: (" + pBFim.getLatitude() + ", " + pBFim.getLongitude() + ")");
                
                pInt = FuncLib.calculaIntersecao(pAIni, pAFim, pBIni, pBFim);
                
                if (pInt!=null){                 
                    achou = true;
                    break;   
                }
                
            }
            
            if(achou) break;
        
         }
        
        if(achou){
        
            conTrab.setSegmentoA(segA);
            conTrab.setSegmentoB(segB);
            
            pTemp = new Ponto(pInt.getLatitude(), pInt.getLongitude());
            pTemp.setNoSentidoDireto(new No());
            pTemp.setNoSentidoInverso(new No());
            conTrab.setPontoRotaAInt(pTemp);
            
            pTemp = new Ponto(pInt.getLatitude(), pInt.getLongitude());
            pTemp.setNoSentidoDireto(new No());
            pTemp.setNoSentidoInverso(new No());
            conTrab.setPontoRotaBInt(pTemp);
                                                
            ligacoes = new ArrayList<Ligacao>();
            conTrab.setLigacoes(ligacoes);
            
            if((segA.getSentido()==1) || (segA.getSentido()==3)){
                
                if((segB.getSentido()==1) || (segB.getSentido()==3)){
                  
                    ligacao = new Ligacao();
                    ligacao.setOrigem(1);
                    ligacao.setDestino(2);
                    ligacao.setSentidoOrigem(1);
                    ligacao.setSentidoDestino(1);
                    ligacoes.add(ligacao);
                    
                    ligacao = new Ligacao();
                    ligacao.setOrigem(2);
                    ligacao.setDestino(1);
                    ligacao.setSentidoOrigem(1);
                    ligacao.setSentidoDestino(1);
                    ligacoes.add(ligacao);
                                        
                }
                
                if((segB.getSentido()==2) || (segB.getSentido()==3)){
                                      
                    ligacao = new Ligacao();
                    ligacao.setOrigem(1);
                    ligacao.setDestino(2);
                    ligacao.setSentidoOrigem(1);
                    ligacao.setSentidoDestino(2);
                    ligacoes.add(ligacao);
                    
                    ligacao = new Ligacao();
                    ligacao.setOrigem(2);
                    ligacao.setDestino(1);
                    ligacao.setSentidoOrigem(2);
                    ligacao.setSentidoDestino(1);
                    ligacoes.add(ligacao);
                    
                    
                }
                
            }
            
            if((segA.getSentido()==2) || (segA.getSentido()==3)){
                
                if((segB.getSentido()==1) || (segB.getSentido()==3)){
                  
                    ligacao = new Ligacao();
                    ligacao.setOrigem(1);
                    ligacao.setDestino(2);
                    ligacao.setSentidoOrigem(2);
                    ligacao.setSentidoDestino(1);
                    ligacoes.add(ligacao);
                    
                    ligacao = new Ligacao();
                    ligacao.setOrigem(2);
                    ligacao.setDestino(1);
                    ligacao.setSentidoOrigem(1);
                    ligacao.setSentidoDestino(2);
                    ligacoes.add(ligacao);
                                        
                }
                
                if((segB.getSentido()==2) || (segB.getSentido()==3)){
                                      
                    ligacao = new Ligacao();
                    ligacao.setOrigem(1);
                    ligacao.setDestino(2);
                    ligacao.setSentidoOrigem(2);
                    ligacao.setSentidoDestino(2);
                    ligacoes.add(ligacao);
                    
                    ligacao = new Ligacao();
                    ligacao.setOrigem(2);
                    ligacao.setDestino(1);
                    ligacao.setSentidoOrigem(2);
                    ligacao.setSentidoDestino(2);
                    ligacoes.add(ligacao);
                                        
                }
            
            }
        }
        
        ofrmEditarConexao.atualizar();
    }
    
    public void editarLocalidades(){
        int idx;
        idx = ofrmEditarRota.tblSegm.getSelectedRow();
        segTrab = (Segmento)rotaTrab.getSegmentos().get(idx);
        if (segTrab.getLocalidades()==null){
            segTrab.setLocalidades(new ArrayList<Localidade>());            
        }
        ofrmLocalidades = new frmLocalidades(this);
        ofrmLocalidades.setSegmento(segTrab);
        ofrmPrincipal.getDesktop().add(ofrmLocalidades);
        ofrmLocalidades.setVisible(true);
    }
    
    public void adicionarLocalidade(){
        Localidade nLoca;
        
        ofrmLocalidades.atualizarLocals();
        
        nLoca = new Localidade();
        nLoca.setPosicao(new Ponto(0.0, 0.0));
        segTrab.getLocalidades().add(nLoca);
        ofrmLocalidades.recarregar();
        
    }
    
    public void removerLocalidade(){
        int idx;
        
        ofrmLocalidades.atualizarLocals();
        
        idx = ofrmLocalidades.tblLoca.getSelectedRow();
        segTrab.getLocalidades().remove(idx);
        ofrmLocalidades.recarregar();
        
    }
    
    public void fecharEditLocal(){
    
        ofrmLocalidades.atualizarLocals();
        
        ofrmLocalidades.setVisible(false);
        ofrmLocalidades = null;
        segTrab = null;
    }
    
    public void sair(){
        System.exit(0);
    }
    
    public void pontoCapturado(MouseEvent e){
        
        
        int desx = opnlImagemMapa.desX;
        int desy = opnlImagemMapa.desY;
        double Latitude = (oMapa.getPontoInicial().getLatitude() - e.getY() - desy) * nivelZoomAtual.getEscala();
        double Longitude = (oMapa.getPontoInicial().getLongitude() + e.getX() + desx) * nivelZoomAtual.getEscala();
        
        List pontos = rotaTrab.getPontos();
        if(pontos==null) {
            pontos = new ArrayList();
            rotaTrab.setPontos(pontos);       
            Ponto ponto = new Ponto(Latitude, Longitude );           
            pontos.add(ponto);
            ofrmEditarRota.recarregarPontos();
        }
        else{
            Ponto ponto = new Ponto(Latitude, Longitude );           
            pontos.add(ponto);
            ofrmEditarRota.recarregarPontos();
            
            if (pontos.size()>=2){
                List segmentos = rotaTrab.getSegmentos();
                if(segmentos==null) {
                    segmentos = new ArrayList();
                    rotaTrab.setSegmentos(segmentos);        
                }
                Segmento segmento = new Segmento();
                segmento.setIndicePontoInicial(pontos.size()-2);
                segmento.setIndicePontoFinal(pontos.size()-1);
                segmentos.add(segmento);        
                ofrmEditarRota.recarregarSegmentos();
            }
            
        }
                
    }
    
    public void abrirConexao(){
        ofrmPesquisarCon = new frmPesquisarCon(this);
        ofrmPrincipal.getDesktop().add(ofrmPesquisarCon);
        ofrmPesquisarCon.setVisible(true);
        novaCon = false;
    }
    
    public void pesquisarCon(){
        ofrmPesquisarCon.pesquisar();
    }
    
    public void cancelarAbrirCon(){            
        ofrmPesquisarCon.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPesquisarCon);
        ofrmPesquisarCon = null;
    }
    
    public void abrindoConOk(){
        int idx, idCon;
        idx = ofrmPesquisarCon.tblResult.getSelectedRow();
        idCon = Integer.parseInt((String)ofrmPesquisarCon.oTabelaResultadoModel.getValueAt(idx,0));
        String st = "from Conexao con where con.id = " + idCon;
        conTrab = (Conexao)oCtrlPersist.executaQuery(st).get(0);
        ofrmEditarConexao.setConexao(conTrab);
        ofrmPesquisarCon.setVisible(false);
        ofrmPrincipal.getDesktop().remove(ofrmPesquisarCon);
        ofrmPesquisarCon=null;
    }
    
    public void atualizarPainelImagem(){
        opnlImagemMapa.atualizarImagens();
    }

    public void gerarImagens(){
        String dirSaida;
        Iterator readers ;
        ImageReader reader;
        ImageInputStream iis;
        ImageReadParam param;
        File arqSaida;
        File imgFile;
        BufferedImage base=null, dest;
        String nomearq;
        String imgFileName;
        int x, y, totX, totY, iAlturaImg, iLarguraImg, len = 128, restX, restY;
        
        
        imgFileName = nivelZoomAtual.getImagem();
        imgFile = new File(imgFileName);
        dirSaida = JOptionPane.showInputDialog(null, "Informe o diretório de destino","Gerar Imagens",JOptionPane.QUESTION_MESSAGE);

        readers = ImageIO.getImageReadersByFormatName("jpg");
        reader = (ImageReader)readers.next();
        param = reader.getDefaultReadParam();

        try {
            iis = ImageIO.createImageInputStream(imgFile);     
            reader.setInput(iis, true);
            iLarguraImg = reader.getWidth(0);
            iAlturaImg = reader.getHeight(0);
            
            totX = iLarguraImg / len;
            totY = iAlturaImg / len;
            
            restX = iLarguraImg % len;
            restY = iAlturaImg % len;
            
            /* Usar pedaços maiores como buffer, pq soh de 128 fica mto lento ler 
             * ver se nao exixte alguma funcao ou algoritmo
             * que ja faca isto, picotar imagem
        
            */
            
           
            for(x=0; x < totX; x++){
                for (y=0; y < totY; y++){
                   
                    Rectangle rect = new Rectangle(x*len, y*len, len, len);        
                    param.setSourceRegion(rect);
                    dest = reader.read(0, param);                 
                    
                    nomearq = dirSaida + "x" + x + "y" + y + ".jpg";
                    arqSaida = new File(nomearq);
                    try {
                        ImageIO.write(dest, "jpg", arqSaida );
                    } catch (IOException f) {
                        // TODO
                    }
                }
                ofrmPrincipal.repaint();
            }
            
            } catch (IOException e) {
                         // TODO
            }
        
        
    }
    
}
