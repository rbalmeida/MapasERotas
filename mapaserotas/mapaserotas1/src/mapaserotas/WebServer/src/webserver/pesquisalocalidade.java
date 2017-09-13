package webserver;

import java.io.PrintWriter;

import java.util.List;

public class pesquisalocalidade {
    private conexao con;
    
    public pesquisalocalidade() {
    }
    
    public pesquisalocalidade(conexao ncon) {
        con = ncon;
    }


    public List executarpesquisa(String endereco){
        List rl = con.executaQuery("select mapa.nome from Mapa mapa");
        return rl;
    }
}
