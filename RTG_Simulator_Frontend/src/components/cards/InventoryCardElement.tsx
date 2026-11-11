import { InventoryCardElementProps} from "../../types/types";
import PlayerCard from "./PlayerCard";
import {Link} from 'react-router-dom';

function InventoryCardElement({ card, onSell }: InventoryCardElementProps) {

  return (

    <div className='inventory-element'>
      <div className='inventory-card'>
        <PlayerCard key={card.playerID} {...card}></PlayerCard>
      </div>
      <div className='inventory-cardname'>
        <p>{card.name}</p>
      </div>
      <div className='price'>
        <img src='coin.svg' alt='coin' />
        <p>{card.price}</p>
      </div>
      <div className='inventory-card-nav'>
        <Link to={`/card/${card.playerID}`}>
          <button>View details</button>
        </Link>
        <button onClick={() => onSell(card.playerID, card.price)}>Sell card</button>
      </div>
    </div>
  );
}

export default InventoryCardElement;