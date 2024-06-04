package arbol;

import java.util.ArrayList;
import java.util.Scanner;

public class Arbol {
	/*
	 * Desarrollar una implementación en Java que cumpla con los siguientes métodos
	 * y Requerimientos:
	 * 
	 * Creación y construcción de un ABB.
	 * 
	 * Inserción de Nodos, después de que el Árbol ya se encuentre creado y
	 * construido.
	 * 
	 * Eliminación de un Nodo en un ABB.
	 * 
	 * Recorrido InOrden del ABB. Imprimir la secuencia del recorrido.
	 * 
	 * Destruir el ABB.
	 * 
	 */

	/*
	 * Atributos
	 */
	Nodo raiz = null;
	Scanner scan = new Scanner(System.in);
	int cont;
	int height;
	/*
	 * Metodos
	 */

	// Crea el arbol
	public Arbol() {
	}

	public void destruir() {
		raiz = null;
		cont = 0;
		height = 0;
		System.out.println("Arbol destruido.");
	}

	public void insercion() {
		// Se pregunta que dato se ingresara.
		System.out.println("INSERTE EL DATO QUE DESEA INGRESAR:");
		int dato = scan.nextInt();
		scan.nextLine();
		// Se guarda el dato en un nodo.
		Nodo n = new Nodo();
		n.dato = dato;
		// Si la raiz es nula este nodo se convertira en la raiz.
		if (raiz == null) {
			// Se actualizan los valores.
			raiz = n;
			raiz.nivel = 0;
			height = 0;
		} else if (!repit(raiz, n.dato)) {
			// Si el dato no esta en el arbol puede ser ingresado.
			free(raiz, n);
			System.out.println("Nodo insertado.");
		} else if (repit(raiz, n.dato)) {
			// En caso de que el dato ya este en el arbol se muestra un mensaje.
			System.out.println("Ese dato ya se encuentra en el arbol.");
			return;
		}
		// Aumentar el contador.
		cont++;
	}

	/**
	 * Insercion conociendo el dato. (Es para facilitar el hardcodeo.
	 * 
	 * @param d Dato a guardar en el nodo.
	 */
	public void insercion(int d) {
		Nodo n = new Nodo();
		n.dato = d;
		if (raiz == null) {
			raiz = n;
			raiz.nivel = 0;
			height = 0;
		} else if (!repit(raiz, n.dato)) {
			free(raiz, n);
		} else if (repit(raiz, n.dato)) {
			return;
		}
		cont++;
	}

	/**
	 * Metodo que se usa para eliminar un nodo.
	 */
	public void eliminar() {
		// Se ingresa el dato del nodo que se busca eliminar.
		System.out.println("INSERTE EL DATO QUE DESEA ELIMINAR:");
		int dato = scan.nextInt();
		scan.nextLine();
		Nodo n = new Nodo();
		n.dato = dato;
		// Si la raiz es nula no tiene nada ingresado el arbol.
		if (raiz == null) {
			System.out.println("No hay ningun dato en este arbol.");
			return;
		} else if (!repit(raiz, n.dato)) { // Si no esta repetido no se encuentra en el arbol.
			System.out.println("El dato ingresado no se encuentra en el arbol.");
			return;
		} else if (repit(raiz, n.dato)) { // Si esta repetido es que si se encuentra.
			// Se busca el nodo con el dato que buscamos.
			Nodo search = busqueda(raiz, n.dato);
			// Si el hijo izquierdo existe, este reemplazara al padre.
			if (search.izq != null) {
				// Creo un arbol temporal donde se reorganizara el arbol.
				Arbol x = new Arbol();
				// El nodo raiz sera el hijo izquierdo.
				x.raiz = search.izq;
				// El hijo derecho original se guardara en una variable para reorganizarlo.
				Nodo hijo_der = x.raiz.der;
				// El hijo derecho de la raiz sera el mismo que el del nodo que se eliminara.
				x.raiz.der = search.der;
				// Se le asigna el nodo raiz como padre a su hijo derecho.
				x.raiz.der.padre = x.raiz;
				// Se le busca un espacio al hijo derecho original y se guarda en el.
				free(x.raiz, hijo_der);
				// Si el nodo raiz es el hijo izquierdo se pone como tal.
				if (search == search.padre.izq) {
					search.padre.izq = x.raiz;
				} else if (search == search.padre.der) { // Si era el hijo derecho se le pine como tal.
					search.padre.der = x.raiz;
				}
				x.raiz.nivel = search.padre.nivel + 1; // Se actualiza el nivel del nodo.
				Nodo y = update(x.raiz.izq); // Se actualiza el nivel de todos los nodos hijos de su nodo izq.
				Nodo nivel = nivel(raiz, y.dato);
				// Se comprueba el nivel de los nodos para determinar si se tiene que modificar
				// la altura.
				if (y.nivel > nivel.nivel) {
					height = y.nivel;
				}
			} else {
				// En caso de que no exista el hijo izquierdo se asigna al derecho en la
				// posicion que ocupaba el nodo.
				if (search == search.padre.izq) {
					search.padre.izq = search.der;
				} else if (search == search.padre.der) { // Si era el hijo derecho se le pine como tal.
					search.padre.der = search.der;
				}
				search.der.padre = search.padre; // Se le asigna su nuevo padre.

				Nodo y = update(search.der); // Se actualiza el nivel de todos los nodos hijos de su nodo izq.
				Nodo nivel = nivel(raiz, y.dato);
				// Se comprueba el nivel de los nodos para determinar si se tiene que modificar
				// la altura.
				if (y.nivel > nivel.nivel) {
					height = y.nivel;
				}
			}
		}
		cont--; // El contador disminuye.
		System.out.println("Eliminado.");
	}

