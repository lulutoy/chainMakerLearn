package chainmaker.sdk.demo;

import org.chainmaker.pb.common.ContractOuterClass;
import org.chainmaker.pb.common.Request;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.utils.FileUtils;
import org.chainmaker.sdk.utils.SdkUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class Contract {

    private static final String QUERY_CONTRACT_METHOD = "findByFileHash";
    private static final String INVOKE_CONTRACT_METHOD = "save";
    private static final String CONTRACT_NAME = "fact_docker_go_2";
    private static final String CONTRACT_FILE_PATH = "docker-fact.7z";

    public static void createContract(ChainClient chainClient, User user) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            byte[] byteCode = FileUtils.getResourceFileBytes(CONTRACT_FILE_PATH);

            // 1. create payload
            Request.Payload payload = chainClient.createContractCreatePayload(CONTRACT_NAME, "1", byteCode,
                    ContractOuterClass.RuntimeType.DOCKER_GO, null);
            //2. create payloads with endorsement
            Request.EndorsementEntry[] endorsementEntries = SdkUtils
                    .getEndorsers(payload, new User[]{user});

            // 3. send request
            responseInfo = chainClient.sendContractManageRequest(payload, endorsementEntries, 10000, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("创建合约结果：");
        System.out.println(responseInfo);
    }
    public static void invokeContract(ChainClient chainClient) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            Map<String, byte[]> params = new HashMap<>();
            params.put("method", "save".getBytes(StandardCharsets.UTF_8));
            params.put("file_name", "name007".getBytes(StandardCharsets.UTF_8));
            params.put("file_hash", "ab3456df5799b87c77e7f88".getBytes(StandardCharsets.UTF_8));
            params.put("time", "6543234".getBytes(StandardCharsets.UTF_8));

            responseInfo = chainClient.invokeContract(CONTRACT_NAME, INVOKE_CONTRACT_METHOD,
                    null, params,10000, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行合约结果：");
        System.out.println(responseInfo);
    }

    public static void queryContract(ChainClient chainClient) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            Map<String, byte[]> params = new HashMap<>();
            params.put("method", "findByFileHash".getBytes(StandardCharsets.UTF_8));
            params.put("file_hash", "ab3456df5799b87c77e7f88".getBytes(StandardCharsets.UTF_8));
            responseInfo = chainClient.queryContract(CONTRACT_NAME, QUERY_CONTRACT_METHOD,
                    null,  params,10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("查询合约结果：");
        System.out.println(responseInfo);
    }
}
