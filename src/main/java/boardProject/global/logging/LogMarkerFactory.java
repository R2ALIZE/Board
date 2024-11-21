package boardProject.global.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogMarkerFactory {


    public static final Marker PERFORMANCE = MarkerFactory.getMarker("PERFORMANCE");
    public static final Marker APP_INIT = MarkerFactory.getMarker("APP_INIT");
    public static final Marker BUSINESS = MarkerFactory.getMarker("BUSINESS");
    public static final Marker ENCRYPTION = MarkerFactory.getMarker("ENCRYPTION");
    public static final Marker AUTH = MarkerFactory.getMarker("AUTH");




    public static Marker getMarker(String markerName) {
        return MarkerFactory.getMarker(markerName);
    }


}
