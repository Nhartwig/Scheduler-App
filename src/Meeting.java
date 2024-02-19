import java.time.LocalDateTime;
import java.util.List;

public class Meeting {

	
	// do we want max number of meeting participants? 
	private List<Person> participants;
	private LocalDateTime startTime;
	
	public Meeting(List<Person> participants, LocalDateTime startTime) {
		this.participants = participants;
		this.startTime = startTime;
	}
	
	public List<Person> getParticipants() {
		return participants;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	
}
