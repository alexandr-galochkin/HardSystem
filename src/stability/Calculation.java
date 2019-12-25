package stability;

import matrix.*;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import static stability.Solutions.*;

public class Calculation {
    public static void main(String[] args) {
        System.out.println("Задача определения устойчивости жёстких уравнений.");
        Scanner scanner = new Scanner(System.in);
        try {
            Matrix a = new Matrix("src\\a");
            double[] t = new double[a.length];
            File f = new File("src\\time");
            Scanner input = new Scanner(f);
            for (int i = 0; i < t.length; i++) {
                t[i] = input.nextDouble();
            }
            double[] y = new double[a.length];
            f = new File("src\\y");
            input = new Scanner(f);
            for (int i = 0; i < y.length; i++) {
                y[i] = input.nextDouble();
            }

            SystemOfEquations systemOfEquations = new SystemOfEquations(t, y, a);
            System.out.println("Рассмотрим систему уравнений y' = ay, где " + systemOfEquations.toString());
            System.out.println("Введите 2 числа: количество точек и шаг.");
            int N = scanner.nextInt();
            double h = scanner.nextDouble();
            double[][] exactSol = exactSolutions(systemOfEquations, 0, N, h);
            double[][] eulerSol = eulerSolutions(systemOfEquations, 0, N, h);
            double[][] reverseEulerSol = reverseEulerSolutions(systemOfEquations, 0, N, h);
            double[][] extrAdamsSol = extrapolationAdamsSolutions(systemOfEquations, 0, N, h);

            System.out.println("Точное решение:");
            for (int i = 0; i < N; i++) {
                System.out.println(Arrays.toString(exactSol[i]));
            }
            System.out.println();
            System.out.println("Приближённое значение методом Эйлера:");
            for (int i = 0; i < N; i++) {
                System.out.println(Arrays.toString(eulerSol[i]));
            }
            System.out.println();
            System.out.println(stabilityOfEuler(systemOfEquations, h) + "\n");
            System.out.println("Приближённое значение обратным методом Эйлера:");
            for (int i = 0; i < N; i++) {
                System.out.println(Arrays.toString(reverseEulerSol[i]));
            }
            System.out.println();
            System.out.println(stabilityOfReverseEuler(systemOfEquations, h) + "\n");
            System.out.println("Приближённое значение экстраполяционным методом Адамса второго порядка:");
            for (int i = 0; i < N; i++) {
                System.out.println(Arrays.toString(extrAdamsSol[i]));
            }
            System.out.println();
            System.out.println(stabilityOfExtrapolationAdams(systemOfEquations, h) + "\n");

        } catch (Exception e) {
            System.out.println("Ошибка");
        }
    }
}
