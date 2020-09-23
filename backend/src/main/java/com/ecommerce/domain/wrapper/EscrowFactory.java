package com.ecommerce.domain.wrapper;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>
 * Auto generated code.
 * <p>
 * <strong>Do not modify!</strong>
 * <p>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j
 * command line tools</a>, or the
 * org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen
 * module</a> to update.
 *
 * <p>
 * Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class EscrowFactory extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516115553803806115558339818101604052604081101561003357600080fd5b50805160209091015160008054336001600160a01b03199182168117835560018054831690911790556002805482166001600160a01b0395861617905560038054909116939092169290921790556114c490819061009190396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c80638da5cb5b1161008c578063c8f84cd611610066578063c8f84cd61461023f578063f2fde38b1461025c578063f851a44014610282578063ffb5d2391461028a576100cf565b80638da5cb5b14610227578063b8f099421461022f578063c6438d0214610237576100cf565b8063012f52ee146100d45780633a25a9241461010d578063437012651461016f57806366676547146101a45780636c466c41146101d957806374910a8714610201575b600080fd5b6100f1600480360360208110156100ea57600080fd5b50356102f3565b604080516001600160a01b039092168252519081900360200190f35b6101386004803603604081101561012357600080fd5b5063ffffffff8135811691602001351661031a565b6040805163ffffffff95861681529390941660208401526001600160a01b0390911682840152606082015290519081900360800190f35b6101906004803603602081101561018557600080fd5b503561ffff166103fd565b604080519115158252519081900360200190f35b6101c7600480360360208110156101ba57600080fd5b503563ffffffff1661048e565b60408051918252519081900360200190f35b6101ff600480360360208110156101ef57600080fd5b50356001600160a01b03166106fb565b005b6101ff6004803603602081101561021757600080fd5b50356001600160a01b0316610734565b6100f161076d565b6100f161077c565b6100f161078b565b6100f16004803603602081101561025557600080fd5b503561079a565b6101ff6004803603602081101561027257600080fd5b50356001600160a01b03166107b5565b6100f161083a565b6102ad600480360360208110156102a057600080fd5b503563ffffffff16610849565b6040805163ffffffff97881681529590961660208601526001600160a01b039093168486015260608401919091526080830152151560a082015290519081900360c00190f35b6005818154811061030057fe5b6000918252602090912001546001600160a01b0316905081565b600080600080610328610896565b50506040805160c08101825263ffffffff9687168082529587166020808301918252338385019081524260608501908152600060808601818152600160a088018181529c83526004909552969020945185549451925163ffffffff19909516818d161767ffffffff0000000019166401000000009c84169c909c029b909b1768010000000000000000600160e01b031916600160401b6001600160a01b03861602178555519184018290559351600284015596516003909201805460ff19169215159290921790915595969095949350915050565b61ffff811660009081526004602052604081208054600160401b90046001600160a01b03163314610475576040805162461bcd60e51b815260206004820152601c60248201527f4f6e6c792073656c6c65722063616e2064656c657465206974656d2e00000000604482015290519081900360640190fd5b426002820155600301805460ff19169055506001919050565b6000610498610896565b5063ffffffff828116600090815260046020908152604091829020825160c08101845281548086168252640100000000810490951692810192909252600160401b9093046001600160a01b0316918101829052600183015460608201526002830154608082015260039092015460ff16151560a0830152331415610559576040805162461bcd60e51b8152602060048201526013602482015272436865636b206275796572206164647265737360681b604482015290519081900360640190fd5b60a081015115156001146105a9576040805162461bcd60e51b81526020600482015260126024820152714974656d206e6f7420617661696c61626c6560701b604482015290519081900360640190fd5b600254600354604080840151602085015191516000946001600160a01b039081169416928892913391906105dc906108cb565b6001600160a01b039687168152948616602086015263ffffffff938416604080870191909152928616606086015294166080840152921660a082015290519081900360c001906000f080158015610637573d6000803e3d6000fd5b5060058054600181019091557f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db0810180546001600160a01b038085166001600160a01b031992831681179093556000848152600660209081526040918290208054909416851790935587810151815163ffffffff8c16815293840186905283820194909452519495509293339392909216917faa2d605f5f6537bca132ac73803dc6247ef9bb84e742fe25e5e83d3dc5de28fa9181900360600190a3949350505050565b6000546001600160a01b0316331461071257600080fd5b600380546001600160a01b0319166001600160a01b0392909216919091179055565b6000546001600160a01b0316331461074b57600080fd5b600280546001600160a01b0319166001600160a01b0392909216919091179055565b6000546001600160a01b031681565b6003546001600160a01b031681565b6002546001600160a01b031681565b6006602052600090815260409020546001600160a01b031681565b6000546001600160a01b031633146107cc57600080fd5b6001600160a01b0381166107df57600080fd5b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001546001600160a01b031681565b600460205260009081526040902080546001820154600283015460039093015463ffffffff80841694640100000000850490911693600160401b90046001600160a01b0316929160ff1686565b6040805160c081018252600080825260208201819052918101829052606081018290526080810182905260a081019190915290565b610bb7806108d98339019056fe608060405234801561001057600080fd5b50604051610bb7380380610bb7833981810160405260c081101561003357600080fd5b5080516020808301516040808501516060860151608087015160a090970151600080546001600160a01b03199081166001600160a01b03808b169190911792839055600180548316828a1690811790915560028054841690911763ffffffff60a01b199081167401000000000000000000000000000000000000000063ffffffff808b16820292909217909355600380548616858a1617905560048054909516848f16179091169086169091021782554260055586517f70a0823100000000000000000000000000000000000000000000000000000000815230928101929092529551989996989497939695929491909216926370a082319260248082019391829003018186803b15801561014757600080fd5b505afa15801561015b573d6000803e3d6000fd5b505050506040513d602081101561017157600080fd5b5051600655600254604080517f517807e400000000000000000000000000000000000000000000000000000000815230600482015290516001600160a01b039092169163517807e49160248082019260009290919082900301818387803b1580156101db57600080fd5b505af11580156101ef573d6000803e3d6000fd5b505050505050505050506109af806102086000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c806380e41ac11161008c578063b8f0994211610066578063b8f099421461019c578063c19d93fb146101a4578063ca6158cb146101d0578063ea8a1af0146101d8576100ea565b806380e41ac114610169578063a035b1fe14610171578063b46300ec14610192576100ea565b806357c4b262116100c857806357c4b262146101495780636177a9f8146101515780637022b58e146101595780637150d8ae14610161576100ea565b806308551a53146100ef5780634c4cea5a1461011357806355be04ad1461012d575b600080fd5b6100f76101e0565b604080516001600160a01b039092168252519081900360200190f35b61011b6101ef565b60408051918252519081900360200190f35b6101356101f5565b604080519115158252519081900360200190f35b61011b610368565b6100f761036e565b61013561037d565b6100f76105c8565b6100f76105d7565b6101796105e6565b6040805163ffffffff9092168252519081900360200190f35b61019a6105f9565b005b6100f76106ad565b6101ac6106bc565b604051808260048111156101bc57fe5b60ff16815260200191505060405180910390f35b6101796106cc565b61019a6106df565b6003546001600160a01b031681565b60075481565b60008060048054600160c01b900460ff169081111561021057fe5b1461021a57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561026657600080fd5b505afa15801561027a573d6000803e3d6000fd5b505050506040513d602081101561029057600080fd5b505160045490915063ffffffff600160a01b9091048116601401168110156102e95760405162461bcd60e51b815260040180806020018281038252602c81526020018061094f602c913960400191505060405180910390fd5b6004805460ff60c01b1916600160c01b17815560025460408051636452fca160e01b81523093810193909352516001600160a01b0390911691636452fca191602480830192600092919082900301818387803b15801561034857600080fd5b505af115801561035c573d6000803e3d6000fd5b50505050600191505090565b60055481565b6002546001600160a01b031681565b6004546000906001600160a01b0316331461039757600080fd5b600260048054600160c01b900460ff16908111156103b157fe5b146103bb57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561040757600080fd5b505afa15801561041b573d6000803e3d6000fd5b505050506040513d602081101561043157600080fd5b505160008054600354600480546040805163a9059cbb60e01b81526001600160a01b0394851693810193909352600160a01b90910463ffffffff1660248301525194955091169263a9059cbb92604480840193602093929083900390910190829087803b1580156104a157600080fd5b505af11580156104b5573d6000803e3d6000fd5b505050506040513d60208110156104cb57600080fd5b505060008054600480546040805163a9059cbb60e01b81526001600160a01b0380841694820194909452600160a01b90920463ffffffff168603602483015251919092169263a9059cbb92604480820193602093909283900390910190829087803b15801561053957600080fd5b505af115801561054d573d6000803e3d6000fd5b505050506040513d602081101561056357600080fd5b50506004805460ff60c01b1916600360c01b1781554260075560025460408051630956cfb760e11b81523093810193909352516001600160a01b03909116916312ad9f6e91602480830192600092919082900301818387803b15801561034857600080fd5b6004546001600160a01b031681565b6000546001600160a01b031681565b600454600160a01b900463ffffffff1681565b6003546001600160a01b0316331461061057600080fd5b600160048054600160c01b900460ff169081111561062a57fe5b1461063457600080fd5b6004805460ff60c01b1916600160c11b17815560025460408051638ff06ab760e01b81523093810193909352516001600160a01b0390911691638ff06ab791602480830192600092919082900301818387803b15801561069357600080fd5b505af11580156106a7573d6000803e3d6000fd5b50505050565b6001546001600160a01b031681565b600454600160c01b900460ff1681565b600254600160a01b900463ffffffff1681565b6003546001600160a01b031633148061070257506004546001600160a01b031633145b61070b57600080fd5b600060048054600160c01b900460ff169081111561072557fe5b14806107485750600160048054600160c01b900460ff169081111561074657fe5b145b61075157600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561079d57600080fd5b505afa1580156107b1573d6000803e3d6000fd5b505050506040513d60208110156107c757600080fd5b505160045490915063ffffffff600160a01b90910481166014011681106108d45760008054600480546040805163a9059cbb60e01b81526001600160a01b0392831693810193909352602483018690525192169263a9059cbb926044808401936020939083900390910190829087803b15801561084357600080fd5b505af1158015610857573d6000803e3d6000fd5b505050506040513d602081101561086d57600080fd5b505060025460408051637d44a00d60e11b815230600482015290516001600160a01b039092169163fa89401a9160248082019260009290919082900301818387803b1580156108bb57600080fd5b505af11580156108cf573d6000803e3d6000fd5b505050505b6004805460ff60c01b1916600160c21b178155600254604080516320ad1b7d60e21b81523093810193909352516001600160a01b03909116916382b46df491602480830192600092919082900301818387803b15801561093357600080fd5b505af1158015610947573d6000803e3d6000fd5b505050505056fe47726561746572207468616e206f7220657161756c7320746f2032302043617368206e656365737361727921a265627a7a72315820eae21722e68fb7ad5a592a8597918f1a72c9722f277cd290c932be412269d55764736f6c634300050c0032a265627a7a72315820e8b4a94780c61a7c331f4b55b0a66ff482ef6ebf4a45c0307c24c793d716bae464736f6c634300050c0032";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_CASHCONTRACTADDRESS = "cashContractAddress";

    public static final String FUNC_DEREGISTERITEM = "deregisterItem";

    public static final String FUNC_ESCROWS = "escrows";

    public static final String FUNC_ITEMS = "items";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PURCHASEIDTOESCROW = "purchaseIdToEscrow";

    public static final String FUNC_PURCHASEITEM = "purchaseItem";

    public static final String FUNC_PURCHASERECORDCONTRACTADDRESS = "purchaseRecordContractAddress";

    public static final String FUNC_REGISTERITEM = "registerItem";

    public static final String FUNC_SETPAYMENTMETHOD = "setPaymentMethod";

    public static final String FUNC_SETPURCHASERECORDCONTRACT = "setPurchaseRecordContract";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event NEWESCROW_EVENT = new Event("NewEscrow",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Address>() {
            }));;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));;

    @Deprecated
    protected EscrowFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EscrowFactory(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EscrowFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EscrowFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<NewEscrowEventResponse> getNewEscrowEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWESCROW_EVENT,
                transactionReceipt);
        ArrayList<NewEscrowEventResponse> responses = new ArrayList<NewEscrowEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewEscrowEventResponse typedResponse = new NewEscrowEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.purchaseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewEscrowEventResponse> newEscrowEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewEscrowEventResponse>() {
            @Override
            public NewEscrowEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWESCROW_EVENT, log);
                NewEscrowEventResponse typedResponse = new NewEscrowEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.purchaseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewEscrowEventResponse> newEscrowEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWESCROW_EVENT));
        return newEscrowEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT,
                transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(
                valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT,
                        log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> admin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMIN,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> cashContractAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CASHCONTRACTADDRESS,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deregisterItem(BigInteger itemId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEREGISTERITEM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(itemId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> escrows(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ESCROWS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>> items(
            BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ITEMS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {
                }, new TypeReference<Uint32>() {
                }, new TypeReference<Address>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Bool>() {
                }));
        return new RemoteFunctionCall<Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), (BigInteger) results.get(1).getValue(),
                                (String) results.get(2).getValue(), (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue(), (Boolean) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> purchaseIdToEscrow(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PURCHASEIDTOESCROW,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> purchaseItem(BigInteger itemId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PURCHASEITEM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(itemId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> purchaseRecordContractAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PURCHASERECORDCONTRACTADDRESS, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerItem(BigInteger itemId, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REGISTERITEM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(itemId),
                        new org.web3j.abi.datatypes.generated.Uint32(price)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPaymentMethod(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SETPAYMENTMETHOD,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPurchaseRecordContract(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPURCHASERECORDCONTRACT, Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EscrowFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return new EscrowFactory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EscrowFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new EscrowFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EscrowFactory load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new EscrowFactory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EscrowFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new EscrowFactory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _cashContractAddress, String _purchaseRecordAddress) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress),
                        new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, credentials, contractGasProvider, BINARY,
                encodedConstructor);
    }

    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _cashContractAddress, String _purchaseRecordAddress) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress),
                        new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, transactionManager, contractGasProvider, BINARY,
                encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit, String _cashContractAddress, String _purchaseRecordAddress) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress),
                        new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, credentials, gasPrice, gasLimit, BINARY,
                encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String _cashContractAddress, String _purchaseRecordAddress) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress),
                        new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY,
                encodedConstructor);
    }

    public static class NewEscrowEventResponse extends BaseEventResponse {
        public String seller;

        public String buyer;

        public BigInteger itemId;

        public BigInteger purchaseId;

        public String _address;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
