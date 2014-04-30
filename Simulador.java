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
    private int seed_prep;
    private int seed_fix;
    private int seed_test;
    private int atend_prep;
    private int atend_fix;
    private int atend_test;
    // Tempo de Simulação
    private double tSimulacao;
    // Serviço - pode haver mais do que um num simulador
    private Servico preparacao;
    public double dados_prep[] = new double[6];
    private Servico fixacao;
    public double dados_fix[] = new double[6];
    private Servico teste;
    public double dados_test[] = new double[6];
    // Lista de eventos - onde ficam registados todos os eventos que vão ocorrer na simulação
    // Cada simulador só tem uma
    private ListaEventos lista;

    // Construtor
    public Simulador(double media_cheg, double media_prep, double desvio_prep, int seed_prep, int atend_prep, double media_fix, double desvio_fix, int seed_fix, int atend_fix, double media_test, double desvio_test, int seed_test, int atend_test, int tSimulacao) 
    {
        // Inicialização de parâmetros do simulador
        this.media_cheg = media_cheg;
        
        this.media_prep = media_prep;        
        this.desvio_prep = desvio_prep;
        this.seed_prep = seed_prep;
        this.atend_prep = atend_prep;
        
        
        this.media_fix = media_fix;
        this.desvio_fix = desvio_fix;
        this.seed_fix = seed_fix;
        this.atend_fix = atend_fix;
        
        this.media_test = media_test;
        this.desvio_test = desvio_test;
        this.seed_test = seed_test;
        this.atend_test = atend_test;
        
        this.tSimulacao = tSimulacao * 60;
        
//        // Inicialização de parâmetros do simulador
//        this.media_cheg = 1;
//        
//        this.media_prep = 6;        
//        this.desvio_prep = 1.5;
//        this.seed_prep = 0;
//        this.atend_prep = 1;
//        
//        
//        this.media_fix = 5;
//        this.desvio_fix = 1.3;
//        this.seed_fix = 1;
//        this.atend_fix = 1;
//        
//        this.media_test = 4.5;
//        this.desvio_test = 1.1;
//        this.seed_test = 2;
//        this.atend_test = 1;
//        
//        this.tSimulacao = 50 * 60;
        // Inicialização do relógio de simulação
        instante = 0;
        // Criação do serviço
        preparacao = new Servico(this, 1, atend_prep, media_prep, desvio_prep, seed_prep);
        fixacao = new Servico(this, 2, atend_fix, media_fix, desvio_fix, seed_fix);
        teste = new Servico(this, 3, atend_test, media_test, desvio_test, seed_test);
        // Criação da lista de eventos
        lista = new ListaEventos(this);
        // Agendamento da primeira chegada
        // Se não for feito, o simulador não tem eventos para simular
        insereEvento (new Chegada(instante, this, 1));
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
    public String relat ()
    {                        
        this.dados_prep[0] = preparacao.soma_temp_esp / (preparacao.atendidos + preparacao.fila.size());    //temp_med_fila
        this.dados_prep[1] = preparacao.soma_temp_esp / preparacao.soma_temp_esp / this.getInstante();      //comp_med_fila
        this.dados_prep[2] = preparacao.soma_temp_serv / this.getInstante() / preparacao.atendedores;                                //utilizacao_serv
        this.dados_prep[3] = this.getInstante();
        this.dados_prep[4] = preparacao.atendidos;
        this.dados_prep[5] = preparacao.fila.size();
        
        String result_prep = "\nTempo médio de espera "+dados_prep[0]
                +"\nComp. médio da fila "+dados_prep[1]
                +"\nUtilização do serviço "+dados_prep[2]
                +"\nTempo de simulação "+dados_prep[3]
                +"\nNúmero de clientes atendidos "+dados_prep[4]
                +"\nNúmero de clientes na fila "+dados_prep[5];
        
        this.dados_fix[0] = fixacao.soma_temp_esp / (fixacao.atendidos + fixacao.fila.size());
        this.dados_fix[1] = fixacao.soma_temp_esp / fixacao.soma_temp_esp / this.getInstante();
        this.dados_fix[2] = fixacao.soma_temp_serv / this.getInstante() / fixacao.atendedores;
        this.dados_fix[3] = this.getInstante();
        this.dados_fix[4] = fixacao.atendidos;
        this.dados_fix[5] = fixacao.fila.size();
        
        String result_fix = "\nTempo médio de espera "+dados_fix[0]
                +"\nComp. médio da fila "+dados_fix[1]
                +"\nUtilização do serviço "+dados_fix[2]
                +"\nTempo de simulação "+dados_fix[3]
                +"\nNúmero de clientes atendidos "+dados_fix[4]
                +"\nNúmero de clientes na fila "+dados_fix[5];
        
        this.dados_test[0] = teste.soma_temp_esp / (teste.atendidos + teste.fila.size());
        this.dados_test[1] = teste.soma_temp_esp / teste.soma_temp_esp / this.getInstante();
        this.dados_test[2] = teste.soma_temp_serv / this.getInstante() / teste.atendedores;
        this.dados_test[3] = this.getInstante();
        this.dados_test[4] = teste.atendidos;
        this.dados_test[5] = teste.fila.size();
        
        String result_test = "\nTempo médio de espera "+dados_test[0]
                +"\nComp. médio da fila "+dados_test[1]
                +"\nUtilização do serviço "+dados_test[2]
                +"\nTempo de simulação "+dados_test[3]
                +"\nNúmero de clientes atendidos "+dados_test[4]
                +"\nNúmero de clientes na fila "+dados_test[5];
        
        String final_text = "\n------- Resultados preparacao -------\n"
                +result_prep
                +"\n------- Resultados fixacao -------\n"
                +result_fix
                +"\n------- Resultados teste -------\n"
                +result_test;
        
        
        return final_text;
    }

    // Método executivo do simulador
    public void executa (){
        Evento e1;
        // Enquanto não atender todos os clientes
        while (this.instante < this.tSimulacao)
        {
            //lista.print();                        // Mostra lista de eventos - desnecessário; é apenas informativo
            e1 = (Evento)(lista.removeFirst());  // Retira primeiro evento (é o mais iminente) da lista de eventos
            instante = e1.getInstante();         // Actualiza relógio de simulação
            act_stats();                         // Actualiza valores estatísticos
            e1.executa();                        // Executa evento
        }
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