package com.oasis.oasisaiagent.advisor;

import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

public class MyCustomAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {



    @Override
    public String getName() {
        return "oasis自定义的 Advisor";
    }

    @Override
    public int getOrder() {
        // 值越小优先级越高，越先执行
        return 100;
    }

    /**
     * 非流式处理
     * @param advisedRequest
     * @param chain
     * @return
     */
    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
//        // 1. 处理请求（前置处理）
//        AdvisedRequest modifiedRequest = processRequest(advisedRequest);
//
//        // 2. 调用链中的下一个Advisor
//        AdvisedResponse response = chain.nextAroundCall(modifiedRequest);
//
//        // 3. 处理响应（后置处理）
//        return processResponse(response);

        return null;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
//        // 1. 处理请求
//        AdvisedRequest modifiedRequest = processRequest(advisedRequest);
//
//        // 2. 调用链中的下一个Advisor并处理流式响应
//        return chain.nextAroundStream(modifiedRequest)
//                .map(response -> processResponse(response));

        return null;
    }
}
