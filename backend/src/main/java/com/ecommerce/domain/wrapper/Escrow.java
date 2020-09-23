package com.ecommerce.domain.wrapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class Escrow extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610bb7380380610bb7833981810160405260c081101561003357600080fd5b5080516020808301516040808501516060860151608087015160a090970151600080546001600160a01b03199081166001600160a01b03808b169190911792839055600180548316828a1690811790915560028054841690911763ffffffff60a01b199081167401000000000000000000000000000000000000000063ffffffff808b16820292909217909355600380548616858a1617905560048054909516848f16179091169086169091021782554260055586517f70a0823100000000000000000000000000000000000000000000000000000000815230928101929092529551989996989497939695929491909216926370a082319260248082019391829003018186803b15801561014757600080fd5b505afa15801561015b573d6000803e3d6000fd5b505050506040513d602081101561017157600080fd5b5051600655600254604080517f517807e400000000000000000000000000000000000000000000000000000000815230600482015290516001600160a01b039092169163517807e49160248082019260009290919082900301818387803b1580156101db57600080fd5b505af11580156101ef573d6000803e3d6000fd5b505050505050505050506109af806102086000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c806380e41ac11161008c578063b8f0994211610066578063b8f099421461019c578063c19d93fb146101a4578063ca6158cb146101d0578063ea8a1af0146101d8576100ea565b806380e41ac114610169578063a035b1fe14610171578063b46300ec14610192576100ea565b806357c4b262116100c857806357c4b262146101495780636177a9f8146101515780637022b58e146101595780637150d8ae14610161576100ea565b806308551a53146100ef5780634c4cea5a1461011357806355be04ad1461012d575b600080fd5b6100f76101e0565b604080516001600160a01b039092168252519081900360200190f35b61011b6101ef565b60408051918252519081900360200190f35b6101356101f5565b604080519115158252519081900360200190f35b61011b610368565b6100f761036e565b61013561037d565b6100f76105c8565b6100f76105d7565b6101796105e6565b6040805163ffffffff9092168252519081900360200190f35b61019a6105f9565b005b6100f76106ad565b6101ac6106bc565b604051808260048111156101bc57fe5b60ff16815260200191505060405180910390f35b6101796106cc565b61019a6106df565b6003546001600160a01b031681565b60075481565b60008060048054600160c01b900460ff169081111561021057fe5b1461021a57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561026657600080fd5b505afa15801561027a573d6000803e3d6000fd5b505050506040513d602081101561029057600080fd5b505160045490915063ffffffff600160a01b9091048116601401168110156102e95760405162461bcd60e51b815260040180806020018281038252602c81526020018061094f602c913960400191505060405180910390fd5b6004805460ff60c01b1916600160c01b17815560025460408051636452fca160e01b81523093810193909352516001600160a01b0390911691636452fca191602480830192600092919082900301818387803b15801561034857600080fd5b505af115801561035c573d6000803e3d6000fd5b50505050600191505090565b60055481565b6002546001600160a01b031681565b6004546000906001600160a01b0316331461039757600080fd5b600260048054600160c01b900460ff16908111156103b157fe5b146103bb57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561040757600080fd5b505afa15801561041b573d6000803e3d6000fd5b505050506040513d602081101561043157600080fd5b505160008054600354600480546040805163a9059cbb60e01b81526001600160a01b0394851693810193909352600160a01b90910463ffffffff1660248301525194955091169263a9059cbb92604480840193602093929083900390910190829087803b1580156104a157600080fd5b505af11580156104b5573d6000803e3d6000fd5b505050506040513d60208110156104cb57600080fd5b505060008054600480546040805163a9059cbb60e01b81526001600160a01b0380841694820194909452600160a01b90920463ffffffff168603602483015251919092169263a9059cbb92604480820193602093909283900390910190829087803b15801561053957600080fd5b505af115801561054d573d6000803e3d6000fd5b505050506040513d602081101561056357600080fd5b50506004805460ff60c01b1916600360c01b1781554260075560025460408051630956cfb760e11b81523093810193909352516001600160a01b03909116916312ad9f6e91602480830192600092919082900301818387803b15801561034857600080fd5b6004546001600160a01b031681565b6000546001600160a01b031681565b600454600160a01b900463ffffffff1681565b6003546001600160a01b0316331461061057600080fd5b600160048054600160c01b900460ff169081111561062a57fe5b1461063457600080fd5b6004805460ff60c01b1916600160c11b17815560025460408051638ff06ab760e01b81523093810193909352516001600160a01b0390911691638ff06ab791602480830192600092919082900301818387803b15801561069357600080fd5b505af11580156106a7573d6000803e3d6000fd5b50505050565b6001546001600160a01b031681565b600454600160c01b900460ff1681565b600254600160a01b900463ffffffff1681565b6003546001600160a01b031633148061070257506004546001600160a01b031633145b61070b57600080fd5b600060048054600160c01b900460ff169081111561072557fe5b14806107485750600160048054600160c01b900460ff169081111561074657fe5b145b61075157600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561079d57600080fd5b505afa1580156107b1573d6000803e3d6000fd5b505050506040513d60208110156107c757600080fd5b505160045490915063ffffffff600160a01b90910481166014011681106108d45760008054600480546040805163a9059cbb60e01b81526001600160a01b0392831693810193909352602483018690525192169263a9059cbb926044808401936020939083900390910190829087803b15801561084357600080fd5b505af1158015610857573d6000803e3d6000fd5b505050506040513d602081101561086d57600080fd5b505060025460408051637d44a00d60e11b815230600482015290516001600160a01b039092169163fa89401a9160248082019260009290919082900301818387803b1580156108bb57600080fd5b505af11580156108cf573d6000803e3d6000fd5b505050505b6004805460ff60c01b1916600160c21b178155600254604080516320ad1b7d60e21b81523093810193909352516001600160a01b03909116916382b46df491602480830192600092919082900301818387803b15801561093357600080fd5b505af1158015610947573d6000803e3d6000fd5b505050505056fe47726561746572207468616e206f7220657161756c7320746f2032302043617368206e656365737361727921a265627a7a72315820eae21722e68fb7ad5a592a8597918f1a72c9722f277cd290c932be412269d55764736f6c634300050c0032";

    public static final String FUNC_BUYER = "buyer";

    public static final String FUNC_CANCEL = "cancel";

    public static final String FUNC_CASHCONTRACT = "cashContract";

    public static final String FUNC_CHECKDEPOSIT = "checkDeposit";

    public static final String FUNC_COMPLETEAT = "completeAt";

    public static final String FUNC_CONFIRM = "confirm";

    public static final String FUNC_ITEMID = "itemId";

    public static final String FUNC_PRICE = "price";

    public static final String FUNC_PURCHASERECORDCONTRACT = "purchaseRecordContract";

    public static final String FUNC_PURCHASERECORDCONTRACTADDRESS = "purchaseRecordContractAddress";

    public static final String FUNC_PURCHASEDAT = "purchasedAt";

    public static final String FUNC_SELLER = "seller";

    public static final String FUNC_SEND = "send";

    public static final String FUNC_STATE = "state";

    @Deprecated
    protected Escrow(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Escrow(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Escrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Escrow(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> buyer() {
        final Function function = new Function(FUNC_BUYER, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> cancel() {
        final Function function = new Function(FUNC_CANCEL, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> cashContract() {
        final Function function = new Function(FUNC_CASHCONTRACT, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> checkDeposit() {
        final Function function = new Function(FUNC_CHECKDEPOSIT, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> completeAt() {
        final Function function = new Function(FUNC_COMPLETEAT, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> confirm() {
        final Function function = new Function(FUNC_CONFIRM, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> itemId() {
        final Function function = new Function(FUNC_ITEMID, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> price() {
        final Function function = new Function(FUNC_PRICE, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> purchaseRecordContract() {
        final Function function = new Function(FUNC_PURCHASERECORDCONTRACT, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> purchaseRecordContractAddress() {
        final Function function = new Function(FUNC_PURCHASERECORDCONTRACTADDRESS, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> purchasedAt() {
        final Function function = new Function(FUNC_PURCHASEDAT, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> seller() {
        final Function function = new Function(FUNC_SELLER, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> send() {
        final Function function = new Function(FUNC_SEND, Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> state() {
        final Function function = new Function(FUNC_STATE, Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Escrow load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return new Escrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Escrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Escrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Escrow load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Escrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Escrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new Escrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Escrow> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _contractAddress, String _purchaseRecordAddress,
            BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(
                new org.web3j.abi.datatypes.Address(160, _contractAddress),
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress),
                new org.web3j.abi.datatypes.generated.Uint32(_itemId),
                new org.web3j.abi.datatypes.Address(160, _seller), new org.web3j.abi.datatypes.Address(160, _buyer),
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Escrow> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _contractAddress, String _purchaseRecordAddress,
            BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(
                new org.web3j.abi.datatypes.Address(160, _contractAddress),
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress),
                new org.web3j.abi.datatypes.generated.Uint32(_itemId),
                new org.web3j.abi.datatypes.Address(160, _seller), new org.web3j.abi.datatypes.Address(160, _buyer),
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, transactionManager, contractGasProvider, BINARY,
                encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Escrow> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit, String _contractAddress, String _purchaseRecordAddress, BigInteger _itemId,
            String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(
                new org.web3j.abi.datatypes.Address(160, _contractAddress),
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress),
                new org.web3j.abi.datatypes.generated.Uint32(_itemId),
                new org.web3j.abi.datatypes.Address(160, _seller), new org.web3j.abi.datatypes.Address(160, _buyer),
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Escrow> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit, String _contractAddress, String _purchaseRecordAddress, BigInteger _itemId,
            String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(
                new org.web3j.abi.datatypes.Address(160, _contractAddress),
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress),
                new org.web3j.abi.datatypes.generated.Uint32(_itemId),
                new org.web3j.abi.datatypes.Address(160, _seller), new org.web3j.abi.datatypes.Address(160, _buyer),
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, transactionManager, gasPrice, gasLimit, BINARY,
                encodedConstructor);
    }
}