	/**
	 * Metodo usado para encontrar un nodo hoja diferente a otro.
	 * 
	 * @param n Nodo explorado
	 * @param y Dato del otro nodo.
	 * @return Devuelve el nodo hoja
	 */
	public Nodo nivel(Nodo n, int y) {
		if (n != null) {
			// Si el nivel del nodo es igual a la altura y su dato distinto al de "y".
			if (n.nivel == height && n.dato != y) {
				// Se retorna ese nodo.
				return n;
			}

			// De lo contrario se chequean los hijos izquierdos y derechos.
			Nodo izq = nivel(n.izq, y);
			Nodo der = nivel(n.der, y);
			// Si ninguno es nulo.
			if (izq != null && der != null) {
				// Se pregunta si el nivel de izq es igual a la altura. Si es asi se retorna
				// izq, de lo contrario se retorna der.
				return izq.nivel == height ? izq : der;
			} else if (izq == null && der != null) {
				// Si izq era null y der no se retorna der
				return der;
			} else if (izq != null) {
				// Si der era null e izq no se retorna izq.
				return izq;
			}

		}
		return n;
	}

	/**
	 * Metodo que se usa para actualizar el nivel de los nodos en el metodo eliminar
	 * 
	 * @param n Nodo.
	 * @return Devuelve el nodo hoja.
	 */
	public Nodo update(Nodo n) {
		if (n != null) {
			// Se decrementa el nivel del nodo.
			n.nivel--;
			// Si es una hoja sus hijos son nulos.
			if (n.izq == null && n.der == null) {
				return n;
			}
			// Se sigue con el lado izquierdo.
			Nodo izq = update(n.izq);
			// Se sigue con el lado derecho.
			Nodo der = update(n.der);
			// Verifica que no sean nulos para devolver la hoja de ese camino.
			if (izq != null && izq.izq == null && izq.der == null) {
				return izq;
			} else if (der != null && der.izq == null && der.der == null) {
				return der;
			}
		}
		return n;
	}

	/**
	 * Busca la hoja donde se pondra el nodo y lo agrega..
	 * 
	 * @param n Nodo en el que empieza la busqueda
	 * @param inicial Nodo que se busca introducir.
	 * @return Devuelve el nodo hoja disponible.
	 */

	public Nodo free(Nodo n, Nodo inicial) {
		// Se chequea que el nodo no este vacio.
		if (n != null) {
			// Si el dato del nodo es mayor al del inicial.
			if (n.dato > inicial.dato) {
				if (n.izq != null) {
					n = free(n.izq, inicial);
				}
			} else if (n.dato < inicial.dato) { // Si el dato del nodo es menor al del inicial.
				if (n.der != null) {
					n = free(n.der, inicial);
				}
			}
			if (n.izq == null || n.der == null) {
				// Ya con la hoja si el dato es mayor al del inicial.
				if (n.dato > inicial.dato) {
					// El hijo izquierdo de la hoja es el inicial.
					n.izq = inicial;
				} else if (n.dato < inicial.dato) {
					// El hijo derecho de la hoja es el inicial.
					n.der = inicial;
				}
				inicial.padre = n; // El padre del inicial es el nodo que encontramos.
				inicial.nivel = n.nivel + 1; // El nivel del inicial es 1 mayor al de su padre.
			}
			// Si la altura del arbol es menor al nivel del inicial se reemplaza su valor.
			if (height < inicial.nivel) {
				height = inicial.nivel;
			}
		}
		return n;
	}

	/**
	 * Metodo que busca si un dato ya se encuentra.
	 * 
	 * @param n Nodo usado.
	 * @param d Nodo dato.
	 * @return True si el dato ya estaba. False si no estaba.
	 */
	public boolean repit(Nodo n, int d) {
		// Si el nodo no es nulo.
		if (n != null) {
			// Si el dato del nodo es igual al ingresado.
			if (n.dato == d) {
				return true;
			}
			// Revisa el lado izquierdo de la rama.
			boolean iz = repit(n.izq, d);
			// Revisa el lado derecho.
			boolean der = repit(n.der, d);
			// Si alguna de las ramas contiene el dato devolvemos true.
			return iz || der;
		}
		return false;
	}

