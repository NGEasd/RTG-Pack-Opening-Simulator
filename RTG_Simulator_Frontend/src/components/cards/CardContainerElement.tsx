import { Role, SimpleCardProps } from "../../types/types";
import { Link } from 'react-router-dom';
import { getRarityImage } from "../../utils/cards";
import { AuthContext } from "../../global context/AuthContext";
import { useContext } from "react";

export type CardContainerProp = {
    card: SimpleCardProps,
    handler: (id:number) => void,
}

function CardContainerElement(prop:CardContainerProp) {

    const user = useContext(AuthContext);
    const rarityImage = getRarityImage(prop.card.rarity);
    const rarityClassName = `${prop.card.rarity.toLowerCase()}`;

    return (
             
        <div className={`cardcontainer-element ${rarityClassName}`}>
            <Link to={`/card/${prop.card.playerID}`} className='card-link'>
                <div className='card-section rarity'>
                    <img className='rarity-image' src={rarityImage} alt='rarity' />
                    <p className='rarity-text'>{prop.card.rarity}</p>
                </div>

                <div className='card-section center'>
                    <h3 className='player-name'>{prop.card.name}</h3>
                    <p className='player-position'>{prop.card.position}</p>
                </div>

                <div className='card-section overall'>
                    <p className='player-overall'>{prop.card.overall}</p>
                </div>
            </Link>

            {(user?.user?.role === Role.ADMIN) && (
                <button className='update-card' onClick={() => prop.handler(prop.card.playerID)}>Update card</button>
            )}
        </div> 
    )
}

export default CardContainerElement;