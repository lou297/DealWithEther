import { createInstance } from "./index.js";

const instance = createInstance();

function create(rating, success, fail) {
  instance
    .post("/api/ratings", JSON.stringify(rating))
    .then(success)
    .catch(fail);
}

export { create };
