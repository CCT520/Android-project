package entity;

public class Contacts {
	public String first_name = "";
	public String last_name = "";
	public String phone_number = "";
	public String email = "";
	public int id;
	
	public Contacts(String f, String l, String p, String e){
		this.first_name=f;
		this.last_name=l;
		this.phone_number=p;
		this.email=e;
		//this.id=id;
	}
	public Contacts(){
		
	}

}
