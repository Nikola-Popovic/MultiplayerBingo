import Joueur from "./joueur";
import * as database from '../database'
import GeoLocation from "./geolocation";
import Util from "../util";
import shuffle from 'lodash.shuffle';

export default class Lobby{
    private _joueurs : Joueur[];
    private _host : Joueur;
    private _geolocation : GeoLocation;

    readonly id : number;
    readonly nom : string;
    private _chiffrePiges : number[];
    private _estCommencee : boolean;
    private _nextChiffres : number[];
    static nextId : number = 0;

    constructor(host : Joueur, name : string, geolocation: GeoLocation){
        this._host = host;
        this.nom = name;
        this._geolocation = geolocation;
        database.addLobby(this);
        this.id = Lobby.nextId++;
        this._chiffrePiges = [];
        this._nextChiffres = this.generateRandomNextChiffres();
        this._joueurs = [];
        this._joueurs.push(this._host);
        host.assignerALobby(this);
        this._estCommencee = false;
    }

    private generateRandomNextChiffres() : number[] {
        // Generate number array from 1 to 75
        const numbers = Array.from({length: 75}, (_, i) => i + 1);

        // Shuffle the numbers to randomize which numbers to send to the players
        return shuffle(numbers);
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

    get estCommencee() {
        return this._estCommencee;
    }

    startGame() {
        this._estCommencee = true;
    }

    selectNewRandomHost() {
        const ranHostIndex = Math.floor(Math.random() * this._joueurs.length);
        this._host = this._joueurs[ranHostIndex];
    }

    givenNumbersWereDrawn(numberArray: number[]) {
        for (let i = 0; i < numberArray.length; i ++){
            if (!this._chiffrePiges.includes(numberArray[i])) {
                return false;
            }
        }
        return true;
    }

    isInAcceptableDistance(location: GeoLocation): boolean {
        return this._geolocation.distanceToLocation(location) / 1000 < Util.MAX_LOBBY_DISTANCE
    }

    getNextNumber() : number {
        if (this._nextChiffres.length !== 0) {   
            const nextNumber = this._nextChiffres.pop();
            this._chiffrePiges.push(nextNumber);
            return nextNumber;
        } else {
            return -1; // no more numbers
        }
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
