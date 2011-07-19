package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.TipoTarea;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class TipoTareaDAO {
	
	private static TipoTareaDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(TipoTareaDAO.class);

	public static TipoTareaDAO getInstancia() {
		if (instancia == null)
			instancia = new TipoTareaDAO();
		return instancia;
	}

	public void insert(TipoTarea t) throws RuntimeException {

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
				throw new RuntimeException("Error: Imposible guardar el TipoTarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(TipoTarea t) throws RuntimeException {
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
				throw new RuntimeException("Error: Imposible eliminar el TipoTarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(TipoTarea t) throws RuntimeException {

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
				throw new RuntimeException("Error: Imposible actualizar el TipoTarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public TipoTarea select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<TipoTarea> tipoTareas = new ArrayList<TipoTarea>();
		try {
			
			tx = session.beginTransaction();
			tipoTareas = session.createCriteria(TipoTarea.class)
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
				throw new RuntimeException("Error: Imposible seleccionar el TipoTarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(tipoTareas.size()>0){
			return tipoTareas.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public TipoTarea select(String descripcion) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<TipoTarea> tipoTareas = new ArrayList<TipoTarea>();
		try {
			
			tx = session.beginTransaction();
			tipoTareas = session.createCriteria(TipoTarea.class)
		    .add( Restrictions.eq("descripcion",descripcion) )
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
				throw new RuntimeException("Error: Imposible seleccionar el TipoTarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(tipoTareas.size()>0){
			return tipoTareas.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipoTarea> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<TipoTarea> tipotareas = new ArrayList<TipoTarea>();
		try {
			
			tx = session.beginTransaction();
			tipotareas = session.createCriteria(TipoTarea.class).list();
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar los TipoTarea.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return tipotareas;
	}
	
	
}
