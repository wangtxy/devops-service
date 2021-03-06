package io.choerodon.devops.infra.dto;

import java.util.Date;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;

/**
 * Created by n!Ck
 * Date: 2018/8/20
 * Time: 19:51
 * Description:
 */
@ModifyAudit
@VersionAudit
@Table(name = "devops_certification")
public class CertificationDTO extends AuditDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long certificationFileId;
    /**
     * 项目层证书需要组织id
     */
    private Long organizationId;
    private String name;
    private Long envId;
    private String domains;
    private Long commandId;
    private String status;
    private Date validFrom;
    private Date validUntil;
    private Boolean skipCheckProjectPermission;
    private Long orgCertId;
    private Long projectId;

    @Transient
    @ApiModelProperty("key文件内容")
    private String keyValue;
    @Transient
    @ApiModelProperty("cert文件内容")
    private String certValue;
    @Transient
    private String commandType;
    @Transient
    private String commandStatus;
    @Transient
    private String error;

    public CertificationDTO() {
    }

    /**
     * Certification constructor
     *
     * @param name    Certification's name
     * @param envId   Certification's environment ID
     * @param domains Certification's domains json format
     * @param status  Certification's status
     */
    public CertificationDTO(String name, Long envId, String domains, String status) {
        this.name = name;
        this.envId = envId;
        this.domains = domains;
        this.status = status;
    }

    public CertificationDTO(Long id, String name, Long envId, String domains, String status, Long orgCertId) {
        this.id = id;
        this.name = name;
        this.envId = envId;
        this.domains = domains;
        this.status = status;
        this.orgCertId = orgCertId;
    }

    /**
     * Certification constructor
     *
     * @param name  Certification's name
     * @param envId Certification's enviroment ID
     */
    public CertificationDTO(String name, Long envId) {
        this.name = name;
        this.envId = envId;
    }

    public void setValid(Date from, Date until) {
        this.setValidFrom(from);
        this.setValidUntil(until);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public String getDomains() {
        return domains;
    }

    public void setDomains(String domains) {
        this.domains = domains;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(String commandStatus) {
        this.commandStatus = commandStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getCertificationFileId() {
        return certificationFileId;
    }

    public void setCertificationFileId(Long certificationFileId) {
        this.certificationFileId = certificationFileId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getSkipCheckProjectPermission() {
        return skipCheckProjectPermission;
    }

    public void setSkipCheckProjectPermission(Boolean skipCheckProjectPermission) {
        this.skipCheckProjectPermission = skipCheckProjectPermission;
    }

    public Long getOrgCertId() {
        return orgCertId;
    }

    public void setOrgCertId(Long orgCertId) {
        this.orgCertId = orgCertId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getCertValue() {
        return certValue;
    }

    public void setCertValue(String certValue) {
        this.certValue = certValue;
    }
}
