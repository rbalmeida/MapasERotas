package editormapa;

import editormapa.fsm.AmbienteFSM;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.awt.event.FocusEvent;

import java.util.List;
import java.awt.Rectangle;

import java.awt.event.ActionListener;

import java.awt.event.FocusListener;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import mapa.Mapa;
import mapa.NivelZoom;

public class frmPropriedadesMapa extends JInternalFrame{
    /* Objetos privados */
    private AmbienteFSM AmbFSM;
    private Mapa oMapa;
    private JLabel lblNome = new JLabel();
    private JLabel lblDescritivo = new JLabel();
    private JTextField txtNome = new JTextField();
    private JTextField txtDescr = new JTextField();
    private JLabel lblNiveisZoom = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private TabelaNiveisZoomModel oTabelaNiveisZoomModel = new TabelaNiveisZoomModel();
    private JTable tblNiveisZoom = new JTable(oTabelaNiveisZoomModel);
    private JScrollPane scrlTblNiveisZoom = new JScrollPane(tblNiveisZoom);
    private JButton btnPropMapaAdNivel = new JButton();
    private JButton btnRemNivel = new JButton();
    private JSeparator jSeparator2 = new JSeparator();
    private JButton btnPropMapaOk = new JButton();
    private JButton btnPropMapaCancelar = new JButton();
    private frmPropMapaListener ofrmPropMapaListener = new frmPropMapaListener();
    private ImageIcon imageAdd = new ImageIcon(frmPropriedadesMapa.class.getResource("add16.gif"));
    private ImageIcon imageDel = new ImageIcon(frmPropriedadesMapa.class.getResource("Delete16.gif"));
    private JLabel lblCoord = new JLabel();
    private JTextField txtLatIni = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtLongIni = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JLabel lblId = new JLabel();
    private JLabel lblIdVal = new JLabel();


    public frmPropriedadesMapa(Ambiente nAmb) {
        this.AmbFSM = (AmbienteFSM) nAmb;
        oMapa = AmbFSM.getMapa();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setTitle("Propriedades Mapa");
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(522, 367));
        lblNome.setText("Nome: ");
        lblNome.setBounds(new Rectangle(10, 35, 75, 20));
        lblDescritivo.setText("Descrição:");
        lblDescritivo.setBounds(new Rectangle(10, 60, 95, 15));
        txtNome.setBounds(new Rectangle(70, 35, 435, 20));
        txtDescr.setBounds(new Rectangle(70, 60, 435, 20));
        lblNiveisZoom.setText("Níveis de Zoom");
        lblNiveisZoom.setBounds(new Rectangle(10, 130, 120, 20));
        jSeparator1.setBounds(new Rectangle(5, 120, 500, 5));
        
