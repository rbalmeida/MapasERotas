package editormapa;

import editormapa.fsm.AmbienteFSM;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

public class frmPesquisarCon extends JInternalFrame {
    private AmbienteFSM AmbFSM;
    private JLabel lblRotaA = new JLabel();
    private JLabel lblRotaB = new JLabel();
    private JTextField txtRotaA = new JTextField();
    private JButton btnPesqConPesq = new JButton();
    private JTextField txtRotaB = new JTextField();
    private JSeparator jSeparator1 = new JSeparator();
    TabelaResultadoModel oTabelaResultadoModel = new TabelaResultadoModel();
    JTable tblResult = new JTable(oTabelaResultadoModel);
    private JScrollPane scrlTblResult = new JScrollPane(tblResult);
    private JButton btnPesqConCancel = new JButton();
    private JButton btnPesqConOk = new JButton();
    private JSeparator jSeparator2 = new JSeparator();
    private ControlePersistencia oCtrlPersist;
    

    public frmPesquisarCon(Ambiente nAmb) {
        AmbFSM = (AmbienteFSM)nAmb;
        oCtrlPersist = AmbFSM.getCtrlPersist();
        try{ 
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(432, 295));
        this.setTitle("Pesquisar Conexão");
        lblRotaA.setText("Rota A:");
        lblRotaA.setBounds(new Rectangle(5, 10, 50, 25));
        lblRotaB.setText("Rota B:");
        lblRotaB.setBounds(new Rectangle(5, 35, 65, 25));
        txtRotaA.setBounds(new Rectangle(45, 15, 275, 20));
        txtRotaA.setSize(new Dimension(275, 20));
        btnPesqConPesq.setText("Pesquisar");
        btnPesqConPesq.setBounds(new Rectangle(325, 15, 95, 25));
        btnPesqConPesq.setActionCommand("btnPesqConPesq");
        txtRotaB.setBounds(new Rectangle(45, 40, 275, 20));
        txtRotaB.setSize(new Dimension(275, 20));
        jSeparator1.setBounds(new Rectangle(5, 70, 415, 2));
        scrlTblResult.setBounds(new Rectangle(5, 80, 410, 130));
        btnPesqConCancel.setText("Cancelar");
        btnPesqConCancel.setBounds(new Rectangle(335, 235, 85, 25));
        btnPesqConCancel.setActionCommand("btnPesqConCancel");
        btnPesqConCancel.setSize(new Dimension(85, 25));
        btnPesqConOk.setText("Ok");
        btnPesqConOk.setBounds(new Rectangle(245, 235, 85, 25));
        btnPesqConOk.setActionCommand("btnPesqConOk");
        btnPesqConOk.setSize(new Dimension(85, 25));
        jSeparator2.setBounds(new Rectangle(5, 220, 415, 2));
        this.getContentPane().add(scrlTblResult, null);
        this.getContentPane().add(jSeparator2, null);
        this.getContentPane().add(scrlTblResult, null);
        this.getContentPane().add(jSeparator1, null);
        this.getContentPane().add(txtRotaB, null);
        this.getContentPane().add(txtRotaA, null);
        this.getContentPane().add(lblRotaB, null);
        this.getContentPane().add(lblRotaA, null);
        this.getContentPane().add(btnPesqConPesq, null);
        this.getContentPane().add(btnPesqConOk, null);
        this.getContentPane().add(btnPesqConCancel, null);
        
        btnPesqConOk.addActionListener(AmbFSM);
        btnPesqConPesq.addActionListener(AmbFSM);
        btnPesqConCancel.addActionListener(AmbFSM);
        
        
    }
    
    public void pesquisar(){
        List rs;
        ListIterator itrCons;
        String st = "select c.id as con_id, ra.id as ra_id, ra.nome as ra_nome, rb.id as rb_id, rb.nome as rb_nome" + 
        " from conexao c, rota ra, rota rb" + 
        " where ra.id = c.rotaa_id" + 
        " and rb.id = c.rotab_id and c.mapa_id = " + 
                AmbFSM.getMapa().getId();
                
        if(!txtRotaA.getText().equals("")){
            st = st + 
            " and (ra.nome like '" + txtRotaA.getText() + "'" +
            " or rb.nome like '" + txtRotaA.getText() + "')";
        }
        
        if(!txtRotaB.getText().equals("")){
            st = st + 
            " and (ra.nome like '" + txtRotaB.getText() + "'" +
            " or rb.nome like '" + txtRotaB.getText() + "')"; 
        }
       
        Object [] linhaRs;
                    
        //  oCtrlPersist.conectaBanco();
        rs = oCtrlPersist.executaQueryNativa(st);
        itrCons= rs.listIterator();
        
        if (rs.size()!=0)
            oTabelaResultadoModel.data = new Object[rs.size()][5];
        else
            oTabelaResultadoModel.data = null;
            
        int i=0; 
        while(itrCons.hasNext()){
            linhaRs =(Object[]) itrCons.next();
            oTabelaResultadoModel.setValueAt("" + linhaRs[0],i,0);
            oTabelaResultadoModel.setValueAt("" + linhaRs[1],i,1);
            oTabelaResultadoModel.setValueAt("" + linhaRs[2],i,2);
            oTabelaResultadoModel.setValueAt("" + linhaRs[3],i,3);
            oTabelaResultadoModel.setValueAt("" + linhaRs[4],i,4);
            i++;
        }
        
        //  oCtrlPersist.desconectaBanco();
        
        oTabelaResultadoModel.fireTableDataChanged();
    }
    
    class TabelaResultadoModel extends AbstractTableModel {
        private String[] columnNames = {"Id Con", "Orig Id", "Orig Nome", "Dest Id", "Dest Nome"};
        private Object[][] data;
        
        TabelaResultadoModel(){}

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
                  return false;
        }

        public void setValueAt(Object value, int row, int col) {
                    data[row][col] = value;
                    fireTableCellUpdated(row, col);
                }
        
        
    }
}
