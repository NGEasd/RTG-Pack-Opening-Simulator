import { useContext, useEffect, useState } from "react";
import { SimpleCardProps, UrlProp } from "../../types/types";
import { Link } from "react-router-dom";
import PlayerCard from "../cards/PlayerCard";
import { openingPack } from "../../services/packsapi";
import { AuthContext } from "../../global context/AuthContext";
import { getLoggedUser } from "../../services/userapi";

function OpeningScene({ url }: UrlProp) {

  const numberPackID = Number(url);
  const contex = useContext(AuthContext);

  const [opened, setOpened] = useState<boolean>(false);
  const [openedCard, setCard] = useState<SimpleCardProps | null>(null);

  useEffect(() => {
    const getOpenedPlayer = async() => {
      const token = localStorage.getItem('JWT');
      if (token !== null) {
        // get card
        const card = await openingPack(numberPackID, token);
        setCard(card);

        // get new user data (balance refresh at the server!)
        const newUserData = await getLoggedUser(token);
        console.log(newUserData?.balance);
        if (newUserData) {
          contex?.login(newUserData);
          console.log("User adat (foleg egyenleg) frissulnie kellett!");
        }
      } else {
        console.log('NO JWT TOKEN!! (not possible)');
      }
    }

    if (opened) {
      getOpenedPlayer();
    }
      
  }, [opened])

  const openPack = () => {
    setOpened(true);
  }

  return (
    <div className="open-pack-container">
      {!opened && (
        <div className="pack-placeholder">
          <img className="opening-image" src="/pack.svg" alt="opening image" />
          <button onClick={openPack}>OPEN PACK!</button>
        </div>
      )}
      {openedCard && (
        <div className="opened-card-container">
          <PlayerCard key={openedCard?.playerID} {...openedCard} />
          <div className="opened-nav">
            <Link to="/packs">
              <button className="game-button">CONTINUE</button>
            </Link>
            <Link to={`/card/${openedCard.playerID}`}>
              <button className="game-button">SEE PLAYER STATS</button>
            </Link>
          </div>
        </div>
      )}
    </div>
  )
}

export default OpeningScene;