        scrlTblNiveisZoom.setBounds(new Rectangle(10, 155, 390, 135));
        btnPropMapaAdNivel.setBounds(new Rectangle(405, 155, 100, 25));
        btnPropMapaAdNivel.setActionCommand("btnPropMapaAdNivel");
        btnPropMapaAdNivel.setText("Adicionar");
        btnPropMapaAdNivel.setIcon(imageAdd);
        btnRemNivel.setBounds(new Rectangle(405, 185, 100, 25));
        btnRemNivel.setActionCommand("btnRemNivel");
        btnRemNivel.setText("Remover");
        btnRemNivel.setIcon(imageDel);
        jSeparator2.setBounds(new Rectangle(10, 300, 500, 5));
        btnPropMapaOk.setText("Ok");
        btnPropMapaOk.setBounds(new Rectangle(330, 310, 85, 25));
        btnPropMapaOk.setActionCommand("btnPropMapaOk");
        btnPropMapaCancelar.setText("Cancelar");
        btnPropMapaCancelar.setBounds(new Rectangle(420, 310, 85, 25));
        btnPropMapaCancelar.setActionCommand("btnPropMapaCancelar");
        this.getContentPane().add(lblIdVal, null);
        this.getContentPane().add(lblId, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(txtLongIni, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(txtLatIni, null);
        this.getContentPane().add(lblCoord, null);
        this.getContentPane().add(btnPropMapaCancelar, null);
        this.getContentPane().add(btnPropMapaOk, null);
        this.getContentPane().add(jSeparator2, null);
        this.getContentPane().add(btnRemNivel, null);
        this.getContentPane().add(btnPropMapaAdNivel, null);
        this.getContentPane().add(scrlTblNiveisZoom, null);
        this.getContentPane().add(jSeparator1, null);
        this.getContentPane().add(lblNiveisZoom, null);
        this.getContentPane().add(txtDescr, null);
        this.getContentPane().add(txtNome, null);


        /* Atribui a classe manipuladora de enventos aos objetos */
        this.getContentPane().add(lblDescritivo, null);
        this.getContentPane().add(lblNome, null);
        btnPropMapaAdNivel.addActionListener(ofrmPropMapaListener);
        btnPropMapaOk.addActionListener(ofrmPropMapaListener);
        btnRemNivel.addActionListener(ofrmPropMapaListener);
        
        /* Atribui classe manipuladora de Foco, para eventos de edição de campos */
        txtNome.addFocusListener(ofrmPropMapaListener);
        txtDescr.addFocusListener(ofrmPropMapaListener);
        oTabelaNiveisZoomModel.addTableModelListener(ofrmPropMapaListener);
        //tblNiveisZoom.addFocusListener(ofrmPropMapaListener);
        
        btnPropMapaCancelar.addActionListener(AmbFSM);

        lblCoord.setText("Coordenada Inicial:");
        lblCoord.setBounds(new Rectangle(10, 85, 100, 20));
        txtLatIni.setBounds(new Rectangle(175, 85, 120, 20));
        txtLatIni.setSize(new Dimension(120, 20));
        jLabel1.setText("Longitude:");
        jLabel1.setBounds(new Rectangle(310, 85, 80, 20));
        txtLongIni.setBounds(new Rectangle(385, 85, 120, 20));
        txtLongIni.setSize(new Dimension(120, 20));
        jLabel2.setText("Latitude:");
        jLabel2.setBounds(new Rectangle(120, 85, 60, 20));
        lblId.setText("ID:");
        lblId.setBounds(new Rectangle(10, 10, 30, 20));
        lblIdVal.setBounds(new Rectangle(70, 10, 135, 20));
        carregarDados();
        
    //  tblNiveisZoom.setSelectionMode();
      tblNiveisZoom.setRowSelectionAllowed(false);
         
    }
    
    /* Método para carregar e atualizar a janela com todos os dados do mapa */
    private void carregarDados(){
        if(oMapa.getId()!=null)
            lblIdVal.setText("" + oMapa.getId());
        else
            lblIdVal.setText("Novo");
        txtNome.setText(oMapa.getNome());
        txtDescr.setText(oMapa.getDescricao());
        txtLatIni.setText("" + oMapa.getPontoInicial().getLatitude());
        txtLongIni.setText("" + oMapa.getPontoInicial().getLongitude());
        carregarNiveisZoom();            
   }
   
   /* Método para carregar e atualizar somente os níveis de zoom */
   private void carregarNiveisZoom(){
       List niveis = oMapa.getNiveisZoom();
       NivelZoom nivel = null;
       
       if(niveis != null){            
           oTabelaNiveisZoomModel.data = new Object[niveis.size()][4];
           for(int i=0; i < niveis.size(); i++){
               nivel = (NivelZoom) niveis.get(i);
               oTabelaNiveisZoomModel.setValueAt(nivel.getNivel(),i,0);             
               oTabelaNiveisZoomModel.setValueAt(new String("" + nivel.getEscala()),i,1); 
               oTabelaNiveisZoomModel.setValueAt(new String("" + nivel.getDescricao()),i,2);
               oTabelaNiveisZoomModel.setValueAt(new String("" + nivel.getImagem()),i,3);             
           };           
           
           /* Método chamado para que a tabela seja redesenhada */
           oTabelaNiveisZoomModel.fireTableDataChanged();
       }
   }
    
    /* Recarega todos os dados */
    public void recarregar(){
       carregarDados();
    }
    
    /* Recarrega somente os dados correspondentes aos niveis de zoom */
    public void recarregarNiveisZoom(){
       carregarNiveisZoom();
    }
    
    /* Atualiza os niveis de zoom do objeto mapa com as informações 
     * editadas na tela de propriedades */
    public void atualizarNiveisZoom(){
    
        List niveis = oMapa.getNiveisZoom();  
        if(niveis != null){            
                for(int i=0; i < niveis.size(); i++){
                NivelZoom nivel = (NivelZoom) niveis.get(i);                
                nivel.setEscala(Double.parseDouble((String) oTabelaNiveisZoomModel.getValueAt(i,1)));    
                nivel.setDescricao((String) oTabelaNiveisZoomModel.getValueAt(i,2));           
                nivel.setImagem((String) oTabelaNiveisZoomModel.getValueAt(i,3));             
            };           
            
        }
    
    }
    
    public void atualizar(){
        oMapa.setNome(txtNome.getText());
        oMapa.setDescricao(txtDescr.getText());
        oMapa.getPontoInicial().setLatitude(Double.parseDouble(txtLatIni.getText()));
        oMapa.getPontoInicial().setLongitude(Double.parseDouble(txtLongIni.getText()));
        atualizarNiveisZoom();
    }
    
    /* Classe que define a tabela de Niveis de Zoom */
    class TabelaNiveisZoomModel extends AbstractTableModel {
        private String[] columnNames = {"Nível", "Escala", "Descrição", "Arquivo Imagem"};
        private Object[][] data;
        
        TabelaNiveisZoomModel(){}

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
           
     /*  public Class getColumnClass(int c) {


            try {
                return Class.forName("javax.swing.JTextField");
            } catch (ClassNotFoundException e) {
                // TODO
            }
            
            return String.class;
        }*/

        public boolean isCellEditable(int rowIndex, int columnIndex) {
                   /* Permite a edição de todas as colunas exceto 
                    * a coluna correspondente ao sequencial do nivel */
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
    
    /* Classe controladora de enventos interna para tratar de eventos 
     * da página de propriedades do mapa */
    private class frmPropMapaListener implements ActionListener, FocusListener, TableModelListener{
    
          public void actionPerformed(ActionEvent e) {
            String cmd;
            cmd = e.getActionCommand();
            
            /* Atualiza as alterações feitas antes de executar as ações */
            
            
            /* Botao Adicionar Nivel */
             if (cmd=="btnPropMapaAdNivel"){     
                  AmbFSM.adicionarNivelZoom();
            }
            
            if (cmd=="btnPropMapaOk"){
                atualizar();
                /* Chamar o metodo actionPerformed da classe Manipuladora de Eventos 
                 * para que haja o processamento da maquina de estados do editor */
                AmbFSM.actionPerformed(e);
            }
            
           
             if (cmd=="btnRemNivel"){
                 int indice = tblNiveisZoom.getSelectedRow();
                 if (indice !=-1) AmbFSM.removerNivelZoom(indice);
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
            
            if (obj == txtNome) oMapa.setNome(txtNome.getText()); 
            if (obj == txtDescr) oMapa.setDescricao(txtDescr.getText());
        }
        
        /* Alterações feitas na tabela */ 
        public void tableChanged(TableModelEvent e) {
        //e.g
                     //   System.out.println("tabela mudou por " + e.getSource());
        }
    }

}


