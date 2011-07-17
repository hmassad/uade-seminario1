package controlador;

import junit.framework.TestCase;
import modelo.Cliente;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import persistencia.ClienteDAO;
import persistencia.hibernate.SessionFactoryUtil;


/**
 * Test para probar la configuracion y mapeo de hibernate
 * 
 * @author Pablo Lamas
 *
 */
public class TestDeModelo extends TestCase {

	private Cliente cliente1;
	
	private Cliente cliente2;

	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.cliente1 = new Cliente("Pedro Coria","pcoria@gmail.com",4296351,1123097832,"Rocha 111");
		this.cliente2 = new Cliente("Cacho Castania","ccastania@gmail.com",4296351,1189734498,"Mariano Moreno 111");
	  }

	  public void testCrearCliente() {
		  Transaction tx = null;
		  Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		  try {
			  tx = session.beginTransaction();
			  session.save(this.cliente1);
			  session.save(this.cliente2);
			  tx.commit();
		  } catch (RuntimeException e) {
			  if (tx != null && tx.isActive()) {
				  try {
					  // Second try catch as the rollback could fail as well
					  tx.rollback();
				  } catch (HibernateException e1) {
					  System.out.println("Error rolling back transaction");
				  }
				  throw e;
			  }
		  }
		  TestCase.assertEquals(ClienteDAO.getInstancia().selectAll().size(),2);
	  }

	  public void testDeleteCliente() {
	    Transaction tx = null;
	    Session session = SessionFactoryUtil.getInstance().getCurrentSession();
	    try {
	      tx = session.beginTransaction();
	      session.delete(this.cliente1);
	      session.delete(this.cliente2);
	      tx.commit();
	    } catch (RuntimeException e) {
	      if (tx != null && tx.isActive()) {
	        try {
	        // Second try catch as the rollback could fail as well
	          tx.rollback();
	        } catch (HibernateException e1) {
	        	System.out.println("Error rolling back transaction");
	        }
	        throw e;
	      }
	    }
	    TestCase.assertEquals(ClienteDAO.getInstancia().selectAll().size(),0);
	  }
	
}
