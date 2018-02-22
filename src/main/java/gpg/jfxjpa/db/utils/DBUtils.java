package gpg.jfxjpa.db.utils;

import java.util.logging.Logger;

import gpg.jfxjpa.application.model.Student;
import gpg.jfxjpa.application.service.JPAService;

public class DBUtils {
	private static final Logger logger = Logger.getLogger(DBUtils.class.getName());

	private JPAService jpaService;

	public DBUtils(JPAService  jpaService) {
		this.jpaService = jpaService;
	}

	public boolean checkSeedData() {
		logger.info("checkSeedData");
		if ((Long) this.jpaService.getEntityManager().createNamedQuery("Student.Count").getSingleResult() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void populateSeedData() {
		logger.info("populateSeedData");
		this.jpaService.getEntityManager().getTransaction().begin();
		Student s1 = new Student();
		s1.setRollno(1);
		s1.setsName("John Smith");
		s1.setMarks(78.3);

		Student s2 = new Student();
		s2.setRollno(2);
		s2.setsName("Joe Smith");
		s2.setMarks(88.2);

		Student s3 = new Student();
		s3.setRollno(3);
		s3.setsName("Jeff Smith");
		s3.setMarks(66.87);

		Student s4 = new Student();
		s4.setRollno(4);
		s4.setsName("Jill Smith");
		s4.setMarks(37.9);

		this.jpaService.getEntityManager().persist(s1);
		this.jpaService.getEntityManager().persist(s2);
		this.jpaService.getEntityManager().persist(s3);
		this.jpaService.getEntityManager().persist(s4);

		this.jpaService.getEntityManager().getTransaction().commit();
	}

}
