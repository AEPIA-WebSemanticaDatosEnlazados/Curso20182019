package App;

import java.io.InputStream;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

public class PaleoApp {
	public static void main (String[] args){
		String filename = "PaleoRegisters.rdf";
		
		// Creamos un modelo vacío
		Model model = ModelFactory.createDefaultModel();
		
		// Usamos FileManager para localizar el fichero de entrada
		InputStream in = FileManager.get().open(filename);
		
		if(in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
		
		// Leemos el fichero RDF/XML
		model.read(in, null);
		
		Property plsw_name = model.getProperty("http://paleosw.org/vocabulary#", "name");
		Property plsw_hasCounty = model.getProperty("http://paleosw.org/vocabulary#", "hasCounty");
		Property plsw_hasPlace = model.getProperty("http://paleosw.org/vocabulary#", "hasPlace");
		
		Resource cuenca = model.getResource("http://paleosw.org/resources/Cuenca");
		Resource species = model.getResource("http://paleosw.org/resources/species");
		
		System.out.println("\n--------------------------------------");
		System.out.println("Caso 1: Muestra los yacimientos donde se han encontrado fósiles del Turiasaurus riodevensis");
		System.out.println("--------------------------------------\n");
		
		// Buscamos aquellos fósiles con dicho nombre
		StmtIterator iter1 = model.listStatements(null, plsw_name, "Turiasaurus riodevensis");
		
		while (iter1.hasNext())
		{
			Statement s = iter1.next();
			Resource r = s.getSubject();
			
			// Buscamos yacimientos donde se han encontrado los fósiles
			StmtIterator iter2 = model.listStatements(r, plsw_hasPlace, (RDFNode) null);
			
			while(iter2.hasNext())
			{
				Statement st = iter2.next();
				RDFNode subj = st.getObject();
				
				// Imprimimos la información por pantalla
				System.out.println("Place -> "+subj.toString());
			}
		}	
		
		System.out.println("\n--------------------------------------");
		System.out.println("Caso 2: Muestra todos los nombres de los fósiles encontrados en la provincia de Cuenca");
		System.out.println("--------------------------------------\n");
		
		// Buscamos aquellos yacimientos que pertenezcan a la provincia de Cuenca
		iter1 = model.listStatements(null, plsw_hasCounty, cuenca);
		
		while (iter1.hasNext())
		{
			Statement s = iter1.next();
			Resource r = s.getSubject();
			
			// Buscamos los fósiles que se han encontrado en estos yacimientos
			StmtIterator iter2 = model.listStatements(null, plsw_hasPlace, r);
			
			while(iter2.hasNext())
			{
				Statement st = iter2.next();
				Resource subj = st.getSubject();
				
				// Obtenemos el nombre de la especie
				StmtIterator iter3 = model.listStatements(subj, plsw_name, (RDFNode) null);
				
				while(iter3.hasNext())
				{
					Statement sta = iter3.next();
					RDFNode node = sta.getObject();
					
					//Imprimimos la información por pantalla
					System.out.println(node.asLiteral()+" -> "+subj.getURI());
				}
			}
		}
		
		System.out.println("\n--------------------------------------");
		System.out.println("Caso 3: Muestra todos los nombres de los fósiles encontrados en la provincia de Huesca");
		System.out.println("        que hayan sido identificados como especie");
		System.out.println("--------------------------------------\n");
		
		// Definimos los recursos y propiedades que vamos a usar en la consulta
		Resource huesca = model.getResource("http://paleosw.org/resources/Huesca");
		Resource specie = model.getResource("http://paleosw.org/resources/species");
		Property plsw_hasTaxon = model.getProperty("http://paleosw.org/vocabulary#", "hasTaxon");
		
		// Buscamos aquellos yacimientos que pertenezcan a la provincia de Huesca
		iter1 = model.listStatements(null, plsw_hasCounty, huesca);
		
		while (iter1.hasNext())
		{
			Statement s = iter1.next();
			Resource r = s.getSubject();
			
			// Buscamos los fósiles que se han encontrado en estos yacimientos
			StmtIterator iter2 = model.listStatements(null, plsw_hasPlace, r);
			
			while(iter2.hasNext())
			{
				Statement st = iter2.next();
				Resource subj = st.getSubject();
				
				// Nos quedamos con el taxón de cada fósil
				StmtIterator iter3 = model.listStatements(subj, plsw_hasTaxon, (RDFNode)null);
				
				while(iter3.hasNext())
				{
					Statement sta = iter3.next();
					Resource node = (Resource) sta.getObject();
					
					// Filtra por aquellos fósiles que estén identificados como especie
					if (node.getURI().equals(specie.toString()))
					{
						// Obtenemos el nombre de la especie
						StmtIterator iter4 = model.listStatements(subj, plsw_name, (RDFNode) null);
						
						while(iter4.hasNext())
						{
							Statement stat = iter4.next();
							RDFNode nam = stat.getObject();
							
							// Imprimimos la información por pantalla
							System.out.println(node.getURI().substring(29)+" -> "+nam.asLiteral()+" -> "+subj.getURI());
						}
					}
				}
			}
		}
		
		System.out.println("\n--------------------------------------");
		System.out.println("Caso 4: Muestra la provincia, latitud y longitud donde se han encontrado terópodos (Theropoda)");
		System.out.println("--------------------------------------\n");
		
		Property latitude = model.getProperty("http://schema.org/", "latitude");
		Property longitude = model.getProperty("http://schema.org/", "longitude");
		
		// Buscamos los fósiles que tengan de nombre "Theropoda"
		iter1 = model.listStatements(null, plsw_name, "Theropoda");
		
		while (iter1.hasNext())
		{
			Statement s = iter1.next();
			Resource r = s.getSubject();
			
			// Buscamos los fósiles que se han encontrado en estos yacimientos
			StmtIterator iter2 = model.listStatements(r, plsw_hasPlace, (RDFNode) null);
			
			while(iter2.hasNext())
			{
				Statement st = iter2.next();
				Resource subj = (Resource) st.getObject();
				
				//Buscamos las provincias a la que pertenecen los yacimientos
				StmtIterator iter3 = model.listStatements(subj, plsw_hasCounty, (RDFNode) null);
				
				while(iter3.hasNext())
				{
					Statement stat = iter3.next();
					Resource county = (Resource) stat.getObject();					
					
					// Buscamos la latitud del yacimiento
					StmtIterator iter4 = model.listStatements(subj, latitude, (RDFNode) null);
					
					// Las coordenadas pueden no haber sido introducidas, por lo que las inicializamos antes
					RDFNode lat = null;
					RDFNode lon = null;
					
					while(iter4.hasNext())
					{
						Statement state = iter4.next();
						lat = state.getObject();
						
						// Buscamos la longitud del yacimiento
						StmtIterator iter5 = model.listStatements(subj, longitude, (RDFNode) null);
						
						while(iter5.hasNext())
						{
							Statement statt = iter5.next();
							lon = statt.getObject();
						}
					}
					
					// Tratamos las strings y las mostramos por pantalla
					String lati = lat.asLiteral().toString().substring(0,6);
					String longi = lon.asLiteral().toString().substring(0,6);
					System.out.println(county.getURI().substring(29)+" || Longitude -> "+longi+" Latitude -> "+lati+" || URI -> "+subj.getURI());
					
					
				}
			}
		}			
		
	}

}
