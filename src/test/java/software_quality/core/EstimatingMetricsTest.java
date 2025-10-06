package software_quality.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EstimatingMetricsTest {

    // getFinalMetricValue
    @Test
    void testGetFinalMetricValue_SingleValue() {
        double value = 0.9946;
        double result =  EstimatingMetrics.getFinalMetricValue(value);
        assertEquals(value, result, 0.0001);
    }

    @Test
    void testGetFinalMetricValue_TwoValues() {
        double[] values = {0.8125, 0,7692};
        double expected = Arrays.stream(values).sum() / values.length;
        double result =  EstimatingMetrics.getFinalMetricValue(values);
        assertEquals(expected, result, 0.0001);
    }

    @Test
    void testGetFinalMetricValue_EmptyArray() {
        double[] values = {};
        double result =  EstimatingMetrics.getFinalMetricValue(values);
        assertEquals(0.0, result, 0.0001);
    }

    // getAbsoluteMetricValue
    @Test
    void testGetAbsoluteMetricValue_DiffWeights() {
        double[] metrics = {0.5, 0.8};
        double[] weights = {2.0, 3.0};
        double result = EstimatingMetrics.getAbsoluteMetricValue(metrics, weights);
        double expected = metrics[0] * weights[0] + metrics[1] * weights[1];
        assertEquals(expected, result, 0.0001);
    }

    @Test
    void testGetAbsoluteMetricValue_EmptyArrays() {
        double[] metrics = {};
        double[] weights = {};
        double result = EstimatingMetrics.getAbsoluteMetricValue(metrics, weights);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void testGetAbsoluteMetricValue_MismatchedArrayLengths() {
        double[] metrics = {0.5, 0.8, 0.9};
        double[] weights = {1.0, 2.0};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> EstimatingMetrics.getAbsoluteMetricValue(metrics, weights));
    }

    // getRelativeMetricValue
    @Test
    void testGetRelativeMetricValue_Variant3() {
        double absoluteIndicator = 1.7859824061155272;
        double baseValue = 0.9;
        double result = EstimatingMetrics.getRelativeMetricValue(absoluteIndicator, baseValue);
        double expected = 1.7859824061155272 / 0.9;
        assertEquals(expected, result, 0.0001);
    }

    // getQualityValue
    @Test
    void testGetQualityValue_DiffWeights() {
        double[] indicators = {0.5, 0.8};
        double[] weights = {2.0, 3.0};
        double result = EstimatingMetrics.getQualityValue(indicators, weights);
        double expected = indicators[0] * weights[0] + indicators[1] * weights[1];
        assertEquals(expected, result, 0.0001);
    }

    @Test
    void testGetQualityValue_EmptyArrays() {
        double[] indicators = {};
        double[] weights = {};
        double result = EstimatingMetrics.getQualityValue(indicators, weights);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void testGetQualityValue_MismatchedArrayLengths() {
        double[] indicators = {0.5, 0.8, 0.9};
        double[] weights = {1.0, 2.0};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> EstimatingMetrics.getQualityValue(indicators, weights));
    }

}
