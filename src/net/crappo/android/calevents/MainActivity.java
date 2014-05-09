package net.crappo.android.calevents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import net.crappo.android.calevents.Model4Main.EventDto;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int RL_PADDING_TOP = 5;
    private static final int RL_PADDING_BOTTOM = 10;

    MainActivity activityObj;
    Model4Main model;
    ListView lv;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		activityObj = this;
        try { // CalendarProviderからEventを全部取得するAsyncTaskを実行する。
            new GetEventsAsync(this).execute(new String[0]);
        } catch (Exception e) {
            Toast.makeText(this, "Error Ocurred. (in GetEventsAsync().execute())", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    /* API 4.0からContentProviderのURI取得方法が刷新されたので、それに合わせたURIを返すメソッド */
    public static Uri getEventProvider() {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return CalendarContract.Events.CONTENT_URI;
        } else {
            return Uri.parse("content://com.android.calendar/events");
        }
    }
    
    
    void refreshLayout(){
        lv = (ListView)findViewById(R.id.main_listView);
        lv.setAdapter(new ViewHolderAdapter(this, 0, model.events));
    }

    
    /* ListViewの子要素になるViewの構成を持つHolder */
    private class ViewHolder {
        ArrayList<TextView> tvArray = new ArrayList<TextView>();
        RelativeLayout rlay;

        ViewHolder(RelativeLayout rlay) { // xmlで作ったlayoutから子要素を取ってきてtvArrayに入れていくだけ
            this.rlay = rlay;
            rlay.setPadding(0, RL_PADDING_TOP, 0, RL_PADDING_BOTTOM);
            for(int i=0; i<rlay.getChildCount(); i++) {
                TextView tv = (TextView)rlay.getChildAt(i);
                tvArray.add(tv);
            }
        }
    }

    /* ViewHolderをListViewにVEventの数だけ差し込む処理をするadapter */
    private class ViewHolderAdapter extends ArrayAdapter<EventDto> {
        ViewHolder holder;
        EventDto event;
        public ViewHolderAdapter (MainActivity activityObj, int resource, ArrayList<EventDto> list) {
            super(activityObj, resource, list);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 下記のif文はArrayAdapterにおけるconvertView使い回しの常套句のようなものだと思われる。
            if (convertView == null) {
                holder = new ViewHolder((RelativeLayout)getLayoutInflater().inflate(R.layout.layout_for_showeventlist, null));
                convertView = holder.rlay;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            // eventの開始時刻・終了時刻・タイトル・場所の４項目を１セットにして表示する。
            event = getItem(position);
            if(event == null)    for (TextView entry : holder.tvArray) { setDummyForView(entry); }
            else                 for (TextView entry : holder.tvArray) { setTextToView(entry); }

            return convertView;
        }

        void setDummyForView(TextView entry) { // eventがnullだった場合に呼ばれるメソッド。ダミー文字列だけ表示する。
            switch(entry.getId()){
            case R.id.showeventlist_dtstart:
                entry.setText("開始日時");
                break;
            case R.id.showeventlist_dtend:
                entry.setText("終了日時");
                break;
            case R.id.showeventlist_summary:
                entry.setText("イベントタイトル");
                break;
            case R.id.showeventlist_location:
                entry.setText("場所");
                break;
            }
        }

        void setTextToView(TextView entry) { // holderの持つ各Viewにeventの各種値をセットするメソッド
            if( entry != null) {
                String str;
                switch(entry.getId()){
                case R.id.showeventlist_dtstart: // eventの開始時刻(エポック秒)
                    if(event.getDtstart() != null){
                        str = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Long.parseLong(event.getDtstart()));
                        entry.setText(str);
                    } else { entry.setText(""); }
                    break;
                case R.id.showeventlist_dtend: // eventの終了時刻(エポック秒)
                    if(event.getDtend() != null){
                        str = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Long.parseLong(event.getDtend()));
                        entry.setText(str);
                    } else {
                    	entry.setText("終日 or 繰り返し"); }
                    break;
                case R.id.showeventlist_summary: // eventのタイトル
                    if(event.getTitle() != null) {
                    	entry.setText(event.getTitle());
                    } else { entry.setText("タイトル未設定"); }
                    break;
                case R.id.showeventlist_location: // eventの場所
                    if(event.getEventLocation() != null){
                    	entry.setText(event.getEventLocation());
                    } else { entry.setText("場所未設定"); }
                    break;
                }
            } else {
                Log.e("setTextToView", "entry is Null!");
            }
        }
    }

}
