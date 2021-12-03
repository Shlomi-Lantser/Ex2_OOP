import api.GeoLocation;
import api.NodeData;

import java.util.LinkedHashMap;
import java.util.LinkedList;


public class Node implements NodeData , GeoLocation{

    private int key;
    private GeoLocation geo;
    private int tag =0;
    private double x;
    private double y;
    private double z;

    public Node(int key , double x, double y, double z){
        this.key = key;
        this.geo = new GeoLoc(x,y,z);
    }
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.geo;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.geo = p;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {
        return;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {}

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double xDif = Math.pow(g.x() - this.x , 2);
        double yDif = Math.pow(g.y() - this.y , 2);
        double zDif = Math.pow(g.z() - this.z , 2);
        return Math.sqrt(xDif + yDif + zDif);
    }

//    public String toString(){
//        return "{"+
//                "pos:" + this.x() + "," +this.y() + "," + this.z() +","
//                +"id:" +this.getKey()+
//                "}";
//    }
}
