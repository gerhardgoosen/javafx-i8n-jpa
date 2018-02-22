package gpg.jfxjpa.application.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import gpg.jfxjpa.application.model.ModelSuper;

public class JPAService {

	private EntityManagerFactory emFactory;
	private EntityManager entityManager;

	public EntityManager initilizeEntityManager() {
		emFactory = Persistence.createEntityManagerFactory("jfxjpa_db");
		setEntityManager(emFactory.createEntityManager());

		return getEntityManager();
	}

	public EntityManager getEntityManager() {
		if (entityManager == null || emFactory == null) {
			initilizeEntityManager();
		}
		return entityManager;
	}

	public ModelSuper saveModel(ModelSuper data) {

		ModelSuper retVal = data;
		getEntityManager().getTransaction().begin();
		if (data.getId() != null) {
			retVal = getEntityManager().merge(data);
		} else {
			getEntityManager().persist(data);
			retVal = data;
		}
		getEntityManager().getTransaction().commit();

		return retVal;

	}

	public ModelSuper deleteModel(ModelSuper data) {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(data);
		getEntityManager().getTransaction().commit();
		return null;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();

		closeConnection();
	}

	public void closeConnection() {
		getEntityManager().close();
		emFactory.close();
	}

	private void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
