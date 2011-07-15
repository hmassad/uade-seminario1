package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;


public class ClienteDAO {
	
	private static ClienteDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(ClienteDAO.class);

	public static ClienteDAO getInstancia() {
		if (instancia == null)
			instancia = new ClienteDAO();
		return instancia;
	}

	public void insert(Cliente c) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(c);
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
				throw new RuntimeException("Error: Imposible guardar el cliente.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(Cliente c) throws RuntimeException {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(c);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible eliminar el cliente.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(Cliente c) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(c);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible actualizar el cliente.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Cliente select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			
			tx = session.beginTransaction();
			clientes = session.createCriteria(Cliente.class)
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
				throw new RuntimeException("Error: Imposible seleccionar el cliente.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(clientes.size()>0){
			return clientes.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			
			tx = session.beginTransaction();
			clientes = session.createCriteria(Cliente.class).list();
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar los clientes.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return clientes;
	}

}
