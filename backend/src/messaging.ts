import firebase from './firebase-config';
import Carte from './models/carte';

export function sendCardToTokens(tokens : string[], lobbyId : number) {
  tokens.forEach(token => {
    firebase.messaging().sendToDevice(token, {
      data: {
        "type": "carte",
        "payload": JSON.stringify(new Carte(lobbyId).toJSON())
      }
    });
  });
}

export function subscribeTokensToLobbyTopic(tokens : string[], lobbyId : number) {
  const topic = `lobby${lobbyId}Topic`;
  firebase.messaging().subscribeToTopic(tokens, topic);
}
