import { SimpleCardProps } from "../../types/types";
import { getRarityImage } from "../../utils/cards";

function PlayerCard(card:SimpleCardProps) {
  const rarityImage = getRarityImage(card.rarity);
  const rarityClassName = `rarity-${card.rarity.toLowerCase()}`

  return (
    <div className={`player-card ${rarityClassName}`}>
      <img className="card-rarity-image" src={rarityImage} alt={card.rarity} />
      <div className="card-rarity-text">{card.rarity}</div>
      <div className="player-name">{card.name}</div>
      <div className="ovr">OVR: {card.overall}</div>
      <div className="position">{card.position}</div>
    </div>
  )
}

export default PlayerCard;