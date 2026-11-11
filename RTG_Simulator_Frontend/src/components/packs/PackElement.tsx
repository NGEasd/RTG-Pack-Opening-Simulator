import { Link } from "react-router-dom";

import { PackProps } from "../../types/types";
import { useContext, useState } from "react";
import { AuthContext } from "../../global context/AuthContext";

function PackElement(pack: PackProps) {

  const user = useContext(AuthContext)?.user;
  const [error, setError] = useState<string>('');

  function checkBalance(e:React.MouseEvent) {
    if (user?.balance === undefined || pack.price > user?.balance) {
      setError('Insuficient balance!');
      e.preventDefault();
    }
  }

  return (
    <>
      <div className="pack-element">
        <img src="/pack.svg" alt="pack-image" />
        <h3>{pack.name}</h3>
        <p className='pack-desc'>{pack.description}</p>
        <div className="pack-price-container">
          <img src="/coin.svg" alt="coin-image" />
          <p className="pack-price">{pack.price}</p>
        </div>
        <Link to={`/packs/${pack.packID}`}>
          <button className="game-button" onClick={checkBalance}>OPEN!</button>
        </Link>
        {error !== null && (
        <div className='error-box'>
          {error}
        </div>        
      )}
      </div>
    </>
  )
}

export default PackElement;