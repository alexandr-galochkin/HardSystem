package matrix;

import static matrix.OperationsWithMatrix.*;

public class Spectr {
    public static WrapForSpectr eigenValues(Matrix A, double eps) {
        Matrix X = new Matrix(A.length, A.hight);
        Matrix V = new Matrix(A.length, A.hight);
        Matrix ANext = A.clone();
        X.E();
        double[] resultValues = new double[A.length];
        double normMatrix = eps;
        int i_k = 0, j_k = 0;
        double c, s, d;
        while (true) {
            normMatrix = 0;
            for (int i = 0; i < ANext.hight; i++) {
                for (int j = i + 1; j < ANext.length; j++) {
                    if (normMatrix < Math.abs(ANext.val[i][j])) {
                        i_k = i;
                        j_k = j;
                        normMatrix = Math.abs(ANext.val[i][j]);
                    }
                }
            }
            if (normMatrix < eps) {
                break;
            }
            d = Math.sqrt(Math.pow(ANext.val[i_k][i_k] - ANext.val[j_k][j_k], 2) +
                    4 * Math.pow(ANext.val[i_k][j_k], 2));
            c = Math.sqrt(0.5 * (1 +
                    Math.abs(ANext.val[i_k][i_k] - ANext.val[j_k][j_k]) / d));
            s = Math.signum(ANext.val[i_k][j_k] * (ANext.val[i_k][i_k] - ANext.val[j_k][j_k])) *
                    Math.sqrt(0.5 * (1 - Math.abs(ANext.val[i_k][i_k] - ANext.val[j_k][j_k]) / d));
            Matrix ATemp = ANext.clone();
            for (int i = 0; i < ANext.hight; i++) {
                for (int j = i; j < ANext.length; j++) {
                    if ((i != i_k) && (i != j_k) && (j == i_k)) {
                        ATemp.val[i][i_k] = c * ANext.val[i][i_k] + s * ANext.val[i][j_k];
                        ATemp.val[i_k][i] = ATemp.val[i][i_k];
                    } else if ((i != i_k) && (i != j_k) && (j == j_k)) {
                        ATemp.val[i][j_k] = -s * ANext.val[i][i_k] + c * ANext.val[i][j_k];
                        ATemp.val[j_k][i] = ATemp.val[i][j_k];
                    } else if ((i == i_k) && (j == i_k)) {
                        ATemp.val[i_k][i_k] = c * c * ANext.val[i_k][i_k] + 2 * c * s * ANext.val[i_k][j_k] +
                                s * s * ANext.val[j_k][j_k];
                    } else if ((i == j_k) && (j == j_k)) {
                        ATemp.val[j_k][j_k] = s * s * ANext.val[i_k][i_k] - 2 * c * s * ANext.val[i_k][j_k] +
                                c * c * ANext.val[j_k][j_k];
                    } else if ((i == i_k) && (j == j_k)) {
                        ATemp.val[i_k][j_k] = (c * c - s * s) * ANext.val[i_k][j_k] +
                                c * s * (ANext.val[j_k][j_k] - ANext.val[i_k][i_k]);
                        ATemp.val[j_k][i_k] = ATemp.val[i_k][j_k];
                    }
                }
            }
            ANext = ATemp.clone();
            V.E();
            V.val[i_k][i_k] = c;
            V.val[j_k][j_k] = c;
            V.val[i_k][j_k] = -s;
            V.val[j_k][i_k] = s;
            X = mul(X, V);
        }
        for (int i = 0; i < ANext.hight; i++) {
            resultValues[i] = ANext.val[i][i];
            for (int j = 0; j < ANext.length; j++) {
                if (j != i) {
                    resultValues[i] += (ANext.val[i][j] /
                            (ANext.val[i][i] - ANext.val[j][j]));
                }
            }
        }
        ;
        WrapForSpectr result = new WrapForSpectr(resultValues, X);
        return result;
    }
}
