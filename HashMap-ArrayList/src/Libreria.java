import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Libreria {

    //Atributos
    Map<String, ArrayList<Libro>> mapaLibreria;

    //Métodos constructores
    public Libreria() {
        this.mapaLibreria = new HashMap<>();
    }

    //Otros métodos
    public Libro pedirDatosLibro(Scanner scanner) {

        // Variables locales
        SimpleDateFormat convertirCadenaEnDate = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        boolean formatoValido = false;
        boolean precioValido = false;
        String tituloLibro, autorLibro;
        Double precioLibro = null;

        // Verificación del título del libro
        System.out.println("Ingrese el titulo del Libro: ");
        tituloLibro = scanner.nextLine().trim();

        while (tituloLibro.isEmpty()) {
            System.out.println("El título no puede estar vacío. Ingrese nuevamente el titulo del Libro: ");
            tituloLibro = scanner.nextLine().trim();
        }
        System.out.println("\n");

        // Verificación del autor de libro
        System.out.println("Ingrese el autor del Libro: ");
        autorLibro = scanner.nextLine().trim();

        while (autorLibro.isEmpty()) {
            System.out.println("El nombre del autor no puede estar vacío. Ingrese nuevamente el autor del Libro: ");
            autorLibro = scanner.nextLine().trim();
        }
        System.out.println("\n");

        // Verificación de la fecha de publicación del libro
        do {
            System.out.println("Ingrese la fecha de publicación del Libro en formato dd/MM/yyyy: ");
            String fechaStr = scanner.nextLine();

            try {
                fecha = convertirCadenaEnDate.parse(fechaStr);
                formatoValido = true;
            } catch (Exception e) {
                System.out.println("\nFormato de fecha incorrecto. Por favor, ingrese la fecha en formato dd/MM/yyyy\n");
            }
        } while (!formatoValido);
        System.out.println("\n");

        // Verificación del precio del libro
        do {
            System.out.println("Ingrese el precio del Libro: ");
            String precioLibroStr = scanner.nextLine();

            try {
                precioLibro = Double.parseDouble(precioLibroStr);
                if (precioLibro > 0) precioValido = true;
                else System.out.println("El precio debe ser mayor que 0. Por favor, ingrese un precio válido.");
            } catch (NumberFormatException e) {
                System.out.println("Formato de precio incorrecto. Ingrese nuevamente un valor numérico:");
            }
        } while(!precioValido);
        System.out.println("\n");

        Libro libroBuffer = new Libro(tituloLibro, autorLibro, fecha, precioLibro);
        return libroBuffer;
    }

    public void agregarLibro(Libro nuevoLibro) {

        ArrayList<Libro> arrayListBuffer = new ArrayList<>();

        if(mapaLibreria.containsKey(nuevoLibro.getAutorLibro())) arrayListBuffer = this.mapaLibreria.get(nuevoLibro.getAutorLibro());

        arrayListBuffer.add(nuevoLibro);
        mapaLibreria.put(nuevoLibro.getAutorLibro(), arrayListBuffer);
    }

    public void agregarLibros(Scanner scanner) {

        int flag = 0;
        do {
            System.out.println("\nBienvenido al sistema de agregación de Libros\n");
            agregarLibro(pedirDatosLibro(scanner));

            System.out.println("Pulse '0' para agregar otro libro o pulse '1' para salir");
            flag = scanner.nextInt();
            scanner.nextLine();
        }while(flag == 0);
    }

    public Libro buscarLibro(String autorLibro, String tituloLibro) {

        ArrayList<Libro> arrayListBuffer = this.mapaLibreria.get(autorLibro);

        if(arrayListBuffer != null) {
            for(Libro libroBuffer : arrayListBuffer) {
                if(libroBuffer.getTituloLibro().equals(tituloLibro)) return libroBuffer;
            }
        }
        return null;
    }

    public ArrayList<Libro> buscarLibroTodasLasCoincidencias(String tituloLibro) {

        ArrayList<Libro> arrayListResultados = new ArrayList<>();

        for(ArrayList<Libro> arrayListBuffer : this.mapaLibreria.values()) {

           for(Libro libroBuffer : arrayListBuffer) {

               if(libroBuffer.getTituloLibro().equals(tituloLibro)) arrayListResultados.add(libroBuffer);
           }
        }
        return arrayListResultados;
    }

    public boolean eliminarLibro(String tituloLibro, String autorLibro) {

        ArrayList<Libro> arrayListBuffer = this.mapaLibreria.get(autorLibro);

        Iterator<Libro> iteratorArrayList = arrayListBuffer.iterator();

        while(iteratorArrayList.hasNext()) { //Mientras tenga elementos por recorrer

            Libro libroBuffer = iteratorArrayList.next();

            if (libroBuffer.getTituloLibro().equals(tituloLibro)) {
                iteratorArrayList.remove();
                mapaLibreria.put(autorLibro, arrayListBuffer);
                return true;
            }
        }
        return false;
    }

    public void mostrarLibreria() {

        for(Map.Entry<String, ArrayList<Libro>> entry : mapaLibreria.entrySet()) {

            System.out.println("\nAutor: " + entry.getKey() + "\n" + "Libros: " + "\n");
            for(Libro libroBuffer : mapaLibreria.get(entry.getKey())) System.out.println(libroBuffer.toString());
        }
    }

    public void mostrarLibro(Libro libroBuffer) {

        System.out.println("\n" + libroBuffer.toString());
    }
    //Revisar este método
    public boolean actualizarPrecioLibro( String autorLibro, String tituloLibro, Double precioLibro) {

        ArrayList<Libro> arrayListBuffer = this.mapaLibreria.get(autorLibro);

        for(Libro librosBuffer : arrayListBuffer) {

            if(librosBuffer.getTituloLibro().equals(tituloLibro)) {
                librosBuffer.setPrecioLibro(precioLibro);
                mapaLibreria.put(autorLibro, arrayListBuffer);
                return true;
            }
        }
        return false;
    }

    public Double calcularPrecioTotalLibreria() {

        Double totalLibreria = 0.0;

        for(ArrayList<Libro> arrayListBuffer : mapaLibreria.values()) {

            for(Libro libroBuffer : arrayListBuffer) totalLibreria += libroBuffer.getPrecioLibro();
        }
        return totalLibreria;
    }

    public int calcularTotalLibros() {

        int totalLibros = 0;

        for(ArrayList<Libro> arrayListBuffer : mapaLibreria.values()) totalLibros += arrayListBuffer.size();

        return totalLibros;
    }

    public Libro encontrarLibroMasCaro() {

        Libro libroMasCaro = null;
        Double mayorPrecio = Double.MIN_VALUE;

        for(ArrayList<Libro> arrayListBuffer : mapaLibreria.values()) {

            for(Libro libroBuffer : arrayListBuffer) {

                if(libroBuffer.getPrecioLibro() > mayorPrecio) {
                    libroMasCaro = libroBuffer;
                    mayorPrecio = libroBuffer.getPrecioLibro();
                }
            }
        }
        return libroMasCaro;
    }

    public Libro encontrarLibroMasBarato() {

        Libro libroMasBarato = null;
        Double menorPrecio = Double.MAX_VALUE;

        for(ArrayList<Libro> arrayListBuffer : mapaLibreria.values()) {

            for(Libro libroBuffer : arrayListBuffer) {
                if(libroBuffer.getPrecioLibro() < menorPrecio) {
                    libroMasBarato = libroBuffer;
                    menorPrecio = libroBuffer.getPrecioLibro();
                }
            }
        }
        return libroMasBarato;
    }

    public void testeoLibreria(Scanner scanner) {
        SimpleDateFormat convertirCadenaEnDate = new SimpleDateFormat("dd/MM/yyyy");
        String fechaDefault = "29/04/2002";
        Date fecha = null;
        try {
            fecha = convertirCadenaEnDate.parse(fechaDefault);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Libro libro1 = new Libro("El poder del metabolismo", "Frank Suarez", fecha, (double)50000);
        Libro libro2 = new Libro("El poder del metabolismo", "Rocio Ortiz", fecha, (double)100000);
        Libro libro3 = new Libro("Metabolismo ultrapoderoso", "Frank Suarez", fecha, (double)26000);
        Libro libro4 = new Libro("Las 48 leyes del poder", "Robert Greene", fecha, (double)20500);
        Libro libro5 = new Libro("Harry Potter y la piedra filosofal", "J.K Rowling", fecha, (double)20500);
        Libro libro6 = new Libro("Harry Potter y la cámara de los secretos", "J.K Rowling", fecha, (double)20500);
        Libro libro7 = new Libro("Mi historia", "Rocio Ortiz", fecha, (double)32400);

        agregarLibro(libro1);
        agregarLibro(libro2);
        agregarLibro(libro3);
        agregarLibro(libro4);
        agregarLibro(libro5);
        agregarLibro(libro6);
        agregarLibro(libro7);

        mostrarLibreria();

        Libro libroBuscado = buscarLibro("Pepa Pig", "Pepito123");
        if(libroBuscado != null) System.out.println("Libro encontrado");
        else System.out.println("Libro no encontrado");

        ArrayList<Libro> conincidenciasBusquedaPorTitulo = buscarLibroTodasLasCoincidencias("El poder del metabolismo");
        System.out.println("\nMostrando los libros con el título igual\n");
        for(Libro librosBuffer : conincidenciasBusquedaPorTitulo) System.out.println(librosBuffer.toString());

        boolean isEliminado = eliminarLibro("Mi historia", "Rocio Ortiz");
        if(isEliminado) System.out.println("Libro eliminado con éxito");
        else System.out.println("Libro no encontrado");
        mostrarLibreria();

        boolean isPrecioModificado = actualizarPrecioLibro("Rocio Ortiz", "El poder del metabolismo", (double)150500);
        if(isPrecioModificado) System.out.println("Precio del libro " + "El poder del metabolismo" + " actualizado con éxito");
        else System.out.println("Libro no encontrado");
        mostrarLibreria();

        Double precioTotalLibreria = calcularPrecioTotalLibreria();
        System.out.println("El precio total: " + precioTotalLibreria);

        int stockLibros = calcularTotalLibros();
        System.out.println("El stock de libros: " + stockLibros);

        Libro libroMasBarato = encontrarLibroMasBarato();
        Libro libroMasCaro = encontrarLibroMasCaro();

        System.out.println("El libro más barato es: " + "\n" + libroMasBarato.toString() + "\n");
        System.out.println("El libro más caro es: " + "\n" + libroMasCaro.toString() + "\n");

        mostrarLibreria();
    }
}

