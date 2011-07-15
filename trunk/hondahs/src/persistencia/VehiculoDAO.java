package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.Vehiculo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class VehiculoDAO {
	
	private static VehiculoDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(VehiculoDAO.class);

	public static VehiculoDAO getInstancia() {
		if (instancia == null)
			instancia = new VehiculoDAO();
		return instancia;
	}

	public void insert(Vehiculo v) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(v);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				// throw again the first exception
				throw new RuntimeException("Error: Imposible guardar el vehiculo.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(Vehiculo v) throws RuntimeException {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(v);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible eliminar el vehiculo.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(Vehiculo v) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(v);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible actualizar el vehiculo.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Vehiculo select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		try {
			
			tx = session.beginTransaction();
			vehiculos = session.createCriteria(Vehiculo.class)
		    .add( Restrictions.eq("numero",numero) )
		    .list();
			
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar el vehiculo.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(vehiculos.size()>0){
			return vehiculos.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Vehiculo> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		try {
			
			tx = session.beginTransaction();
			vehiculos = session.createCriteria(Vehiculo.class).list();
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar los vehiculos.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return vehiculos;
	}

}
