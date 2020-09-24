import { createInstance } from "./index.js";

const instance = createInstance();

function findAll(page, success, fail) {
    instance
        .get("/api/items/page/" + page)
        .then(success)
        .catch(fail);
}

function findByCategory(keyword, page, success, fail) {
    instance
        .get("/api/items/category/page/" + keyword + "/" + page)
        .then(success)
        .catch(fail);
}

function findByMainCategory(keyword, success, fail) {
    instance
        .get("/api/items/category/" + keyword)
        .then(success)
        .catch(fail);
}

function findItemsByOwner(userId, success, fail) {
    instance
        .get("/api/items/seller/" + userId)
        .then(success)
        .catch(fail);
}

function findByName(name, page, success, fail) {
    instance
        .get("/api/items/name/" + name + "/" + page)
        .then(success)
        .catch(fail);
}

function findById(itemId, success, fail) {
    instance
        .get("/api/items/" + itemId)
        .then(success)
        .catch(fail);
}

function findHistoryById(itemId, success, fail) {
    instance
        .get("/api/items/history/" + itemId)
        .then(function(response) {
            success(response);
        })
        .catch(fail);
}

function findBySeller(id, success, fail) {
    instance
        .get("/api/items/seller/" + id)
        .then(success)
        .catch(fail);
}
// 구매 완료한 상품들 가져오기
function findByBuyer(id, success, fail) {
    instance
        .get("/api/items/buyer/" + id)
        .then(success)
        .catch(fail);
}
// 입찰 중인 상품들 가져오기
function findByBidder(id, success, fail) {
    instance
        .get("/api/items/bidder/" + id)
        .then(success)
        .catch(fail);
}

function create(body, success, fail, final) {
    instance
        .post("/api/items", JSON.stringify(body))
        .then(success)
        .catch(fail)
        .finally(final);
}

function save(data, id, success, fail, final) {
  instance
    .post("/api/items/images/" + id , data, {headers: { "Content-Type": "multipart/form-data"}})
    .then(success)
    .catch(fail)
    .finally(final);
}

function update(body, success, fail) {
    instance
        .put("/api/items", JSON.stringify(body))
        .then(success)
        .catch(fail);
}

function remove(id, success, fail) {
    instance
        .delete("/api/items/" + id)
        .then(success)
        .catch(fail);
}

// 구매자가 배송중인 상품을 구매 확정
function confirm(itemId, buyer, success, fail) {
    instance
        .put("/api/items/" + itemId + "/by/" + buyer)
        .then(success)
        .catch(fail);
}

function findMySaleItems(userId, success, fail) {
    instance
        .get("api/items/of/" + userId)
        .then(res => success(res))
        .catch(err => fail(err));
}

export {
    findAll,
    findByCategory,
    findItemsByOwner,
    findById,
    findHistoryById,
    findBySeller,
    findByBuyer,
    findByBidder,
    create,
    save,
    update,
    remove,
    confirm,
    findMySaleItems,
    findByName,
    findByMainCategory
};
