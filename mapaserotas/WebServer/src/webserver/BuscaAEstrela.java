package webserver;

import grafo.No;

import mapa.Localidade;
import mapa.Ponto;
import java.util.*;



public class BuscaAEstrela {
    private conexao con;
    private AStar mAStar;
    private Vector estadosIniciais;
    private Vector estadosFinais;
    private Ponto pDest;

    public BuscaAEstrela(conexao Con) {
        con = Con;
        mAStar = new AStar();
        estadosIniciais = new Vector();
        estadosFinais = new Vector();
    }

    public void addEstadoInicial(No no, Ponto ponto){
        State mState = new State(no, ponto);
        estadosIniciais.add(mState);
    }

    public void addEstadoFinal(No no, Ponto ponto){
        State mState = new State(no, ponto);
        estadosFinais.add(mState);
    }

    public void setPontoDestino(Ponto ponto){
        pDest = ponto;
    }

    public List buscarCaminho(){

        double estimation;
        node firstNode;
        mAStar.expanded = 0;
        mAStar.evaluated = 1;
        Vector sequencia;
        Vector nosIniciais = new Vector();;
        List ret = new ArrayList();
        int i;

        System.out.println("Buscar Caminho - Inicio");



       for(i=0; i<estadosIniciais.size(); i++){
            estimation = ((State)estadosIniciais.get(i)).estimate(pDest);
            firstNode = new node((State)estadosIniciais.get(i), null, 0, estimation);
            mAStar.open.put(((State)estadosIniciais.get(i)).getId(), firstNode);
            nosIniciais.addElement(firstNode);
        }

        mAStar.addToNodes(nosIniciais);

        /* Estados iniciais podera conter conexoes, considerar aqui */

         System.out.println("Buscar Caminho - Chamando o Search");
        node solution = mAStar.search();
        System.out.println("Buscar Caminho - Search OK");
        System.out.println("Buscar Caminho - Chamando Get sequence");
        sequencia =  mAStar.getSequence(solution);
        System.out.println("Buscar Caminho - Get sequence OK");
        mAStar.nodes.removeAllElements();
        mAStar.open.clear();
        mAStar.closed.clear();
        mAStar.ready = true;
        mAStar.setBest(mAStar.bestTotal);


        System.out.println("Sequencia.size: " + sequencia.size());
        for(i=0; i< sequencia.size(); i++){
            ret.add(((State)sequencia.get(i)).mPonto);
        }

        System.out.println("Buscar Caminho - Fim");

        return ret;

    }




    final class node {
            State state; /* representa um estado do grafo */
            double costs; /* custo para se chegar até o nó */
            double distance; /* distancia estimada até o nó destino */
            double total; /* custo total estimado */
            node parent;

            node(State theState, node theParent,  double theCosts, double theDistance) {
            state = theState;
            parent = theParent;
            costs = theCosts;
            distance = theDistance;
            total = theCosts + theDistance;
      };

    }

    final class AStar {

            private final  Hashtable open = new Hashtable(500); /* nós ainda passíveis de serem avaliados */
            private final  Hashtable closed = new Hashtable(500); /* nós já avaliados */
            public int evaluated = 0;
            public  int expanded = 0;
            public double bestTotal = 0;
            public boolean ready = false;
            private  boolean newBest = false;
            private final  Vector nodes = new Vector(); /* nos sendo avaliados, ordenados pelo custo */
            private Ponto destino;

            /* para servlet talvez tenha q mudar para signle thread */
            private  synchronized void setBest (double value) {
            bestTotal = value;
            newBest = true;
                notify(); // All?
                Thread.currentThread().yield();  //for getNewBest
            }

            public  synchronized double getNewBest() {
            while(!newBest) {
                            try {
                                    wait();
                            }
                            catch (InterruptedException e) {
                            }
            }

            newBest = false;
            return bestTotal;

      }

