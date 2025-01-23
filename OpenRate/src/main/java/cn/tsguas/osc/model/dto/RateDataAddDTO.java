package cn.tsguas.osc.model.dto;

import cn.tsguas.osc.model.domain.Metric;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author fsq
 * @since 2025-01-21
 */
@Data
public class RateDataAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String repoName;


    private String repoOwner;


    private String prTitle;


    private Integer prNumber;


    private Double workload;


    private Integer lines;


    private  List<Metric>  metrics;

}
