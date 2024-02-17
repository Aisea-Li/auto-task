package com.example.task.client;


import com.example.task.entity.response.Response;
import com.example.task.entity.response.SunShines;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(
        name = "mexcWebClient",
        url = "${mexc.web.url:https://www.mexc.co}",
        configuration = {MexcWebClientFeignConfig.class},
        fallbackFactory = MexcWebClientFallbackFactory.class
)
public interface MexcWebClient {

    /**
     * token校验续期
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/ucenter/api/login/validation")
    Response<?> refreshToken();

    /**
     * 查询阳光普照
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/api/operateactivity/sun_shines/list")
    Response<List<SunShines>> querySunShinesList();


    /**
     * 查询阳光普照
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/api/operateactivity/sun_shines/apply_lock/un_lock_mode/batch")
    Response<List<String>> applySunShinesBatch(@RequestParam("poolId") String poolId);

}

@Slf4j
@Component
class MexcWebClientFallbackFactory implements FallbackFactory<MexcWebClient> {

    @Override
    public MexcWebClient create(Throwable cause) {
        log.warn("MexcWebClient,request fail,msg:" + cause.getMessage(), cause);
        return null;
    }
}