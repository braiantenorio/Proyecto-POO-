package modelo;

import java.time.LocalDate;

public class Relacion {
    private Usuario usr1;
    private Usuario usr2;
    private LocalDate fechaComienzoAmistad;
    private int tInterDiaria;
    private int likes;

    public Relacion(Usuario usr1, Usuario usr2, int tInterDiaria, int likes, LocalDate fechaComienzoAmistad) {
        super();
        this.usr1 = usr1;
        this.usr2 = usr2;
        this.tInterDiaria = tInterDiaria;
        this.likes = likes;
        this.fechaComienzoAmistad = fechaComienzoAmistad;
    }

    public Usuario getUsr1() {
        return usr1;
    }

    public void setUsr1(Usuario usr1) {
        this.usr1 = usr1;
    }

    public Usuario getUsr2() {
        return usr2;
    }

    public void setUsr2(Usuario usr2) {
        this.usr2 = usr2;
    }

    public int gettInterDiaria() {
        return tInterDiaria;
    }

    public void settInterDiaria(int tInterDiaria) {
        this.tInterDiaria = tInterDiaria;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDate gettSiendoAmigos() {
        return fechaComienzoAmistad;
    }

    public void settSiendoAmigos(LocalDate tSiendoAmigos) {
        this.fechaComienzoAmistad = tSiendoAmigos;
    }

    @Override
    public String toString() {
        return "Tiempo de interaccion diaria: " + tInterDiaria + " Cantidad de likes:" + likes
                + " Tiempo siendo amigos: " + fechaComienzoAmistad;
    }

}
