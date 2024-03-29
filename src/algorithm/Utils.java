package algorithm;

public class Utils {
    public static final double EARTHRADIUS = 6371000; //METERS

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTHRADIUS * c);
    }

    //para testes
    public static void main(String[] args) {
        System.out.println("Lisboa->Porto:" + distFrom(38.7222524, -9.1393366, 41.1579438, -8.629105299999999));
    }
}
