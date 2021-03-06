package io.choerodon.devops.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.choerodon.devops.infra.dto.DevopsCdJobRecordDTO;
import io.choerodon.mybatis.common.BaseMapper;

public interface DevopsCdJobRecordMapper extends BaseMapper<DevopsCdJobRecordDTO> {

    DevopsCdJobRecordDTO queryFirstByStageRecordId(@Param("stageRecordId") Long stageRecordId);

    List<DevopsCdJobRecordDTO> queryRetryJob(@Param("stageRecordId") Long stageRecordId);

    DevopsCdJobRecordDTO queryFailedOrCancelJob(@Param("stageRecordId") Long stageRecordId);

    DevopsCdJobRecordDTO queryPendingAndRunning(@Param("stageRecordId") Long stageRecordId);

    int updateNotAuditJobStatus(@Param("jobRecordId") Long jobRecordId,
                                @Param("status") String status);
}
