package net.javaguides.sms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "ore")
	private String ore;
	
	public Student() {
		
	}
	
	public Student(String firstName, String lastName, String ore) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ore = ore;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getOre() {
		return ore;
	}
	public void setOre(String ore) {
		this.ore = ore;
	}
}
