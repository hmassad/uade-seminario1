package controlador;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configuracion {

	private static Configuracion instancia = null;

	public static Configuracion getInstancia() {
		if (instancia == null) {
			instancia = new Configuracion();
		}
		return instancia;
	}

	public static class Persistencia {
		private String direccion;
		private int puerto;
		private String catalogo;
		private String usuario;
		private String contrasena;

		private Persistencia(String direccion, int puerto, String catalogo, String usuario, String contrasena) {
			this.direccion = direccion;
			this.puerto = puerto;
			this.catalogo = catalogo;
			this.usuario = usuario;
			this.contrasena = contrasena;
		}

		public String getDireccion() {
			return direccion;
		}

		public int getPuerto() {
			return puerto;
		}

		public String getCatalogo() {
			return catalogo;
		}

		public String getUsuario() {
			return usuario;
		}

		public String getContrasena() {
			return contrasena;
		}

		public static Persistencia parse(Element element) {
			String direccion = element.getElementsByTagName("direccion").item(0).getFirstChild().getNodeValue();
			int puerto = Integer.parseInt(element.getElementsByTagName("puerto").item(0).getFirstChild().getNodeValue());
			String catalogo = element.getElementsByTagName("catalogo").item(0).getFirstChild().getNodeValue();
			String usuario = element.getElementsByTagName("usuario").item(0).getFirstChild().getNodeValue();
			String contraseña = element.getElementsByTagName("contrasena").item(0).getFirstChild().getNodeValue();
			return new Persistencia(direccion, puerto, catalogo, usuario, contraseña);
		}
	}

	private Persistencia persistencia = null;

	private Configuracion() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse("configuracion.xml");
			Element docElement = dom.getDocumentElement();
			NodeList nl = docElement.getElementsByTagName("persistencia");
			if (nl != null && nl.getLength() == 1) {
				persistencia = Persistencia.parse((Element) nl.item(0));
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public Persistencia getPersistencia() {
		return persistencia;
	}
}
