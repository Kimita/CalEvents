package net.crappo.android.calevents;

import java.util.ArrayList;

public class Model4Main {
	
	ArrayList<EventDto> events;
	
	public Model4Main(){
		events = new ArrayList<EventDto>();
	}
	
	public void add(EventDto event){
		events.add(event);
	}
	
	public EventDto getEventDtoInstance(String _id, String title) {
		return new EventDto(_id, title);
	}

    class EventDto {
        String _id;
        String dtstart;
        String dtend;
        String title;
        String eventLocation;
        String description;
        String duration;
        String rdate;
        String rrule;
        String account_type;
        String account_name;
        public EventDto(String _id, String title) {
            this._id = _id;
            this.title = title;
        }
		public String get_id() {
			return _id;
		}
		public void set_id(String _id) {
			this._id = _id;
		}
		public String getDtstart() {
			return dtstart;
		}
		public void setDtstart(String dtstart) {
			this.dtstart = dtstart;
		}
		public String getDtend() {
			return dtend;
		}
		public void setDtend(String dtend) {
			this.dtend = dtend;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getEventLocation() {
			return eventLocation;
		}
		public void setEventLocation(String eventLocation) {
			this.eventLocation = eventLocation;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getRdate() {
			return rdate;
		}
		public void setRdate(String rdate) {
			this.rdate = rdate;
		}
		public String getRrule() {
			return rrule;
		}
		public void setRrule(String rrule) {
			this.rrule = rrule;
		}
		public String getAccount_type() {
			return account_type;
		}
		public void setAccount_type(String account_type) {
			this.account_type = account_type;
		}
		public String getAccount_name() {
			return account_name;
		}
		public void setAccount_name(String account_name) {
			this.account_name = account_name;
		}
    }

}
