package stability;

import static matrix.OperationsWithMatrix.*;

public class Solutions {
    public static double[][] exactSolutions(SystemOfEquations equations, double t, int N, double h) {
        double[][] result = new double[N][equations.coeff.length];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < equations.coeff.length; j++) {
                result[i][j] = equations.valueSolution(t + h * i)[j];
            }
        }
        return result;
    }

    public static double[][] eulerSolutions(SystemOfEquations equations, double t, int N, double h) {
        double[][] result = new double[N][equations.coeff.length];
        for (int j = 0; j < equations.coeff.length; j++) {
            result[0][j] = equations.valueSolution(0)[j];
        }
        for (int i = 1; i < N; i++) {
            result[i] = mul(addE(mul(equations.coeff, h)), result[i - 1]);
        }
        return result;
    }

    public static String stabilityOfEuler(SystemOfEquations equations, double h) {
        double maxEilerValue = 0;
        for (int i = 0; i < equations.spectr.eigenValues.length; i++) {
            maxEilerValue = Math.max(maxEilerValue, Math.abs(equations.spectr.eigenValues[i]));
        }
        if (h < 2 / maxEilerValue) {
            return "Метод Эйлера устойчив.";
        }
        return "Метод Эйлера неустойчив.";
    }

    public static double[][] reverseEulerSolutions(SystemOfEquations equations, double t, int N, double h) {
        double[][] result = new double[N][equations.coeff.length];
        for (int j = 0; j < equations.coeff.length; j++) {
            result[0][j] = equations.valueSolution(0)[j];
        }
        for (int i = 1; i < N; i++) {
            result[i] = mul(inverse(addE(mul(equations.coeff, -h))), result[i - 1]);
        }
        return result;
    }

    public static String stabilityOfReverseEuler(SystemOfEquations equations, double h) {
        return "Обратный метод Эйлера устойчив.";
    }

    public static double[][] extrapolationAdamsSolutions(SystemOfEquations equations, double t, int N, double h){
        double[][] result = new double[N][equations.coeff.length];
        for (int j = 0; j < equations.coeff.length; j++) {
            result[0][j] = equations.valueSolution(0)[j];
        }
        result[1] = mul(addE(mul(equations.coeff, h)), result[0]);
        for (int i = 2; i < N; i++) {
            double[] tempF = mul(addE(mul(equations.coeff, 1.5*h)), result[i - 1]);
            double[] tempS = mul(mul(equations.coeff, -0.5*h), result[i-2]);
            for (int j = 0; j < tempF.length; j++) {
                result[i][j] = tempF[j]+ tempS[j];
            }
        }
        return result;
    }

    public static String stabilityOfExtrapolationAdams(SystemOfEquations equations, double h) {
        double maxEilerValue = 0;
        for (int i = 0; i < equations.spectr.eigenValues.length; i++) {
            maxEilerValue = Math.max(maxEilerValue, Math.abs(equations.spectr.eigenValues[i]));
        }
        if (h < 1 / maxEilerValue) {
            return "Экстраполяционный метод Адамса второго порядка устойчив.";
        }
        return "Экстраполяционный метод Адамса второго порядка неустойчив.";
    }

}
