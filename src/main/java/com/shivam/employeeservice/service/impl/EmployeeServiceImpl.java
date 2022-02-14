//package com.shivam.employeeservice.service.impl;
//
//import com.shivam.employeeservice.constants.ApiConstants;
//import com.shivam.employeeservice.constants.EntityConstants;
//import com.shivam.employeeservice.dto.EmployeeRequest;
//import com.shivam.employeeservice.dto.EmployeeResponse;
//import com.shivam.employeeservice.dto.filter.FilterCriteria;
//import com.shivam.employeeservice.dto.filter.SearchCriteria;
//import com.shivam.employeeservice.dto.filter.SearchOperation;
//import com.shivam.employeeservice.entity.Employee;
//import com.shivam.employeeservice.repository.EmployeeRepository;
//import com.shivam.employeeservice.repository.TeamRepository;
//import com.shivam.employeeservice.repository.specification.builder.EmployeeSpecificationsBuilder;
//import com.shivam.employeeservice.service.EmployeeService;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Assert;
//import org.springframework.util.CollectionUtils;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@Log4j2
//public class EmployeeServiceImpl implements EmployeeService {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private TeamRepository teamRepository;
//
//    @Override
//    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public Page<EmployeeResponse> search(final FilterCriteria filterCriteria) {
//        log.info(ApiConstants.SEARCH_employee_WITH_CRITERIA, filterCriteria);
//        List<SearchCriteria> params = filterCriteria.getCriterion();
//        if (CollectionUtils.isEmpty(params)) {
//            params = new ArrayList<>();
//        }
//        if (ObjectUtils.allNotNull(filterCriteria.getFromDate(), filterCriteria.getToDate())) {
//            params.add(
//                    new SearchCriteria(EntityConstants.JOINING_DATE, SearchOperation.GREATER_THAN_OR_EQUAL_TO,
//                            filterCriteria.getFromDate()));
//            params
//                    .add(new SearchCriteria(EntityConstants.JOINING_DATE, SearchOperation.LESS_THAN_OR_EQUAL_TO,
//                            filterCriteria.getToDate()));
//        }
//        final EmployeeSpecificationsBuilder builder = new EmployeeSpecificationsBuilder(
//                filterCriteria.getFullTextSearch(), params, Collections.EMPTY_LIST, null);
//        final Specification<Employee> specification = builder.build();
//        final int page = filterCriteria.getPage();
//        final int limit = filterCriteria.getLimit();
//        final Pageable pageable = PageRequest
//                .of(page, limit, Sort.by(Sort.Direction.fromString(filterCriteria.getSortDir()),
//                        filterCriteria.getSort()));
//        final Page<Employee> pagedShipment = this.employeeRepository
//                .findAll(specification, pageable);
//        final List<Employee> employees = pagedShipment.getContent();
//        final List<EmployeeResponse> EmployeeResponseList = employeeMapper.INSTANCE
//                .mapToEmployeeResponse(employees);
//        return new PageImpl<>(EmployeeResponseList, pagedShipment.getPageable(),
//                pagedShipment.getTotalElements());
//    }
//
//    @Override
//    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public EmployeeResponse getById(final Long id) {
//
//        final Optional<Employee> employeeOptional = this.employeeRepository
//                .findByIdAndDeletedFalse(id);
//        if (!employeeOptional.isPresent()) {
//            throw new RuntimeException("");
//        }
//        final Employee employee = employeeOptional.get();
//        final EmployeeResponse EmployeeResponse = employeeMapper.INSTANCE.mapToEmployeeResponse(employee);
//        return EmployeeResponse;
//    }
//
//
//    @Override
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public EmployeeResponse create(final EmployeeRequest EmployeeRequest) {
//        final Employee employee = employeeMapper.INSTANCE.mapToemployeeCreate(EmployeeRequest);
//        this.employeeRepository.save(employee);
//        return employeeMapper.INSTANCE.mapToEmployeeResponse(employee);
//    }
//
//    private void updateOpeningBalanceInBooks(final employee employee, final BigDecimal employeeAccountAmount,
//                                             final BigDecimal openingAccumulatedDepreciationAmount) {
//
//        final Optional<employeeGroup> employeeGroupOptional = this.TeamRepository
//                .findByIdAndTenantIdAndDeletedFalse(employee.getemployeeGroup().getId(),
//                        this.userSessionProvider.get().getTenantId());
//        if (!employeeGroupOptional.isPresent()) {
//            return;
//        }
//        final employeeGroup employeeGroup = employeeGroupOptional.get();
//        final List<AccountBalanceRequestDto> accountBalanceRequestDtoList = new ArrayList<>();
//        if (BigDecimal.ZERO.compareTo(employeeAccountAmount) != 0) {
//            final AccountBalanceRequestDto employeeAccountRequest = new AccountBalanceRequestDto();
//            employeeAccountRequest.setAccountCode(employeeGroup.getemployeeAccountCode());
//            employeeAccountRequest.setCdType(CREDIT_DEBIT_TYPE.DEBIT);
//            employeeAccountRequest.setOpeningBalance(employeeAccountAmount);
//            accountBalanceRequestDtoList.add(employeeAccountRequest);
//        }
//        if (BigDecimal.ZERO.compareTo(openingAccumulatedDepreciationAmount) != 0) {
//            final AccountBalanceRequestDto accumulatedDepreciationAccountRequest = new AccountBalanceRequestDto();
//            accumulatedDepreciationAccountRequest
//                    .setAccountCode(employeeGroup.getemployeeAccumulatedAccountCode());
//            accumulatedDepreciationAccountRequest.setCdType(CREDIT_DEBIT_TYPE.CREDIT);
//            accumulatedDepreciationAccountRequest.setOpeningBalance(openingAccumulatedDepreciationAmount);
//            accountBalanceRequestDtoList.add(accumulatedDepreciationAccountRequest);
//        }
//        if (!CollectionUtils.isEmpty(accountBalanceRequestDtoList)) {
//            this.externalApiService.updateOpeningBalance(accountBalanceRequestDtoList);
//        }
//    }
//
//    private void validateEmployeeRequest(final EmployeeRequest EmployeeRequest, final TenantInfo tenantInfo) {
//        if (StringUtils.isBlank(EmployeeRequest.getName())) {
//            throw new BadRequestException(ErrorMessageConstants.NAME_IS_REQUIRED);
//        }
//        if (Util.isNullOrZero(EmployeeRequest.getPurchasePrice())) {
//            throw new BadRequestException(ErrorMessageConstants.PURCHASE_PRICE_IS_REQUIRED);
//        }
//        if (!EmployeeRequest.isOpeningemployee() && StringUtils.isBlank(EmployeeRequest.getExpenseBillCode())) {
//            throw new BadRequestException(ErrorMessageConstants.EXPENSE_BILL_IS_REQUIRED);
//        }
//        this.validateemployeeGroupInEmployeeRequest(EmployeeRequest);
//        this.validatePurchaseAndDepreciationStartDates(EmployeeRequest,
//                tenantInfo.getBookBeginningStartDate());
//        final DepreciationDetails depreciationDetails = employeeMapper.INSTANCE
//                .mapToDepreciationDetails(EmployeeRequest);
//        this.employeeHandler.validateDepreciationDetails(depreciationDetails);
//        this.populateDefaultValues(EmployeeRequest, tenantInfo);
//        this.roundAmounts(EmployeeRequest);
//    }
//
//    private void validateemployeeGroupInEmployeeRequest(final EmployeeRequest EmployeeRequest) {
//        Assert.notNull(EmployeeRequest.getemployeeGroupId(),
//                ErrorMessageConstants.employee_GROUP_ID_CANNOT_BE_NULL);
//        final Optional<employeeGroup> employeeGroupOptional = this.TeamRepository
//                .findByIdAndTenantIdAndDeletedFalse(EmployeeRequest.getemployeeGroupId(),
//                        this.userSessionProvider.get().getTenantId());
//        if (!employeeGroupOptional.isPresent()) {
//            throw new BadRequestException(String
//                    .format(ErrorMessageConstants.employee_GROUP_NOT_FOUND, EmployeeRequest.getemployeeGroupId()));
//        }
//    }
//
//    private void validatePurchaseAndDepreciationStartDates(final EmployeeRequest EmployeeRequest,
//                                                           final Date bookBeginningDate) {
//        if (EmployeeRequest.isOpeningemployee()) {
//            if (!EmployeeRequest.getPurchaseDate().before(bookBeginningDate)) {
//                throw new BadRequestException(
//                        ErrorMessageConstants.PURCHASE_DATE_OF_OPENING_employee_MUST_BE_BEFORE_BOOK_BEGINNING);
//            }
//            if (!EmployeeRequest.getDepreciationStartDate().before(bookBeginningDate)) {
//                throw new BadRequestException(
//                        ErrorMessageConstants.DEPRECIATION_START_DATE_OF_OPENING_employee_MUST_BE_BEFORE_BOOK_BEGINNING);
//            }
//        } else {
//            if (EmployeeRequest.getDepreciationStartDate().before(bookBeginningDate)) {
//                throw new BadRequestException(
//                        ErrorMessageConstants.DEPRECIATION_START_DATE_MUST_BE_AFTER_BOOK_BEGINNING);
//            }
//        }
//    }
//
//    private void populateDefaultValues(final EmployeeRequest EmployeeRequest, final TenantInfo tenantInfo) {
//        if (EmployeeRequest.getCurrency() == null) {
//            EmployeeRequest.setCurrency(tenantInfo.getCurrency());
//        }
//        switch (EmployeeRequest.getDepreciationMethod()) {
//            case STRAIGHT_LINE:
//                EmployeeRequest.setDecliningFactor(null);
//                break;
//            case DECLINING_BALANCE:
//                EmployeeRequest.setDepreciationRate(null);
//                break;
//            case INSTANT_employee_WRITE_OFF:
//            case NO_DEPRECIATION:
//                EmployeeRequest.setDepreciationRate(null);
//                EmployeeRequest.setEffectiveLife(null);
//                EmployeeRequest.setDecliningFactor(null);
//                EmployeeRequest.setDepreciationConvention(null);
//                break;
//        }
//    }
//
//    private void roundAmounts(final EmployeeRequest EmployeeRequest) {
//        EmployeeRequest.setPurchasePrice(EmployeeRequest.getPurchasePrice()
//                .setScale(AppConstants.DECIMAL_PRECISION_2, BigDecimal.ROUND_HALF_EVEN));
//        if (ObjectUtils.isNotEmpty(EmployeeRequest.getDepreciationThreshold())) {
//            EmployeeRequest.setDepreciationThreshold(EmployeeRequest.getDepreciationThreshold()
//                    .setScale(AppConstants.DECIMAL_PRECISION_2, BigDecimal.ROUND_HALF_EVEN));
//        }
//        if (ObjectUtils.isNotEmpty(EmployeeRequest.getResidualValue())) {
//            EmployeeRequest.setResidualValue(EmployeeRequest.getResidualValue()
//                    .setScale(AppConstants.DECIMAL_PRECISION_2, BigDecimal.ROUND_HALF_EVEN));
//        }
//        if (ObjectUtils.isNotEmpty(EmployeeRequest.getOpeningAccumulatedDepreciation())) {
//            EmployeeRequest.setOpeningAccumulatedDepreciation(
//                    EmployeeRequest.getOpeningAccumulatedDepreciation()
//                            .setScale(AppConstants.DECIMAL_PRECISION_2, BigDecimal.ROUND_HALF_EVEN));
//        }
//    }
//
//    private void populateemployeeAmounts(final List<employee> employeeList) {
//        if (CollectionUtils.isEmpty(employeeList)) {
//            return;
//        }
//        final TenantInfo tenantInfo = this.externalApiService.getTenantInfo();
//        employeeList.forEach(employee -> {
//            final BigDecimal foreignToBaseExchangeRate = this
//                    .employeeHandler.getForeignToBaseCurrencyExchangeRate(employee.getCurrency(), tenantInfo);
//            final BigDecimal purchasePriceInBaseCurrency = employee.getPurchasePrice()
//                    .multiply(foreignToBaseExchangeRate);
//            employee.setBookValue(purchasePriceInBaseCurrency
//                    .setScale(AppConstants.DECIMAL_PRECISION_2, BigDecimal.ROUND_HALF_EVEN));
//            employee.setNetBookValue(
//                    (purchasePriceInBaseCurrency.subtract(employee.getPostedDepreciationAmount()).subtract(
//                            Objects.nonNull(employee.getOpeningAccumulatedDepreciation()) ? employee
//                                    .getOpeningAccumulatedDepreciation() : BigDecimal.ZERO))
//                            .setScale(AppConstants.DECIMAL_PRECISION_2, BigDecimal.ROUND_HALF_EVEN));
//        });
//    }
//
//    @Override
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public EmployeeResponse update(final EmployeeRequest EmployeeRequest) {
//        Assert.notNull(EmployeeRequest, ErrorMessageConstants.employee_INVALID_REQUEST_OBJECT);
//        Assert.notNull(EmployeeRequest.getId(), ErrorMessageConstants.employee_ID_CANNOT_BE_NULL);
//        final UserSession userSession = this.userSessionProvider.get();
//        final Long tenantId = userSession.getTenantId();
//
//        final Optional<employee> employeeOptional = this.EmployeeRepository
//                .findByIdAndTenantIdAndDeletedFalse(EmployeeRequest.getId(), tenantId);
//        if (!employeeOptional.isPresent()) {
//            log.error(String.format(ErrorMessageConstants.employee_NOT_FOUND, EmployeeRequest.getId()));
//            throw new RecordNotFoundException(
//                    String.format(ErrorMessageConstants.employee_NOT_FOUND, EmployeeRequest.getId()));
//        }
//        final employee employee = employeeOptional.get();
//        if (employee.isViewOnly()) {
//            log.error(String.format(ErrorMessageConstants.employee_IS_NON_EDITABLE, EmployeeRequest.getId()));
//            throw new BadRequestException(
//                    String.format(ErrorMessageConstants.employee_IS_NON_EDITABLE, EmployeeRequest.getId()));
//        }
//        final TenantInfo tenantInfo = this.externalApiService.getTenantInfo();
//        this.validateEmployeeRequest(EmployeeRequest, tenantInfo);
//        this.depreciationScheduleService.hardDeleteByemployee(employee);
//        if (employee.isOpeningemployee() || EmployeeRequest.isOpeningemployee()) {
//            final BigDecimal employeeAmtDiff;
//            final BigDecimal accumulatedDeprAmtDiff;
//            final BigDecimal oldemployeeAmt =
//                    Objects.nonNull(employee.getPurchasePrice()) ? employee.getPurchasePrice() : BigDecimal.ZERO;
//            final BigDecimal oldAccumulatedDeprAmt =
//                    Objects.nonNull(employee.getOpeningAccumulatedDepreciation()) ? employee
//                            .getOpeningAccumulatedDepreciation() : BigDecimal.ZERO;
//            final BigDecimal newemployeeAmt =
//                    Objects.nonNull(EmployeeRequest.getPurchasePrice()) ? EmployeeRequest.getPurchasePrice()
//                            : BigDecimal.ZERO;
//            final BigDecimal newAccumulatedDeprAmt =
//                    Objects.nonNull(EmployeeRequest.getOpeningAccumulatedDepreciation()) ? EmployeeRequest
//                            .getOpeningAccumulatedDepreciation() : BigDecimal.ZERO;
//
//            if (employee.isOpeningemployee() && EmployeeRequest.isOpeningemployee()) {
//                employeeAmtDiff = newemployeeAmt.subtract(oldemployeeAmt);
//                accumulatedDeprAmtDiff = newAccumulatedDeprAmt.subtract(oldAccumulatedDeprAmt);
//            } else if (employee.isOpeningemployee()) {
//                employeeAmtDiff = BigDecimal.ZERO.subtract(oldemployeeAmt);
//                accumulatedDeprAmtDiff = BigDecimal.ZERO.subtract(oldAccumulatedDeprAmt);
//                employee.setOpeningAccumulatedDepreciation(BigDecimal.ZERO);
//            } else {
//                accumulatedDeprAmtDiff = newAccumulatedDeprAmt;
//                employeeAmtDiff = newemployeeAmt;
//            }
//            this.updateOpeningBalanceInBooks(employee, employeeAmtDiff, accumulatedDeprAmtDiff);
//        }
//        employee.setemployeeGroup(null);
//        employeeMapper.INSTANCE.mapToemployeeEntity(EmployeeRequest, employee);
//        employee.setUpdatedBy(userSession.getId());
//        this.EmployeeRepository.save(employee);
//        this.depreciationScheduleService.createForemployee(employee);
//        return employeeMapper.INSTANCE.mapToEmployeeResponse(employee);
//    }
//
//    @Override
//    public void deleteByExpenseCode(final String code) {
//        final UserSession userSession = this.userSessionProvider.get();
//        final Long tenantId = userSession.getTenantId();
//        final Optional<employee> employeeOptional = this.EmployeeRepository
//                .findByExpenseBillCodeAndTenantIdAndDeletedFalse(code, tenantId);
//        if (!employeeOptional.isPresent()) {
//            log.error(String.format(ErrorMessageConstants.employee_NOT_FOUND_FOR_EXPENSE, code));
//            throw new RecordNotFoundException(String.format(ErrorMessageConstants.employee_NOT_FOUND_FOR_EXPENSE, code));
//        }
//        final employee employee = employeeOptional.get();
//        this.deleteemployee(employee);
//    }
//
//    @Override
//    public void delete(final Long id) {
//        Assert.notNull(id, ErrorMessageConstants.employee_ID_CANNOT_BE_NULL);
//        final UserSession userSession = this.userSessionProvider.get();
//        final Long tenantId = userSession.getTenantId();
//        final Optional<employee> employeeOptional = this.EmployeeRepository
//                .findByIdAndTenantIdAndDeletedFalse(id, tenantId);
//        if (!employeeOptional.isPresent()) {
//            log.error(String.format(ErrorMessageConstants.employee_NOT_FOUND, id));
//            throw new RecordNotFoundException(String.format(ErrorMessageConstants.employee_NOT_FOUND, id));
//        }
//        final employee employee = employeeOptional.get();
//        this.deleteemployee(employee);
//    }
//
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void deleteemployee(final employee employee) {
//        if (employee.isViewOnly()) {
//            log.error(String.format(ErrorMessageConstants.employee_IS_NON_DELETABLE, employee.getId()));
//            throw new BadRequestException(
//                    String.format(ErrorMessageConstants.employee_IS_NON_DELETABLE, employee.getId()));
//        }
//        if (employee.isOpeningemployee()) {
//            final BigDecimal employeeAmtDiff = BigDecimal.ZERO.subtract(
//                    Objects.nonNull(employee.getPurchasePrice()) ? employee.getPurchasePrice() : BigDecimal.ZERO);
//            final BigDecimal accumulatedDeprAmtDiff = BigDecimal.ZERO
//                    .subtract(Objects.nonNull(employee.getOpeningAccumulatedDepreciation()) ? employee
//                            .getOpeningAccumulatedDepreciation() : BigDecimal.ZERO);
//            this.updateOpeningBalanceInBooks(employee, employeeAmtDiff, accumulatedDeprAmtDiff);
//        }
//        this.depreciationScheduleService.softDeleteByemployee(employee);
//        employee.setUpdatedBy(this.userSessionProvider.get().getId());
//        employee.delete();
//        this.EmployeeRepository.save(employee);
//        this.releaseDocumentSequenceCode(employee);
//    }
//
//    @Override
//    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public NetBookValueResponse getTotalNetBookValueInPurchaseDateRange(final Date startDate,
//                                                                        final Date endDate) {
//        Assert.notNull(startDate, ErrorMessageConstants.START_DATE_CANNOT_BE_NULL);
//        Assert.notNull(endDate, ErrorMessageConstants.END_DATE_CANNOT_BE_NULL);
//        final UserSession userSession = this.userSessionProvider.get();
//        final List<employee> employeeList = this.EmployeeRepository
//                .findByPurchaseDateBetweenAndTenantIdAndDeletedFalse(startDate, endDate,
//                        userSession.getTenantId());
//        if (CollectionUtils.isEmpty(employeeList)) {
//            return new NetBookValueResponse(BigDecimal.ZERO);
//        }
//        this.populateemployeeAmounts(employeeList);
//        final Optional<BigDecimal> optionalTotalAmount = employeeList.stream().map(employee::getNetBookValue)
//                .filter(Objects::nonNull)
//                .reduce(BigDecimal::add);
//        return new NetBookValueResponse(optionalTotalAmount.orElse(BigDecimal.ZERO));
//    }
//
//    private void updateDocumentSequenceCode(final employee employee) {
//        if (StringUtils.isNotBlank(employee.getSequenceFormat()) || employee
//                .isGenerateSeqCodeWithDefaultSeqFormat()) {
//            log.debug(ApiConstants.GENERATING_SEQUENCE_CODE_FOR_employee, employee.getId());
//            final String nextSequenceCode = this.documentSequenceService
//                    .updateDocumentSequence(employee.getSequenceFormat());
//            employee.setDocumentSequenceCode(nextSequenceCode);
//            log.debug(ApiConstants.GENERATED_SEQUENCE_CODE_FOR_employee, employee.getDocumentSequenceCode(),
//                    employee.getId());
//        } else {
//            log.debug(ApiConstants.DUMPING_SEQUENCE_CODE_FOR_employee, employee.getDocumentSequenceCode(),
//                    employee.getId());
//            this.documentSequenceService.dumpSequenceCode(employee.getDocumentSequenceCode());
//            log.debug(ApiConstants.DUMPED_SEQUENCE_CODE_FOR_employee, employee.getDocumentSequenceCode(),
//                    employee.getId());
//        }
//    }
//
//    private void releaseDocumentSequenceCode(final employee employee) {
//        if (StringUtils.isNotBlank(employee.getDocumentSequenceCode())) {
//            log.debug(ApiConstants.RELEASING_SEQUENCE_CODE_FOR_employee, employee.getDocumentSequenceCode(),
//                    employee.getTenantId());
//            this.documentSequenceService.deleteDocumentSequenceCode(employee.getDocumentSequenceCode());
//            log.debug(ApiConstants.RELEASED_SEQUENCE_CODE_FOR_employee, employee.getDocumentSequenceCode(),
//                    employee.getTenantId());
//        }
//    }
//
//    private void checkDocumentSequenceCode(final employee employee) {
//        if (StringUtils.isNotBlank(employee.getDocumentSequenceCode())) {
//            if (!this.documentSequenceService.isSequenceCodeAvailable(employee.getDocumentSequenceCode())) {
//                throw new BadRequestException(
//                        String.format(ErrorMessageConstants.DOCUMENT_SEQUENCE_CODE_ALREADY_USED,
//                                employee.getDocumentSequenceCode()));
//            } else {
//                this.documentSequenceService.dumpSequenceCode(employee.getDocumentSequenceCode());
//            }
//        }
//    }
//}
