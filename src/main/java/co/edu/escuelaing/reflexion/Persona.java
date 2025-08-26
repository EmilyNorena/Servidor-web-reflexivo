package co.edu.escuelaing.reflexion;


@Component
public class Persona {

    private String name = "";

    public Persona(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
