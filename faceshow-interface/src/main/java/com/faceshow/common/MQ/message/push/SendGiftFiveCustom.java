package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.utils.MessagePushSummaryUtlis;
import com.faceshow.common.utils.PushBeanUtils;
import com.faceshow.common.utils.push.Jpush.JpushTemplet;
import com.faceshow.common.utils.push.Jpush.JpushUtils;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.user.dao.UserInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SendGiftFiveCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.sendGiftFive.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.sendGiftFive.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("送礼物MQ共接到消息:" + list.size()+"条");
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        Map<String,Object> map =JSON.parseObject(logJsonStr, Map.class);
                        SendGiftProduct.SendGiftProduct(map);
                        logger.info("Five送礼物消息已经处理一条");


                        //插入站内信
                        userInfoDao.insertMessage_push_info(JpushTemplet.GIFT_SENT_TYPE,Integer.parseInt(map.get("user_id").toString()),Integer.parseInt(map.get("account_id").toString()),new Date(), MessageFormat.format(JpushTemplet.SEND_GIFT,map.get("account"),map.get("gname")));//(@Param("type_id") int type_id,@Param("user_id") int user_id,@Param("account_id") int account_id,@Param("create_time") Date create_time);
                        Map<String, Object> accountId = messagePushSummaryUtlis.SelectMessage_push_user_set(Integer.parseInt(map.get("account_id").toString()));//查看通知开关是否是开启的
                        if (accountId.get("is_gift").toString().equalsIgnoreCase("1")){//如果通知是打开的 就发送通知
                            Map<String, String> JpushPrameter = new HashMap<>();//推送設置參數
                            JpushPrameter.put("type_id", JpushTemplet.GIFT_SENT_TYPE.toString());
                            JpushUtils.buildIOSParameter(MessageFormat.format(JpushTemplet.SEND_GIFT,map.get("account"),map.get("gname")),JpushPrameter,map.get("account_id").toString());//String alert, Map<String, String> map, String... alias
                            JpushUtils.buildAndroidParameter(MessageFormat.format(JpushTemplet.SEND_GIFT,map.get("account"),map.get("gname")), "",JpushPrameter,map.get("account_id").toString());//String alert, String title, Map<String, String> map, String... alias
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}
