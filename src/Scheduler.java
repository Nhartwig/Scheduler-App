import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler implements IScheduler{
	private Map<Person, List<Meeting>> schedule;
	private static final int START_HOUR = 9;
	private static final int END_HOUR = 17;
	
	
	public Person getPerson(String email) {
		for(Person p : this.schedule.keySet()) {
			if(p.getEmail().equals(email)) {
				return p;
			}
		}
		throw new IllegalArgumentException(String.format("Person with email %s was not found", email));
	}
	
	public Scheduler() {
		this.schedule = new HashMap<>();
	}
	
	private boolean validString(String s) {
		return s != null && s.length() > 0;
	}
	
	private boolean emailIsUnique(String email) {
		List<String> emails = new ArrayList<>();
		for(Person p : this.schedule.keySet()) {
			emails.add(p.getEmail());
		}
		return !emails.contains(email);
	}
	
	private boolean emailIsValid(String email) {
		boolean isValid = validString(email) ? emailIsUnique(email) : false;
		return isValid;
	}
	
	private boolean nameIsValid(String name) {
		return validString(name);
	}
	
	public boolean isValidPersonInfo(String name, String email) {
		return nameIsValid(name) && emailIsValid(email);
	}
	
	public void createPerson(String name, String email) {
		if (!isValidPersonInfo(name, email)) {
			throw new IllegalArgumentException("Person could not be created");
		} else {
			this.schedule.put(new Person(name, email), new ArrayList<>());
		}
	}
	
	public void createMeeting(List<Person> participants, LocalDateTime startTime) {
		if(!isValidMeeting(participants, startTime)) {
			throw new IllegalArgumentException("Meeting could not be created");
		} else {
			Meeting meeting = new Meeting(participants, startTime);
			for(Person p : participants) {
				this.schedule.get(p).add(meeting);
			}
		}
	}
	
	public boolean isValidMeeting(List<Person> participants, LocalDateTime startTime) {
		return meetingHasParticipants(participants) && meetingHasNoConflicts(participants, startTime) && startTimeValid(startTime);
	}
	
	private boolean meetingHasParticipants(List<Person> participants) {
		return participants.size() >= 1; 
	}
	
	private boolean meetingHasNoConflicts(List<Person> participants, LocalDateTime proposedMeetingTime) {
		for(Person p : participants) {
			for(Meeting m : this.showSchedule(p)) {
				if(m.getStartTime().equals(proposedMeetingTime)) {
					return false;
				}
			}
		}
		return true;
	}
	
	// assumption: meetings can only be scheduled between 9 and 17 and must be on same day
	private boolean startTimeValid(LocalDateTime startTime) {
		return (startTime.getHour() >= Scheduler.START_HOUR && startTime.getHour() <= Scheduler.END_HOUR);
	}
	
	public List<Meeting> showSchedule(Person person) {
		return this.schedule.getOrDefault(person, new ArrayList<>());
	}
	
	public List<LocalDateTime> suggestMeetingTimes(List<Person> participants) {
		if(!this.meetingHasParticipants(participants)) {
			throw new IllegalArgumentException("Must give at least one participant");
		}
		int currentHour = Scheduler.START_HOUR;
		List<LocalDateTime> suggestedMeetingTimes = new ArrayList<>();
		while((currentHour + 1) <= Scheduler.END_HOUR) {
			LocalDateTime suggestedTime = LocalDateTime.now().withHour(currentHour).withMinute(0).withSecond(0).withNano(0);
			if(this.meetingHasNoConflicts(participants, suggestedTime)) {
				suggestedMeetingTimes.add(suggestedTime);
			}
			currentHour += 1;
		}
		return suggestedMeetingTimes;
	}
}
