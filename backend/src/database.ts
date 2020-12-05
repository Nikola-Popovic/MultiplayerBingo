import Carte from "./models/carte";
import Joueur from "./models/joueur";
import Lobby from "./models/lobby";

export const cartes : Carte[] = [];
export let joueurs : Joueur[] = [];
export let lobbies : Lobby[] =[];


export function carteExiste(id : number){
    return cartes.filter(carte => carte.id === id).length > 0;
}

export function lobbyExiste(id : number){
    return lobbies.filter(lobby => lobby.id === id).length > 0;
}

export function addCarte(carte : Carte){
    cartes.push(carte);
}

export function addJoueur(joueur : Joueur){
    joueurs.push(joueur);
}

export function addLobby(lobby : Lobby){
    lobbies.push(lobby);
}

export function saveLobby(lobby : Lobby) {
    const lobbyIndex = lobbies.findIndex(l => l.id === lobby.id)
    if (lobbyIndex >= 0) {
        lobbies[lobbyIndex] = lobby;
    }
}

export function deleteLobby(lobby : Lobby) {
    const lobbyIndex = lobbies.findIndex(l => l.id === lobby.id)
    if (lobbyIndex >= 0) {
        lobbies = lobbies.filter(j => j.id !== lobby.id);
    }
}

export function getJoueurById(id : number){
    const joueur = joueurs.filter(j => j.id === id);
    return joueur !== [] ? joueur[0] : null;
}

export function getLobbyById(id : number){
    const lobby = lobbies.filter(l => l.id === id);
    return lobby !== [] ? lobby[0] : null;
}

export function getCarteById(id : number){
    const carte = cartes.filter(c => c.id === id);
    return carte !== [] ? carte[0] : null;
}

export function removeJoueur(id : number){
    joueurs = joueurs.filter(j => j.id !== id);
}
