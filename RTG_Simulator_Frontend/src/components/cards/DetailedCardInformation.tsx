import { DetailedCardProps } from "../../types/types";
import { getRarityImage } from "../../utils/cards";

function DetailedCardInformation(card:DetailedCardProps) {
    const rarityImage = getRarityImage(card.rarity);

    return (
        <>
            <h2>Player details</h2>
            <div className='detailed-card-informations'>

                <div className='detailed-card-statistics'>

                    <div className='card-details-header'>
                        <div className='card-section rarity'>
                            <img className="rarity-image" src={rarityImage} alt='rarity' /> 
                        </div>
                        <div className='card-section center'>
                            <h3 className='player-name'>{card.name}</h3>
                            <p className="player-position">{card.position}</p>
                        </div>
                        <div className='card-section overall'>
                            <p className="player-overall">{card.overall}</p>
                        </div>
                    </div>

                    <div className='card-stats-container'>

                        <div className='stat-item'>
                            <p>Shooting: {card.stats.shooting}</p>
                            <div className='progress-bar-container'>
                                <div className='progress-bar-fill' style={{ width: `${card.stats.shooting}%` }}></div>
                            </div>
                        </div>

                        <div className='stat-item'>
                            <p>Pace: {card.stats.pace}</p>
                            <div className='progress-bar-container'>
                                <div className='progress-bar-fill' style={{ width: `${card.stats.pace}%` }}></div>
                            </div>
                        </div>

                        <div className='stat-item'>
                            <p>Dribbling: {card.stats.dribbling}</p>
                            <div className='progress-bar-container'>
                                <div className='progress-bar-fill' style={{ width: `${card.stats.dribbling}%` }}></div>
                            </div>
                        </div>

                        <div className='stat-item'>
                            <p>Passing: {card.stats.passing}</p>
                            <div className='progress-bar-container'>
                                <div className='progress-bar-fill' style={{ width: `${card.stats.passing}%` }}></div>
                            </div>
                        </div>

                        <div className='stat-item'>
                            <p>Defending: {card.stats.defending}</p>
                            <div className='progress-bar-container'>
                                <div className='progress-bar-fill' style={{ width: `${card.stats.defending}%` }}></div>
                            </div>
                        </div>

                        <div className='stat-item'>
                            <p>GK abilities: {card.stats.keeperAbilities}</p>
                            <div className='progress-bar-container'>
                                <div className='progress-bar-fill' style={{ width: `${card.stats.keeperAbilities}%` }}></div>
                            </div>
                        </div>
                    </div>

                    <div className='card-details-pricetag'>
                        <img className="price-image" src='/coin.svg' alt='price image' />
                        <p className="price-text">PRICE: {card.price}</p>
                    </div>

                </div>
            </div>
        </>
    )
}

export default DetailedCardInformation;