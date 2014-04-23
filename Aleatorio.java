package sccsimulator;

// Classe para geração de números aleatórios segundos várias distribuições
// Apenas a distribuição exponencial negativa está definida

public class Aleatorio {

	// Gera um n�mero segundo uma distribuição exponencial negativa de m�dia m
    static double exponencial (double m, int seed){
		return (-m*Math.log(RandomGenerator.rand64(seed)));
	}

    static double[] normal(double media, double desvio, int seed)
    {
        double v1, v2, w, x1, x2, y1, y2;
        double [] x = new double[2];
        
        do
        {
            v1 = 2 * RandomGenerator.rand64(seed) - 1;
            v2 = 2 * RandomGenerator.rand64(seed) - 1;
            w = Math.pow(v1, 2) + Math.pow(v2, 2);
        }while(w > 1);
        
        y1 = v1 * Math.pow(((-2 * Math.log(w)) / w), (1/2));
        y2 = v2 * Math.pow(((-2 * Math.log(w)) / w), (1/2));
        
        x[0] = media + y1 * desvio;
        x[1] = media + y2 * desvio;
        
        return x;
    }
}