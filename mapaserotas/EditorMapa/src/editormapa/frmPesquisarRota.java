package editormapa;

import editormapa.fsm.AmbienteFSM;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

public class frmPesquisarRota extends JInternalFrame {

    private AmbienteFSM AmbFSM;
    private JLabel lblNome = new JLabel();
    private JTextField txtNome = new JTextField();
    private JButton btnPesqRotaPesq = new JButton();
    TabelaRsultadoModel oTabelaResultadoModel = new TabelaRsultadoModel();
    JTable tblResult = new JTable(oTabelaResultadoModel);
    private JScrollPane scrlTblResult = new JScrollPane(tblResult);
    private JSeparator jSeparator1 = new JSeparator();
    private JButton btnPesqRotaOk = new JButton();
    private JButton btnPesqRotaCancel = new JButton();
    private frmPesquisarRotaListener ofrmPesquisarRotaListener = new frmPesquisarRotaListener();
    private JSeparator jSeparator2 = new JSeparator();
    private ControlePersistencia oCtrlPersist;


    public frmPesquisarRota(Ambiente nAmb) {
        this.AmbFSM = (AmbienteFSM) nAmb;
        oCtrlPersist = AmbFSM.getCtrlPersist();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(431, 295));
        this.setTitle("Pesquisar Rota");
        lblNome.setText("Nome:");
        lblNome.setBounds(new Rectangle(5, 5, 85, 25));
        txtNome.setBounds(new Rectangle(45, 10, 275, 20));
        btnPesqRotaPesq.setText("Pesquisar");
        btnPesqRotaPesq.setBounds(new Rectangle(325, 10, 95, 25));
        btnPesqRotaPesq.setActionCommand("btnPesqRotaPesq");
        scrlTblResult.setBounds(new Rectangle(5, 50, 415, 165));
        jSeparator1.setBounds(new Rectangle(5, 40, 410, 5));

        btnPesqRotaOk.setText("Ok");
        btnPesqRotaOk.setBounds(new Rectangle(245, 235, 85, 25));
        btnPesqRotaOk.setActionCommand("btnPesqRotaOk");
        btnPesqRotaOk.setSize(new Dimension(85, 25));
        btnPesqRotaCancel.setText("Cancelar");
        btnPesqRotaCancel.setBounds(new Rectangle(335, 235, 85, 25));
        btnPesqRotaCancel.setActionCommand("btnPesqRotaCancel");
        btnPesqRotaCancel.setSize(new Dimension(85, 25));
        this.getContentPane().add(jSeparator2, null);
        this.getContentPane().add(scrlTblResult, null);
        this.getContentPane().add(btnPesqRotaCancel, null);
        this.getContentPane().add(btnPesqRotaOk, null);

        this.getContentPane().add(jSeparator1, null);
        this.getContentPane().add(btnPesqRotaPesq, null);
        this.getContentPane().add(txtNome, null);
        this.getContentPane().add(lblNome, null);
        btnPesqRotaOk.addActionListener(ofrmPesquisarRotaListener);
        btnPesqRotaPesq.addActionListener(ofrmPesquisarRotaListener);
        btnPesqRotaCancel.addActionListener(ofrmPesquisarRotaListener);
        jSeparator2.setBounds(new Rectangle(0, 225, 420, 5));
        
        btnPesqRotaCancel.addActionListener(AmbFSM);
        btnPesqRotaOk.addActionListener(AmbFSM);
        btnPesqRotaPesq.addActionListener(AmbFSM);
    }
    
    public void pesquisar(){
        List rs;
        ListIterator itrRotas;
        String st = "select rota.id, rota.nome from Rota rota where rota.mapa = " + 
                AmbFSM.getMapa().getId() + 
                " and rota.nome like '" + 
                    txtNome.getText() + "'";
        Object [] linhaRs;
                    
      //  oCtrlPersist.conectaBanco();
        rs = oCtrlPersist.executaQuery(st);
        itrRotas = rs.listIterator();
        
        if (rs.size()!=0)
            oTabelaResultadoModel.data = new Object[rs.size()][2];
        else
            oTabelaResultadoModel.data = null;
            
        int i=0; 
        while(itrRotas.hasNext()){
            linhaRs =(Object[]) itrRotas.next();
            oTabelaResultadoModel.setValueAt("" + linhaRs[0],i,0);
            oTabelaResultadoModel.setValueAt("" + linhaRs[1],i,1);
            i++;
        }
        
      //  oCtrlPersist.desconectaBanco();
        
        oTabelaResultadoModel.fireTableDataChanged();
    }
    
    /* Classe que define a tabela de Niveis de Zoom */
    class TabelaRsultadoModel extends AbstractTableModel {
        private String[] columnNames = {"Id", "Nome"};
        private Object[][] data;
        
        TabelaRsultadoModel(){}

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
    
    private class frmPesquisarRotaListener implements ActionListener{
    
          public void actionPerformed(ActionEvent e) {
            String cmd;
            cmd = e.getActionCommand();
            
            if(cmd=="btnPesquisar"){
                
            }
            
            if(cmd=="btnOk"){
                
            }
            
            if(cmd=="btnCancelar"){
                
            }
        }
    }
    
}
