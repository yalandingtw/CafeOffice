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
public class SearchAction extends ContextAction {
    private String search;

    public SearchAction(Context context, String search) {
        super(context);
        this.search = search;
    }

    @Override
    public boolean execute(Context context) {
        Uri uri = Uri.parse("http://www.google.com/#q=" + search);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
        return true;
    }
}
