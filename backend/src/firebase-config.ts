var admin = require("firebase-admin");

var serviceAccount = require("./firebase_service_account.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

module.exports.admin = admin;
