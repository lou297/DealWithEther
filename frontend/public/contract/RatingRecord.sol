pragma solidity ^0.5.12;
import "./Ownable.sol";

interface RatingRecordInterface {
    function addRating(address getter, address purchaseContractAddress, uint score) external;
}

contract RatingRecord is RatingRecordInterface, Ownable {

    struct Rating {
        address evaluator;
        uint score;
    }

//특정 판매자 purchase의 평가
    mapping (address => mapping(address => Rating)) ratingRecords;
    mapping (address => uint) ratingCounts;

    function addRating(address getter, address purchaseContractAddress, uint score) external {
        ratingRecords[getter][purchaseContractAddress] = Rating(msg.sender, score);

        uint ratingCount = ratingCounts[getter];
        ratingCounts[getter] = uint(ratingCount + 1);
    }
}