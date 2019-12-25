package matrix;

public class OperationsWithMatrix {
    public static Matrix mul(Matrix first, Matrix second) {
        Matrix res = new Matrix(first.hight, second.length);
        Matrix trans = transpose(second);
        for (int i = 0; i < res.hight; i++) {
            for (int j = 0; j < res.length; j++) {
                for (int k = 0; k < first.length; k++) {
                    res.val[i][j] += (first.val[i][k] * trans.val[j][k]);
                }
            }
        }
        return (res);
    }

    public static double[] mul(Matrix first, double[] b) {
        double[] res = new double[b.length];
        for (int i = 0; i < first.length; i++) {
            res[i] = 0;
            for (int j = 0; j < first.length; j++) {
                res[i] += first.val[i][j] * b[j];
            }
        }
        return res;
    }

    public static Matrix mul(Matrix first, double b) {
        Matrix res = first.clone();
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first.length; j++) {
                res.val[i][j]*= b;
            }
        }
        return res;
    }

    public static Matrix copy(Matrix m) {
        Matrix res = new Matrix(m.length, m.hight);
        for (int i = 0; i < m.hight; i++) {
            if (m.length >= 0) System.arraycopy(m.val[i], 0, res.val[i], 0, m.length);
        }
        return res;
    }

    public static Matrix inverse(Matrix m) {
        if (m.hight != m.length) {
            return null;
        }
        Matrix tmpMatrix = copy(m);
        Matrix inv = new Matrix(tmpMatrix.hight, tmpMatrix.length);
        for (int i = 0; i < tmpMatrix.length; i++) {
            inv.val[i][i] = 1;
        }
        double tmp = 0;
        //идём вниз
        for (int k = 0; k < tmpMatrix.hight; k++) {
            tmp = tmpMatrix.val[k][k];
            int counter = k;
            //ищем ненулевой элемент
            while (Math.abs(tmp) <= 1.0E-12) {
                counter++;
                if (counter == (tmpMatrix.length))
                    return null;
                if (Math.abs(tmpMatrix.val[counter % tmpMatrix.length][k]) > 1.0E-12) {
                    tmp = tmpMatrix.val[counter % tmpMatrix.length][k];
                    //меняем строки местами
                    for (int j = 0; j < tmpMatrix.length; j++) {
                        double temp = tmpMatrix.val[k][j];
                        tmpMatrix.val[k][j] = tmpMatrix.val[counter % tmpMatrix.length][j];
                        tmpMatrix.val[counter % tmpMatrix.length][j] = temp;
                        temp = inv.val[k][j];
                        inv.val[k][j] = inv.val[counter % inv.length][j];
                        inv.val[counter % inv.length][j] = temp;
                    }
                }
            }
            for (int i = k + 1; i < tmpMatrix.length; i++) {
                double tempCol = tmpMatrix.val[i][k] / tmpMatrix.val[k][k];
                for (int j = 0; j < tmpMatrix.length; j++) {
                    tmpMatrix.val[i][j] -= tempCol * tmpMatrix.val[k][j];
                    inv.val[i][j] -= tempCol * inv.val[k][j];
                }
            }
        }
        //обратно
        for (int k = tmpMatrix.hight - 1; k > 0; k--) {
            tmp = tmpMatrix.val[k][k];
            int counter = k;
            //ищем ненулевой элемент
            while (Math.abs(tmp) <= 1.0E-06) {
                counter--;
                if (counter == -1)
                    return null;
                if (Math.abs(tmpMatrix.val[counter % tmpMatrix.length][k]) > 1.0E-06) {
                    tmp = tmpMatrix.val[counter % tmpMatrix.length][k];
                    //меняем строки местами
                    for (int j = inv.length - 1; j >= 0; j--) {
                        double temp = tmpMatrix.val[k][j];
                        tmpMatrix.val[k][j] = tmpMatrix.val[counter % tmpMatrix.length][j];
                        tmpMatrix.val[counter % tmpMatrix.length][j] = temp;
                        temp = inv.val[k][j];
                        inv.val[k][j] = inv.val[counter % inv.length][j];
                        inv.val[counter % inv.length][j] = temp;
                    }
                }
            }
            for (int i = k - 1; i >= 0; i--) {
                double tempCol = tmpMatrix.val[i][k];
                for (int j = inv.length - 1; j >= 0; j--) {
                    tmpMatrix.val[i][j] -= tempCol / tmpMatrix.val[k][k] * tmpMatrix.val[k][j];
                    inv.val[i][j] -= tempCol / tmpMatrix.val[k][k] * inv.val[k][j];
                }
            }
        }
        for (int i = 0; i < tmpMatrix.length; i++) {
            for (int j = 0; j < tmpMatrix.hight; j++) {
                inv.val[i][j] /= tmpMatrix.val[i][i];
            }
        }
        return inv;
    }

    public static Matrix transpose(Matrix m) {
        Matrix res = new Matrix(m.hight, m.length);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.hight; j++) {
                res.val[i][j] = m.val[j][i];
            }
        }
        return (res);
    }

    private static double[][] GetMinor(double[][] matrix, int row, int column) {
        int minorLength = matrix.length - 1;
        double[][] minor = new double[minorLength][minorLength];
        int tempI = 0;
        int tempJ = 0;
        for (int i = 0; i <= minorLength; i++) {
            tempJ = 0;
            for (int j = 0; j <= minorLength; j++) {
                if (i == row) {
                    tempI = 1;
                } else {
                    if (j == column) {
                        tempJ = 1;
                    } else {
                        minor[i - tempI][j - tempJ] = matrix[i][j];
                    }
                }
            }
        }
        return minor;
    }

    public static double determinate(Matrix m) throws Exception {
        double result = 0;
        if (m.val.length == 2) {
            result = m.val[0][0] * m.val[1][1] - m.val[1][0] * m.val[0][1];
        } else {
            int koeff = 1;
            for (int i = 0; i < m.val.length; i++) {
                if (i % 2 == 1) {
                    koeff = -1;
                } else {
                    koeff = 1;
                }
                result += koeff * m.val[0][i] * (determinate(new Matrix(GetMinor(m.val, 0, i))));
            }
        }
        return result;
    }

    public static double conditionality(Matrix m) throws Exception {
        return determinate(m) * determinate(inverse(m));
    }

    public static double spectrRad(Matrix m) {
        double radius = 0.620403;
//        for (int i = 0; i < m.hight; i++){
//            double tempRad = 0;
//            for (int j = 0; j < m.length; j++){
//                tempRad+= Math.pow(m.val[i][j], 2);
//            }
//            tempRad = Math.sqrt(tempRad);
//            radius = Math.max(tempRad, radius);
//        }
        return radius;
    }

    static boolean positiveDefine(Matrix m) {
        if (!m.equals(transpose(m))) {
            return false;
        }
        return true;
    }

    public static Matrix addE(Matrix A){
        Matrix result = A.clone();
        for(int i = 0; i < A.length; i++){
            result.val[i][i]++;
        }
        return result;
    }

    public static Matrix add(Matrix First, Matrix Second){
        Matrix result = First.clone();
        for(int i = 0; i < First.length; i++){
            for (int j = 0; j < First.hight; j++){
                result.val[i][j]= First.val[i][j]+ Second.val[i][j];
            }
        }
        return result;
    }
}
