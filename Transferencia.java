/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sccsimulator;


public class Transferencia extends Evento
{
    protected int servico;
    protected Cliente c;
    
    Transferencia(double i, Simulador s, int servico, Cliente c)
    {
        super(i, s);
        this.servico = servico;
        this.c = c;
    }
    
    @Override
    void executa()
    {
        Servico prev = s.getServico(this.servico - 1);
        Servico serv = s.getServico(this.servico);        
        
        prev.removeServico();
        serv.insereServico(this.c);
        
    }
    
    @Override
    public String toString(){
         return "Transferência em " + instante;
    }
}
