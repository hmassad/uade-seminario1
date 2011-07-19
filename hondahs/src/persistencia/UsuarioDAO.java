package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class UsuarioDAO {
	
	private static UsuarioDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

	public static UsuarioDAO getInstancia() {
		if (instancia == null)
			instancia = new UsuarioDAO();
		return instancia;
	}

	public void insert(Usuario u) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(u);
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
				throw new RuntimeException("Error: Imposible guardar el usuario.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(Usuario u) throws RuntimeException {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(u);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible eliminar el usuario.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(Usuario u) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(u);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible actualizar el usuario.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Usuario select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			
			tx = session.beginTransaction();
			usuarios = session.createCriteria(Usuario.class)
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
				throw new RuntimeException("Error: Imposible seleccionar el usuario.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(usuarios.size()>0){
			return usuarios.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Usuario select(String nombre, String password) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			
			tx = session.beginTransaction();
			usuarios = session.createCriteria(Usuario.class)
		    .add( Restrictions.eq("nombre",nombre))
		    .add( Restrictions.eq("clave",password))
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
				throw new RuntimeException("Error: Imposible seleccionar el usuario.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		
		if(usuarios.size()>0){
			return usuarios.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			
			tx = session.beginTransaction();
			usuarios = session.createCriteria(Usuario.class).list();
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar los usuario.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return usuarios;
	}

}
