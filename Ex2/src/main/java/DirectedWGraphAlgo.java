import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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

    public void DFSShortPath(int src ,int v , double[][] mat){
        return;
    } // O

    @Override
    public double shortestPathDist(int src, int dest) {
//        double[][] mat = new double[g.getNodes().size()][2];
//        Arrays.fill(mat , Integer.MAX_VALUE);
////        mat[src][0] = 0;
//        DFSShortPath(src , src , mat);
        return 0;
    } // O

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    } // to implements first!!

    @Override
    public NodeData center() {
        return null;
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
