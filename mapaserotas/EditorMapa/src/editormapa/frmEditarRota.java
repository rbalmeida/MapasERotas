package editormapa;

/* Este frame é utilizado para edição de rotas.
 * Ele permite criar rotas novas, e editar rotas existentes, selecionando-a
 * através da lista.
 * Apenas uma rota será editada e carregada na memória por vez, pois pode
 * haver mapas com milhares de rota o que poderia causar problema de memória se
 * todas fossem carregadas ao mesmo tempo.
 * Este frame deve trabalhar em sincronia com frame de imagem de mapa, se ele
 * estiver ativo.
 * As alterações feitas devem ser refletidas em ambos, não importando onde
 * a informação foi editada */

import editormapa.fsm.AmbienteFSM;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import mapa.NivelZoom;
import mapa.Ponto;
import mapa.Rota;
import mapa.Segmento;

public class frmEditarRota extends JInternalFrame {
    private AmbienteFSM AmbFSM;
    private JLabel lblNome = new JLabel();
    private JTextField txtNome = new JTextField();
    private JSeparator jSeparator1 = new JSeparator();
    private JLabel lblPontos = new JLabel();
    private JLabel lblSegmentos = new JLabel();
    private JSeparator jSeparator2 = new JSeparator();
    private TabelaPontosModel oTabelaPontosModel = new TabelaPontosModel();
    private JTable tblPontos = new JTable(oTabelaPontosModel);
    private JScrollPane scrlTblPontos = new JScrollPane(tblPontos);    
    private TabelaSegModel oTabelaSegModel = new TabelaSegModel();
    JTable tblSegm = new JTable(oTabelaSegModel);
    private JScrollPane scrlTblSegm = new JScrollPane(tblSegm);
    private JButton btnAdPonto = new JButton();
    private JButton btnExNivel = new JButton();
    private JButton btnPropMapaAdNivel = new JButton();
    private JButton btnExNivel1 = new JButton();
    private JButton btnAdSeg = new JButton();
    private JButton btnRemPonto = new JButton();
    private ImageIcon imageAdd = new ImageIcon(frmPropriedadesMapa.class.getResource("add16.gif"));
    private ImageIcon imageDel = new ImageIcon(frmPropriedadesMapa.class.getResource("Delete16.gif"));
    private ImageIcon imageOpen = new ImageIcon(frmPropriedadesMapa.class.getResource("Open16.gif"));
    private ImageIcon imageNew = new ImageIcon(frmPropriedadesMapa.class.getResource("New16.gif"));
    private ImageIcon imageSave = new ImageIcon(frmPropriedadesMapa.class.getResource("Save16.gif"));
    private ImageIcon imageSearch = new ImageIcon(frmPropriedadesMapa.class.getResource("Search16.gif"));
    private JButton btnRemSeg = new JButton();
    private JSeparator jSeparator3 = new JSeparator();
    private JButton btnNovaRota = new JButton();
    private JButton btnSalvarRota = new JButton();
    private JButton btnExRota = new JButton();
    private JButton btnEditRotaLocal = new JButton();
    private JButton btnEditRotaAbrir = new JButton();
    private Rota rotaTrab;
    private frmEditarRotaListener ofrmEditarRotaListener = new frmEditarRotaListener();
    private JButton btnCriarSeg = new JButton();
    private JButton btnCapturarRota = new JButton();
    private JButton btnEditRotaFechar = new JButton();
    private JLabel lblIdRota = new JLabel();
    private JLabel lblIdRotaVal = new JLabel();

