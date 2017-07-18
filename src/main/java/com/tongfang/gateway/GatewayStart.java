package com.tongfang.gateway;

import com.tongfang.gateway.callback.service.GatewayCMD;
import com.tongfang.gateway.callback.service.GatewayHeartBeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * gateway 鍏ュ彛鍚姩绫�
 *
 * @author lyl
 */
public class GatewayStart {
    private static Logger LOGGER = LoggerFactory.getLogger(GatewayStart.class);

    public static void main(String[] args) throws Exception {
        LOGGER.debug("gateway engine starting.....");
        GatewayCMD.subCmdTopic();
        LOGGER.debug("gateway engine started");

        GatewayHeartBeat.startLocalHeart();
        GatewayHeartBeat.subHeartBeat();
        LOGGER.debug("heart beat stated");
    }
}
