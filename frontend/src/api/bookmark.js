import { createInstance } from "./index.js";
const instance = createInstance();

function bookMarkSave(body, success, fail) {
    instance
        .post("/api/bookmarks", JSON.stringify(body))
        .then(success)
        .catch(fail);
}

function loadBookMark(userId, success, fail) {
    instance
        .get("/api/bookmarks/" + userId)
        .then(success)
        .catch(fail);
}

export {
    bookMarkSave,
    loadBookMark
};