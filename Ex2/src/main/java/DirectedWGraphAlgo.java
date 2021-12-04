import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DirectedWGraphAlgo implements DirectedWeightedGraphAlgorithms {
    DirectedWGraph g;

    public DirectedWGraphAlgo(){
        this.g = new DirectedWGraph();
    } //v

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = (DirectedWGraph) g;
    } //v

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    } //v

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWGraph gCopy = this.g;
        return gCopy;
    } //v

    @Override
    public boolean isConnected() {
        for (NodeData node : this.g.getNodes().values()) { //O(V)
            DFS(node.getKey()); // (OV+E)
            for (NodeData node1 : this.g.getNodes().values()) { //O(V)
                if (node1.getTag() == 0) return false;
            }
            setTag0();
        }
        return true; //total : O(V*(V+E)) = O(V^2+VE)
    } //v

    public void DFS(int v){
        this.getGraph().getNode(v).setTag(1);
        for (EdgeData e : this.g.getEdges().get(v).values()){
            if (g.getNodes().get(e.getDest()).getTag() ==0) DFS(e.getDest());
        }
    } //v

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        HashMap<Integer , Double> totalCost = new HashMap<>();
        HashMap<Integer , Integer> prevNode = new HashMap<>();
        HashMap<Integer , Double> minQueue = new HashMap<>();


        for (NodeData node: g.getNodes().values()){
            totalCost.put(node.getKey() , Double.MAX_VALUE);
        }
        totalCost.put(src , 0.0);
        minQueue.put(src , 0.0);

        while (!minQueue.isEmpty()){
            int smallest = removeMin(minQueue);

            for (EdgeData e : g.getEdges().get(smallest).values()){
                if (g.getNodes().get(e.getDest()).getTag() !=1){
                    minQueue.put(e.getDest() , e.getWeight());
                    double newpath = totalCost.get(smallest) + e.getWeight();
                    if (newpath < totalCost.get(e.getDest())){
                        totalCost.put(e.getDest() , newpath);
                        prevNode.put(e.getDest() , smallest);
                    }
                }
            }
        }
        setTag0();
        double result = -1;
        if (totalCost.get(dest) == Double.MAX_VALUE) return result;
        return totalCost.get(dest);
    } //v

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        int counter =0;
        if (src == dest) return null;
        List<NodeData> result = new LinkedList<>();
        HashMap<Integer , Double> totalCost = new HashMap<>();
        HashMap<Integer , Integer> prevNode = new HashMap<>();
        HashMap<Integer , Double> minQueue = new HashMap<>();


        for (NodeData node: g.getNodes().values()){ //O(|V|)
            totalCost.put(node.getKey() , Double.MAX_VALUE);
        }
        totalCost.put(src , 0.0);
        minQueue.put(src , 0.0);

        while (!minQueue.isEmpty()){
            int smallest = removeMin(minQueue);

            for (EdgeData e : g.getEdges().get(smallest).values()){
                if (g.getNodes().get(e.getDest()).getTag() !=1){
                    minQueue.put(e.getDest() , e.getWeight());
                    double newpath = totalCost.get(smallest) + e.getWeight();
                    if (newpath < totalCost.get(e.getDest())){
                        totalCost.put(e.getDest() , newpath);
                        prevNode.put(e.getDest() , smallest);
                    }
                }
            }
        }

        result.add(g.getNodes().get(dest));
        int curr =prevNode.get(dest);

        while (curr != src){
            result.add(g.getNodes().get(curr));
            curr = prevNode.get(curr);
        }
        result.add(g.getNodes().get(src));

        int resultsize = result.size();
        setTag0();
        return result;
    } //Needs to reverse the List !!! //v

    public int removeMin(HashMap<Integer , Double> minQueue){
        double min = Double.MAX_VALUE;
        int index =0;
        for (Integer node : minQueue.keySet()){
            if (minQueue.get(node) < min){
                min = minQueue.get(node);
                index = node;
            }
        }
        minQueue.remove(index);
        g.getNodes().get(index).setTag(1);
        return index;
    } //v

    @Override
    public NodeData center() {
        if (!this.isConnected()) return null;
        HashMap<Integer , Double> comperator = new HashMap<>();
        int index;
        for (NodeData node : g.getNodes().values()){
            index = node.getKey();
            double maxShortestDist =0;
            for (NodeData nodeDest : g.getNodes().values()){
                if (maxShortestDist < shortestPathDist(node.getKey() , nodeDest.getKey())) {
                    maxShortestDist = shortestPathDist(node.getKey() , nodeDest.getKey());
                }
            }
            comperator.put(index , maxShortestDist);
        }
        int i=0;
        int j=0;
        double min = comperator.get(0);
        for (double node : comperator.values()){
            if (comperator.get(i) <= min){
                min = comperator.get(i);
                j=i;
            }
            i++;
        }

        return g.getNodes().get(j);
    } //v

    private List<List<NodeData>> permutation(){

    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        Gson gson = new Gson();
        String result = gson.toJson(this.g.getNodes());
        try {
            FileWriter filee = new FileWriter(file);
            filee.write(result);
            filee.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    } // To fix !

    @Override
    public boolean load(String file) {
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();
            JsonArray nodesJson = fileObject.get("Nodes").getAsJsonArray();
            for (int i = 0; i < nodesJson.size(); i++) {
                JsonObject nodeObj = new Gson().fromJson(nodesJson.get(i) , JsonObject.class);
                String posStr = nodeObj.get("pos").getAsString();
                double[] pos = new double[3];
                String[] posArray = posStr.split(",");
                for (int j = 0; j < posArray.length; j++) {
                    pos[j] = Double.parseDouble(posArray[j]);
                }
                String idStr = nodeObj.get("id").getAsString();
                int id = Integer.parseInt(idStr);
                double x = pos[0];
                double y = pos[1];
                double z = pos[2];
                Node tmp = new Node(id , x , y ,z);
                this.g.addNode(tmp);
            }

            JsonArray edgesJson = fileObject.get("Edges").getAsJsonArray();
            for (int i = 0; i < edgesJson.size(); i++) {
                JsonObject edgeObj = new Gson().fromJson(edgesJson.get(i) , JsonObject.class);
                String srcStr = edgeObj.get("src").getAsString();
                String wStr = edgeObj.get("w").getAsString();
                String destStr = edgeObj.get("dest").getAsString();
                int src = Integer.parseInt(srcStr);
                double w = Double.parseDouble(wStr);
                int dest = Integer.parseInt(destStr);
                this.g.connect(src , dest , w);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    } //v

    public void setTag0(){
        for (NodeData node : this.g.getNodes().values()){
            node.setTag(0);
        }
    } //set all nodes tag to 0.
}
