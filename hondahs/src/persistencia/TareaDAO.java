package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.Tarea;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class TareaDAO {
	
	private static TareaDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(TareaDAO.class);

	public static TareaDAO getInstancia() {
		if (instancia == null)
			instancia = new TareaDAO();
		return instancia;
	}

	public void insert(Tarea t) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(t);
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
				throw new RuntimeException("Error: Imposible guardar la tarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(Tarea t) throws RuntimeException {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(t);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible eliminar la tarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(Tarea t) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(t);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible actualizar la tarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Tarea select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Tarea> tareas = new ArrayList<Tarea>();
		try {
			
			tx = session.beginTransaction();
			tareas = session.createCriteria(Tarea.class)
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
				throw new RuntimeException("Error: Imposible seleccionar la tarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(tareas.size()>0){
			return tareas.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Tarea> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Tarea> tareas = new ArrayList<Tarea>();
		try {
			
			tx = session.beginTransaction();
			tareas = session.createCriteria(Tarea.class).list();
			for (Tarea tarea : tareas) {
				tarea.getOrdenTrabajo().getNumero();
			}
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar las tareas.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return tareas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tarea> selectByOt(int numeroOt) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Tarea> tareas = new ArrayList<Tarea>();
		try {
			
			tx = session.beginTransaction();
			
			Criteria baseCriteria =  session.createCriteria(Tarea.class);
			Criteria otCriteria = baseCriteria.createCriteria("ordenTrabajo");
			otCriteria.add(Restrictions.eq("numero", numeroOt));
			
			tareas = baseCriteria.list();
			
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar las tareas.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return tareas;
	}

}
