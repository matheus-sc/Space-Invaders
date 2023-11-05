package src;

// Classe AlienForte que extende de Alien
public class AlienMedio extends Alien {

    // Possui vida e velocidade maior que o alien fraco, mas menor que o forte
    public AlienMedio() {
        setVida(20);
        setVelocidade(10);

       setSprite("/assets/AlienMedio.png");
    }

    // Método para checar se sofreu dano e alterar o sprite
    public void sofreuDano() {
        if (getVida() < 20) setSprite("/assets/AlienMedioDano.png");
    }
}