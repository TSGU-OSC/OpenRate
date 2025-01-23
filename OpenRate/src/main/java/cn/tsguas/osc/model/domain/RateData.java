package cn.tsguas.osc.model.domain;

import cn.tsguas.osc.utils.JsonTypeHandler;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author fsq
 * @since 2025-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "rate_data" ,autoResultMap = true)
public class RateData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("repo_name")
    private String repoName;

    @TableField("repo_owner")
    private String repoOwner;

    @TableField("pr_title")
    private String prTitle;

    @TableField("pr_number")
    private Integer prNumber;

    @TableField("workload")
    private Double workload;

    @TableField("`lines`")
    private Integer lines;

    @TableField(value = "metrics" ,typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private  List<Metric>  metrics;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除(0未删除 1已删除)
     */
    @TableField(value = "is_deleted",fill = FieldFill.INSERT)
    @TableLogic
    @Schema(description = "是否删除(0未删除 1已删除)")
    private String isDeleted;


}
