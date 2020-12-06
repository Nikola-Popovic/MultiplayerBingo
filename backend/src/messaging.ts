import firebase from './firebase-config';
import Carte from './models/carte';
import Joueur from './models/joueur';

export function sendCardToTokens(tokens : string[], lobbyId : number) {
  tokens.forEach(token => {
    firebase.messaging().sendToDevice(token, {
      data: formatMessage("carte", JSON.stringify(new Carte(lobbyId).toJSON()))
    }).then(response => {
      console.log(response);
    })
    .catch(error => {
      console.log(error);
    });;
  });
}

export async function subscribeTokenToLobbyTopic(token : string, lobbyId : number) {
  try {
    const response = await firebase.messaging().subscribeToTopic(token, getLobbyTopic(lobbyId));
    console.log('Successfully subscribed to topic:', response);
  }
  catch (error) {
    console.log('Error subscribing to topic:', error);
  }
}

export function unSubscribeTokenToLobbyTopic(token : string | string[], lobbyId : number) {
  firebase.messaging().unsubscribeFromTopic(token, getLobbyTopic(lobbyId))
  .then(response => {
    console.log(response);
  })
  .catch(error => {
    console.log(error);
  });
}

export async function sendAddedPlayerMessageToLobby(joueur : Joueur, lobbyId : number) {
  try {
    const response = await firebase.messaging().send({
      data: formatMessage("addedPlayer", JSON.stringify(joueur.toJSON())),
      topic: getLobbyTopic(lobbyId)
    });
    console.log(response);
  }
  catch (error) {
    console.log(error);
  }
}

export function sendRemovedPlayerMessageToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().send({
    data: formatMessage("removedPlayer", JSON.stringify(joueur.toJSON())),
    topic: getLobbyTopic(lobbyId)
  }).then(response => {
    console.log(response);
  })
  .catch(error => {
    console.log(error);
  });
}

export function sendNextBouleToLobby(nextBoule : string, lobbyId : number) {
  firebase.messaging().send({
    data: formatMessage("nextBoule", nextBoule),
    topic: getLobbyTopic(lobbyId)
  }).then(response => {
    console.log(response);
  })
  .catch(error => {
    console.log(error);
  });
}

export function sendWinnerToLobby(joueur : Joueur, lobbyId : number) {
  firebase.messaging().send({
    data: formatMessage("winner", JSON.stringify(joueur.toJSON())),
    topic: getLobbyTopic(lobbyId)
  }).then(response => {
    console.log(response);
  })
  .catch(error => {
    console.log(error);
  });
}

export function sendGameOverToLobby(lobbyId : number) { 
  firebase.messaging().send({
    data: formatMessage("gameOver", ""),
    topic: getLobbyTopic(lobbyId)
  }).then(response => {
    console.log(response);
  })
  .catch(error => {
    console.log(error);
  });
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
