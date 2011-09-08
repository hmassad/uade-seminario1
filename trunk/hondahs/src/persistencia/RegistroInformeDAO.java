package persistencia;

import java.util.ArrayList;
import java.util.List;

import modelo.Informe;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistencia.hibernate.SessionFactoryUtil;
import vista.tablesModel.RegistroInforme;

public class RegistroInformeDAO {
	
	private static RegistroInformeDAO instancia;
	final static Logger logger = LoggerFactory.getLogger(RegistroInformeDAO.class);

	public static RegistroInformeDAO getInstancia() {
		if (instancia == null)
			instancia = new RegistroInformeDAO();
		return instancia;
	}

	public List<RegistroInforme> selectAll(String cliente, String vehiculo,
			String estado, String fechaInicio, String fechaFin) throws RuntimeException {

		Transaction tx = null;
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		
		List<RegistroInforme> registros = new ArrayList<RegistroInforme>();
		
		try {
			
			tx = session.beginTransaction();
			registros =(List<RegistroInforme>)session.createQuery(
					"SELECT ot.numero,c.nombre,v.patente,ot.fechaInicio,ot.fechaFin,ot.estado"
					+"FROM ordentrabajo ot"
					+"inner join presupuesto p on p.numero = ot.idPresupuesto"
					+"inner join cliente c on c.numero = p.idCliente"
					+"inner join vehiculo v on v.numero = p.idVehiculo").list();
			
			tx.commit();
			
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				throw new RuntimeException("Error: Imposible crear el informe pedido.\n" +
						"detalles del error[ "+e.getMessage()+"]");
			}
		}	
		return registros;
	}
	
}
