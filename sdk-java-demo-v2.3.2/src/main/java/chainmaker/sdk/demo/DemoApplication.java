package chainmaker.sdk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static chainmaker.sdk.demo.InitClient.inItChainClient;

@SpringBootApplication
public class DemoApplication  {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
        inItChainClient();
        //查询链配置
        ChainConfig.getChainConfig(InitClient.chainClient);
//        //创建合约
        Contract.createContract(InitClient.chainClient, InitClient.user);
//        //调用合约
        Contract.invokeContract(InitClient.chainClient);
//        //查询合约
        Contract.queryContract(InitClient.chainClient);
        //订阅区块
        new Thread(new Subscribe()).start();
        //等待订阅
        Thread.sleep(1000 * 10);

        //退出
        System.exit(0);
    }
}

