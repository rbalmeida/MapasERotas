package editormapa;

import editormapa.fsm.AmbienteFSM;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import java.util.List;

import javax.swing.JCheckBox;

import mapa.Conexao;
import mapa.Ligacao;
import mapa.Ponto;

public class frmEditarConexao extends JInternalFrame {
    private AmbienteFSM AmbFSM;
    private JLabel lblRotaA = new JLabel();
    private JLabel lblNomeRotaA = new JLabel();
    private JLabel lblRotaB = new JLabel();
    private JLabel lblNomeRotaB = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JButton btnEditConSelA = new JButton();
    private JButton btnEditConSelB = new JButton();
    private JLabel lblTipo = new JLabel();
    private JComboBox cboTipo = new JComboBox();
    private Conexao mConexao;
    private JSeparator jSeparator3 = new JSeparator();
    private JButton btnNovaCon = new JButton();
    private JButton btnEditConAbrir = new JButton();
    private JButton btnSalvarCon = new JButton();
    private JButton btnExCon = new JButton();
    private JButton btnEditConFechar = new JButton();
    private JButton btnEditConGerar = new JButton();
    private TabelaLigModel oTabelaLigModel = new TabelaLigModel();
    private JTable tblLigacoes = new JTable(oTabelaLigModel);
    private JScrollPane scrlTblLigacao = new JScrollPane(tblLigacoes);

    public frmEditarConexao(Ambiente nAmb) {
        AmbFSM = (AmbienteFSM) nAmb;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(518, 499));
        this.setTitle("Editar Conexão");
        lblRotaA.setText("Rota A:");
        lblRotaA.setBounds(new Rectangle(15, 15, 65, 20));
        lblNomeRotaA.setBounds(new Rectangle(65, 15, 405, 20));
        lblNomeRotaA.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        lblRotaB.setText("Rota B:");
        lblRotaB.setBounds(new Rectangle(15, 45, 40, 25));
        lblNomeRotaB.setBounds(new Rectangle(65, 45, 405, 25));
        lblNomeRotaB.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        jSeparator1.setBounds(new Rectangle(5, 115, 500, 2));
        btnEditConSelA.setText("...");
        btnEditConSelA.setBounds(new Rectangle(475, 15, 30, 25));
        btnEditConSelA.setActionCommand("btnEditConSelA");
        btnEditConSelB.setText("...");
        btnEditConSelB.setBounds(new Rectangle(475, 45, 30, 25));
        btnEditConSelB.setActionCommand("btnEditConSelB");
        lblTipo.setText("Tipo:");
        lblTipo.setBounds(new Rectangle(15, 80, 60, 25));
        cboTipo.setBounds(new Rectangle(65, 80, 150, 20));
        cboTipo.setActionCommand("cboTipo");
        cboTipo.setEnabled(false);
        jSeparator3.setBounds(new Rectangle(10, 405, 495, 2));
        btnNovaCon.setText("Nova");
        btnNovaCon.setBounds(new Rectangle(10, 415, 120, 25));
        btnNovaCon.setActionCommand("btnNovaCon");
        btnEditConAbrir.setText("Abrir");
        btnEditConAbrir.setBounds(new Rectangle(135, 415, 120, 25));
        btnEditConAbrir.setActionCommand("btnEditConAbrir");
        btnSalvarCon.setText("Salvar");
        btnSalvarCon.setBounds(new Rectangle(260, 415, 120, 25));
        btnSalvarCon.setActionCommand("btnSalvarCon");
        btnExCon.setText("Excluir");
        btnExCon.setBounds(new Rectangle(385, 415, 120, 25));
        btnExCon.setActionCommand("btnExCon");
        btnEditConFechar.setText("Fechar");
        btnEditConFechar.setBounds(new Rectangle(385, 445, 120, 25));
        btnEditConFechar.setSize(new Dimension(120, 25));
        btnEditConFechar.setActionCommand("btnEditConFechar");
        this.getContentPane().add(scrlTblLigacao, null);
        this.getContentPane().add(btnEditConGerar, null);
        this.getContentPane().add(btnExCon, null);
        this.getContentPane().add(jSeparator3, null);
        this.getContentPane().add(cboTipo, null);
        this.getContentPane().add(lblTipo, null);
        this.getContentPane().add(btnEditConSelB, null);
        this.getContentPane().add(btnEditConSelA, null);
        this.getContentPane().add(jSeparator1, null);
        this.getContentPane().add(lblNomeRotaB, null);
        this.getContentPane().add(lblRotaB, null);
        this.getContentPane().add(lblNomeRotaA, null);
        this.getContentPane().add(lblRotaA, null);
        this.getContentPane().add(btnNovaCon, null);
        this.getContentPane().add(btnEditConAbrir, null);
        this.getContentPane().add(btnSalvarCon, null);
        this.getContentPane().add(btnEditConFechar, null);
        
