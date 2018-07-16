package material.tree.got;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import material.tree.*;

import material.tree.iterator.PreOrderIterator;




public class GameOfThrones {
	
	
	
	private LinkedTree<FamilyMember> gotTree;
	private HashMap<String,FamilyMember> integrantes;
	private HashMap<String, Position<FamilyMember>> posTree;
	
	

	/**
	 * Inicializo el arbol con el nodo raiz, inicializo la lista donde guardo las personas que se guardaran en el arbol, 
	 * inicializo el hashMap aqui guardare las posiciones de los nodos que se han creado, para evitar recorrer el arbol para crear un nuevo nodo
	 */
	public GameOfThrones() {
		
		gotTree = new LinkedTree<>();
		integrantes = new HashMap<>();
		posTree = new HashMap<>();
		FamilyMember root = new FamilyMember("ROOT","ROOT","ROOT","ROOT",0);
		gotTree.addRoot(root);
		
	}
	
	
	/*
	 * Getters and Setters
	 */
	
	
	public LinkedTree<FamilyMember> getGotTree(){
		
		
		return gotTree;
	}
	
	/**
	 * Cargar de un fichero de texto los árboles genealógicos de la familia en el formato
	 * descrito al final de este documento. El prototipo de este método será:
	 *  
	 */
	public void loadFile(String pathToFile){
	       
		
	       BufferedReader br = null;
	       int cont = 0;
	       String linea = "", idSon = null, idParent = null;
	      // (.+)=\s([a-zA-Z]+)\s([a-zA-Z]+)\s(?:[(])([M|F])(?:[)])\s(\d+)
	       String expRegular = "(.+)=\\s([a-zA-Z]+)\\s([a-zA-Z]+)\\s(?:[(])([M|F])(?:[)])\\s(\\d+)";
	       //String expRegular2="([A-Z]+)(?=\n)";
	       String expRegular3 = "([\\w]+)\\s->\\s([\\w]+)";
	       Pattern patron1= Pattern.compile(expRegular);
	       // Pattern patron2= Pattern.compile(expRegular2);
	       Pattern patron3= Pattern.compile(expRegular3);
	       Matcher match1 = null, match3 = null;
	       FamilyMember p = null, hijo = null;
	       Position<FamilyMember> newNode = null, posParent = null;
	       
	       try {
	    	   
	    	 br = new BufferedReader (new FileReader(pathToFile));
	    	 
		    	 while((linea = br.readLine())!= null) {
		    		  
		    		 
		    		 
		    		 /*Formateamos la expresion regular con el patron compilado anteriormente y emparejamos, para ello utilizamos la 
		    		  * clase matcher seguidamente buscamos las cadenas con el metodo find(), construimos la clase persona
		    		  * y rellenamos la cola
		    		  */
		    		 
		    		  match1 = patron1.matcher(linea);
		    		//  match2 = patron2.matcher(linea);
		    		  match3 = patron3.matcher(linea);
		    		  
		    	       if(match1.find()) {
		    	    	    int edad = Integer.parseInt(match1.group(5));
			    	        p = new FamilyMember(match1.group(1).trim(),match1.group(2).trim(),match1.group(3).trim(),
			    	        		match1.group(4).trim(),edad);  
			    	        
			    	        integrantes.put(match1.group(1).trim(), p);
			    	       
		    	       }
		    	       // Iniciamos la construccion del arbol con el cabeza de cada familia
		    	        
		    	      else if (cont <= 7){
		    	    	
		    	    	   cont++;
		    	    	 
			    	    	
		    	    	    if (cont > 2) {
		    	    	    	  
				    	    	   idSon = linea.substring(0, 2);
			    	    	       hijo =   integrantes.get(idSon);
			    	    	       newNode = gotTree.add(hijo, gotTree.root());
			    	    	       posTree.put(idSon, newNode);
			    	    	      
		    	    	    }
		   
		    	     // Construimos el arbol con el resto de nodos
		    	    }else if(match3.find()) {
		    	  
		    	           // " padre" -> "hijo "
		    	    	   idParent = match3.group(1);
		    	    	   idSon = match3.group(2);
		    	    	  
		    	    	   //Buscamos al hijo(integrante) en la lista de integrantes
		    	    	   
		    	    	   hijo =  integrantes.get(idSon) ;
		    	    	   
		    	    	
		    	    	   /*Sacamos la posicion que ocupa el padre en el arbol y creamos el nuevo nodo indicando la posicion  
		    	    	   del padre y el nuevo integrante*/
		    	    	   
		    	    	   posParent = posTree.get(idParent);
		    	    	   newNode = gotTree.add(hijo, posParent);
		    	    	   
		    	    	   //Añadimos la nueva posicion a nuestro mapa de posiciones junto con el nuevo nodo
		    	    	   posTree.put(idSon, newNode);
		    	    	    
		    	              
		    	       }
		    		 
		    	      }
		    	      
		    	 
	    	   
	       }catch(Exception e) {
	    	   
	    	   System.err.println(e.getMessage());
	       }finally {
	    	   
	    	   try {
	    		   br.close();
	    	   }catch(Exception e2) {
	    		   System.err.println(e2.getMessage());
	    	   }
	    	   
	       }
	       
	       
	       
	  
	      
	}

	
	
	
	
/*
 * Consultar los integrantes de una familia. Recibe un apellido de la familia a
 * visualizar y devuelve el árbol genealógico correspondiente. El prototipo de este
 * método será:
 */
	
