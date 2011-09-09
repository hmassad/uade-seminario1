package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.EstadoOrdenTrabajo;
import modelo.OrdenTrabajo;
import modelo.Presupuesto;
import modelo.Tarea;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;

public class OrdenTrabajoDAO {

	private static OrdenTrabajoDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(OrdenTrabajoDAO.class);

	public static OrdenTrabajoDAO getInstancia() {
		if (instancia == null)
			instancia = new OrdenTrabajoDAO();
		return instancia;
	}

	public void insert(OrdenTrabajo o) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(o);
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
				throw new RuntimeException(
						"Error: Imposible guardar el ordenTrabajo.\n"
								+ "detalles del error[ " + e.getMessage() + "]");
			}
		}
	}

	public void delete(OrdenTrabajo o) throws RuntimeException {
		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(o);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException(
						"Error: Imposible eliminar el ordenTrabajo.\n"
								+ "detalles del error[ " + e.getMessage() + "]");
			}
		}
	}

	public void update(OrdenTrabajo o) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(o);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException(
						"Error: Imposible actualizar la ordenTrabajo.\n"
								+ "detalles del error[ " + e.getMessage() + "]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public OrdenTrabajo select(Integer numero) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<OrdenTrabajo> ordenes = new ArrayList<OrdenTrabajo>();
		try {

			tx = session.beginTransaction();
			ordenes = session.createCriteria(OrdenTrabajo.class)
					.add(Restrictions.eq("numero", numero)).list();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException(
						"Error: Imposible seleccionar la ordenTrabajo.\n"
								+ "detalles del error[ " + e.getMessage() + "]");
			}
		}
		if (ordenes.size() > 0) {
			return ordenes.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<OrdenTrabajo> selectAll() throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<OrdenTrabajo> ordenes = new ArrayList<OrdenTrabajo>();
		try {

			tx = session.beginTransaction();
			ordenes = session.createCriteria(OrdenTrabajo.class).list();
			/*
			 * Recorro la lista de tareas para que el lazy de hibernate traiga
			 * las tareas de la orden de trabajo
			 */
			for (OrdenTrabajo ordenTrabajo : ordenes) {
				for (Tarea t : ordenTrabajo.getListaTareas()) {
					t.getEstado();
				}
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
				throw new RuntimeException(
						"Error: Imposible seleccionar las ordenes de Trabajo.\n"
								+ "detalles del error[ " + e.getMessage() + "]");
			}
		}
		return ordenes;
	}

	@SuppressWarnings("unchecked")
	public List<OrdenTrabajo> selectToReport(String cliente, String vehiculo,
			String estado) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		List<OrdenTrabajo> ordenes = new ArrayList<OrdenTrabajo>();
		try {

			tx = session.beginTransaction();

			Criteria baseCriteria = session.createCriteria(OrdenTrabajo.class);

			// Filtro de estado
			if (estado != null && !estado.isEmpty() && !estado.equals("Todos los estados")) {
				baseCriteria.add(Restrictions.eq("estado",EstadoOrdenTrabajo.fromCode(estado)));
			}

			Criteria presupuestoCriteria = baseCriteria.createCriteria("presupuesto");

			// Filtro de cliente
			if (cliente != null && !cliente.isEmpty()) {
				Criteria clienteCriteria = presupuestoCriteria.createCriteria("cliente");
				clienteCriteria.add(Restrictions.like("nombre", "%"+cliente+"%"));
			}

			// Filtro de vehiculo
			if (vehiculo != null && !vehiculo.isEmpty()) {
				Criteria vehiculoCriteria = presupuestoCriteria.createCriteria("vehiculo");
				vehiculoCriteria.add(Restrictions.like("patente", "%"+vehiculo+"%"));
			}
			
			ordenes = (List<OrdenTrabajo>) baseCriteria.list();

			/*
			 * Recorro la lista de tareas para que el lazy de hibernate traiga
			 * las tareas de la orden de trabajo
			 */
			for (OrdenTrabajo ordenTrabajo : ordenes) {
				Presupuesto p = ordenTrabajo.getPresupuesto();
				p.getCliente();
				p.getVehiculo();
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
				throw new RuntimeException(
						"Error: Imposible seleccionar las ordenes de Trabajo para el reporte.\n"
								+ "detalles del error[ " + e.getMessage() + "]");
			}
		}
		return ordenes;
	}

}
