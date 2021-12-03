import api.GeoLocation;

public class GeoLoc implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public GeoLoc(double x , double y , double z){
        this.x = x;
        this.y = y;
        this.z = z;
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
}
