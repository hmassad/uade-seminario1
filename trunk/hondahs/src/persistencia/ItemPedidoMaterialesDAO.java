package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.ItemPedidoMateriales;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class ItemPedidoMaterialesDAO {
	
	private static ItemPedidoMaterialesDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(ItemPedidoMaterialesDAO.class);

	public static ItemPedidoMaterialesDAO getInstancia() {
		if (instancia == null)
			instancia = new ItemPedidoMaterialesDAO();
		return instancia;
	}

	public void insert(ItemPedidoMateriales i) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(i);
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
				throw new RuntimeException("Error: Imposible guardar el itemPedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void delete(ItemPedidoMateriales i) throws RuntimeException {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(i);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible eliminar el itemPedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	public void update(ItemPedidoMateriales i) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(i);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible actualizar el itemPedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ItemPedidoMateriales select(Integer numero) throws RuntimeException {
		
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<ItemPedidoMateriales> items = new ArrayList<ItemPedidoMateriales>();
		try {
			
			tx = session.beginTransaction();
			items = session.createCriteria(ItemPedidoMateriales.class)
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
				throw new RuntimeException("Error: Imposible seleccionar el itemPedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		if(items.size()>0){
			return items.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ItemPedidoMateriales> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<ItemPedidoMateriales> items = new ArrayList<ItemPedidoMateriales>();
		try {
			
			tx = session.beginTransaction();
			items = session.createCriteria(ItemPedidoMateriales.class).list();
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible seleccionar los itemPedidoMateriales.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return items;
	}
}
