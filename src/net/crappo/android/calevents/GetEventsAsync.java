package net.crappo.android.calevents;

import net.crappo.android.calevents.Model4Main.EventDto;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.Log;

public class GetEventsAsync extends AsyncTask<String, Void, Void> {
	MainActivity activityObj;
	
	public GetEventsAsync(MainActivity activityObj) {
		this.activityObj = activityObj;
	}
	
	@Override
	protected Void doInBackground(String... arg0) {
		Model4Main model = new Model4Main();
        Cursor cursor = null;
        cursor = activityObj.getContentResolver().query(MainActivity.getEventProvider(), null, null, null, null);
        if(cursor != null) { // cursor��null�ł����Ă͂Ȃ�Ȃ��B(Uri���Ԉ���Ă��鎞�Ȃǂ�null�ƂȂ����悤�ȋC������)
            if(cursor.moveToFirst()) { // 
                do {
                	int eventId; // �R���e���g�v���o�C�_���Ŏ����Ă�event��ID
                	String eventName = null;     // event�̃^�C�g���̂���
                	String dtstart = null;       // event�̊J�n����(�G�|�b�N�b)
                	String dtend = null;         // event�̏I������(�G�|�b�N�b)
                	String eventLocation = null; // event�̏ꏊ
                	String rdate = null;
                	String rrule = null;
                    String account_type = null;
                    String account_name = null;
                	if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    	eventId = cursor.getInt(cursor.getColumnIndex(CalendarContract.Events._ID));
                    	eventName = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                    	dtstart = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                    	dtend = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTEND));
                    	eventLocation = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
                    	rdate = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RDATE));
                    	rrule = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RRULE));
                    	account_type = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ACCOUNT_TYPE));
                    	account_name = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ACCOUNT_NAME));
                	} else { // API 14�����̒[���ł�CalendarAPI�����J����Ă��Ȃ��̂ŁA�Y�����郉�J�����𒲂ׂĒ��ڎw�肵�Ď��o���B
                    	eventId = cursor.getInt(cursor.getColumnIndex("_id"));
                    	eventName = cursor.getString(cursor.getColumnIndex("title"));
                    	dtstart = cursor.getString(cursor.getColumnIndex("dtstart"));
                    	dtend = cursor.getString(cursor.getColumnIndex("dtend"));
                    	eventLocation = cursor.getString(cursor.getColumnIndex("eventLocation"));
                    	rdate = cursor.getString(cursor.getColumnIndex("rdate"));
                    	rrule = cursor.getString(cursor.getColumnIndex("rrule"));
                    	account_type = cursor.getString(cursor.getColumnIndex("_sync_account_type"));
                    	account_name = cursor.getString(cursor.getColumnIndex("_sync_account"));
                	}
                	// �f�o�b�O�p��LogCat��
                	Log.v("Event output", cursor.getPosition() + " : " + eventId + "/" + eventName + " : " + account_type + "/" + account_name);
                	EventDto event = model.getEventDtoInstance(Integer.valueOf(eventId).toString(), eventName);
                	event.setDtstart(dtstart);
                	event.setDtend(dtend);
                	event.setEventLocation(eventLocation);
                	event.setRdate(rdate);
                	event.setRrule(rrule);
                	event.setAccount_type(account_type);
                	event.setAccount_name(account_name);
                	model.add(event);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Log.e("CalendarProvider", "cursor is null.");
        }
        activityObj.model = model;
		return null;
    }
	
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        activityObj.refreshLayout();
    }
}
