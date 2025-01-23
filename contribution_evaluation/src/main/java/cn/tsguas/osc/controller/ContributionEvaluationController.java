package cn.tsguas.osc.controller;


import cn.tsguas.osc.config.ResultUtils;
import cn.tsguas.osc.model.domain.ContributionEvaluation;
import cn.tsguas.osc.model.domain.Metric;
import cn.tsguas.osc.model.dto.ContributionEvaluationAddDTO;
import cn.tsguas.osc.model.vo.ResponseVO;
import cn.tsguas.osc.service.IContributionEvaluationService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fsq
 * @since 2025-01-21
 */
@ApiSupport(author = "fsq") // 接口作者
@Tag(name = "PR分析管理接口")   // 接口分组名
@Slf4j
@RestController
@RequestMapping("/contribution-evaluation")
public class ContributionEvaluationController {

    @Resource
    IContributionEvaluationService contributionEvaluationService;

    /**
     * 添加PR分析
     *
     * @return 新PR分析id
     */
    @PostMapping("/add")
    @Operation(description = "添加新PR分析")  // 接口信息描述
    public ResponseVO<Boolean> addContributionEvaluation(@RequestBody @Validated ContributionEvaluationAddDTO contributionEvaluationAddDTO) {
        ContributionEvaluation contributionEvaluation = new ContributionEvaluation();
        contributionEvaluation.setRepoName(contributionEvaluationAddDTO.getRepoName());
        contributionEvaluation.setRepoOwner(contributionEvaluationAddDTO.getRepoOwner());
        contributionEvaluation.setPrTitle(contributionEvaluationAddDTO.getPrTitle());
        contributionEvaluation.setPrNumber(contributionEvaluationAddDTO.getPrNumber());
        if(contributionEvaluationAddDTO.getWorkload() != null){
            contributionEvaluation.setWorkload(Math.round(contributionEvaluationAddDTO.getWorkload() * 100.0) / 100.0);
        }
        contributionEvaluation.setLines(contributionEvaluationAddDTO.getLines());
        contributionEvaluation.setMetrics(contributionEvaluationAddDTO.getMetrics());

        List<ContributionEvaluation> list = contributionEvaluationService.lambdaQuery()
                .eq(ContributionEvaluation::getRepoName, contributionEvaluation.getRepoName())
                .eq(ContributionEvaluation::getRepoOwner, contributionEvaluation.getRepoOwner())
                .eq(ContributionEvaluation::getPrNumber, contributionEvaluation.getPrNumber())
                .list();
        boolean i = false;
        if (list.size() > 0) {
            i = contributionEvaluationService.lambdaUpdate()
                    .eq(ContributionEvaluation::getRepoName, contributionEvaluation.getRepoName())
                    .eq(ContributionEvaluation::getRepoOwner, contributionEvaluation.getRepoOwner())
                    .eq(ContributionEvaluation::getPrNumber, contributionEvaluation.getPrNumber())
                    .update(contributionEvaluation);
        }else{
            i = contributionEvaluationService.save(contributionEvaluation);
        }

        return ResultUtils.success(i);
    }

    /**
     * 查询PR分析
     *
     * @return PR分析列表
     */
    @GetMapping("/search")
    @Operation(description = "根据标题查询PR分析信息")  // 接口信息描述
    public ResponseVO<List<ContributionEvaluation>> listContributionEvaluations() {
        List<ContributionEvaluation> ContributionEvaluationList = contributionEvaluationService.list();
        return ResultUtils.success(ContributionEvaluationList);
    }

    /**
     * 根据ID删除PR分析
     *
     * @param id 需要删除的PR分析id
     * @return 被删除的PR分析id
     */
    @PostMapping("/delete")
    @Operation(description = "删除PR分析")  // 接口信息描述
    public ResponseVO<Boolean> deleteContributionEvaluation(@NotNull Long id) {
        boolean b = contributionEvaluationService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新/修改PR分析
     *
     * @param contributionEvaluation 要修改的原PR分析
     * @return 修改后的新PR分析
     */
    @PostMapping("/update")
    @Operation(description = "更新/修改PR分析")  // 接口信息描述
    public ResponseVO<ContributionEvaluation> updateContributionEvaluation(@RequestBody ContributionEvaluation contributionEvaluation) {
        contributionEvaluationService.lambdaUpdate().update(contributionEvaluation);
        return ResultUtils.success(contributionEvaluationService.getById(contributionEvaluation.getId()));
    }

}
