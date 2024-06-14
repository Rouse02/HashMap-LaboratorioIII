import java.util.Date;
import java.util.Objects;
/**------------------------------------------------------------------------------------------------------------------**/
public class Libro {

    //Atributos
    private String tituloLibro;
    private String autorLibro;
    private Date añoPublicacion;
    private Double precioLibro;

    //Métodos constructores
    public Libro(String tituloLibro, String autorLibro, Date añoPublicacion, Double precioLibro) {
        this.tituloLibro = tituloLibro;
        this.añoPublicacion = añoPublicacion;
        this.precioLibro = precioLibro;
        this.autorLibro = autorLibro;
    }

    public Libro() {
        this.tituloLibro = " ";
        this.autorLibro = " ";
        this.añoPublicacion = new Date();
        this.precioLibro = 0.0;

    }

    //Métodos getter y setter
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

    public Double getPrecioLibro() { return precioLibro; }
    public void setPrecioLibro(Double precioLibro) { this.precioLibro = precioLibro; }

    public Date getAñoPublicacion() { return añoPublicacion; }
    public void setAñoPublicacion(Date añoPublicacion) { this.añoPublicacion = añoPublicacion; }

    public String getAutorLibro() { return autorLibro; }
    public void setAutorLibro(String autorLibro) { this.autorLibro = autorLibro; }

    //Otros métodos
    @Override
    public String toString() {
        return  "Titulo: " + tituloLibro + "\n" +
                "Autor: " + autorLibro + "\n" +
                "Año de publicación: " + añoPublicacion + "\n" +
                "Precio: $" + precioLibro + "\n" +
                "-----------------------------------------------------------------";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Libro libro = (Libro) object;
        return Objects.equals(tituloLibro, libro.tituloLibro) && Objects.equals(autorLibro, libro.autorLibro) && Objects.equals(añoPublicacion, libro.añoPublicacion) && Objects.equals(precioLibro, libro.precioLibro);
    }
}
