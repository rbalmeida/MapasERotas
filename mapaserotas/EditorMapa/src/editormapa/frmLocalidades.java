package editormapa;

import editormapa.fsm.AmbienteFSM;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import mapa.Localidade;
import mapa.Ponto;
import mapa.Segmento;

public class frmLocalidades extends JInternalFrame {
    private AmbienteFSM AmbFSM ;
    private Segmento segTrab;
    private TabelaLocaModel oTabelaLocaModel = new TabelaLocaModel();
    JTable tblLoca = new JTable(oTabelaLocaModel);
    private JScrollPane scrlTblLoca = new JScrollPane(tblLoca);
    private JButton btnLocalAdi = new JButton();
    private JButton btnLocalRem = new JButton();
    private JButton btnLocalFechar = new JButton();

    public frmLocalidades(Ambiente nAmb) {
        AmbFSM = (AmbienteFSM)nAmb;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(374, 239));
        this.setTitle("Localidades");
        scrlTblLoca.setBounds(new Rectangle(5, 10, 235, 195));
        btnLocalAdi.setText("Adicionar");
        btnLocalAdi.setBounds(new Rectangle(245, 10, 120, 25));
        btnLocalAdi.setActionCommand("btnLocalAdi");
        btnLocalRem.setText("Remover");
        btnLocalRem.setBounds(new Rectangle(245, 40, 120, 25));
        btnLocalRem.setActionCommand("btnLocalRem");
        btnLocalFechar.setText("Fechar");
        btnLocalFechar.setBounds(new Rectangle(245, 70, 120, 25));
        btnLocalFechar.setSize(new Dimension(120, 25));
        btnLocalFechar.setActionCommand("btnLocalFechar");
        this.getContentPane().add(scrlTblLoca, null);
        this.getContentPane().add(btnLocalAdi, null);
        this.getContentPane().add(btnLocalRem, null);
        this.getContentPane().add(btnLocalFechar, null);
        
        btnLocalAdi.addActionListener(AmbFSM);
        btnLocalRem.addActionListener(AmbFSM);
        btnLocalFechar.addActionListener(AmbFSM);
        
        
    }

    public void setSegmento(Segmento segTrab) {
        this.segTrab = segTrab;
        carregarLocals();
    }

    public Segmento getSegmento() {
        return segTrab;
    }
    
    public void recarregar(){
        carregarLocals();
        
    }
    
    private void carregarLocals(){
        List locals = segTrab.getLocalidades();
        Localidade local = null;
         
        if(locals != null){            
            oTabelaLocaModel.data = new Object[locals.size()][3];
            for(int i=0; i < locals.size(); i++){
                local = (Localidade) locals.get(i);
                oTabelaLocaModel.setValueAt(new String("" + local.getNumero()),i,0);             
                oTabelaLocaModel.setValueAt(new String("" + local.getPosicao().getLatitude()),i,1); 
                oTabelaLocaModel.setValueAt(new String("" + local.getPosicao().getLongitude()),i,2);
            };           
            
            /* Método chamado para que a tabela seja redesenhada */
            oTabelaLocaModel.fireTableDataChanged();
        }
    }
    
    public void atualizarLocals(){
        List locals = segTrab.getLocalidades();
        Localidade local = null;
         
        if(locals != null){            
             for(int i=0; i < locals.size(); i++){
                local = (Localidade) locals.get(i);
                local.setNumero((String)oTabelaLocaModel.getValueAt(i,0));
                local.getPosicao().setLatitude(Double.parseDouble((String) oTabelaLocaModel.getValueAt(i,1))); 
                local.getPosicao().setLongitude(Double.parseDouble((String) oTabelaLocaModel.getValueAt(i,2))); 
            };           
 
        }
    
        
    }

    class TabelaLocaModel extends AbstractTableModel {
        private String[] columnNames = {"Número", "Latitude", "Longitude"};
        private Object[][] data;
        
        TabelaLocaModel(){}

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
}
