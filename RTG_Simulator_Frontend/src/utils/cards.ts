import { Rarity } from "../types/types";

export function getRarityImage(rarity:Rarity) {
    switch (rarity) {
        case Rarity.BASE: return "/commonRarity.svg";
        case Rarity.BRONZE: return "/bronzeRarity.svg";
        case Rarity.SILVER: return "/silverRarity.svg";
        case Rarity.GOLD: return "/goldRarity.svg";
    }
}