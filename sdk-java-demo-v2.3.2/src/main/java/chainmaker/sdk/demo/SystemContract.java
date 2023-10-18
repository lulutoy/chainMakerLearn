package chainmaker.sdk.demo;

import org.chainmaker.pb.common.ChainmakerBlock;
import org.chainmaker.sdk.ChainClient;

public class SystemContract {

    public static void getBlockByHeight(ChainClient chainClient) {
        ChainmakerBlock.BlockInfo blockInfo = null;
        try {
            blockInfo = chainClient.getBlockByHeight(3, false, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(blockInfo);
    }
}
