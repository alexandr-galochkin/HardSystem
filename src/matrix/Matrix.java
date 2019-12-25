package matrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix {
    public int length, hight;
    public double[][] val;

    public Matrix(int length, int hight) {
        this.length = length;
        this.hight = hight;
        val = new double[hight][length];
    }

    public Matrix(double[][] val) {
        this.length = val.length;
        this.hight = val.length;
        this.val = val.clone();
    }

    public Matrix(String fileName) {
        try {
            File f = new File(fileName);
            Scanner input = new Scanner(f);
            String[] line;
            ArrayList<Double[]> a = new ArrayList<>();
            Double[] temp = {};
            int check = 0;
            if (input.hasNextLine()) {
                line = input.nextLine().split(" ");
                check = line.length;
                temp = new Double[check];
                for (int i = 0; i < check; i++) {
                    temp[i] = Double.parseDouble(line[i]);
                }
                a.add(temp);
            }
            while (input.hasNextLine()) {
                line = input.nextLine().split(" ");
                if (check != line.length) {
                    throw new IOException("Неверная размерность матрицы.");
                }
                temp = new Double[line.length];
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = Double.parseDouble(line[i]);
                }
                a.add(temp);
            }
            double[][] result = new double[a.size()][temp.length];
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    result[i][j] = a.get(i)[j];
                }
            }
            this.val = result;
            this.hight = result.length;
            this.length = result[0].length;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла.\n" + e.getMessage());
        }
    }

    public double norm(){
        double result = 0;
        for (int i = 0; i < length; i++){
            double tempResult = 0;
            for (int j = 0; j< hight; j++){
                tempResult+=Math.abs(val[i][j]);
            }
            if (tempResult > result){
                result = tempResult;
            }
        }
        return result;
    }

    public static double vectorNorm(double[] vector){
        double result = 0;
        for (int i = 0; i < vector.length; i++){
            result = Math.max(result, Math.abs(vector[i]));
        }
        return result;
    }

    public void E(){
        for (int i = 0; i < hight; i++){
            for (int j = 0; j < length; j++) {
                if (i != j){
                    val[i][j] = 0;
                } else{
                    val[i][j] = 1;
                }
            }
        }
    }

    public Matrix clone(){
        Matrix newMatrix = new Matrix(length, hight);
        for (int i = 0; i < hight; i++){
            for (int j = 0; j < length; j++) {
                newMatrix.val[i][j] = val[i][j];
            }
        }
        return newMatrix;
    }

    @Override
    public String toString() {
        StringBuilder dat = new StringBuilder();
        for (int i = 0; i < hight; i++) {
            dat.append(Arrays.toString(val[i]));
            dat.append("\n");
        }
        return (dat.toString());
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Matrix)){
            return false;
        }
        Matrix m = (Matrix) o;
        if ((hight != m.hight)|| (length != m.length)){
            return false;
        }
        for (int i =0; i< length; i++){
            for (int j =0; j< hight; j++){
                if (Math.abs(Math.abs(val[i][j]) - Math.abs(m.val[i][j])) > 1.0E-12){
                    return false;
                }
            }
        }
        return true;
    }
}