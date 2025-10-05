package software_quality.core;

public class RankingElements {
    /**
     * Метод для расчёта вероятности безотказной работы
     * @param failuresCount число отказов, целое
     * @param testCount число экспериментов, целое
     * @return вероятность 0 < p ≤ 1, double
     */
    public static double getProperWorkProbability(double failuresCount, double testCount) {
        //return 1 - (failuresCount / testCount);
        return 1;
    }

    public static double getAvgRecoveryTimeRank(double[] recoveryTimes, double allowedRecoveryTime) {
        //double avgRecoveryTime = 0;
        //for (double time: recoveryTimes) {
        //    avgRecoveryTime += time;
        //}
        //avgRecoveryTime /= recoveryTimes.length;
        //
        //if (allowedRecoveryTime >= avgRecoveryTime) {
        //    return 1;
        //} else {
        //    return allowedRecoveryTime / avgRecoveryTime;
        //}
        return 1;
    }

    public static double getProcessingTimeRank(double[] processingTimes, double allowedProcessingTime) {
        return 1;
    }
}
