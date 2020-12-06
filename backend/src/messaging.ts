import firebase from './firebase-config';
import Carte from './models/carte';
import Joueur from './models/joueur';

export function sendCardToTokens(tokens : string[], lobbyId : number) {
  tokens.forEach(token => {
    firebase.messaging().sendToDevice(token, {
      data: formatMessage("carte", JSON.stringify(new Carte(lobbyId).toJSON()))
    });
  });
}

export function subscribeTokenToLobbyTopic(token : string, lobbyId : number) {
  firebase.messaging().subscribeToTopic(token, getLobbyTopic(lobbyId)).catch(err => console.log(err));
}

export function unSubscribeTokenToLobbyTopic(token : string | string[], lobbyId : number) {
  firebase.messaging().unsubscribeFromTopic(token, getLobbyTopic(lobbyId)).catch(err => console.log(err));
}

export function sendAddedPlayerMessageToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("addedPlayer", JSON.stringify(joueur.toJSON()))
  }).catch(err => console.log(err));
}

export function sendRemovedPlayerMessageToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("removedPlayer", JSON.stringify(joueur.toJSON()))
  }).catch(err => console.log(err));
}

export function sendNextBouleToLobby(nextBoule : string, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("nextBoule", nextBoule)
  }).catch(err => console.log(err));
}

export function sendWinnerToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("winner", JSON.stringify(joueur.toJSON()))
  }).catch(err => console.log(err));
}

export function sendGameOverToLobby(lobbyId : number) { 
  firebase.messaging().sendToTopic(getLobbyTopic(lobbyId), {
    data: formatMessage("gameOver", "")
  }).catch(err => console.log(err));
}

function getLobbyTopic(lobbyId : number) {
  return `lobby${lobbyId}Topic`;
}

function formatMessage(type : string, payload : string) : { "type" : string, "payload" : string} {
  return {
    "type": type,
    "payload": payload
  }
}
