package cn.tsguas.osc.model.dto;

import cn.tsguas.osc.model.domain.Metric;
import cn.tsguas.osc.utils.JsonTypeHandler;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author fsq
 * @since 2025-01-21
 */
@Data
public class ContributionEvaluationAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String repoName;


    private String repoOwner;


    private String prTitle;


    private Integer prNumber;


    private Double workload;


    private Integer lines;


    private  List<Metric>  metrics;

}
