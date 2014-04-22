package sccsimulator;

public class Simulador 
{
    // Relógio de simulação - variável que contém o valor do tempo em cada instante
    private double instante;
    // Médias das distribuições de chegadas e de atendimento nos serviços
    private double media_cheg;
    private double media_prep;
    private double desvio_prep;
    private double media_fix;
    private double desvio_fix;
    private double media_test;
    private double desvio_test;
    private double seed_prep;
    private double seed_fix;
    private double seed_test;
    // Tempo de Simulação
    private double tSimulacao;
    // Serviço - pode haver mais do que um num simulador
    private Servico preparacao;
    private Servico fixacao;
    private Servico teste;
    // Lista de eventos - onde ficam registados todos os eventos que vão ocorrer na simulação
    // Cada simulador só tem uma
    private ListaEventos lista;

    // Construtor
    public Simulador() {
        // Inicialização de parâmetros do simulador
        media_cheg = 1;
        media_prep = 6;
        desvio_prep = 1.5;
        seed_prep = 20;
        media_fix = 5;
        desvio_fix = 1.3;
        seed_fix = 25;
        media_test = 4.5;
        desvio_test = 1.1;
        seed_test = 30;
        tSimulacao = 50 * 60;
        // Inicialização do relógio de simulação
        instante = 0;
        // Criação do serviço
        preparacao = new Servico(this, 1, media_prep, desvio_prep, seed_prep);
        fixacao = new Servico(this, 2, media_fix, desvio_fix, seed_fix);
        teste = new Servico(this, 3, media_test, desvio_test, seed_test);
        // Criação da lista de eventos
        lista = new ListaEventos(this);
        // Agendamento da primeira chegada
        // Se não for feito, o simulador não tem eventos para simular
        insereEvento (new Chegada(instante, this, 1));
    }

    // programa principal
    public static void main(String[] args) {
        // Cria um simulador e
        Simulador s = new Simulador();
        // põe-o em marcha
        s.executa();
    }

    // Método que insere o evento e1 na lista de eventos
    void insereEvento (Evento e1){
            lista.insereEvento (e1);
    }

    // Método que actualiza os valores estatísticos do simulador
    private void act_stats(){
            preparacao.act_stats();
            fixacao.act_stats();
            teste.act_stats();
    }

    // Método que apresenta os resultados de simula��o finais
    private void relat (){
        System.out.println();
        System.out.println("------- Resultados preparacao -------");
        System.out.println();
        preparacao.relat();
        System.out.println();
        System.out.println("------- Resultados fixacao -------");
        System.out.println();
        fixacao.relat();
        System.out.println();
        System.out.println("------- Resultados teste -------");
        System.out.println();
        teste.relat();
    }

    // Método executivo do simulador
    public void executa (){
        Evento e1;
        // Enquanto não atender todos os clientes
        while (this.instante < this.tSimulacao)
        {
            //lista.print();                       // Mostra lista de eventos - desnecessário; é apenas informativo
            e1 = (Evento)(lista.removeFirst());  // Retira primeiro evento (é o mais iminente) da lista de eventos
            instante = e1.getInstante();         // Actualiza relógio de simulação
            act_stats();                         // Actualiza valores estatísticos
            e1.executa();                        // Executa evento
        }
        relat();  // Apresenta resultados de simulação finais
    }

    // Método que devolve o instante de simulação corrente
    public double getInstante() {
        return instante;
    }

    // Método que devolve a média dos intervalos de chegada
    public double getMedia_cheg() {
        return media_cheg;
    }

    // Método que devolve a média dos tempos de serviço
    public double getMedia_serv(int servico) {
        switch(servico)
        {
            case 1:
                return this.media_prep;
            case 2:
                return this.media_fix;
            case 3:
                return this.media_test;
        }
        System.err.println("ERRO no getMedia_serv");
        return 0;
    }
    
    public Servico getServico(int servico)
    {
        switch(servico)
        {
            case 1:
                return this.preparacao;
            case 2:
                return this.fixacao;
            case 3:
                return this.teste;
        }
        return null;
    }
}