import { ReactNode } from "react"

// ***** ENUMS: **** // 

// Cards
export enum Position {
    GOALKEEPER = "GOALKEEPER",
    CENTERBACK = "DEFENDER",
    MIDFIELDER = "MIDFIELDER",
    STRIKER = "STRIKER"     
}

export enum Rarity {
    BASE = "BASE",
    BRONZE = "BRONZE",
    SILVER = "SILVER",
    GOLD = "GOLD"
}

// User
export enum Role {
    ADMIN = "ADMIN",
    DEFAULT = "DEFAULT"
}

// **** TYPES **** //

export type Stat = {
    pace: number,
    shooting: number,
    dribbling: number,
    passing: number,
    defending: number,
    keeperAbilities: number
}

export type User = {
    userID: number,
    username: string,
    role: Role,
    balance: number
}

export type VerifiedUser = {
    jwtToken: string,
    userRespDTO: User
}

// **** PROPS **** //

// Cards:
export type SimpleCardProps = {
    playerID : number,
    name : string,
    position : Position,
    rarity: Rarity,
    overall : number,
    price: number,
}

export type DetailedCardProps = {
    playerID : number,
    name : string,
    position : Position,
    rarity: Rarity,
    stats: Stat,
    overall : number,
    price: number
}

export type InventoryCardElementProps= {
  card: SimpleCardProps;
  onSell: (playerID: number, price: number) => void;
}

// url
export type UrlProp = {
    url:string;
}

// Packs
export type PackProps = {
    packID: number,
    name: string,
    description: string,
    price: number
}

// User & Authentication
export type AuthProviderProps ={
  children: ReactNode;
}


// **** DTO-s **** //

// Cards
export type CardRequestDTO = {
    name: string,
    position: Position,
    rarity: Rarity,
    stats: Stat,
    overall: number,
    price: number,
}

export type UpdatedCardDTO = {
    playerID: number,
    name: string,
    position: Position,
    rarity: Rarity,
    overall: number,
    price: number,
}

// User transfer
export type UserAuthDTO = {
    username: string;
    password: string;
}


// ERRORS
export type ErrorVerificator = {
    value: boolean,
    message: string,
}

export type LoginVerificator = {
    value: boolean,
    message: string,
    userRespDTO: VerifiedUser | null,
}