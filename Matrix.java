import java.util.*;
public class Matrix{
    int rows;
    int cols;
    double[][] els;

    public Matrix(double[][] els){
        this.els = els;
        this.rows = els.length;
        this.cols = els[0].length;
    }

    public Matrix(int rows, int cols){
        this.els = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public static Matrix transpose(Matrix m){
        Matrix transposed = new Matrix(m.cols, m.rows);
        for(int i = 0; i < m.rows; i++){
            for(int j = 0; j < m.cols; j++){
                transposed.els[j][i] = m.els[i][j];
            }
        }
        return transposed;
    }

    public static int checkSign(int n){
        return n%2==0?1:-1;
    } 

    public static Matrix subMatrix(Matrix m, int exRow, int exCol){
        Matrix sub = new Matrix(m.rows-1, m.cols-1);
        for(int i = 0, r = -1; i < m.rows; i++){
            if(i == exRow)continue;
            r++;
            for(int j = 0, c = -1; j < m.cols; j++){
                if(j==exCol)continue;
                c++;
                sub.els[r][c] = m.els[i][j];
            }
        }
        return sub;
    }

    public static double Det2(Matrix m){
        return (m.els[0][0]*m.els[1][1])-(m.els[0][1]*m.els[1][0]);
    }

    public static double Det(Matrix m){
        if(m.rows == 1 && m.cols == 1){
            return m.els[0][0];
        }
        if(m.rows == 2 && m.cols == 2){
            return Det2(m);
        }
        double sum = 0.0;
        for(int i = 0; i < m.rows; i++){
            sum+=checkSign(i)*m.els[0][i]*Det2(subMatrix(m, 0, i));
        }
        return sum;
    }

    public static Matrix multiplyByConst(Matrix m, double cons){
        Matrix mc = new Matrix(m.rows, m.cols);
        for(int i = 0; i  < m.rows; i++){
            for(int j = 0; j < m.cols; j++){
                mc.els[i][j] = cons*m.els[i][j];
            }
        }
        return mc;
    }

    public static Matrix multiply(Matrix X, Matrix Y) throws Exception{
       Matrix prod = new Matrix(X.rows, Y.cols);
       for (int i = 0; i < X.rows; i++) {
           for (int j = 0; j < Y.cols; j++) {
               for (int k = 0; k < X.cols; k++) {
                   prod.els[i][j] += X.els[i][k] * Y.els[k][j];
               }
           }
       }
       return prod;
    }

    public static Matrix cofactor(Matrix m){
        Matrix cof = new Matrix(m.rows, m.cols);
        for(int i = 0; i  < m.rows; i++){
            for(int j = 0; j < m.cols; j++){
                cof.els[i][j] = checkSign(i)*checkSign(j)*Det(subMatrix(m, i, j));
            }
        }
        return cof;
    }

    public static Matrix inv(Matrix m){
        return multiplyByConst((transpose(cofactor(m))), 1.0/Det(m));
    }

    public static void main() throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("How many accepted ED?");
        int ED = sc.nextInt();
        System.out.println("How many accepted RD?");
        int RD = sc.nextInt();
        
        Matrix X = new Matrix(new double[][]{{1,798,2819},{1,757,3062},{1,647,3441}});
        //2014, 2013, 2012 i.e. class of 2018, 2017, 2016
        Matrix Y = new Matrix(new double[][]{{75},{102},{170}});
        Matrix XT = transpose(X);
        Matrix XXT = multiply(XT, X);
        Matrix InvOfXXT = inv(XXT);
        if(InvOfXXT == null){
        System.out.println("method didnt work");
        return;
        }
        Matrix XTY = multiply(XT, Y);
        Matrix weights = multiply(InvOfXXT, XTY);
        System.out.println(Arrays.deepToString(weights.els));
        double beta0 = weights.els[0][0];
        double beta1 = weights.els[1][0];
        double beta2 = weights.els[2][0];
        
        System.out.println("Predicted waitlist acceptances = ");
        System.out.println((int)(beta0+beta1*ED+beta2*RD));
    }
}

