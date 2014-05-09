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
        try { // CalendarProvider����Event��S���擾����AsyncTask�����s����B
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
	
    /* API 4.0����ContentProvider��URI�擾���@�����V���ꂽ�̂ŁA����ɍ��킹��URI��Ԃ����\�b�h */
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

    
    /* ListView�̎q�v�f�ɂȂ�View�̍\��������Holder */
    private class ViewHolder {
        ArrayList<TextView> tvArray = new ArrayList<TextView>();
        RelativeLayout rlay;

        ViewHolder(RelativeLayout rlay) { // xml�ō����layout����q�v�f������Ă���tvArray�ɓ���Ă�������
            this.rlay = rlay;
            rlay.setPadding(0, RL_PADDING_TOP, 0, RL_PADDING_BOTTOM);
            for(int i=0; i<rlay.getChildCount(); i++) {
                TextView tv = (TextView)rlay.getChildAt(i);
                tvArray.add(tv);
            }
        }
    }

    /* ViewHolder��ListView��VEvent�̐������������ޏ���������adapter */
    private class ViewHolderAdapter extends ArrayAdapter<EventDto> {
        ViewHolder holder;
        EventDto event;
        public ViewHolderAdapter (MainActivity activityObj, int resource, ArrayList<EventDto> list) {
            super(activityObj, resource, list);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ���L��if����ArrayAdapter�ɂ�����convertView�g���񂵂̏퓅��̂悤�Ȃ��̂��Ǝv����B
            if (convertView == null) {
                holder = new ViewHolder((RelativeLayout)getLayoutInflater().inflate(R.layout.layout_for_showeventlist, null));
                convertView = holder.rlay;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            // event�̊J�n�����E�I�������E�^�C�g���E�ꏊ�̂S���ڂ��P�Z�b�g�ɂ��ĕ\������B
            event = getItem(position);
            if(event == null)    for (TextView entry : holder.tvArray) { setDummyForView(entry); }
            else                 for (TextView entry : holder.tvArray) { setTextToView(entry); }

            return convertView;
        }

        void setDummyForView(TextView entry) { // event��null�������ꍇ�ɌĂ΂�郁�\�b�h�B�_�~�[�����񂾂��\������B
            switch(entry.getId()){
            case R.id.showeventlist_dtstart:
                entry.setText("�J�n����");
                break;
            case R.id.showeventlist_dtend:
                entry.setText("�I������");
                break;
            case R.id.showeventlist_summary:
                entry.setText("�C�x���g�^�C�g��");
                break;
            case R.id.showeventlist_location:
                entry.setText("�ꏊ");
                break;
            }
        }

        void setTextToView(TextView entry) { // holder�̎��eView��event�̊e��l���Z�b�g���郁�\�b�h
            if( entry != null) {
                String str;
                switch(entry.getId()){
                case R.id.showeventlist_dtstart: // event�̊J�n����(�G�|�b�N�b)
                    if(event.getDtstart() != null){
                        str = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Long.parseLong(event.getDtstart()));
                        entry.setText(str);
                    } else { entry.setText(""); }
                    break;
                case R.id.showeventlist_dtend: // event�̏I������(�G�|�b�N�b)
                    if(event.getDtend() != null){
                        str = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Long.parseLong(event.getDtend()));
                        entry.setText(str);
                    } else {
                    	entry.setText("�I�� or �J��Ԃ�"); }
                    break;
                case R.id.showeventlist_summary: // event�̃^�C�g��
                    if(event.getTitle() != null) {
                    	entry.setText(event.getTitle());
                    } else { entry.setText("�^�C�g�����ݒ�"); }
                    break;
                case R.id.showeventlist_location: // event�̏ꏊ
                    if(event.getEventLocation() != null){
                    	entry.setText(event.getEventLocation());
                    } else { entry.setText("�ꏊ���ݒ�"); }
                    break;
                }
            } else {
                Log.e("setTextToView", "entry is Null!");
            }
        }
    }

}
