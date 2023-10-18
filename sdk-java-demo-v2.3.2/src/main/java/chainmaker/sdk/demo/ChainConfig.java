package chainmaker.sdk.demo;

import org.chainmaker.pb.config.ChainConfigOuterClass;
import org.chainmaker.sdk.ChainClient;

public class ChainConfig {

    public static void getChainConfig(ChainClient chainClient) {
        ChainConfigOuterClass.ChainConfig chainConfig = null;
        try {
            chainConfig = chainClient.getChainConfig(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(chainConfig.toString());
    }

//    public static void updateChainConfig(ChainClient chainClient, User adminUser1, User adminUser2, User adminUser3) {
//        ResultOuterClass.TxResponse responseInfo = null;
//        try {
//            Request.Payload payload = chainClient.createPayloadOfChainConfigBlockUpdate(false,
//                    9002, 200, 225, 2000, 10000);
//            Request.EndorsementEntry[] endorsementEntries = SdkUtils.getEndorsers(payload,
//                    new User[]{adminUser1, adminUser2, adminUser3});
//            responseInfo = chainClient.updateChainConfig(payload, endorsementEntries, 10000, 10000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(responseInfo);
//    }
}
