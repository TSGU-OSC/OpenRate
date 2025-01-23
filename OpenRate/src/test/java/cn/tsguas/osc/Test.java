package cn.tsguas.osc;

import cn.tsguas.osc.service.IRateDataService;
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
    private IRateDataService rateDataService;

    @org.junit.jupiter.api.Test
    public void test(){
        rateDataService.list();
    }
}
