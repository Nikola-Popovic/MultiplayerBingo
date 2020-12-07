import * as database from '../database'
import Lobby from "./lobby";
import Carte from './carte';

export default class Joueur{
    readonly id : number;
    private _lobby : Lobby;
    private _token : string;
    private _username : string;
    private _carte : Carte;
    static nextId : number = 0;

    constructor(username : string, token : string) {
        database.addJoueur(this);
        this.id = Joueur.nextId++;
        this._lobby = null;
        this._username = username;
        this._token = token;
    }

    assignerALobby(lobby : Lobby){
        this._lobby = lobby;
    }

    quitterPartie(){
        this._lobby = null;
    }

    get lobby(){
        return this._lobby;
    }

    resetLobby() {
        this._lobby = null;
    }

    get token() {
        return this._token;
    }

    updateToken(newToken : string) {
        this._token = newToken;
    }

    get username() {
        return this._username;
    }

    updateUsername(newUsername : string) {
        this._username = newUsername;
    }

    get carte() {
        return this._carte;
    }

    setCarte(carte : Carte) {
        this._carte = carte;
    }

    toJSON(){
        return {
            "id" : this.id,
            "username" : this.username
        }
    }
}
