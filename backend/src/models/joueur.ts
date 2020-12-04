import * as database from '../database'
import Lobby from "./lobby";

export default class Joueur{
    readonly id : number;
    private _lobby : Lobby;
    private _token : string;
    readonly username : string;
    static nextId : number = 0;

    constructor(username : string, token : string) {
        database.addJoueur(this);
        this.id = Joueur.nextId++;
        this._lobby = null;
        this.username = username;
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

    get token() {
        return this._token;
    }

    toJSON(){
        return {
            "id" : this.id,
            "username" : this.username
        }
    }
}
