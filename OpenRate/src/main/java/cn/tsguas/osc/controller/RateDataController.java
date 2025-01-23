package cn.tsguas.osc.controller;


import cn.tsguas.osc.config.ResultUtils;
import cn.tsguas.osc.model.domain.RateData;
import cn.tsguas.osc.model.dto.RateDataAddDTO;
import cn.tsguas.osc.model.vo.ResponseVO;
import cn.tsguas.osc.service.IRateDataService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/rate")
public class RateDataController {

    @Resource
    IRateDataService rateDataService;

    /**
     * 添加PR分析
     *
     * @return 新PR分析id
     */
    @PostMapping("/add")
    @Operation(description = "添加新PR分析")  // 接口信息描述
    public ResponseVO<Boolean> addRateData(@RequestBody @Validated RateDataAddDTO rateDataAddDTO) {
        RateData rateData = new RateData();
        rateData.setRepoName(rateDataAddDTO.getRepoName());
        rateData.setRepoOwner(rateDataAddDTO.getRepoOwner());
        rateData.setPrTitle(rateDataAddDTO.getPrTitle());
        rateData.setPrNumber(rateDataAddDTO.getPrNumber());
        if(rateDataAddDTO.getWorkload() != null){
            rateData.setWorkload(Math.round(rateDataAddDTO.getWorkload() * 100.0) / 100.0);
        }
        rateData.setLines(rateDataAddDTO.getLines());
        rateData.setMetrics(rateDataAddDTO.getMetrics());

        List<RateData> list = rateDataService.lambdaQuery()
                .eq(RateData::getRepoName, rateData.getRepoName())
                .eq(RateData::getRepoOwner, rateData.getRepoOwner())
                .eq(RateData::getPrNumber, rateData.getPrNumber())
                .list();
        boolean i = false;
        if (list.size() > 0) {
            i = rateDataService.lambdaUpdate()
                    .eq(RateData::getRepoName, rateData.getRepoName())
                    .eq(RateData::getRepoOwner, rateData.getRepoOwner())
                    .eq(RateData::getPrNumber, rateData.getPrNumber())
                    .update(rateData);
        }else{
            i = rateDataService.save(rateData);
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
    public ResponseVO<List<RateData>> listRateDatas() {
        List<RateData> rateDataList = rateDataService.list();
        return ResultUtils.success(rateDataList);
    }

    /**
     * 根据ID删除PR分析
     *
     * @param id 需要删除的PR分析id
     * @return 被删除的PR分析id
     */
    @PostMapping("/delete")
    @Operation(description = "删除PR分析")  // 接口信息描述
    public ResponseVO<Boolean> deleteRateData(@NotNull Long id) {
        boolean b = rateDataService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新/修改PR分析
     *
     * @param rateData 要修改的原PR分析
     * @return 修改后的新PR分析
     */
    @PostMapping("/update")
    @Operation(description = "更新/修改PR分析")  // 接口信息描述
    public ResponseVO<RateData> updateRateData(@RequestBody RateData rateData) {
        rateDataService.lambdaUpdate().update(rateData);
        return ResultUtils.success(rateDataService.getById(rateData.getId()));
    }

}