    public frmEditarRota(Ambiente nAmb) {
        this.AmbFSM = (AmbienteFSM) nAmb;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(387, 522));
        this.setTitle( "Editar Rota" );
        lblNome.setText("Nome:");
        lblNome.setBounds(new Rectangle(5, 35, 75, 20));
        txtNome.setBounds(new Rectangle(40, 35, 335, 20));
        jSeparator1.setBounds(new Rectangle(5, 65, 370, 5));
        lblPontos.setText("Pontos");
        lblPontos.setBounds(new Rectangle(10, 75, 50, 25));
        scrlTblPontos.setBounds(new Rectangle(5, 100, 240, 120));
        scrlTblSegm.setBounds(new Rectangle(5, 265, 245, 140));
        btnAdPonto.setText("Adicionar");
        btnAdPonto.setBounds(new Rectangle(255, 100, 120, 25));
        btnAdPonto.setActionCommand("btnAdPonto");
        btnAdPonto.setIcon(imageAdd);
        btnExNivel.setBounds(new Rectangle(405, 135, 100, 25));
        btnExNivel.setActionCommand("btnExNivel");
        btnExNivel.setText("Remover");
        btnPropMapaAdNivel.setBounds(new Rectangle(405, 105, 100, 25));
        btnPropMapaAdNivel.setActionCommand("btnPropMapaAdNivel");
        btnPropMapaAdNivel.setText("Adicionar");
        btnExNivel1.setBounds(new Rectangle(405, 135, 100, 25));
        btnExNivel1.setActionCommand("btnExNivel");
        btnExNivel1.setText("Remover");
        btnAdSeg.setText("Adicionar");
        btnAdSeg.setBounds(new Rectangle(255, 265, 120, 25));
        btnAdSeg.setActionCommand("btnAdSeg");
        btnAdSeg.setIcon(imageAdd);
        btnRemPonto.setText("Remover");
        btnRemPonto.setBounds(new Rectangle(255, 130, 120, 25));
        btnRemPonto.setActionCommand("btnRemPonto");
        btnRemPonto.setIcon(imageDel);
        btnRemSeg.setText("Remover");
        btnRemSeg.setBounds(new Rectangle(255, 295, 120, 25));
        btnRemSeg.setActionCommand("btnRemSeg");
        btnRemSeg.setIcon(imageDel);
        jSeparator3.setBounds(new Rectangle(5, 420, 370, 2));
        btnNovaRota.setText("Nova");
        btnNovaRota.setBounds(new Rectangle(5, 435, 120, 25));
        btnNovaRota.setActionCommand("btnNovaRota");
        btnNovaRota.setIcon(imageNew);
        btnSalvarRota.setText("Salvar");
        btnSalvarRota.setBounds(new Rectangle(255, 435, 120, 25));
        btnSalvarRota.setActionCommand("btnSalvarRota");
        btnSalvarRota.setIcon(imageSave);
        btnExRota.setText("Excluir");
        btnExRota.setBounds(new Rectangle(130, 465, 120, 25));
        btnExRota.setActionCommand("btnExRota");
        btnExRota.setIcon(imageDel);
        btnEditRotaLocal.setText("Localidades");
        btnEditRotaLocal.setBounds(new Rectangle(255, 325, 120, 25));
        btnEditRotaLocal.setActionCommand("btnEditRotaLocal");
        btnEditRotaLocal.setIcon(imageSearch);
        btnEditRotaAbrir.setText("Abrir");
        btnEditRotaAbrir.setBounds(new Rectangle(130, 435, 120, 25));
        btnEditRotaAbrir.setActionCommand("btnEditRotaAbrir");
        btnEditRotaAbrir.setIcon(imageOpen);
        btnCriarSeg.setText("Criar Segmento");
        btnCriarSeg.setBounds(new Rectangle(255, 160, 120, 25));
        btnCriarSeg.setSize(new Dimension(120, 25));
        btnCriarSeg.setActionCommand("btnCriarSeg");
        btnCapturarRota.setText("Capturar");
        btnCapturarRota.setBounds(new Rectangle(255, 355, 120, 25));
        btnCapturarRota.setActionCommand("btnCapturarRota");
        btnCapturarRota.setSize(new Dimension(120, 25));
        btnEditRotaFechar.setText("Fechar");
        btnEditRotaFechar.setBounds(new Rectangle(255, 465, 120, 25));
        btnEditRotaFechar.setSize(new Dimension(120, 25));
        btnEditRotaFechar.setActionCommand("btnEditRotaFechar");
        lblSegmentos.setText("Segmentos");
        lblSegmentos.setBounds(new Rectangle(10, 240, 80, 25));
        jSeparator2.setBounds(new Rectangle(5, 235, 370, 2));
        this.getContentPane().add(lblIdRotaVal, null);
        this.getContentPane().add(lblIdRota, null);
        this.getContentPane().add(btnEditRotaFechar, null);
        this.getContentPane().add(btnCapturarRota, null);
        this.getContentPane().add(btnCriarSeg, null);
        this.getContentPane().add(btnEditRotaAbrir, null);
        this.getContentPane().add(btnEditRotaLocal, null);
        this.getContentPane().add(btnExRota, null);
        this.getContentPane().add(btnSalvarRota, null);
        this.getContentPane().add(btnNovaRota, null);
        this.getContentPane().add(jSeparator3, null);
        this.getContentPane().add(btnRemSeg, null);
        this.getContentPane().add(btnRemPonto, null);
        this.getContentPane().add(btnAdSeg, null);
        this.getContentPane().add(btnAdPonto, null);
        this.getContentPane().add(scrlTblSegm, null);
        this.getContentPane().add(jSeparator2, null);
        this.getContentPane().add(lblSegmentos, null);
        this.getContentPane().add(scrlTblPontos, null);
        this.getContentPane().add(lblPontos, null);
        this.getContentPane().add(jSeparator1, null);


