package software_quality.core;

public class EstimatingMetrics {
    /**
     * Метод для расчета итоговой оценки
     * @param mkqValues массив значений метрик
     * @return итоговая оценка показатель
     */
    public static double getFinalMetricValue(double... mkqValues) {
        if (mkqValues.length == 0) return 0;
        double sum = 0;
        for (double mValue : mkqValues) {
            sum += mValue;
        }
        return sum / mkqValues.length;
    }

    /**
     * Метод для расчета абсолютного показателя
     * @param P_jk массив значений метрики
     * @param V_jk массив весовых коэффициентов метрик
     * @return абсолютный показатель
     */
    public static double getAbsoluteMetricValue(double[] P_jk, double[] V_jk) {
        if (P_jk.length == 0)
            return 0;
        double sum = 0;
        for (int k = 0; k < P_jk.length; k++) {
            sum += P_jk[k] * V_jk[k];
        }
        return sum;
    }

    /**
     * Метод для расчета относительного показателя критерия
     * @param P_ij абсолютный показатель
     * @param allowedP_ij базовое значение показателя
     * @return относительный показатель
     */
    public static double getRelativeMetricValue(double P_ij, double allowedP_ij) {
        if (allowedP_ij == 0) return 0;
        return P_ij / allowedP_ij;
    }

    /**
     * Метод для расчета фактора качества
     * @param K_ij массив относительных показателей
     * @param V_ij массив весовых коэффициентов
     * @return фактор качества
     */
    public static double getQualityValue(double[] K_ij, double[] V_ij) {
        if (K_ij.length == 0)
            return 0;
        double sum = 0;
        for (int i = 0; i < K_ij.length; i++) {
            sum += K_ij[i] * V_ij[i];
        }
        return sum;
    }
}
