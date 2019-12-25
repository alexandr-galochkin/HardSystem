package matrix;

import java.util.Arrays;

public class WrapForSpectr {
    public double[] eigenValues;
    public Matrix eigenVector;

    public WrapForSpectr(double[] values, Matrix vector){
        eigenValues = values.clone();
        eigenVector = vector.clone();
    }

    @Override
    public String toString(){
        return "Собственные значения: " + Arrays.toString(eigenValues) + "\nСобственные вектора:\n" + eigenVector.toString();
    }
}
