package software_quality;

import software_quality.core.EstimatingMetrics;
import software_quality.core.RankingElements;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Main {
    /**
     * Вспомогательный метод для генерации случайной выборки.
     * @param sampleSize размер выборки
     * @param intervalStart начало интервала
     * @param intervalEnd конец интервала
     * @return массив случайных значений
     */
    private static double[] generateSample(int sampleSize, double intervalStart, double intervalEnd) {
        if (sampleSize <= 0) {
            return new double[0];
        }

        Random random = new Random();
        double[] sample = new double[sampleSize];

        for (int i = 0; i < sampleSize; i++) {
            sample[i] = intervalStart + (intervalEnd - intervalStart) * random.nextDouble();
        }

        return sample;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: Укажите путь к файлу конфигурации в качестве аргумента командной строки.");
            return;
        }

        String configFilePath = args[0];
        var properties = new Properties();

        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Ошибка: Не удалось прочитать файл конфигурации: " + configFilePath);
            throw new RuntimeException(e);
        }

        System.out.println("=======================================================");
        System.out.println("\n--- Расчет фактора надежности по ГОСТ 28195-89 ---\n");
        System.out.println("=======================================================");


        // Входные данные
        var q = Integer.parseInt(properties.getProperty("variant3.q"));
        var n = Integer.parseInt(properties.getProperty("variant3.n"));
        var recoveryTimeStart = Double.parseDouble(properties.getProperty("variant3.recoveryTime.start"));
        var recoveryTimeEnd = Double.parseDouble(properties.getProperty("variant3.recoveryTime.end"));
        var allowedRecoveryTime = Double.parseDouble(properties.getProperty("variant3.recoveryTime.allowed"));
        var processingTimeStart = Double.parseDouble(properties.getProperty("variant3.processingTime.start"));
        var processingTimeEnd = Double.parseDouble(properties.getProperty("variant3.processingTime.end"));
        var allowedProcessingTime = Double.parseDouble(properties.getProperty("variant3.processingTime.allowed"));
        var baseCriterion = Double.parseDouble(properties.getProperty("variant3.baseCriterion"));

        // Генерация выборок
        int recoverySampleSize = 100;
        int processingSampleSize = 200;
        double[] recoveryTimes = generateSample(recoverySampleSize, recoveryTimeStart, recoveryTimeEnd);
        double[] processingTimes = generateSample(processingSampleSize, processingTimeStart, processingTimeEnd);

        System.out.println("\n--- Входные данные ---\n");
        System.out.println("Число зарегистрированных отказов (Q): " + q);
        System.out.println("Число экспериментов (N): " + n);
        System.out.println("Интервал времени восстановления (Тв): [" + recoveryTimeStart + "; " + recoveryTimeEnd + "]");
        System.out.println("|-> Размер выборки: " + recoverySampleSize + " замеров");
        System.out.println("Допустимое время восстановления (Тв_доп): " + allowedRecoveryTime);
        System.out.println("Интервал времени преобразования (Тп): [" + processingTimeStart + "; " + processingTimeEnd + "]");
        System.out.println("|-> Размер выборки: " + processingSampleSize + " замеров");
        System.out.println("Допустимое время преобразования (Тп_доп): " + allowedProcessingTime);
        System.out.println("Базовый критерий надежности (P_ij^баз): " + baseCriterion);

        // Шаг 2: Расчет Оценочных Элементов
        System.out.println("\n=======================================================");
        System.out.println("\n--- Расчет оценочных элементов ---\n");

        var properWorkProbability = RankingElements.getProperWorkProbability(q, n);
        System.out.println("ОЭ для H0401 (Вероятность безотказной работы): " + properWorkProbability);

        var avgRecoveryTimeRank = RankingElements.getAvgRecoveryTimeRank(recoveryTimes, allowedRecoveryTime);
        System.out.println("ОЭ для H0501 (Оценка времени восстановления): " + avgRecoveryTimeRank);

        var processingTimeRank = RankingElements.getProcessingTimeRank(processingTimes, allowedProcessingTime);
        System.out.println("ОЭ для H0502 (Оценка времени преобразования): " + processingTimeRank);

        // Расчет Метрик
        System.out.println("\n=======================================================");
        System.out.println("\n--- Расчет метрик ---\n");

        var finalMetricValue = EstimatingMetrics.getFinalMetricValue(properWorkProbability);
        System.out.println("Метрика H0401: " + finalMetricValue);

        var finalMetricValues = EstimatingMetrics.getFinalMetricValue(avgRecoveryTimeRank, processingTimeRank);
        System.out.println("Метрика H0501-H0502 (среднее двух ОЭ): " + finalMetricValues);

        // Расчет Абсолютного Показателя
        System.out.println("\n=======================================================");
        System.out.println("\n--- Расчет абсолютного показателя ---\n");

        // Весовые коэффициенты метрик
        double[] metrics = {finalMetricValue, finalMetricValues};
        double[] metricWeights = {1.0, 1.0};

        var absoluteIndicator = EstimatingMetrics.getAbsoluteMetricValue(metrics, metricWeights);
        System.out.println("P_ij = " + absoluteIndicator);

        // Расчет Относительного Показателя
        System.out.println("\n=======================================================");
        System.out.println("\n--- Расчет относительного показателя ---\n");

        double relativeIndicator = EstimatingMetrics.getRelativeMetricValue(absoluteIndicator, baseCriterion);
        System.out.println("K_ij = " + relativeIndicator);

        // Расчет Фактора Надежности
        System.out.println("\n=======================================================");
        System.out.println("\n--- Расчет фактора надежности ---\n");

        double[] relativeIndicators = {relativeIndicator};
        double[] criterionWeights = {1.0};

        var qualityFactor = EstimatingMetrics.getQualityValue(relativeIndicators, criterionWeights);
        System.out.println("K^Ф = " + qualityFactor);

        System.out.println("\n=======================================================");
    }
}
