package chainmaker.sdk.demo;

import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.stub.StreamObserver;
import org.bouncycastle.util.encoders.Hex;
import org.chainmaker.pb.common.ChainmakerBlock;
import org.chainmaker.pb.common.ChainmakerTransaction;
import org.chainmaker.pb.common.ResultOuterClass;

public class Subscribe extends InitClient implements Runnable {

    public void run() {
        testSubscribeBlock();
    }

    static public void testSubscribeBlock() {
        StreamObserver<ResultOuterClass.SubscribeResult> responseObserver = new StreamObserver<ResultOuterClass.SubscribeResult>() {
            @Override
            public void onNext(ResultOuterClass.SubscribeResult result) {
                try {
                    com.google.protobuf.ByteString d = result.getData();
                    ChainmakerBlock.BlockInfo blockInfo = ChainmakerBlock.BlockInfo.parseFrom(d);
                    System.out.println("订阅到： blockHeight: " + blockInfo.getBlock().getHeader().getBlockHeight() +
                            ", blockHash: " + Hex.toHexString(blockInfo.getBlock().getHeader().getBlockHash().toByteArray()) +
                            ", txCount: " + blockInfo.getBlock().getTxsCount() +
                            ", chainId: " + blockInfo.getBlock().getHeader().getChainId());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // just do nothing
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                // just do nothing
            }
        };

        StreamObserver<ResultOuterClass.SubscribeResult> responseObserverTx = new StreamObserver<ResultOuterClass.SubscribeResult>() {
            @Override
            public void onNext(ResultOuterClass.SubscribeResult result) {
                try {
                    ChainmakerTransaction.Transaction transactionInfo = ChainmakerTransaction.Transaction.parseFrom(result.getData());
                    System.out.print("订阅到： txId:" + transactionInfo.getPayload().getTxId());
                    System.out.print(", code:" + transactionInfo.getResult().getCode().getNumber());
                    if (transactionInfo.getResult().getCode().getNumber() == ResultOuterClass.TxStatusCode.SUCCESS.getNumber()) {
                        System.out.println(", result :" + transactionInfo.getResult().getContractResult().getResult());
                    } else {
                        System.out.print(", message:" + transactionInfo.getResult().getMessage());
                        System.out.println(", contract message:" + transactionInfo.getResult().getContractResult().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // can add log here
            }

            @Override
            public void onCompleted() {
                // can add log here
            }
        };

        try {
            chainClient.subscribeBlock(-1, 5, true, false, responseObserver);
            System.out.println("开始订阅区块");


            chainClient.subscribeTx(-1, 5, "", new String[]{}, responseObserverTx);
            System.out.println("开始订阅交易");
            Thread.sleep(1000 * 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
