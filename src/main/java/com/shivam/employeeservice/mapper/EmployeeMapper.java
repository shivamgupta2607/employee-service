package com.shivam.employeeservice.mapper;

import com.shivam.employeeservice.constants.AppConstants;
import com.shivam.employeeservice.dto.EmployeeResponse;
import com.shivam.employeeservice.dto.TeamRequest;
import com.shivam.employeeservice.dto.TeamResponse;
import com.shivam.employeeservice.entity.Employee;
import com.shivam.employeeservice.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.Month;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    TeamResponse mapToTeamResponse(final Team team);

    List<TeamResponse> mapToAssetGroupResponse(final List<Team> assetGroupList);

    @Mappings(value = {
            @Mapping(target = AppConstants.ID, ignore = true),
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true),
            @Mapping(target = AppConstants.DELETED, ignore = true)
    })
    Team mapToTeamCreate(final TeamRequest teamRequest);

    @Mappings(value = {
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true)
    })
    Team mapToTeamEntity(final TeamRequest teamRequest,
                                     @MappingTarget final Team team);

    EmployeeResponse mapToAssetResponse(final Employee employee);

    List<EmployeeResponse> mapToAssetResponse(final List<Employee> assetList);

    @Mappings(value = {
            @Mapping(target = AppConstants.ID, ignore = true),
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true),
            @Mapping(target = AppConstants.DELETED, ignore = true),
            @Mapping(target = AppConstants.ASSET_GROUP_DOT_ID, source = AppConstants.ASSET_GROUP_ID)
    })
    Asset mapToAssetCreate(final TeamRequest teamRequest);

    @Mappings(value = {
            @Mapping(target = AppConstants.ID, ignore = true),
            @Mapping(target = AppConstants.DOCUMENT_SEQUENCE_CODE, ignore = true),
            @Mapping(target = AppConstants.SEQUENCE_FORMAT, ignore = true),
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true),
            @Mapping(target = AppConstants.ASSET_GROUP_DOT_ID, source = AppConstants.ASSET_GROUP_ID)
    })
    void mapToAssetEntity(final AssetRequest assetRequest, @MappingTarget final Asset asset);

    @Mappings(value = {
            @Mapping(target = AppConstants.ID, ignore = true),
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true),
            @Mapping(target = AppConstants.DELETED, ignore = true),
    })
    DepreciationSchedule mapToDepreciationScheduleCreate(
            final DepreciationScheduleDto depreciationScheduleDto);

    List<DepreciationSchedule> mapToDepreciationScheduleCreate(
            final List<DepreciationScheduleDto> depreciationScheduleDto);

    @Mappings(value = {
            @Mapping(target = AppConstants.CREATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_BY, ignore = true),
            @Mapping(target = AppConstants.UPDATED_AT, ignore = true),
            @Mapping(target = AppConstants.CREATED_AT, ignore = true)
    })
    void mapToDepreciationScheduleEntity(
            final DepreciationScheduleDto depreciationScheduleDto,
            @MappingTarget final DepreciationSchedule depreciationSchedule);


    DepreciationScheduleDto mapToDepreciationScheduleDto(
            final DepreciationSchedule depreciationSchedule);

    List<DepreciationScheduleDto> mapToDepreciationScheduleDtos(
            final List<DepreciationSchedule> depreciationSchedules);

    FiscalMonth mapToFiscalMonth(final FiscalPeriod fiscalPeriod, final Month month);

    FiscalPeriodResponse mapToFiscalPeriodResponse(final FiscalPeriod fiscalPeriod,
                                                   final List<FiscalMonth> fiscalMonths);

    AssetDepreciationEventItemDetails mapToAssetDepreciationEventItemDetails(
            final DepreciationLineItem depreciationLineItem);

    List<AssetDepreciationEventItemDetails> mapToAssetDepreciationEventItemDetailsList(
            final List<DepreciationLineItem> depreciationLineItemList);

    @Mapping(target = AppConstants.ASSET_DEPRECIATION_EVENT_ITEM_DETAILS_LIST, source = AppConstants.DEPRECIATION_LINE_ITEMS)
    AssetDepreciationEventItem mapToAssetDepreciationEventItem(
            final DepreciationItem depreciationItem);

    List<AssetDepreciationEventItem> mapToAssetDepreciationEventItemList(
            final List<DepreciationItem> depreciationItemList);

    @Mapping(target = AppConstants.ASSET_GROUP_ID, source = AppConstants.ID)
    @Mapping(target = AppConstants.ASSET_GROUP_NAME, source = AppConstants.NAME)
    AssetGroupRollbackDepreciation mapToAssetGroupRollbackDepreciation(final AssetGroup assetGroup);


    @Mapping(target = AppConstants.ASSET_GROUP_ID, source = AppConstants.ID)
    @Mapping(target = AppConstants.ASSET_GROUP_NAME, source = AppConstants.NAME)
    AssetGroupPostDepreciation mapToAssetGroupPostDepreciation(
            final AssetGroupResponse assetGroupResponse);

    DepreciationDetails mapToDepreciationDetails(final AssetRequest assetRequest);

    DepreciationDetails mapToDepreciationDetails(final AssetGroupRequest assetGroupRequest);


}