package sgr.com.sgrcoreapi.service.statistics;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.tableservice.TableService;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceRepository;
import sgr.com.sgrcoreapi.domain.user.UserRepository;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;
import sgr.com.sgrcoreapi.service.statistics.dto.PeriodStatisticsResponse;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TableServiceRepository tableServiceRepository;
    private final UserRepository userRepository;

    public PeriodStatisticsResponse getPeriodStatistics(LocalDateTime startDate, LocalDateTime endDate, UUID waiterId) {
        if (waiterId != null) {
            userRepository.findByIdAndIsDeletedFalse(waiterId).orElseThrow(() -> new BadRequestException("Waiter not found"));
        }

        var tableServices = tableServiceRepository.findByDateRangeAndWaiter(startDate, endDate, waiterId);

        var paidAmount = tableServices.stream()
                .mapToDouble(TableService::getPaidAmount)
                .sum();

        var ordersCount = tableServices.stream()
                .mapToInt(tableService -> tableService.getOrders().size())
                .sum();

        var tableServicesCount = tableServices.size();

        return new PeriodStatisticsResponse(tableServicesCount, ordersCount, paidAmount);
    }
}
