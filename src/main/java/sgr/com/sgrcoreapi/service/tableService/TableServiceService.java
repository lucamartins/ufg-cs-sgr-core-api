package sgr.com.sgrcoreapi.service.tableService;

import static sgr.com.sgrcoreapi.converters.tableservice.TableServiceConversionUtil.toClosingTableServiceDetails;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.converters.table.TableConversionUtil;
import sgr.com.sgrcoreapi.converters.tableservice.TableServiceConversionUtil;
import sgr.com.sgrcoreapi.converters.user.UserConversionUtil;
import sgr.com.sgrcoreapi.domain.order.OrderStatusEnum;
import sgr.com.sgrcoreapi.domain.saleitem.SaleItem;
import sgr.com.sgrcoreapi.domain.table.CustomerTableRepository;
import sgr.com.sgrcoreapi.domain.tableservice.TableService;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceRepository;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceStatus;
import sgr.com.sgrcoreapi.domain.user.UserRepository;
import sgr.com.sgrcoreapi.domain.user.UserRoleEnum;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;
import sgr.com.sgrcoreapi.infra.exception.custom.NotFoundException;
import sgr.com.sgrcoreapi.service.order.dto.ClosingTableServiceOrderDetails;
import sgr.com.sgrcoreapi.service.table.dto.TableDetails;
import sgr.com.sgrcoreapi.service.tableService.dto.AddTableServiceRequest;
import sgr.com.sgrcoreapi.service.tableService.dto.CloseTableServiceRequest;
import sgr.com.sgrcoreapi.service.tableService.dto.ClosingTableServiceDetails;
import sgr.com.sgrcoreapi.service.tableService.dto.TableServiceDetails;
import sgr.com.sgrcoreapi.service.user.dto.TableServiceResponsibleUser;

@Service
@RequiredArgsConstructor
public class TableServiceService {
    private final TableServiceRepository tableServiceRepository;
    private final CustomerTableRepository customerTableRepository;
    private final UserRepository userRepository;

    public void createTableService(AddTableServiceRequest addTableServiceRequest) {
        TableService tableService = new TableService();

        var table = customerTableRepository.findByIdAndIsDeletedFalse(addTableServiceRequest.tableId())
                .orElseThrow(() -> new BadRequestException("Table not found"));
        var waiter = userRepository.findByIdAndIsDeletedFalse(addTableServiceRequest.waiterId())
                .orElseThrow(() -> new BadRequestException("Waiter not found"));

        if(!waiter.getRole().equals(UserRoleEnum.WAITER)) {
            throw new BadRequestException("User is not a waiter");
        }

        if(!table.isAvailable()) {
            throw new BadRequestException("Table is not available");
        }

        table.changeAvailability();
        tableService.setCustomerTable(table);
        tableService.setWaiter(waiter);
        tableServiceRepository.save(tableService);
    }

    public Page<TableServiceDetails> getTableServices(TableServiceStatus tableServiceStatus, UUID waiterId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        var tableServicesPage = tableServiceRepository.findByStatusAndWaiter(tableServiceStatus, waiterId, pageable);

        List<TableServiceDetails> detailsList = tableServicesPage.getContent().stream()
                .map(tableService -> {

                    TableDetails tableDetails = customerTableRepository.findById(tableService.getCustomerTable().getId())
                            .map(TableConversionUtil::toTableDetails)
                            .orElseThrow(() -> new BadRequestException("Table not found"));

                    TableServiceResponsibleUser tableServiceResponsibleUser = userRepository.findById(tableService.getWaiter().getId())
                            .map(UserConversionUtil::toTableServiceResponsibleUser)
                            .orElseThrow(() -> new BadRequestException("Waiter not found"));

                    return TableServiceConversionUtil.toTableServiceDetails(tableService, tableDetails, tableServiceResponsibleUser);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(detailsList, pageable, tableServicesPage.getTotalElements());
    }

    public TableServiceDetails getTableServiceDetails(UUID tableServiceId) {
        var tableService = tableServiceRepository.findById(tableServiceId)
                .orElseThrow(NotFoundException::new);

        var tableDetails = customerTableRepository.findById(tableService.getCustomerTable().getId())
                .map(TableConversionUtil::toTableDetails)
                .orElseThrow(() -> new BadRequestException("Table not found"));

        var tableServiceResponsibleUser = userRepository.findById(tableService.getWaiter().getId())
                .map(UserConversionUtil::toTableServiceResponsibleUser)
                .orElseThrow(() -> new BadRequestException("Waiter not found"));

        return TableServiceConversionUtil.toTableServiceDetails(tableService, tableDetails, tableServiceResponsibleUser);
    }

    public ClosingTableServiceDetails getClosingTableServiceDetails(UUID tableServiceId) {
        var tableService = tableServiceRepository.findById(tableServiceId)
                .orElseThrow(NotFoundException::new);
        var orders = tableService.getOrders();

        var dueAmount = orders.stream()
                .flatMap(order -> order.getSaleItems().stream())
                .mapToDouble(SaleItem::getPrice)
                .sum();

        var closingTableServiceOrderDetails = orders.stream()
                .map(ClosingTableServiceOrderDetails::new)
                .collect(Collectors.toList());

        return toClosingTableServiceDetails(closingTableServiceOrderDetails, dueAmount);
    }

    public void closeTableService(UUID tableServiceId, CloseTableServiceRequest closeTableServiceRequest) {
        var tableService = tableServiceRepository.findById(tableServiceId)
                .orElseThrow(NotFoundException::new);

        var orders = tableService.getOrders();
        boolean hasPendingOrders = orders.stream()
                .anyMatch(order -> order.getStatus() == OrderStatusEnum.PENDING);

        if (hasPendingOrders) {
            throw new BadRequestException("Existem pedidos pendentes para este serviço de mesa.");
        }

        if(tableService.getStatus() == TableServiceStatus.FINISHED) {
            throw new BadRequestException("Atendimento já finalizado");
        }

        tableService.setPaidAmount(closeTableServiceRequest.paidAmount());
        tableService.setStatus(TableServiceStatus.FINISHED);
        tableService.getCustomerTable().changeAvailability();
    }
}
