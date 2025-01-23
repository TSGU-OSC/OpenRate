package cn.tsguas.osc;

import cn.tsguas.osc.service.IContributionEvaluationService;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: fsq
 * @Date: 2025/1/21 14:22
 * @Version: 1.0
 */
@SpringBootTest
public class Test {

    @Resource
    private IContributionEvaluationService contributionEvaluationService;

    @org.junit.jupiter.api.Test
    public void test(){
        contributionEvaluationService.list();
    }
}
