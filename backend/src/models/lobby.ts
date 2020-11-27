import Joueur from "./joueur";
import * as database from '../database'
import GeoLocation from "./geolocation";
import Util from "../util";

export default class Lobby{
    private _joueurs : Joueur[];
    private _host : Joueur;
    private _geolocation : GeoLocation;

    readonly id : number;
    readonly nom : string;
    readonly chiffrePiges : number[];
    readonly estCommencee : boolean;

    constructor(host : Joueur, name : string, geolocation: GeoLocation){
        this._host = host;
        this.nom = name;
        this._geolocation = geolocation;
        database.addLobby(this);
        this.id = database.lobbies.length;
        this.chiffrePiges = [];
        this._joueurs = [];
        this._joueurs.push(this._host);
        host.assignerALobby(this);
        this.estCommencee = false;
    }

    addToLobby(joueur : Joueur){
        this._joueurs.push(joueur);
    }

    removeFromLobby(joueur : Joueur){
        this._joueurs = this._joueurs.filter(j => j.id !== joueur.id);
    }

    get joueurs(){
        return this._joueurs;
    }

    get host() {
        return this._host;
    }

    selectNewRandomHost() {
        const ranHostIndex = Math.floor(Math.random() * this._joueurs.length);
        this._host = this._joueurs[ranHostIndex];
    }

    givenNumbersWereDrawn(numberArray: number[]) {
        for (let i = 0; i < numberArray.length; i ++){
            if (!this.chiffrePiges.includes(numberArray[i])) {
                return false;
            }
        }
        return true;
    }

    isInAcceptableDistance(location: GeoLocation): boolean {
        return this._geolocation.distanceToLocation(location) / 1000 < Util.MAX_LOBBY_DISTANCE
    }

    toJSON(){
        const participants : object[] = [];
        for(const joueur of this.joueurs){
            participants.push(joueur.toJSON());
        }
        return {
            "id" : this.id,
            "nom" : this.nom,
            "host" : this._host.toJSON(),
            "participants" : participants
        }
    }

    equals(lobby : Lobby){
        return this.id === lobby.id;
    }
}
