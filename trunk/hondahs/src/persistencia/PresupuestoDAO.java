package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.Presupuesto;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class PresupuestoDAO {
	
	private static PresupuestoDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(PresupuestoDAO.class);

	public static PresupuestoDAO getInstancia() {
		if (instancia == null)
			instancia = new PresupuestoDAO();
		return instancia;
	}

	public void insert(Presupuesto p) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(p);
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
				throw new RuntimeException("Error: Imposible guardar el presupuesto.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(Presupuesto p) throws RuntimeException {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(p);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible eliminar el presupuesto.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(Presupuesto p) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(p);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible actualizar el presupuesto.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Presupuesto select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
		try {
			
			tx = session.beginTransaction();
			presupuestos = session.createCriteria(Presupuesto.class)
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
				throw new RuntimeException("Error: Imposible seleccionar el presupuesto.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(presupuestos.size()>0){
			return presupuestos.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Presupuesto> selectAllByDates(String fechaInicio,String fechaFin) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
		try {
			
			List<String> fechas = new ArrayList<String>();
			fechas.add(fechaInicio);
			fechas.add(fechaFin);
			
			tx = session.beginTransaction();
			presupuestos = session.createCriteria(Presupuesto.class)
		    .add( Restrictions.in("fecha", fechas))
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
				throw new RuntimeException("Error: Imposible seleccionar lo presupuestos.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		
		if(presupuestos.size()>0){
			return presupuestos;
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Presupuesto> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Presupuesto> pedidos = new ArrayList<Presupuesto>();
		try {
			
			tx = session.beginTransaction();
			pedidos = session.createCriteria(Presupuesto.class).list();
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar los presupuesto.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return pedidos;
	}
}
