package com.tongfang.gateway;

import com.tongfang.gateway.util.Config;
import com.tongfang.gateway.util.SleepHelper;
import com.tongfang.gateway.vo.DeviceSlice;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Kafka 客户端 9092
 */
public class KafkaClient {
    private static Config CONFIG = Config.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaClient.class);

    public volatile int clientStatus = -1;
    private static KafkaClient INSTANCE;

    Producer<String, String> mRespWriter = null;

    public static KafkaClient getInstance() {
        if (INSTANCE == null) INSTANCE = new KafkaClient();
        return INSTANCE;
    }

    public static Properties getPropsConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", CONFIG.getKfkaUrl());
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try (SleepHelper helper = new SleepHelper(Const.LISTEN_INTERVAL)) {
                    LOGGER.debug("wrter status is {} 0:no found brokers 1:lost 2:connected ", clientStatus);
                    if (clientStatus == Const.CONNECTION_CONNECTED)
                        continue;

                    asyncStop();
                    mRespWriter = new KafkaProducer<>(getPropsConfig());
                    clientStatus = Const.CONNECTION_CONNECTED;
                    LOGGER.debug("kafka is ready");
                } catch (Exception e) {
                    LOGGER.warn("Exception ", e);
                    clientStatus = Const.NOT_FOUND_BROKERS;
                }
            }
        }).start();

    }

    public void send(String topic, DeviceSlice ds, int retry) {
        try {
            long sleepTime = 10000 * (3 - retry);
            LOGGER.trace("retry {} sleep {}", retry, sleepTime);
            if (sleepTime > 0)
                Thread.currentThread().sleep(sleepTime);
   
            if (null != mRespWriter) {
                mRespWriter.send(new ProducerRecord(topic, ds.extractSid(),
                        Const.GSON.toJson(ds)), (recordMetadata, e) -> {
                    if (e != null) {
                        LOGGER.warn("async send to kafka,but callback exception SID:"+ds.extractContent(), e);
                        e.printStackTrace();
                    }
                });

            }
        } catch (Exception e) {
            LOGGER.warn("send failed retry {} , maybe connection lose SID:"+ds.extractContent(), retry, e);
            retry--;
            if (retry < 1 || clientStatus == Const.CONNECTION_LOSE) {
                clientStatus = Const.CONNECTION_LOSE;
                throw new RuntimeException("kafka cluster lost");
            } else
                send(topic, ds, retry);
        }
    }

    void asyncStop() {
        if (mRespWriter == null) return;

        final Producer<String, String> tempWriter = mRespWriter;
        mRespWriter = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                tempWriter.close();
                LOGGER.info("stopped");
            }
        });
    }

}
