
public class Person {
	
	private String name;
	private String email;
	
	public Person(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	// can read name and email, we make the assumption that they cannot be changed once created
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
}
