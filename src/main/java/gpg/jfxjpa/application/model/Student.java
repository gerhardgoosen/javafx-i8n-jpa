package gpg.jfxjpa.application.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "Student.All", query = "SELECT student FROM Student student"),
	@NamedQuery(name = "Student.Count", query = "SELECT count(student) FROM Student student")
	})
public class Student extends ModelSuper {

	private int rollno;
	private String name;
	private double marks;

	public Student(int rollno, String sName, double marks) {
		super();
		this.rollno = rollno;
		this.name = sName;
		this.marks = marks;

	}

	public Student() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setsName(String sName) {
		this.name = sName;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public int getRollno() {
		return rollno;
	}

	public void setRollno(int rollno) {
		this.rollno = rollno;
	}

	@Override
	public String toString() {
		return "Employee [Student id=" + super.getId() + ", name=" + name + ", marks=" + marks + "]";
	}
}