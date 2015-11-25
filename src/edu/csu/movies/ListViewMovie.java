package edu.csu.movies;

import java.util.HashMap;
import java.util.List;

import edu.csu.basetools.C;
import edu.csu.basetools.WebImageBuilder;
import edu.csu.mobiVod.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 

public class ListViewMovie extends BaseAdapter implements OnClickListener{
	private List<HashMap<String,String>> list;
	private Activity context;
	public ListView listView;
	String picurlname;
	public ListViewMovie(Activity context,List<HashMap<String,String>> list,ListView listView)
	{
			this.context = context;
			this.list = list;
			this.listView=listView;
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
             convertView = mInflater.inflate(R.layout.listviewmovie, null);
			 mHolder = new ViewHolder();
			 mHolder.mImageViewleft = (ImageView) convertView.findViewById(R.id.localmovie_imgleft);
			 mHolder.time=(TextView)convertView.findViewById(R.id.localmovie_texttime);
			 mHolder.mImageViewright = (ImageView) convertView.findViewById(R.id.localmovie_imgright);
			 mHolder.title=(TextView)convertView.findViewById(R.id.localmovie_title);
			 HashMap<String, String> map=list.get(position);
			 mHolder.time.setText(map.get("movielasttime"));
			 mHolder.title.setText(map.get("movietitle"));
			 mHolder.mImageViewleft.setImageBitmap(C.BITMAPCATCH.get(map.get("coverurl")));
			 mHolder.mImageViewright.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "点击这个图片，将从底部升起菜单。", Toast.LENGTH_LONG).show();
				}
			});
		return convertView;
		}
	class ViewHolder {
		ImageView mImageViewleft;
		ImageView mImageViewright;
		TextView title;
		TextView time;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}