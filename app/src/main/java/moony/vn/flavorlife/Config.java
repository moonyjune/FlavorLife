package moony.vn.flavorlife;

public class Config {
    //Use for register GCM service.
    public static final String SENDER_ID = "859620921661";

    public static final int SERVER_DEV = 1;
    public static final String IP_ADRESS = "192.168.0.4";
//    public static final String IP_ADRESS = "192.168.43.51";
    public static int BUILD_SERVER = SERVER_DEV;
    public static String SERVER_URL = "http://" + IP_ADRESS + ":8899/";
    public static String SERVER_IMAGES_URL = "http://" + IP_ADRESS + ":8890/";

    //in miliseconds
    public static int INTERVAL_REQUEST_NEW_POST = 30 * 1000;
}
