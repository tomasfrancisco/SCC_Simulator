package sccsimulator;

// Classe que representa a chegada de um cliente. Deriva de Evento.

public class Chegada extends Evento {

    protected int servico;
    //Construtor
    Chegada (double i, Simulador s, int servico)
    {
        super (i, s);
        this.servico = servico;
    }

    // Método que executa as acções correspondentes à chegada de um cliente
    @Override
    void executa ()
    {
        Servico serv = s.getServico(this.servico);
        // Coloca cliente no serviço - na fila ou a ser atendido, conforme o caso
        serv.insereServico (new Cliente());
        // Agenda nova chegada para daqui a Aleatorio.exponencial(s.media_cheg) instantes
        s.insereEvento(new Chegada(this.s.getInstante() + Aleatorio.exponencial(this.s.getMedia_cheg(), serv.getSeed()), this.s, 1));
    }

    // Método que descreve o evento.
    // Para ser usado na listagem da lista de eventos.
    @Override
    public String toString(){
         return "Chegada em " + instante;
    }
}