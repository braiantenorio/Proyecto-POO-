package modelo;

public class Relacion {
    private Usuario usr1;
    private Usuario usr2;
    private int tInterDiaria;
    private int likes;
    private int tSiendoAmigos;

    public Relacion(Usuario usr1, Usuario usr2, int tInterDiaria, int likes, int tSiendoAmigos2) {
        super();
        this.usr1 = usr1;
        this.usr2 = usr2;
        this.tInterDiaria = tInterDiaria;
        this.likes = likes;
        this.tSiendoAmigos = tSiendoAmigos2;
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

    public int gettSiendoAmigos() {
        return tSiendoAmigos;
    }

    public void settSiendoAmigos(int tSiendoAmigos) {
        this.tSiendoAmigos = tSiendoAmigos;
    }

    @Override
    public String toString() {
        return "Tiempo de interaccion diaria: " + tInterDiaria + " Cantidad de likes:" + likes
                + " Tiempo siendo amigos: " + tSiendoAmigos;
    }

}
