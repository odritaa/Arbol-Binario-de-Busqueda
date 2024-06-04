package arbol;

import java.util.Scanner;

public class Menu {

    public static void menu() {
        Arbol arbol = new Arbol();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\nBIENVENIDOS AL MENU"
                    + "\nIngrese la opcion que desea:"
                    + "\n1. Insertar nodo"
                    + "\n2. Mostrar InOrden"
                    + "\n3. Mostrar PreOrden"
                    + "\n4. Mostrar PostOrden"
                    + "\n5. Buscar nodo"
                    + "\n6. Eliminar nodo"
                    + "\n7. Imprimir arbol por niveles"
                    + "\n8. Destruir arbol."
                    + "\n9. Salir"
                    + "\nSelecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    arbol.insercion();
                    break;

                case 2:
                    System.out.println("Recorrido InOrden:");
                    arbol.inOrden(arbol.raiz);
                    break;

                case 3:
                    System.out.println("Recorrido PreOrden:");
                    arbol.preOrden(arbol.raiz);
                    break;

                case 4:
                    System.out.println("Recorrido PostOrden:");
                    arbol.postOrden(arbol.raiz);
                    break;

                case 5:
                    System.out.print("Ingresa el dato a buscar: ");
                    int datoBuscado = scanner.nextInt();
                    scanner.nextLine();
                    Nodo nodoEncontrado = arbol.busqueda(arbol.raiz, datoBuscado);
                    if (nodoEncontrado != null) {
                        System.out.println("Nodo encontrado: " + nodoEncontrado.dato);
                    } else {
                        System.out.println("Nodo no encontrado.");
                    }
                    break;

                case 6:
                    arbol.eliminar();
                    break;

                case 7:
                    System.out.println("Este es su arbol por niveles:");
                    arbol.imprimir();
                    break;

                case 8:
                    arbol.destruir();
                    break;
                case 9:
                    salir = true;
                    break;

                default:
                    System.out.println("Nos vemos prontos!!!");
                    break;

            }
            if (!salir) {
                System.out.println("Presione ENTER para continuar.");
                scanner.nextLine();
            }

        }

    }

}
