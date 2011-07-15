package persistencia;

import java.util.ArrayList;
import java.util.List;


import modelo.PedidoMateriales;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class PedidoMaterialesDAO {
	
	private static PedidoMaterialesDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(PedidoMaterialesDAO.class);

	public static PedidoMaterialesDAO getInstancia() {
		if (instancia == null)
			instancia = new PedidoMaterialesDAO();
		return instancia;
	}

	public void insert(PedidoMateriales p) throws RuntimeException {

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
				throw new RuntimeException("Error: Imposible guardar el pedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(PedidoMateriales p) throws RuntimeException {
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
				throw new RuntimeException("Error: Imposible eliminar el pedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(PedidoMateriales p) throws RuntimeException {

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
				throw new RuntimeException("Error: Imposible actualizar el pedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public PedidoMateriales select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<PedidoMateriales> pedidos = new ArrayList<PedidoMateriales>();
		try {
			
			tx = session.beginTransaction();
			pedidos = session.createCriteria(PedidoMateriales.class)
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
				throw new RuntimeException("Error: Imposible seleccionar el pedido de Materiales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(pedidos.size()>0){
			return pedidos.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PedidoMateriales> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<PedidoMateriales> pedidos = new ArrayList<PedidoMateriales>();
		try {
			
			tx = session.beginTransaction();
			pedidos = session.createCriteria(PedidoMateriales.class).list();
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar los pedido de Materiales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return pedidos;
	}


}
