package editormapa;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import editormapa.fsm.AmbienteFSM;

import java.awt.BorderLayout;

import java.awt.Dimension;

import java.awt.FlowLayout;

import java.awt.GridLayout;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

public class frmPesquisarMapa extends JInternalFrame {
    private AmbienteFSM AmbFSM;
    private JTextField txtNome = new JTextField();
    private JLabel lblNome = new JLabel();
    private JLabel lblDescricao = new JLabel();
    private JTextField txtDescr = new JTextField();
    private JButton btnPesqMapaPesquisar = new JButton();
    private JSeparator jSeparator1 = new JSeparator();
    private JButton btnPesqMapaCancelar = new JButton();
    private JButton btnPesqMapaOk = new JButton();
    private JSeparator jSeparator2 = new JSeparator();
    private TabelaResultadoModel oTabelaResultadoModel = new TabelaResultadoModel();
    private JTable tblResult = new JTable(oTabelaResultadoModel);
    private JLabel lblResultado = new JLabel();
    private JScrollPane scrlTblResult = new JScrollPane(tblResult);
    private ControlePersistencia oCtrlPersist;
    private frmPesquisarMapaLisneter ofrmPesquisarMapaLisneter = new frmPesquisarMapaLisneter();

    public frmPesquisarMapa(Ambiente nAmb) {
        this.AmbFSM = (AmbienteFSM) nAmb;
        oCtrlPersist = AmbFSM.getCtrlPersist();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(427, 388));
        this.getContentPane().setLayout(null);
        this.setTitle("Pesquisar Mapa");
        txtNome.setBounds(new Rectangle(80, 10, 245, 20));
        lblNome.setText("Nome: ");
        lblNome.setBounds(new Rectangle(10, 10, 75, 20));
        lblDescricao.setText("Descrição:");
        lblDescricao.setBounds(new Rectangle(10, 35, 95, 15));
        txtDescr.setBounds(new Rectangle(80, 35, 245, 20));
        btnPesqMapaPesquisar.setText("Pesquisar");
        btnPesqMapaPesquisar.setBounds(new Rectangle(330, 10, 85, 25));
        btnPesqMapaPesquisar.setActionCommand("btnPesqMapaPesquisar");
        jSeparator1.setBounds(new Rectangle(5, 65, 410, 5));
        btnPesqMapaCancelar.setText("Cancelar");
        btnPesqMapaCancelar.setBounds(new Rectangle(320, 325, 85, 25));
        btnPesqMapaCancelar.setActionCommand("btnPesqMapaCancelar");
        btnPesqMapaOk.setText("Ok");
        btnPesqMapaOk.setBounds(new Rectangle(230, 325, 85, 25));
        btnPesqMapaOk.setActionCommand("btnPesqMapaOk");
        jSeparator2.setBounds(new Rectangle(10, 315, 405, 5));
      //  tblResult.setBounds(new Rectangle(10, 95, 395, 210));
        scrlTblResult.setBounds(new Rectangle(10, 95, 395, 210));
        lblResultado.setText("Resultado");
        lblResultado.setBounds(new Rectangle(10, 75, 95, 15));
        
        this.getContentPane().add(lblResultado, null);
        this.getContentPane().add(scrlTblResult, null);
        this.getContentPane().add(jSeparator2, null);
        this.getContentPane().add(btnPesqMapaOk, null);
        this.getContentPane().add(btnPesqMapaCancelar, null);
        this.getContentPane().add(jSeparator1, null);
        this.getContentPane().add(btnPesqMapaPesquisar, null);
        this.getContentPane().add(txtDescr, null);
        this.getContentPane().add(lblDescricao, null);
        this.getContentPane().add(lblNome, null);
        this.getContentPane().add(txtNome, null);
        
        btnPesqMapaOk.addActionListener(ofrmPesquisarMapaLisneter);
        btnPesqMapaCancelar.addActionListener(AmbFSM);
        btnPesqMapaPesquisar.addActionListener(AmbFSM);
        
       // oTabelaResultadoModel.getColumnClass(0).setVisible(false);
        tblResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void pesquisar(){
        List rs;
        ListIterator itrMapas;
        String st = "select mapa.id, mapa.nome, mapa.descricao from Mapa mapa where mapa.nome like '" + 
                    txtNome.getText() + "'";
        Object [] linhaRs;
                    
       // oCtrlPersist.conectaBanco();
        rs = oCtrlPersist.executaQuery(st);
        itrMapas = rs.listIterator();
        
        if (rs.size()!=0)
            oTabelaResultadoModel.data = new Object[rs.size()][3];
        else
            oTabelaResultadoModel.data = null;
            
        int i=0; 
        while(itrMapas.hasNext()){
            linhaRs =(Object[]) itrMapas.next();
            oTabelaResultadoModel.setValueAt("" + linhaRs[0],i,0);
            oTabelaResultadoModel.setValueAt("" + linhaRs[1],i,1);
            oTabelaResultadoModel.setValueAt("" + linhaRs[2],i,2);
            i++;
        }
        
       // oCtrlPersist.desconectaBanco();
        
        oTabelaResultadoModel.fireTableDataChanged();
    }
    
    class TabelaResultadoModel extends AbstractTableModel {
        private String[] columnNames = {"Id", "Nome", "Descricao"};
        private Object[][] data;
        
        public int getRowCount() {
            if (data!=null)
                return data.length;
            else
                return 0;
        }

        public int getColumnCount() {
            return columnNames.length;

        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        
        public String getColumnName(int columnIndex) {
             //  return columnNames[col].toString();
              return columnNames[columnIndex].toString();
           }
           
        public Class getColumnClass(int c) {
                   return getValueAt(0, c).getClass();
               }
        
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
                    data[row][col] = value;
                    fireTableCellUpdated(row, col);
                }


    }
    
    class frmPesquisarMapaLisneter implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String cmd;
            cmd = e.getActionCommand();
            
            if(cmd=="btnPesqMapaOk"){
                if(tblResult.getSelectedRow()!=-1){
                    int id;
                    id = Integer.parseInt((String)oTabelaResultadoModel.getValueAt(tblResult.getSelectedRow(), 0));
                    AmbFSM.setMapa(id);
                    AmbFSM.actionPerformed(e);
                }
                
            }
            
            
        }
    }

}
