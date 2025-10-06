package software_quality.core;

public class RankingElements {
    /**
     * Метод для расчёта вероятности безотказной работы
     * @param failuresCount число отказов (Q)
     * @param testCount число экспериментов (N)
     * @return вероятность 0 < p ≤ 1
     */
    public static double getProperWorkProbability(double failuresCount, double testCount) {
        if (testCount == 0) return 0;
        return 1 - (failuresCount / testCount);
    }

    /**
     * Метод для расчёта оценки по среднему времени восстановления
     * @param recoveryTimes массив замеров времени восстановления
     * @param allowedRecoveryTime допустимое время восстановления (Т_доп_в)
     * @return оценка (Q_в)
     */
    public static double getAvgRecoveryTimeRank(double[] recoveryTimes, double allowedRecoveryTime) {
        double avgRecoveryTime = 0;
        for (double time: recoveryTimes) {
            avgRecoveryTime += time;
        }
        avgRecoveryTime /= recoveryTimes.length;

        if (avgRecoveryTime <= allowedRecoveryTime) {
            return 1.0;
        } else {
            if (avgRecoveryTime == 0) return Double.POSITIVE_INFINITY;
            return allowedRecoveryTime / avgRecoveryTime;
        }
    }

    /**
     * Метод для расчёта оценки по продолжительности преобразования
     * @param processingTimes массив замеров времени преобразования
     * @param allowedProcessingTime допустимое время преобразования (Т_доп_п)
     * @return оценка (Q_п)
     */
    public static double getProcessingTimeRank(double[] processingTimes, double allowedProcessingTime) {
        double avgProcessingTime = 0;
        for (double time: processingTimes) {
            avgProcessingTime += time;
        }
        avgProcessingTime /= processingTimes.length;

        if (avgProcessingTime <= allowedProcessingTime) {
            return 1.0;
        } else {
            if (avgProcessingTime == 0) return Double.POSITIVE_INFINITY;
            return allowedProcessingTime / avgProcessingTime;
        }
    }
}
