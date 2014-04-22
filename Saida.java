package sccsimulator;

// Classe que representa a sa�da de um cliente. Deriva de Evento.

public class Saida extends Evento {

    protected int servico;
    //Construtor
    Saida (double i, Simulador s, int servico)
    {
            super(i, s);
            this.servico = servico;
    }

	// M�todo que executa as ac��es correspondentes � sa�da de um cliente
    @Override
    void executa ()
    {
        Servico serv = this.s.getServico(this.servico);
        // Retira cliente do servi�o
        serv.removeServico();
    }

    // M�todo que descreve o evento.
    // Para ser usado na listagem da lista de eventos.
    @Override
    public String toString(){
         return "Saída em " + instante;
    }


}