        private  node search() {
              System.out.println("Inicio do Search");
                    node best;
                    Vector childStates; /* vetor de trabalho para se recuperar nos filhos do nó atualmente avaliado */
                    double childCosts; /* custo ate o nó atual, que será repassado para os nós filhos */
                        Vector children = new Vector();

                    while (!(nodes.isEmpty())) {

                            System.out.println("Processando no...");
                                best = (node) nodes.firstElement(); /* 1o elemento é atualmente o de melhor custo */

                                /* se no foi fechado, remove do vetor de nos e continua a busca */
                        if(closed.get(best.state.getId()) != null) {
                                        nodes.removeElementAt(0);
                                        continue;
                        }

                        if (!(best.total == bestTotal)) {
                                setBest(best.total);
                        }

                        if ((best.state).goalp()){
                            System.out.println("Fim do Search");
                            return best; /* se é o nó procurado interrompe a busca */
                        }
                        System.out.println("best: " + best.state.getId());
                            System.out.println("best coord: (" + best.state.mPonto.getLatitude() + ", " + best.state.mPonto.getLongitude() + ")");
                        children.removeAllElements(); /* limpa o vetor de trabalho children */
                        childStates = (best.state).generateChildren(); /* obtem os nós filhos do nó atual */

                         childCosts = 0;
                        //childCosts = 1 + best.costs;
                        /* este custo deve ser alterado
                        para que o custo de cada filho seja a distancia
                        entre o pai e o filho */

                        expanded++;

                                /* processa cada nó filho encontrado */
                                for (int i = 0; i < childStates.size(); i++) {
                                        State childState = (State) childStates.elementAt(i);
                                        node closedNode = null;
                                        node openNode = null;
                                        node theNode = null;
                                        /* Distancia entre o no pai e o no filho */
                                        childCosts =  best.costs + childState.estimate((best.state).mPonto);
                                       System.out.println("child: " + childState.getId() + " - Custo: " + childCosts);

                                       if ((closedNode = (node) closed.get(childState.getId())) == null)
                                                  openNode = (node) open.get(childState.getId());

                                        theNode = (openNode != null) ? openNode : closedNode;

                                        if (theNode != null) {
                                                /* Ja existe um nobusca referenciando o mesmo estado */

                                                /* Caminho atual tem custo menor que caminho existente, no de busca
                                                pode ser reaberto ou atualizado */
                                                if (childCosts < theNode.costs) {

                                                if (closedNode != null) {
                                                        open.put(childState.getId(), theNode);
                                                        closed.remove(childState.getId());
                                                }
                                                else {
                                                        double dist = theNode.distance;
                                                        theNode = new node(childState, best, childCosts, dist);
                                                        open.put(childState.getId(), theNode);
                                                        /* como faz o put com a mesma chave,
                                                        este é atualizado
                                                        */

                                                }

                                                theNode.costs = childCosts;
                                                theNode.total = theNode.costs + theNode.distance;
                                                theNode.parent = best;
                                                children.addElement(theNode);

                                                }
                                        }
                                        else {
                                                /* no filho ainda nao carregado.
                                                Cria um novo no de busca */
                                                double estimation;
                                                node newNode;

                                                estimation = childState.estimate(pDest);
                                                newNode = new node(childState, best, childCosts, estimation);
                                                open.put(childState.getId(), newNode);
                                                evaluated++;
                                                children.addElement(newNode);

                                        }
                                }

                        open.remove(best.state.getId()); /* remove o no atual da tabela de nós abertos */
                        closed.put(best.state.getId(), best); /* adiciona o no atual à tabela de nós fechados */
                        nodes.removeElementAt(0); /* */
                        addToNodes(children); // update nodes

                        }

                System.out.println("Fim do Search");
                return null; //no open nodes and no solution
          }


                /* faz a busca binaria no vetor de nos para determinar em qual posicao deve
                ser inserido o novo no de busca */
                private int bsearch(int l, int h, double tot, double costs){

                int lo = l;
                int hi = h;

                while(lo<=hi) {
                        int cur = (lo+hi)/2;
                        double ot = ((node) nodes.elementAt(cur)).total;
                        if((tot < ot) ||
                                (tot == ot && costs >= ((node) nodes.elementAt(cur)).costs))
                                        hi = cur - 1;
                        else
                                lo = cur + 1;
                }

                return lo; //insert before lo
          }


                /* insere cada vetor filho encontrado no vetor de nos */
                private  void addToNodes(Vector children) {

                for (int i = 0; i < children.size(); i++) {

                        node newNode = (node) children.elementAt(i);
                        double newTotal = newNode.total;
                        double newCosts = newNode.costs;
                        boolean done = false;

                        int idx = bsearch(0, nodes.size()-1, newTotal, newCosts);
                        nodes.insertElementAt(newNode, idx);

                    }
                }


                private  Vector getSequence(node n) {
                    Vector result;

                    if (n == null) {
                      result = new Vector();
                    }
                    else {
                        result = getSequence (n.parent);
                        result.addElement(n.state);
                }

                return result;
                }


        }

        class State {
                No mNo;
                Ponto mPonto;

            public State(){

            }

            public State(No no, Ponto ponto){
                mNo = no;
                mPonto = ponto;
            }


            public Vector generateChildren(){
                Vector res = new Vector();
                String st;
                List rs;
                ListIterator rsIter;
                Object linhaRs[];
                Ponto pTemp;
                No nTemp;

                st = "select n.id as n_id, p.id as p_id " +
                " from no n, ponto p, arco a " +
                " where n.id = a.destino_id" +
                " and (p.nosentidoinverso_id = n.id or p.nosentidodireto_id = n.id) " +
                " and a.origem_id = " + mNo.getId();

                rs = con.executaNativeQuery(st);
                rsIter = rs.listIterator();

                while(rsIter.hasNext()){
                    linhaRs = (Object[])rsIter.next();
                     nTemp = (No)con.executaQuery("from No where id = " + linhaRs[0]).get(0);
                    pTemp = (Ponto)con.executaQuery("from Ponto where id = " + linhaRs[1]).get(0);
                    res.add(new State(nTemp, pTemp));
                    System.out.println("child (" + pTemp.getLatitude() + ", " + pTemp.getLongitude() + ")");
                }
                System.out.println("Children.size " + res.size());
                return res;
          }


          public boolean goalp(){
              int i;
              System.out.println("no goalp novo: estadosfinais.size: " + estadosFinais.size());
              for(i=0; i < estadosFinais.size(); i++){
                  if(mNo.getId() == ((State)estadosFinais.get(i)).mNo.getId()) return true;
              }
              return false;
          }

          public double estimate(Ponto Destino){
              return Math.sqrt(
                  Math.pow(Destino.getLatitude() - mPonto.getLatitude(), 2) +
                  Math.pow(Destino.getLongitude() - mPonto.getLongitude(), 2 ));
          }

          public int getId(){
                    return mNo.getId();
           }

        }

}
