package Esercizio2;

import java.util.Objects;

public class Sala {
    private String film = "";
    private int postiDisponibili = 100;
    private int postiOccupati = 0;

    public Sala(String film, int postiDisponibili){
        super();
        this.film = film;
        this.postiDisponibili = postiDisponibili;
    }

    public Sala(String film){
        super();
        this.film = film;
    }

    public String getFilm() {
        return film;
    }

    public int getPostiLiberi(){
        return postiDisponibili - postiOccupati;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public int getPostiOccupati() {
        return postiOccupati;
    }

    public void setPostiOccupati(int postiOccupati) {
        this.postiOccupati = postiOccupati;
    }

    @Override
    public String toString(){
        return "Film: " + film + ", Posti disponibili: " + (postiDisponibili-postiOccupati);
    }

    public int hashCode(){
        return Objects.hash(film, postiDisponibili, postiOccupati);
    }

    public boolean equals(Object obj){
        if (obj == this) return true;
        if (!(obj instanceof Sala)) return false;
        Sala sala = (Sala) obj;
        return sala.film.equals(film) && sala.postiDisponibili == postiDisponibili && sala.postiOccupati == postiOccupati;
    }
}
