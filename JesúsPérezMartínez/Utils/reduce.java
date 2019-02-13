package JenaEjercicios;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

public class Ejercicio8 {
	
	// EJERCICIO 1: LEER Y ESCRIBIR FICHEROS EN RDF
	
	public static void main (String[] args){
		String filename = "PaleoRegisters-RDF.rdf";
		
		//Creamos un modelo vacio
		Model model = ModelFactory.createDefaultModel();
		
		//Usamos FileManager para localizar el fichero de entrada
		InputStream in = FileManager.get().open(filename);
		
		if (in == null)
			throw new IllegalArgumentException("Fichero: "+filename+" no encontrado");
		
		model.read(in, null);
		OutputStream out;
		
		try {
			out = new FileOutputStream("PaleoRegisters.ttl");
			model.write(out, "TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			out = new FileOutputStream("PaleoRegisters.rdf");
			model.write(out, "RDF/XML-ABBREV");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Leemos el fichero RDF/XML
		
		
		//Escribimos por pantalla
		 
		
		// ** TAREA 1.1: Escribir el modelo en formato Turtle **
		//System.out.println("\n Formato turtle:\n");
		//model.write(System.out, "TURTLE");
		
		// Otros formatos
				//System.out.println("\n Formato JSON-LD:\n");
				//model.write(System.out, "JSON-LD");
				
	}
}
