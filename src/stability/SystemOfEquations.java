package stability;

import matrix.*;

import java.util.Arrays;
import static matrix.OperationsWithMatrix.*;
import static matrix.Spectr.*;

public class SystemOfEquations {
    double[] valueCauchy;
    Matrix coeff;
    WrapForSpectr spectr;
    public SystemOfEquations(double[] t, double[] y, Matrix a){
        valueCauchy = new double[2];
        coeff = a.clone();
        spectr = eigenValues(a, 1.0E-8);
        System.out.println(spectr.toString());
        double temp = spectr.eigenVector.val[0][1]/ spectr.eigenVector.val[1][1];
        valueCauchy[0] = (y[0] - y[1] * temp)/(spectr.eigenVector.val[0][0] - spectr.eigenVector.val[1][0]*temp);
        valueCauchy[1] = (y[1] - valueCauchy[0]*spectr.eigenVector.val[0][0])/
                (spectr.eigenVector.val[0][1]);
    }

    double[] valueFunction(double[] y){
        double[] result = mul(coeff, y);
        return result;
    }

    double[] valueSolution(double t){
        double[] result = new double[2];
        result[0] = valueCauchy[0]*Math.exp(spectr.eigenValues[0]*t);
        result[1] = valueCauchy[1]*Math.exp(spectr.eigenValues[1]*t);
        return result;
    }

    @Override
    public String toString(){
        return ("правая часть уравнения:\n" + coeff.toString() + "\nЗначение C при задаче Коши при t = [0, 0]; y = [1, 1]:\n"
                + Arrays.toString(valueCauchy));
    }
}