	public LinkedTree<FamilyMember> getFamily(String surname){
		
		LinkedTree<FamilyMember> aux = new LinkedTree<>();
	    Position<FamilyMember>orig = null;
		
	
		//Buscamos al cabeza de familia 	
		for(Position<FamilyMember> p: gotTree.children(gotTree.root())) {
			
			if(p.getElement().getApellido().equalsIgnoreCase(surname) ) {
				aux.addRoot(p.getElement());
				orig = p;
			}
			
		}
		
		if(orig != null) {
			auxGetFamily(aux,orig,aux.root());
		}
		//Llamada al metodo recursivo envio el árbol donde copiar, el cabeza de familia(orig) y el nodo root de aux
		
		
		
		
		return aux;
	
		}
	


	
	
	
		
/**
 * @param aux contiene el árbol correspondiente al apellido solicitado
 * @param orig contiene el la pos del cabeza de familia del árbol original
 * @param copy  contine la posición del puntero padre para crear un nuevo elemento
 */
 
  private void auxGetFamily(LinkedTree<FamilyMember> aux, Position<FamilyMember>orig,Position<FamilyMember>copy ) {
	  
		
		for(Position<FamilyMember> p: gotTree.children(orig)) {
			//Si el nodo a copiar tiene hijo devuelvo el nuevo nodo(copy) en la llamada recursiva para saber cual es el padre
			    if(gotTree.isInternal(p)) {
			    	
			       copy =aux.add(p.getElement(), copy);
					auxGetFamily(aux,p, copy);
					
		    //Si el nodo a copiar es hoja mantengo el nodo copy
			    }else {
			    	    aux.add(p.getElement(), copy);
						auxGetFamily(aux,p, copy);
			    	
			    }
			
			
	      }


  }
  
  
  
  
  
  
  /**
   * Consultar el heredero de una famila. Para ello, se proporciona un apellido y
   * devuelve el primer hijo varón. En caso de que no exista, se devolvería la mayor de
   * las hijas o el primer nieto barón (y así sucesivamente).
   */
  
