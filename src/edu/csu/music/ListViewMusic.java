package edu.csu.music;

import java.util.HashMap;
import java.util.List;

import edu.csu.mobiVod.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListViewMusic extends BaseAdapter implements OnClickListener {
    private List<HashMap<String, String>> list;
    private Activity context;
    public ListView listView;

    public ListViewMusic(Activity context, List<HashMap<String, String>> list, ListView listView) {
        this.context = context;
        this.list = list;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        ViewHolder mHolder = null;
        convertView = mInflater.inflate(R.layout.musicitem, null);
        mHolder = new ViewHolder();
        mHolder.time = (TextView) convertView.findViewById(R.id.musicduration);
        mHolder.title = (TextView) convertView.findViewById(R.id.musicName);
        mHolder.artist = (TextView) convertView.findViewById(R.id.musicArtist);
        HashMap<String, String> map = list.get(position);
        mHolder.time.setText(map.get("duration"));
        mHolder.title.setText(map.get("name"));
        mHolder.artist.setText(map.get("artist"));
        return convertView;
    }

    class ViewHolder {
        TextView artist;
        TextView title;
        TextView time;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}