        /* Atribui a classe manipuladora de enventos aos objetos */
        this.getContentPane().add(txtNome, null);
        this.getContentPane().add(lblNome, null);
        btnNovaRota.addActionListener(ofrmEditarRotaListener);
        btnSalvarRota.addActionListener(ofrmEditarRotaListener);
        btnAdPonto.addActionListener(ofrmEditarRotaListener);
        btnRemPonto.addActionListener(ofrmEditarRotaListener);
        btnAdSeg.addActionListener(ofrmEditarRotaListener);
        btnRemSeg.addActionListener(ofrmEditarRotaListener);
        btnEditRotaLocal.addActionListener(AmbFSM);
        btnEditRotaAbrir.addActionListener(AmbFSM);
        btnEditRotaFechar.addActionListener(AmbFSM);
        lblIdRota.setText("ID:");
        lblIdRota.setBounds(new Rectangle(5, 10, 75, 20));
        lblIdRotaVal.setBounds(new Rectangle(40, 10, 145, 20));
        btnCapturarRota.addActionListener(AmbFSM);
         
          /* Atribui classe manipuladora como listener de foco, para eventos de edição de campos */
        txtNome.addFocusListener(ofrmEditarRotaListener);
        
    }

    public void setRotaTrab(Rota rotaTrab) {
        this.rotaTrab = rotaTrab;
        if (rotaTrab==null) {
            setEditCampos(false);
            btnNovaRota.setEnabled(true);
            btnEditRotaAbrir.setEnabled(true);
        }
        else {
            setEditCampos(true);
            carregarDados();
        }
    }

    public Rota getRotaTrab() {
        return rotaTrab;
    }
    
    private void setEditCampos(boolean editable){
        txtNome.setEditable(editable);
        btnAdPonto.setEnabled(editable);
        btnRemPonto.setEnabled(editable);
        btnAdSeg.setEnabled(editable);
        btnRemSeg.setEnabled(editable);
        btnEditRotaLocal.setEnabled(editable);
        btnNovaRota.setEnabled(editable);
        btnEditRotaAbrir.setEnabled(editable);
        btnSalvarRota.setEnabled(editable);
        btnExRota.setEnabled(editable);
    }
    
    private void carregarDados(){
        if(rotaTrab.getId()!=null)
            lblIdRotaVal.setText(""+rotaTrab.getId());
        else
            lblIdRotaVal.setText("Novo");
        txtNome.setText(rotaTrab.getNome());
        carregarPontos();
        carregarSegmentos();
        
    }
    
    private void carregarPontos(){
        List pontos = rotaTrab.getPontos();
        Ponto ponto = null;
        
        if(pontos != null){            
            oTabelaPontosModel.data = new Object[pontos.size()][3];
            for(int i=0; i < pontos.size(); i++){
                ponto = (Ponto) pontos.get(i);
                oTabelaPontosModel.setValueAt(i+1,i,0);             
                oTabelaPontosModel.setValueAt(new String("" + ponto.getLatitude()),i,1); 
                oTabelaPontosModel.setValueAt(new String("" + ponto.getLongitude()),i,2);
            };           
            
            /* Método chamado para que a tabela seja redesenhada */
            oTabelaPontosModel.fireTableDataChanged();
        }
    }
    
    private void carregarSegmentos(){
        List segmentos = rotaTrab.getSegmentos();
        Segmento segmento = null;
        
          if(segmentos != null){            
            oTabelaSegModel.data = new Object[segmentos.size()][4];
            for(int i=0; i < segmentos.size(); i++){
                segmento = (Segmento) segmentos.get(i);
                oTabelaSegModel.setValueAt(new String("" + (segmento.getIndicePontoInicial() + 1)),i,0);             
                oTabelaSegModel.setValueAt(new String("" + (segmento.getIndicePontoFinal() + 1)),i,1);             
                oTabelaSegModel.setValueAt(new String("" + segmento.getSentido()),i,2);
                oTabelaSegModel.setValueAt(new String("" + segmento.getLargura()),i,3);
            };           
            
            /* Método chamado para que a tabela seja redesenhada */
            oTabelaSegModel.fireTableDataChanged();
        }
    }

    public void recarregarPontos(){
        carregarPontos();
    }
    
    public void recarregarSegmentos(){
        carregarSegmentos();
    }
    
    /* Atualiza os pontos com as informações 
     * editadas na tela de Edicão de Rota */
    private void atualizarPontos(){    
        List pontos = rotaTrab.getPontos();
        Ponto ponto = null;
        
        if(pontos != null){            
            for(int i=0; i < pontos.size(); i++){
                ponto = (Ponto) pontos.get(i);                         
                ponto.setLatitude(Double.parseDouble((String) oTabelaPontosModel.getValueAt(i,1))); 
                ponto.setLongitude(Double.parseDouble((String) oTabelaPontosModel.getValueAt(i,2)));
            };           
            // Notificar Ambiente que pontos foram atualizados;
        }
    
    }
    
    private void atualizarSegmentos(){
        List segmentos = rotaTrab.getSegmentos();
        Segmento segmento = null;
        
          if(segmentos != null){            
            for(int i=0; i < segmentos.size(); i++){
                segmento = (Segmento) segmentos.get(i);
                segmento.setIndicePontoInicial(Integer.parseInt((String) oTabelaSegModel.getValueAt(i,0))-1);             
                segmento.setIndicePontoFinal(Integer.parseInt((String) oTabelaSegModel.getValueAt(i,1))-1);                  
                segmento.setSentido(Integer.parseInt((String) oTabelaSegModel.getValueAt(i,2)));             
                segmento.setLargura(Double.parseDouble((String) oTabelaSegModel.getValueAt(i,3)));             
            };           
            // Notificar Ambiente que segmentos foram atualizados;
        }
    }
    /* Classe que define a tabela de Pontos */
    class TabelaPontosModel extends AbstractTableModel {
        private String[] columnNames = {"Seq.", "Latitude", "Longitude"};
        private Object[][] data;
        
        TabelaPontosModel(){}

        public int getRowCount() {
            if (data==null) return 0;
            return data.length;
        }

        public int getColumnCount() {
            return columnNames.length;

        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        
        public String getColumnName(int columnIndex) {
                return columnNames[columnIndex].toString();
           }
           
    /*    public Class getColumnClass(int c) {
            //     return getValueAt(0, c).getClass();
            try {
                return Class.forName("javax.swing.JTextField");
            } catch (ClassNotFoundException e) {
                // TODO
            }
        }*/
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            /* Permite a edição de todas as colunas exceto 
             * a coluna correspondente ao sequencial do ponto */
            if (columnIndex > 0 ) {
                return true;
            } else {
                return false;
            }
                           
        }

        public void setValueAt(Object value, int row, int col) {
                    data[row][col] = value;
                    fireTableCellUpdated(row, col);
                }
        
        
    }
    
    /* Classe que define a tabela de Segmentos */
    class TabelaSegModel extends AbstractTableModel {
        private String[] columnNames = {"Inicio", "Fim", "Sentido", "Largura"};
        private Object[][] data;
        
        TabelaSegModel(){}

        public int getRowCount() {
            if (data==null) return 0;
            return data.length;
        }

        public int getColumnCount() {
            return columnNames.length;

        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        
        public String getColumnName(int columnIndex) {
                return columnNames[columnIndex].toString();
           }
           
    /*    public Class getColumnClass(int c) {
            //     return getValueAt(0, c).getClass();
            try {
                return Class.forName("javax.swing.JTextField");
            } catch (ClassNotFoundException e) {
                // TODO
            }
        }*/
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            /* Permite a edição de todas as colunas  */
             return true; 
        }

        public void setValueAt(Object value, int row, int col) {
                    data[row][col] = value;
                    fireTableCellUpdated(row, col);
                }
        
        
    }
    
    /* Classe controladora de enventos interna para tratar de eventos 
     * da página de editar rota */
    private class frmEditarRotaListener implements ActionListener, FocusListener, TableModelListener{
    
          public void actionPerformed(ActionEvent e) {
            String cmd;
            cmd = e.getActionCommand();
            
            
            
            
            /* Botão Nova Rota */
             if (cmd=="btnNovaRota"){
                 AmbFSM.novaRota();
            }
            
            /* Botão Salvar Rota */
            if (cmd=="btnSalvarRota"){
                atualizarPontos();
                atualizarSegmentos();
                AmbFSM.salvarRota();
            }
            
            /* Botão Adicionar Ponto */
            if (cmd=="btnAdPonto"){
                atualizarPontos();
                AmbFSM.adicionarPontoRota();
            }
            
            /* Botão Remover Ponto */
             if (cmd=="btnRemPonto"){
                atualizarPontos();
                int indice = tblPontos.getSelectedRow();
                if (indice !=-1 ) AmbFSM.removerPontoRota(indice);
             }            
            
            /* Botão Adicionar Segmento */
            if (cmd=="btnAdSeg"){
                atualizarSegmentos();
                AmbFSM.adicionarSegmentoRota();
            }
            
            /* Botão Remover Segmento */
            if (cmd=="btnRemSeg"){
                atualizarSegmentos();
                int indice = tblSegm.getSelectedRow();
                if (indice !=-1 ) AmbFSM.removerSegmentoRota(indice);            
            }
            
            
            /* Botão Capturar 
             * Criar segmentos capturando-os através de intereação
             * com o mapa. Deve estar habilitado somente
             * se a visualização do mapa estiver ativa */
            if (cmd=="btnCapturar"){
                            
            }
            
        }

        public void focusGained(FocusEvent e) {
        }

        /* Propriedades dos objetos serão atualizadas quando o objeto visual correspondente
         * perder o foco.
         * A saída do objeto visual de edição indicará que a edição do valor deste objeto foi 
         * concluída devendo então haver a atualização do objeto correspondente. */
        public void focusLost(FocusEvent e) {
            Object obj = e.getSource();
            
            /* Mesmo com os campos desabilitados, o que acontece quando 
             * nehuma rota esta sendo editada, eventos de foco são gerados.
             * Esta condição faz com que não haja atualizações de campo
             * quando não há rota sendo editada. */
            if (rotaTrab==null) return;
            
            if (obj == txtNome) rotaTrab.setNome(txtNome.getText()); 
                
        }
        
        /* Alterações feitas na tabela */ 
        public void tableChanged(TableModelEvent e) {
        //e.g
                
        }
    }


}
