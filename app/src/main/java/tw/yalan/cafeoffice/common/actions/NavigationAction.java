/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.yalan.cafeoffice.common.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.grasea.grandroid.actions.ContextAction;


/**
 * @author Rovers
 */
public class NavigationAction extends ContextAction {

    public static final int DRIVING = 0;
    public static final int PUBLIC = 1;
    public static final int WALK = 2;
    protected double startLat;
    protected double startLng;
    protected double endLat;
    protected double endLng;
    protected String way;

    public NavigationAction(Context context, LatLng start, LatLng end, int type) {
        super(context);
        this.startLat = start.latitude;
        this.startLng = start.longitude;
        this.endLat = end.latitude;
        this.endLng = end.longitude;
        switch (type) {
            case 0:
                this.way = "d";
                break;
            case 1:
                this.way = "r";
                break;
            case 2:
                this.way = "w";
                break;
        }
    }

    public NavigationAction(Context context, double startLat, double startLng, double endLat, double endLng, int type) {
        this(context, null, startLat, startLng, endLat, endLng, type);
    }

    public NavigationAction(Context context, String actionName, double startLat, double startLng, double endLat, double endLng, int type) {
        super(context, actionName);
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        switch (type) {
            case 0:
                this.way = "d";
                break;
            case 1:
                this.way = "r";
                break;
            case 2:
                this.way = "w";
                break;
        }
    }

    @Override
    public boolean execute(Context context) {
        Intent i = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com.tw/maps?f=d&source=s_d&saddr=" + startLat + "," + startLng + "&daddr=" + endLat + "," + endLng + "&hl=zh&t=m&dirflg=" + way));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        i.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        context.startActivity(i);
        return true;
    }
}
