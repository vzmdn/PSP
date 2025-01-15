
import java.io.Serializable;

public class Vehicle implements Serializable {
    private String dni;
    private String matricula;
    private String marca;
    private String model;
    private String combustible;
    private int any;

    private static final long serialVersionUID = 1L;

    public Vehicle(String dni, String matricula, String marca, String model, String combustible, int any) {
        this.dni = dni;
        this.matricula = matricula;
        this.marca = marca;
        this.model = model;
        this.combustible = combustible;
        this.any = any;
    }

    public Vehicle() {
    }

    public String getDni() {
        return dni;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModel() {
        return model;
    }

    public String getCombustible() {
        return combustible;
    }

    public int getAny() {
        return any;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public void setAny(int any) {
        this.any = any;
    }

}
