package software_quality.core;

public class EstimatingMetrics {
    // средняя оценка m_kq
    public static double getAverageElementRank(double[] mValues) {
        return 1;
    }

    // итоговая оценка P_jk
    public static double getFinalMetricValue(double[] mkqValues) {
        return 1;
    }

    // абсолютный показатель P_ij
    public static double getAbsoluteMetricValue(double[] P_jk, double[] V_jk) {
        return 1;
    }

    // относительный показатель K_ij
    public static double getRelativeMetricValue(double P_ij, double allowedP_ij) {
        return 1;
    }

    // Фактор качества (K_i)^Ф
    public static double getQualityValue(double[] K_ij, double[] V_ij) {
        return 1;
    }
}
