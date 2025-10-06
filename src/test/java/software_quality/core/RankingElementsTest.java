package software_quality.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RankingElementsTest {
    // getProperWorkProbability
    @Test
    void testGetProperWorkProbability_FailuresCount0() {
        double result = RankingElements.getProperWorkProbability(0, 1300);
        assertEquals(1.0, result);
    }

    @Test
    void testGetProperWorkProbability_FailuresCount1300() {
        double result = RankingElements.getProperWorkProbability(1300, 1300);
        assertEquals(0, result);
    }

    @Test
    void testGetProperWorkProbability_Variant3() {
        double result = RankingElements.getProperWorkProbability(7, 1300);
        assertEquals(0.9946153846153846, result);
    }

    // getAvgRecoveryTimeRank
    @Test
    void testGetAvgRecoveryTimeRank_WithinLimit() {
        double[] recoveryTime = {0.5, 0.6, 0.55, 0.58, 0.52};
        double allowedRecoveryTime = 0.65;
        double result = RankingElements.getAvgRecoveryTimeRank(recoveryTime, allowedRecoveryTime);
        assertEquals(1, result);
    }

    @Test
    void testGetAvgRecoveryTimeRank_MoreThenLimit() {
        double[] recoveryTime = {0.8, 0.9, 0.8, 0.9};
        double allowedRecoveryTime = 0.65;
        double avgTime = Arrays.stream(recoveryTime).sum() / recoveryTime.length;
        double expected = allowedRecoveryTime / avgTime;
        double result = RankingElements.getAvgRecoveryTimeRank(recoveryTime, allowedRecoveryTime);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testGetAvgRecoveryTimeRank_Match() {
        double[] recoveryTime = {0.65, 0.65, 0.65, 0.65};
        double allowedRecoveryTime = 0.65;
        double result = RankingElements.getAvgRecoveryTimeRank(recoveryTime, allowedRecoveryTime);
        assertEquals(1.0, result);
    }

    // getProcessingTimeRank
    @Test
    void testGetProcessingTimeRank_WithinLimit() {
        double[] processingTime = {8.0, 8.5, 9.0, 9.5, 10.0};
        double allowedProcessingTime = 10.0;
        double result = RankingElements.getProcessingTimeRank(processingTime, allowedProcessingTime);
        assertEquals(1, result);
    }

    @Test
    void testGetProcessingTimeRank_MoreThenLimit() {
        double[] processingTime = {12.0, 13.0, 14.0, 15.0};
        double allowedProcessingTime = 10.0;
        double avgTime = Arrays.stream(processingTime).sum() / processingTime.length;
        double expected = allowedProcessingTime / avgTime;
        double result = RankingElements.getProcessingTimeRank(processingTime, allowedProcessingTime);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testGetProcessingTimeRank_Match() {
        double[] recoveryTime = {10.0, 10.0, 10.0, 10.0, 10.0};
        double allowedRecoveryTime = 10.0;
        double result = RankingElements.getProcessingTimeRank(recoveryTime, allowedRecoveryTime);
        assertEquals(1.0, result);
    }


}
