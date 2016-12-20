package tw.yalan.cafeoffice.adapter;

/**
 * Copyright (C) 2016 Alan Ding
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.grasea.grandroid.adapter.GrandroidRecyclerAdapter;
import com.grasea.grandroid.adapter.ItemConfig;
import com.grasea.grandroid.adapter.RecyclerItemConfig;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.yalan.cafeoffice.R;
import tw.yalan.cafeoffice.model.MenuItem;

/**
 * Created by Alan Ding on 2016/7/25.
 */
public class MenuRecyclerAdapter extends GrandroidRecyclerAdapter<MenuRecyclerAdapter.ItemObject, MenuRecyclerAdapter.ViewHolder> {

    @ItemConfig(id = R.layout.row_slide_menu, viewHolder = ViewHolder.class)
    public static class SimpleItemConfig extends RecyclerItemConfig<ViewHolder> {


    }

    Context context;

    public MenuRecyclerAdapter(Context context, ArrayList<ItemObject> list, RecyclerItemConfig recyclerItemConfigClass) {
        super(list, recyclerItemConfigClass);
        this.context = context;
    }

    @Override
    public void onItemClick(ViewHolder holder, int index, ItemObject item) {

    }

    @Override
    public void fillItem(ViewHolder holder, int position, ItemObject data) {
        holder.tvContent.setText(data.dataObject.getTitle());
        if (data.dataObject.getDrawableLeft() != 0) {
            holder.tvContent.setCompoundDrawablesWithIntrinsicBounds(data.dataObject.getDrawableLeft(), 0, 0, 0);
        }
    }

    public static class ItemObject {
        MenuItem dataObject;

        public ItemObject(MenuItem dataObject) {
            this.dataObject = dataObject;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
