import api.EdgeData;
import api.NodeData;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DirectedWGraph implements api.DirectedWeightedGraph {

    private HashMap<Integer , NodeData> nodes;
    private LinkedHashMap<Integer , HashMap<Integer , EdgeData>> edges;
    private HashMap<Integer , EdgeData> allEdges;
    private LinkedHashMap<Integer , HashMap<Integer , EdgeData>> edgesDest; //the key of each element is the dest of the edge
    private int MC =0;
    private int edgeCounter =0;

    public DirectedWGraph(){
        this.nodes = new HashMap<>();
        this.edges = new LinkedHashMap<>();
        this.edgesDest = new LinkedHashMap<>();
    }
    @Override
    public NodeData getNode(int key) {
        if (this.nodes.get(key) != null) {
            return this.nodes.get(key);
        }
        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (edges.get(src).get(dest) != null) {
            return edges.get(src).get(dest);
        }
        if (edges.get(src).get(dest) == null) return null;
        return null;
    }
    @Override
    public void addNode(NodeData n) {
        if (nodes.get(n.getKey()) != null) return;
        this.nodes.put(n.getKey() , n);
        this.edges.put(n.getKey() , new HashMap<Integer , EdgeData>());
        this.edgesDest.put(n.getKey() , new HashMap<Integer , EdgeData>());
        this.MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge e = new Edge(src , dest , w);
        if (edges.get(src).get(dest) == null && nodes.containsKey(src) && nodes.containsKey(dest)){
             edges.get(src).put(dest , e);
             edgesDest.get(dest).put(src,e);
             edgeCounter++;
            this.MC++;
        }else if (nodes.containsKey(src) && nodes.containsKey(dest)){
            edges.get(src).remove(edges.get(src).get(dest));
            edgesDest.get(dest).remove(edgesDest.get(dest).get(src));
            edges.get(src).put(dest , e);
            edgesDest.get(dest).put(src,e);
            this.MC++;
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return this.nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() throws RuntimeException {
        RuntimeException e = new RuntimeException();
        if (allEdges.size() != edgeCounter) throw e; //need to fix this !!!
        return allEdges.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return this.edges.get(node_id).values().iterator();
    }

    public Iterator<EdgeData> edgeDestIter(int node_id){return this.edgesDest.get(node_id).values().iterator();}

    @Override
    public NodeData removeNode(int key) {
        if (nodes.get(key) != null){
            NodeData tmp = this.nodes.get(key);
            if (this.edges.get(key)!= null) {
                this.edgeCounter = this.edgeCounter - this.edges.get(key).size();
            }
            this.nodes.remove(key);
            this.edges.remove(edges.get(key));
            for (EdgeData i :edgesDest.get(key).values()) {
                removeEdge(i.getSrc(),key);
                this.edgeCounter--;
            }
            edgesDest.remove(key);
            this.MC++;
            return tmp;
        }
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (this.edges.get(src).get(dest) == null && this.edges.get(src) !=null || this.nodes.get(src) == null) return null;
        EdgeData tmp = this.edges.get(src).get(dest);
        this.edges.get(src).remove(dest);
        this.MC++;
        return tmp;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgeCounter;
    }

    @Override
    public int getMC() {
        return this.getMC();
    }

    public HashMap<Integer , NodeData> getNodes(){
        return this.nodes;
    }

    public LinkedHashMap<Integer , HashMap<Integer , EdgeData>> getEdges(){
        return this.edges;
    }

    public LinkedHashMap<Integer , HashMap<Integer , EdgeData>> getEdgesDest(){
        return this.edgesDest;
    }
}
