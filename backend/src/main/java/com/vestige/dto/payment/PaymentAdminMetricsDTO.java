package com.vestige.dto.payment;

import com.vestige.dto.analytics.TimeSeriesDataPoint;
import java.math.BigDecimal;
import java.util.List;

public class PaymentAdminMetricsDTO {

    private long totalPayments;
    private long successfulPayments;
    private long pendingPayments;
    private long failedPayments;
    private BigDecimal totalRevenue;
    private List<TimeSeriesDataPoint> revenueOverTime;
    private List<PaymentSummaryResponse> recentTransactions;

    public PaymentAdminMetricsDTO() {
    }

    public PaymentAdminMetricsDTO(long totalPayments, long successfulPayments, long pendingPayments,
                                  long failedPayments, BigDecimal totalRevenue,
                                  List<TimeSeriesDataPoint> revenueOverTime,
                                  List<PaymentSummaryResponse> recentTransactions) {
        this.totalPayments = totalPayments;
        this.successfulPayments = successfulPayments;
        this.pendingPayments = pendingPayments;
        this.failedPayments = failedPayments;
        this.totalRevenue = totalRevenue;
        this.revenueOverTime = revenueOverTime;
        this.recentTransactions = recentTransactions;
    }

    public long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public long getSuccessfulPayments() {
        return successfulPayments;
    }

    public void setSuccessfulPayments(long successfulPayments) {
        this.successfulPayments = successfulPayments;
    }

    public long getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(long pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public long getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(long failedPayments) {
        this.failedPayments = failedPayments;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<TimeSeriesDataPoint> getRevenueOverTime() {
        return revenueOverTime;
    }

    public void setRevenueOverTime(List<TimeSeriesDataPoint> revenueOverTime) {
        this.revenueOverTime = revenueOverTime;
    }

    public List<PaymentSummaryResponse> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<PaymentSummaryResponse> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
