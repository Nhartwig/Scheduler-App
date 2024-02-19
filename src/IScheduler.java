import java.time.LocalDateTime;
import java.util.List;

public interface IScheduler {
	
	
	void createPerson(String name, String email);
	
	void createMeeting(List<Person> participants, LocalDateTime startTime);
	
	List<Meeting> showSchedule(Person person);
	
	List<LocalDateTime> suggestMeetingTimes(List<Person> participants);

}
