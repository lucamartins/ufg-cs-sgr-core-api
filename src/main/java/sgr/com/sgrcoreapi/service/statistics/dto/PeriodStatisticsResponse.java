package sgr.com.sgrcoreapi.service.statistics.dto;

public record PeriodStatisticsResponse(
        Integer tableServicesCount,
        Integer ordersCount,
        double paidAmount
) {
    public PeriodStatisticsResponse(Integer tableServicesCount, Integer ordersCount, double paidAmount) {
        this.tableServicesCount = tableServicesCount;
        this.ordersCount = ordersCount;
        this.paidAmount = paidAmount;
    }
}