  public  String findHeir(String surname) {
	 
	  //Llamamos al metodo getFamilia para obtener el árbol familiar
	  LinkedTree<FamilyMember>  aux = getFamily(surname);
	  FamilyMember sucesor = null;
	  //Creamos una lista donde guardaremos el orden de sucecion de los integrantes 
	  List<FamilyMember> ordenDeSucesionHombres = new ArrayList<>();
	  List<FamilyMember> ordenDeSucesionMujeres = new ArrayList<>();
	  
      //Recorremos en preOrder el árbol para que el primer nodo a guardar sea el nodo raiz
		PreOrderIterator<FamilyMember> it = new PreOrderIterator<>(aux);
			
			while(it.hasNext()) {

				Position<FamilyMember> p = it.next();
				
				//Separamos los sucesores por genero
				if(p.getElement().getGenero().equalsIgnoreCase("M")) {
					
					ordenDeSucesionHombres.add(p.getElement());
				}else {
					
					 ordenDeSucesionMujeres.add(p.getElement());
				}
	             
				
			}
			
			
			
			ordenDeSucesionHombres.sort(Collections.reverseOrder((FamilyMember a1,FamilyMember a2)-> a1.getEdad()-a2.getEdad()));
			ordenDeSucesionMujeres.sort(Collections.reverseOrder((FamilyMember a1,FamilyMember a2)-> a1.getEdad()-a2.getEdad()));
			
			
			 //Collections.sort(ordenDeSucesionHombres,Collections.reverseOrder() );
			 //Collections.sort(ordenDeSucesionMujeres,Collections.reverseOrder() );
			 
			if(!ordenDeSucesionHombres.isEmpty()) {
				//Buscamos el sucesor (hijo mayor) en este caso utilizamos el indice 1 ya que el primer puesto lo ocupa el rey
				sucesor = ordenDeSucesionHombres.get(1);
			
				//Si no tiene el primer hijo mayor o nietos devolvemos a la hija mayor
			}else {
				
				sucesor = ordenDeSucesionMujeres.get(0);
				
			}	
			
			return sucesor.getNombre()+ " "+ sucesor.getApellido();

}
  
  
  
  
  /*Puesto que se puede descubrir que una persona tiene el apellido que no le
   * corresponde (por ser un descendiente ilegítimo), se debe poder cambiar de
   * familia (por simplicidad, se mantiene el apellido original). Para ello, se debe
   * solicitar al usuario el nombre del personaje correspondiente y a qué familia
   * cambia, indicando el nuevo ascendiente. El cambio afectará a todos sus descendientes.
   */
  public void changeFamily(String memberName, String newParent)  {
	  Position<FamilyMember> orig = null;
	  Position<FamilyMember> dest = null;

			try {
				
			orig = findPeople(memberName);
			dest = findPeople(newParent);
			gotTree.moveSubtree(orig, dest);
						
			
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
	  
	}
  
  
  
  private Position<FamilyMember> findPeople(String nombre){
	  PreOrderIterator<FamilyMember> it = new PreOrderIterator<>(gotTree);
	  Position<FamilyMember> p = null;
	  boolean encontrado = false;
	  while(it.hasNext() && !encontrado) {
		  
		  p = it.next();
	
		  //Buscamos la pos del persona
			  if(nombre.equalsIgnoreCase(p.getElement().getNombre())) {
				
				encontrado = true;
			  }
		  
	  }
	  return p;
	  
	  
  }
  
  
  
  
  
  
  
  /**
   * Consultar parentesco. Determina si dos personajes pertencen a la misma familia.
   * Para ello se solicita al usuario los nombres de dos personajes y comprueba si
   * ambos son parientes o no.
   */
  
  public boolean areFamily(String name1, String name2) {
	  
	  Position<FamilyMember> personaje1= null;
	  Position<FamilyMember> personaje2 = null;
	  LinkedTree<FamilyMember>familia1 = null;
	  LinkedTree<FamilyMember>familia2 = null;
	  boolean sonFamilia = false;
	  personaje1 = findPeople(name1);
	  personaje2 = findPeople(name2);
	  
	  
	  String apellido1 =personaje1.getElement().getApellido();
	  String apellido2 =personaje2.getElement().getApellido();
	  

	
	  //Comprobamos que sean de la misma familia al tener el mismo apellido
	  if(apellido1.equalsIgnoreCase(apellido2)) {
		  
		  sonFamilia = true;
	
	//Si no tienen el mismo apellido  buscamos en el arbol genealogico de cada personaje para comprobar si son familia
	  }else {
		  
		  
		  //Como puede existir personajes que no tengas definido su árbol genealogico buscamos las dos familias
		  familia1 = getFamily(apellido1);//bran
		  familia2 = getFamily(apellido2);// jon
		
		
	   //Buscamos a los integrantes en cada familia
		  if(familia1 != null) {
		     return sonFamilia = (findPeople(familia1,personaje1)&&findPeople(familia1,personaje2))? true:false;
		     
		}
		  
		  if (familia2 != null) {
			  System.out.println(familia2.root().getElement().getApellido());
			 return sonFamilia = (findPeople(familia2,personaje1)&&findPeople(familia2,personaje2))? true:false;
		  }
	
			 
		  }
		  
	  return sonFamilia;
  }
  
  
  
  //
  private  boolean findPeople(LinkedTree<FamilyMember>familia, Position<FamilyMember> pBuscar){
	  PreOrderIterator<FamilyMember> it = new PreOrderIterator<>(familia);
	  Position<FamilyMember> p = null;
	  boolean encontrado = false;
	  while(it.hasNext() && !encontrado) {
		  
		  p = it.next();
	
		  //Comprobamos que la persona ha buscar tenga el mismo nombre, apellido y ID
			  if(pBuscar.getElement().getNombre().equalsIgnoreCase(p.getElement().getNombre()) 
					  && (pBuscar.getElement().getApellido().equalsIgnoreCase(p.getElement().getApellido()))
					  && (pBuscar.getElement().getID().equalsIgnoreCase(p.getElement().getID()))) {
				encontrado = true;
				
			  }
		  
	  }
	  return encontrado;
	  
	  
  }
  
 
  
  
}
