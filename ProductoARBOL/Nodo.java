package arbol;

public class Nodo {
	int dato; // Dato a guardar.
	Nodo izq; // Nodo hijo izquierdo
	Nodo der; // Nodo hijo derecho
	Nodo padre; // Nodo padre
	int nivel; // Nivel de los nodos.

	/**
	 * Instanciar un nodo sin valores.
	 */
	public Nodo() {
	}

	/**
	 * Instancia un nodo sabiendo el dato.
	 * 
	 * @param d Dato a ingresar en un nodo.
	 */
	public Nodo(int d) {
		this.dato = d;
		this.izq = null;
		this.der = null;
		this.padre = null;
		nivel = -1;
	}
}
