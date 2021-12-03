import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.jar.JarEntry;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) throws FileNotFoundException {
        DirectedWeightedGraphAlgorithms result = new DirectedWGraphAlgo();
        result.load(json_file);
        return result.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans =new DirectedWGraphAlgo();
        ans.load(json_file);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }

    public static void main(String[] args){
        DirectedWeightedGraphAlgorithms g = new DirectedWGraphAlgo();
        g.load("data/G1.json");
        System.out.println(g.isConnected());
        System.out.println(g.shortestPathDist(0,15));

//        g.getGraph().addNode(new Node(0,1,2,0));
//        g.getGraph().addNode(new Node(1,2,6,0));
//        g.getGraph().addNode(new Node(2,3,7,0));
//        g.getGraph().addNode(new Node(3,4,8,0));
//        g.getGraph().addNode(new Node(4,5,9,0));
//        g.getGraph().connect(0,1,1);
//        g.getGraph().connect(1,2,3);
//        g.getGraph().connect(2,3,1);
//        g.getGraph().connect(3,4,2);
//        g.getGraph().connect(0,4,13);
//        System.out.println(g.shortestPathDist(0,4));

//        System.out.println(g.getGraph().nodeSize());
//        System.out.println(g.getGraph().edgeSize());
////        g.getGraph().removeNode(2);
//        System.out.println(g.getGraph().edgeSize());
//        System.out.println(g.getGraph().getEdge(3,2));
//        g.getGraph().connect(0,2,10);
//        System.out.println(g.getGraph().getEdge(0,2));
//        System.out.println(g.getGraph().edgeSize());
//        DirectedWGraph gCopy = (DirectedWGraph) g.copy();
//        System.out.println(gCopy.getNodes().get(0).getLocation().x());
////        System.out.println(g.isConnected());
//        g.getGraph().removeNode(11);
////        g.getGraph().removeNode(16);
//        System.out.println("*******************");
//        System.out.println(g.isConnected());
//        System.out.println(g.getGraph().getNode(0).getTag());
//        System.out.println("_____________________");
//        DirectedWeightedGraph g1 = new DirectedWGraph();
//        g1.addNode(new Node(0,1,2,3));
//        g1.addNode(new Node(1,2,4,7));
//        DirectedWeightedGraphAlgorithms gw1 = new DirectedWGraphAlgo();
//        gw1.init(g1);
//        gw1.getGraph().connect(0,1,2);
////        gw1.getGraph().addNode(new Node(5,1,2,3));
//        System.out.println(gw1.isConnected());
//        Gson gson = new Gson();
//        String result = gson.toJson(g.getGraph());
//        System.out.println(result);
//        g.save("data/test.json");

    }
}