        btnEditConSelA.addActionListener(AmbFSM);
        btnEditConSelB.addActionListener(AmbFSM);
        btnNovaCon.addActionListener(AmbFSM);
        btnEditConFechar.addActionListener(AmbFSM);
        btnEditConGerar.addActionListener(AmbFSM);
        btnEditConAbrir.addActionListener(AmbFSM);
        
        btnEditConGerar.setText("Gerar");
        btnEditConGerar.setBounds(new Rectangle(225, 80, 100, 25));
        btnEditConGerar.setActionCommand("btnEditConGerar");
        btnEditConGerar.setSize(new Dimension(120, 25));
        scrlTblLigacao.setBounds(new Rectangle(5, 140, 500, 155));
        btnSalvarCon.addActionListener(AmbFSM);
        
        /* Escopo inicial suporta apenas intersecção */
        cboTipo.insertItemAt("Interseção", 0);
        cboTipo.setSelectedIndex(0);
        
    }

    public void setConexao(Conexao mConexao) {
        this.mConexao = mConexao;
        if(mConexao==null){
            setEditCampos(false);
            btnEditConAbrir.setEnabled(true);
            btnNovaCon.setEnabled(true);                        
        }
        else {
            setEditCampos(true);
            carregarDados();
        }
    }

    public Conexao getConexao() {
        return mConexao;
    }
    
    private void setEditCampos(boolean enabled){
        btnEditConSelA.setEnabled(enabled);
        btnEditConSelB.setEnabled(enabled);
        cboTipo.setEnabled(enabled);
        btnSalvarCon.setEnabled(enabled);
              
        
    }
    
    public void carregarDados(){
        
        List ligacoes;
        Ligacao ligacao;
        ListIterator itrLigacoes;
        int i;
    
        if(mConexao.getRotaA()!=null)
            lblNomeRotaA.setText(mConexao.getRotaA().getNome());
        else
            lblNomeRotaA.setText("");
        if(mConexao.getRotaB()!=null)
            lblNomeRotaB.setText(mConexao.getRotaB().getNome()); 
        else
            lblNomeRotaB.setText("");
        ligacoes = mConexao.getLigacoes();
        if (ligacoes!=null){
            oTabelaLigModel.data = new Object[ligacoes.size()][5];
            
            
            for(i=0 ; i < ligacoes.size(); i++){
                ligacao = (Ligacao)ligacoes.get(i);            
               // oTabelaLigModel.setValueAt(ligacao.isHabilitada(),i,0);
                oTabelaLigModel.setValueAt(ligacao.isHabilitada(),i,0);
                if (ligacao.getOrigem()==1){
                    oTabelaLigModel.setValueAt(mConexao.getRotaA().getNome(),i,1); 
                    oTabelaLigModel.setValueAt(mConexao.getRotaB().getNome(),i,3); 
                }
                else{
                    oTabelaLigModel.setValueAt(mConexao.getRotaB().getNome(),i,1); 
                    oTabelaLigModel.setValueAt(mConexao.getRotaA().getNome(),i,3);                     
                }
                    
                if(ligacao.getSentidoOrigem()==1)                        
                    oTabelaLigModel.setValueAt("Direto",i,2); 
                else
                    oTabelaLigModel.setValueAt("Inverso",i,2); 
                
                if(ligacao.getSentidoDestino()==1)                        
                    oTabelaLigModel.setValueAt("Direto",i,4); 
                else
                    oTabelaLigModel.setValueAt("Inverso",i,4);
            }
        }
        else oTabelaLigModel.data = null;
            
            /* Método chamado para que a tabela seja redesenhada */
            oTabelaLigModel.fireTableDataChanged();
            
    
            
    }
    public void atualizar(){
        carregarDados();
    }
    
    public void atualizarLigacoes(){    
    
        List ligacoes;
        Ligacao ligacao;
        
        ligacoes = mConexao.getLigacoes();
        
        if(ligacoes != null){            
            for(int i=0; i < ligacoes.size(); i++){
                ligacao = (Ligacao) ligacoes.get(i);                         
                ligacao.setHabilitada(Boolean.valueOf((Boolean)oTabelaLigModel.getValueAt(i,0)));                 
                 
            };           
        }
    
    }
    
    class TabelaLigModel extends AbstractTableModel {
        private String[] columnNames = {"Habilitada", "Origem", "Sentido", "Destino", "Sentido"};
        private Object[][] data;
        
        TabelaLigModel(){}

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
           
        public Class getColumnClass(int column) {
            if(column == 1)
               return JCheckBox.class;
            else
             return getValueAt(0, column).getClass();             

        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            /* Permite a edição de todas as colunas  */
             if(columnIndex!=0) return false;
             return true;
        }

        public void setValueAt(Object value, int row, int col) {
                    data[row][col] = value;
                    fireTableCellUpdated(row, col);
                }
        
        
    }
}
