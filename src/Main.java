import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Scheduler scheduler;
	
	public static void main(String[] args) {
		scheduler = new Scheduler();
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("\nSelect an option:");
			System.out.println("1. Create person");
			System.out.println("2. Schedule a meeting");
			System.out.println("3. Show meetings for person");
			System.out.println("4. Suggest meeting times for a group");
			System.out.println("5. Help");
			System.out.println("6. Exit application\n");
			int input;
			try {
				input = scanner.nextInt();
			} catch(InputMismatchException e) {
				input = -1;
			}
			scanner.nextLine();
			switch(input) {
				case 1:
					System.out.println("\nEnter name:");
					String name = scanner.nextLine();
					System.out.println("Enter email:");
					String email = scanner.nextLine().replace(" ", "").toLowerCase();
					try {
						scheduler.createPerson(name, email);	
					} catch (IllegalArgumentException e) {
						System.out.println(e);
						break;
					}
					Person person = scheduler.getPerson(email);
					System.out.println(String.format("\nPerson %s, with email %s created successfully\n", person.getName(), person.getEmail()));
					break;
				case 2:
					System.out.println("\nEnter emails of participants, separated by commas:");
					String[] emails = scanner.nextLine().toLowerCase().split(",");
					List<Person> persons = parseEmailsToPersons(emails);
					System.out.println("Enter time (hour number) of meeting:");
					int hour;
					try {
						hour = scanner.nextInt();
					} catch(InputMismatchException e) {
						System.out.println(e);
						break;
					}
					LocalDateTime startTime = LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
					try { 
						scheduler.createMeeting(persons, startTime);
					} catch (IllegalArgumentException e) {
						System.out.println(e);
						break;
					}
					System.out.println(String.format("\nMeeting created\n"));
					break;
				case 3:
					System.out.println("\nEnter email of person:");
					String email1 = scanner.nextLine().toLowerCase();
					Person person1;
					try {
						person1 = scheduler.getPerson(email1);
					} catch (IllegalArgumentException e){
						System.out.println(e);
						break;
					}
					List<Meeting> meetings = scheduler.showSchedule(person1);
					System.out.println(String.format("Meetings for %s:", person1.getName()));
					for(Meeting m : meetings) {
						System.out.println(String.format("\nMeeting at %s with participants:", m.getStartTime()));
						for(Person p : m.getParticipants()) {
							System.out.println(String.format("\t%s , %s ", p.getName(), p.getEmail()));
						}
						System.out.println("\n");
					}
					System.out.println("\n");
					break;
				case 4:
					System.out.println("\nEnter emails of the group separated by commas:");
					String[] emails1 = scanner.nextLine().toLowerCase().split(",");
					List<Person> persons1 = parseEmailsToPersons(emails1);
					List<LocalDateTime> suggestedTimes;
					try {
						suggestedTimes = scheduler.suggestMeetingTimes(persons1);	
					} catch (IllegalArgumentException e) {
						System.out.println(e);
						break;
					}					
					System.out.println("The following meeting times are suggested:");
					for(LocalDateTime d : suggestedTimes) {
						System.out.println(d);
					}
					break;
				case 5:
					System.out.println("\nMeetings can only be planned on the current day\n");
					break;
				case 6:
					return;
				default:
					System.out.println("\nInvalid choice entered, please enter choice between 1 - 6\n");
			}
		}
	}
	public static List<Person> parseEmailsToPersons(String[] emails){
		List<Person> persons = new ArrayList<>();
		for(String email : emails) {
			try {
				email = email.replace(" ", "");
				persons.add(scheduler.getPerson(email));
			} catch (IllegalArgumentException e){
				System.out.println(e);							
			}
		}
		return persons;
	}
}
