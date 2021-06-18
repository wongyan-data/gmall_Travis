package com.travis.logger.gmall_travis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //jiang chuang jian dui xiang jiao gei spring
@Slf4j
public class LoggerController {


    @Autowired //zhu ru
    KafkaTemplate kafkaTemplate;
//    http://localhost:8080/applog

//    ti gong yi ge fang fa ,chu li mo ni qi sheng cheng de shu ju
//    @RequestMapping("/applog")   ba applog qingqiu jiao gei fang fa  jin xing chu li
//    @requestBody     biao shi cong qing qiu ti zhong  huo qu shu ju
@RequestMapping("/applog")
    public String applog(@RequestBody String mockLog){
    System.out.println (mockLog);
//    luo pan
    log.info(mockLog);
//    gen ju ri zi lei xing ,fa song dao bu tong de zbu ti zhong qu
    JSONObject jsonObject = JSON.parseObject (mockLog);
    JSONObject startJson = jsonObject.getJSONObject ("start");
    if(startJson != null ){
//        qi dong ri zhi
        kafkaTemplate.send ("gmall_start_bak",mockLog);
    }else{
//        shi jian ri zhi
        kafkaTemplate.send ("gmall_event_bak",mockLog);
    }
    return "success";
}

}
