package com.example.karthik.mydashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AvatarListAdaptor extends BaseAdapter {
    private Context mContext;
    private List<AvatarList> listAvatars;

    public AvatarListAdaptor(Context context, List<AvatarList> list) {
        mContext = context;
        listAvatars = list;
    }

    @Override
    public int getCount() {
        return listAvatars.size();
    }

    @Override
    public Object getItem(int pos) {
        return listAvatars.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // get selected entry
        AvatarList entry = listAvatars.get(pos);

        // inflating list view layout if null
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.avatar_dialog_content, null);
        }

        ImageView imageAvatar = (ImageView) convertView.findViewById(R.id.avatar_image);
        imageAvatar.setImageResource(entry.getImage());

        TextView avatarName = (TextView) convertView.findViewById(R.id.avatar_name);
        avatarName.setText(entry.getTitle());

        return convertView;
    }

}