	public Nodo busqueda(Nodo n, int d) {
		// Si el nodo no es nulo.
		if (n != null) {
			// Si el dato del nodo es igual al ingresado.
			if (n.dato == d) {
				return n;
			}
			// Revisa el lado izquierdo de la rama.
			Nodo iz = busqueda(n.izq, d);
			// Revisa el lado derecho.
			Nodo der = busqueda(n.der, d);
			// Al usar el metodo busqueda sobre iz y der estos se pueden convertir en nulos
			// entonces toca verificar.
			if (iz != null && iz != n) {
				return iz;
			} else if (der != null && der != n) {
				return der;
			}
		}
		return null;
	}

	/**
	 * Metodo en donde se recorre el primer sub-árbol, luego se recorre la raíz y
	 * al final se recorre los demás sub-árboles
	 */
	public void inOrden(Nodo n) {
		if (n != null) {
			inOrden(n.izq); // InOrden por izquierda
			System.out.println(n.dato); // mostrar la raiz
			inOrden(n.der); // InOrden por derecha
		}
	}

	/**
	 * Metodo en que inicia en la Raíz y luego se recorre en cada unos
	 * de los sub-árboles de izquierda a derecha.
	 */
	public void preOrden(Nodo n) {
		if (n != null) {
			System.out.println(n.dato); // mostrar la raiz
			preOrden(n.izq); // PreOrden por izquierda
			preOrden(n.der); // PreOrden por derecha
		}
	}

	/**
	 * Metodo en que se recorre cada uno de los sub-árboles y al final
	 * se recorre la raíz.
	 */
	public void postOrden(Nodo n) {
		if (n != null) {
			postOrden(n.izq); // PostOrden por izquierda
			postOrden(n.der); // PostOrden por derecha
			System.out.println(n.dato); // mostrar la raiz
		}
	}

	/**
	 * Metodo para imprimir.
	 */
	public void imprimir() {
		// Se crea el texto en el que se guardara todo el arbol.
		String text = "";
		// Y un arraylist en el que se guardaran los espacios que se usaran como el
		// margen.
		ArrayList<String> lineas = new ArrayList<>();
		for (int i = height; i >= 0; i--) {
			lineas.add(" ");
		}
		// Se usa un ciclo for en el que se va recorriendo cada nivel del arbol con i.
		for (int i = 0; i <= height; i++) {
			// Se crea un Array secundario para poder manipularlo.
			ArrayList<String> line = new ArrayList<>(lineas);
			// El texto se actualiza por su nueva version.
			text = imprimir(i, text, raiz, line);
			// Se separa la siguiente linea.
			text += "\n";
			// Se elimina un espacion del margen.
			lineas.remove(0);
		}
		// Se imprime el texto.
		System.out.println(text);
	}

	/**
	 * Metodo que sirve para recorrer el arbol y ayudar a imprimir.
	 * 
	 * @param lvl    Nivel que se busco
	 * @param text   Texto que se va a imprimir.
	 * @param n      Nodo usado.
	 * @param lineas Lista con espacios a usar.
	 * @return Devuelve el string actualizado.
	 */
	public String imprimir(int lvl, String text, Nodo n, ArrayList<String> lineas) {
		// Se crea un string que juntara todos los espacios almacenados en el array.
		String s = "";
		// Se concatenan los espacios guardados.
		for (String x : lineas) {
			s += x;
		}
		// Si el nodo usado no es nulo pasa esto.
		if (n != null) {
			// Si el nivel del nodo coincide con el del nivel buscado se agregara al texto.
			if (n.nivel == lvl) {
				// Si el nivel es 0 tendra una sintaxis especial.
				if (n.nivel == 0) {
					text += s + "   " + n.dato;
				} else {
					if (n.nivel == 1) {
						// En el nivel 1 tambien hay una sintaxis especial para hacerlos coincidir
						// mejor.
						if (n.padre.der == n) {
							text += n.dato;
						} else if (n.padre.izq == n) {
							text += s + n.dato + s + "   ";
						}
					} else {
						// Para los demas niveles se dejo una sintaxis predeterminada.
						if (n.padre.der == n) {
							text += "  " + n.dato;
						} else if (n.padre.izq == n) {
							text += s + n.dato;
						}
					}
				}

			} else { // En caso de no coincidir el nivel del nodo con el buscado continua buscando.
				if (n.izq != null && n.der != null) {
					// Si el lado izq y el lado der no son nulos se chequean ambos.
					text = imprimir(lvl, text, n.izq, lineas);
					text = imprimir(lvl, text, n.der, lineas);
				} else if (n.izq != null) {
					// Si izq no es nulo solo se chequea ese lado.
					text = imprimir(lvl, text, n.izq, lineas);
					text += "  " + ".";
				} else if (n.der != null) {
					// Si der no es nulo solo se chequea ese lado.
					text += s + ".";
					text = imprimir(lvl, text, n.der, lineas);
				} else {
					// En caso de que ambos fueran nulos solo se ponen los espacios.
					text += s;
				}
			}
		} else {
			// Si el nodo es nulo se agrega un doble espacio.
			text += s + ".";
		}

		return text;
	}

	@Override
	public String toString() {
		return "Arbol{" +
				"raiz=" + raiz +
				", cont=" + cont +
				'}';
	}
}
