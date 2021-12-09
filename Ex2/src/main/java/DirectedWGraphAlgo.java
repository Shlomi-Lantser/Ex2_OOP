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
    private HashMap<NodeData, NodeData> parents = new HashMap<>();

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
        if (this.getGraph() == null) return -1;

        NodeData srcPoint = this.getGraph().getNode(src);
        NodeData destPoint = this.getGraph().getNode(dest);

        if (srcPoint == null || destPoint == null) return -1;
        if (src == dest) return 0;

        double pathWeight;

        setWeightINF(this.getGraph());

        Dijkstra((Node) srcPoint, (Node) destPoint);

        pathWeight = destPoint.getWeight() == Double.MAX_VALUE ? -1 : destPoint.getWeight();

        return pathWeight;
    }

    private double Dijkstra(Node curr, Node dest) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        curr.setWeight(0);
        pq.add(curr);
        curr.setTag(1);
        double shortest = Double.MAX_VALUE;

        while (!pq.isEmpty()) {
            //Start to explore this node
            curr = pq.poll();
            curr.setTag(1);

            //if he doesnt have any adj
            if (this.getGraph().edgeIter(curr.getKey()) == null) continue;


            //Explore all the adj nodes
            Iterator<EdgeData> it = getGraph().edgeIter(curr.getKey());
            Node finalCurr = curr;
            while(it.hasNext()){
                EdgeData edgeData = it.next();
                NodeData adj = getGraph().getNode(edgeData.getDest());

                //Here we replace the weight if there any shorter path
                double pathWeight = finalCurr.getWeight() + edgeData.getWeight();
                if (adj.getWeight() > pathWeight) {
                    adj.setWeight(pathWeight);

                    //set adj node to be child of curr node
                    parents.put(adj, finalCurr);
                }

                if (adj.getTag() == 0) pq.add((Node) adj);
            }
            if (dest != null && curr.getKey() == dest.getKey()) return curr.getWeight();
//            it.forEachRemaining(edgeData -> {
//                NodeData adj = getGraph().getNode(edgeData.getDest());
//
//                //Here we replace the weight if there any shorter path
//                double pathWeight = finalCurr.getWeight() + edgeData.getWeight();
//                if (adj.getWeight() > pathWeight) {
//                    adj.setWeight(pathWeight);
//
//                    //set adj node to be child of curr node
//                    parents.put(adj, finalCurr);
//                    if (adj == dest) return ;
//                }
//
//                if (adj.getTag() == 0) pq.add((Node) adj);
//
//
//            });

//            while (it.hasNext()) {
//                EdgeData details = it.next();
//                NodeData adj = getGraph().getNode(details.getDest());
//
//                //Here we replace the weight if there any shorter path
//                double pathWeight = curr.getWeight() + details.getWeight();
//                if (adj.getWeight() > pathWeight) {
//                    adj.setWeight(pathWeight);
//
//                    //set adj node to be child of curr node
//                    parents.put(adj, curr);
//                    if(adj==dest) return;
//                }
//
//                if (adj.getTag()==0) pq.add((Node) adj);
//
//            }
        }
        return -1;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
            return null;
        }

    @Override
    public NodeData center() {
        HashMap<Integer , Double> maxShortestSrc = new HashMap<>();
        if (!this.isConnected()) return null;
        for (NodeData nodeSrc : g.getNodes().values()){
            int indexmax = 0;
            double maxShortest = nodeSrc.getWeight();
            int index = nodeSrc.getKey();
            for (NodeData nodeDest : g.getNodes().values()){
                if (nodeDest.getKey() == nodeSrc.getKey()) continue;
                double dist = shortestPathDist(nodeSrc.getKey() , nodeDest.getKey());
                if (maxShortest < dist){
                    maxShortest = dist;
                    indexmax = nodeDest.getKey();
                }
            }
            maxShortestSrc.put(nodeSrc.getKey() , maxShortest);
            System.out.println(nodeSrc.getKey() + ":" + maxShortest + " Dest :" + indexmax);
        }
        double minWeight = Double.MAX_VALUE;
        int minNode =0;

        Iterator<Integer> node = maxShortestSrc.keySet().iterator();
        for (Double weight : maxShortestSrc.values()){
            int nnode = node.next();
            if (weight < minWeight){
                minWeight = weight;
                minNode = nnode;
            }
        }

        return g.getNodes().get(minNode);
    } //v


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

    private void setWeightINF(DirectedWeightedGraph graph) {
        Iterator<NodeData> it = graph.nodeIter();
        it.forEachRemaining(nodeData -> {
            nodeData.setWeight(Double.MAX_VALUE);
            nodeData.setTag(0);
        });
    }
}
