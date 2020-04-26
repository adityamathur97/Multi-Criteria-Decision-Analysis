package aditya.com.dba;

public class DataBasedApproach {
    private static int row,col;
    private static double[] optimalValue,avgValue,stdValue,stdOptimalValue,eucDis,weights,sumOfCompMatrixCol;
    private static double[][] dataSet;
    private static double[][] comparisionMatrix;
    private static double[][] stdDataSet;
    private static double[][] disDataSet;
    private static int[] optimalSet;
    private static int decVar;
    private static double sumOfCol;
    private static double sumOfCompMatrixRow;
    private static double sumOfColSqr;
    private static double sumOfRowSqr;
    private static double minEucDis;

    public static void initialize(int row, int col) {
        DataBasedApproach.row = row;
        DataBasedApproach.col = col;

        sumOfCol = sumOfColSqr = sumOfRowSqr = sumOfCompMatrixRow=0;

        dataSet= new double[row][col];
        comparisionMatrix= new double[col][col];
        optimalSet= new int[col];
        optimalValue= new double[col];
        avgValue= new double[col];
        stdValue= new double[col];
        stdDataSet= new double[row][col];
        stdOptimalValue= new double[col];
        disDataSet= new double[row][col];
        eucDis= new double[row];
        weights= new double[col];
        sumOfCompMatrixCol= new double[col];
    }

    public static int getRow() { return row; }
    public static int getCol() { return col; }

    public static void setDataSet(double dataSet[][]) {
        DataBasedApproach.dataSet = dataSet;
        for(int j=0;j<col;j++){
            for(int i=0;i<row;i++){
                dataSet[i][j]=dataSet[i][j]*weights[j];
            }
        }
    }

    public static void setOptimalSet(int optimalSet[]) {
        DataBasedApproach.optimalSet = optimalSet;
    }

    public static int begin() {
        calWeights();
        findOptimalValue();
        calStdDeviation();
        createStdDataMatrix();
        calStdOptimalValue();
        createDistanceMatrix();
        calEuclDistance();
        findMinEuclDistance();
        return calBestAlternative();
    }

    public static void setComparisionMatrix(double comparisionMatrix[][]){
        DataBasedApproach.comparisionMatrix = comparisionMatrix;
    }

    private static void calWeights(){
        for (int j=0;j<col;j++){
            for(int i=0;i<col;i++){
                sumOfCompMatrixCol[j]=sumOfCompMatrixCol[j]+comparisionMatrix[i][j];
            }
        }

        for (int j=0;j<col;j++){
            for(int i=0;i<col;i++){
                comparisionMatrix[i][j]=comparisionMatrix[i][j]/sumOfCompMatrixCol[j];
            }
        }

        for(int i=0;i<col;i++){
            for(int j=0;j<col;j++){
                sumOfCompMatrixRow=sumOfCompMatrixRow+comparisionMatrix[i][j];
            }
            weights[i]=sumOfCompMatrixRow/col;
            sumOfCompMatrixRow=0;
        }
    }

    private static void findOptimalValue(){
        for(int j=0;j<col;j++){
            decVar=optimalSet[j];
            double var = dataSet[0][j];
            sumOfCol=0;
            for(int i=0;i<row;i++){
                if(decVar==1){
                    if(dataSet[i][j]> var)
                        var =dataSet[i][j];
                }
                else{
                    if(dataSet[i][j]< var)
                        var =dataSet[i][j];
                }
                sumOfCol=sumOfCol+dataSet[i][j];
            }
            optimalValue[j]= var;
            avgValue[j]=sumOfCol/row;
        }
    }

    private static void calStdDeviation(){
        for(int j=0;j<col;j++){
            sumOfColSqr=0;
            for(int i=0;i<row;i++){

                sumOfColSqr=sumOfColSqr+((dataSet[i][j]-avgValue[j])*(dataSet[i][j]-avgValue[j]));
            }
            stdValue[j]=Math.sqrt(sumOfColSqr/row);
        }
    }

    private static void createStdDataMatrix(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                stdDataSet[i][j]=(dataSet[i][j]-avgValue[j])/stdValue[j];
            }
        }
    }

    private static void calStdOptimalValue(){
        for(int j=0;j<col;j++){
            stdOptimalValue[j]=(optimalValue[j]-avgValue[j])/stdValue[j];
        }
    }

    private static void createDistanceMatrix(){
        for(int j=0;j<col;j++){
            decVar=optimalSet[j];
            for(int i=0;i<row;i++){
                if(decVar==1){
                    disDataSet[i][j]=stdOptimalValue[j]-stdDataSet[i][j];
                }
                else{
                    disDataSet[i][j]=stdDataSet[i][j]-stdOptimalValue[j];
                }
            }
        }
    }

    private static void calEuclDistance(){
        for(int i=0;i<row;i++){
            sumOfRowSqr=0;
            for(int j=0;j<col;j++){
                sumOfRowSqr=sumOfRowSqr+(disDataSet[i][j]*disDataSet[i][j]);
            }
            eucDis[i]=Math.sqrt(sumOfRowSqr);
        }
    }

    private static void findMinEuclDistance(){
        minEucDis=eucDis[0];
        for(int i=0;i<row;i++){
            if(minEucDis>eucDis[i])
                minEucDis=eucDis[i];
        }
    }

    private static int calBestAlternative(){
        for(int i=0;i<row;i++){
            if(minEucDis==eucDis[i])
                return i + 1;
        }
        return 0;
    }
}
